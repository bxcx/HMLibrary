package com.hm.library.http


import android.app.Activity
import com.google.gson.Gson
import com.hm.library.app.Cacher
import com.hm.library.base.BaseActivity
import com.orhanobut.logger.Logger
import com.zhy.http.okhttp.OkHttpUtils
import com.zhy.http.okhttp.callback.Callback
import com.zhy.http.okhttp.callback.FileCallBack
import okhttp3.Call
import okhttp3.Response
import org.jetbrains.anko.toast
import java.io.File
import java.net.ConnectException
import java.net.UnknownHostException
import java.util.*

/**
 * 数据模型的基类，所有网络实体对象都要继承此类
 */
abstract class HMModel() {
    abstract var hm_valid: Boolean
    abstract var hm_message: String
}

interface OnHMResponse<T> {
    fun onResponse(obj: T?)
}

interface HMExceptionInfo {
    fun parseError(e: Exception): String
    fun onError(message: String)
}

/**
 * 网络请求类
 */
class HMRequest {

    companion object : HMExceptionInfo {


        open var params: HashMap<String, Any> = HashMap()
        open var header: HashMap<String, String> = HashMap()
        open var method: Method = Method.POST
        open var server: String = ""

        open var parse: HMExceptionInfo = this

        override fun parseError(e: Exception): String {
            var domain = when (e) {
                is ConnectException,
                is UnknownHostException
                -> "网络不佳"
                else -> "未知错误"
            }

            return domain
        }

        override fun onError(message: String) {
        }

        //临时解决方案 供java调用
        fun <T : Any> go(clazz: Class<T>, url: String = HMRequest.server, params: HashMap<String, Any> = HMRequest.params, method: Method = HMRequest.method,
                         header: HashMap<String, String> = HMRequest.header, activity: Activity? = null,
                         cache: Boolean = false, needCallBack: Boolean = false,
                         rsp: OnHMResponse<T>?) {


            val builder = when (method) {
                Method.GET -> OkHttpUtils.get()
                Method.POST -> OkHttpUtils.post()
                else -> null
            } ?: return

            params.keys.forEach { key ->
                builder.addParams(key, "${params[key]}")
            }
            header.keys.forEach { key ->
                builder.addHeader(key, header[key])
            }

            //拼装带参数的URL地址，在控制台输出并根据它设置缓存
            val fullUrl = getFullURL(url, params)
            Logger.d("${Date()}\n$method\n$fullUrl")

            //如果调用的地方需要读取缓存
            if (cache) {
                //查询缓存，如果不为null则说明之前缓存成功
                val obj: T? = Cacher[fullUrl]
                if (obj != null) {
                    Logger.w("$fullUrl\n-------LoadCache------")
                    //直接返回这个缓存对象
                    rsp?.onResponse(obj)
                    return
                }
            }

            val call = builder.url(url).build()
            call.execute(object : Callback<T>() {

                @Throws(Exception::class)
                override fun parseNetworkResponse(response: Response): T? {

                    val str: String = response.body().string()
                    Logger.d("${Date()}\n$method\n$fullUrl")
                    Logger.json(str)

                    val obj: T? = Gson().fromJson(str, clazz)
                    return obj
                }

                override fun onError(call: Call, e: Exception) {

                    try {
                        val obj: T? = Gson().fromJson(e.message, clazz)
                        if (obj != null) {
                            onResponse(obj)
                            println("onError : ${e.message}")
                            return
                        }
                    } catch(e: Exception) {
                    }

                    println(e.message)
                    activity?.toast(parse.parseError(e))
                    if (activity != null && activity is BaseActivity) {
                        activity.cancelLoading()
                    }

                    //失败时，如果调用者要求必须回调，则返回一个null，否则不会触发
                    //多用于上拉加载请求失败时page需要不变
                    if (needCallBack) {
                        rsp?.onResponse(null)
                    }
                }

                override fun onResponse(response: T) {

                    //检查返回数据是否正常
                    if (!checkResult(response, activity)) {
                        if (needCallBack) {
                            rsp?.onResponse(null)
                        }
                        return
                    }

                    //如果需要缓存
                    if (cache) {
                        //以完整的url地址缓存此对象结果
                        Cacher[fullUrl] = response
                    }
                    rsp?.onResponse(response)
                }
            })
        }

        //url 接口地址
        //params 参数 选传 默认为空
        //method 请求方式 选传 默认为GET
        //header 请求头 选传 默认为空
        //activity 请求所在的Activity, 用于请求失败时给出Toast错误提示 选传 默认为空
        //cache 是否缓存到本地 选传 默认为默认为false
        //needCallBack 请求失败时是否执行回调 选传 默认为false
        inline fun <reified T : HMModel> go(url: String = HMRequest.server, params: HashMap<String, Any> = HMRequest.params, method: Method = HMRequest.method,
                                            header: HashMap<String, String> = HMRequest.header, activity: Activity? = null,
                                            cache: Boolean = false, needCallBack: Boolean = false,
                                            crossinline completionHandler: (T?) -> Unit) {

            val builder = when (method) {
                Method.GET -> OkHttpUtils.get()
                Method.POST -> OkHttpUtils.post()
                else -> null
            } ?: return

            params.keys.forEach { key ->
                builder.addParams(key, "${params[key]}")
            }
            header.keys.forEach { key ->
                builder.addHeader(key, header[key])
            }

            //拼装带参数的URL地址，在控制台输出并根据它设置缓存
            val fullUrl = getFullURL(url, params)
            Logger.w("${Date()}\n$method\n$fullUrl")

            //如果调用的地方需要读取缓存
            if (cache) {
                //查询缓存，如果不为null则说明之前缓存成功
                val obj: T? = Cacher[fullUrl]
                if (obj != null) {
                    Logger.w("$fullUrl\n-------LoadCache------")
                    //直接返回这个缓存对象
                    completionHandler(obj)
                    return
                }
            }

            val call = builder.url(url).build()
            call.execute(object : Callback<T>() {

                @Throws(Exception::class)
                override fun parseNetworkResponse(response: Response): T? {

                    val str: String = response.body().string()
                    Logger.d("${Date()}\n$method\n$fullUrl")
                    Logger.json(str)

                    val obj: T? = Gson().fromJson(str, T::class.java)
                    return obj
                }

                override fun onError(call: Call, e: Exception) {
                    try {
                        val obj: T? = Gson().fromJson(e.message, T::class.java)
                        if (obj != null) {
                            onResponse(obj)
                            Logger.e("onError : ${e.message}")
                            return
                        }
                    } catch(e: Exception) {
                    }

                    Logger.e("${Date()}\n$method\n$fullUrl\n$e\n${e.message}")
                    activity?.toast(parse.parseError(e))
                    if (activity != null && activity is BaseActivity) {
                        activity.cancelLoading()
                    }

                    //失败时，如果调用者要求必须回调，则返回一个null，否则不会触发
                    //多用于上拉加载请求失败时page需要不变
                    if (needCallBack) {
                        completionHandler(null)
                    }
                }

                override fun onResponse(response: T) {

                    //检查返回数据是否正常
                    if (!checkResult(response, activity)) {
                        if (needCallBack) {
                            completionHandler(null)
                        }
                        return
                    }

                    //如果需要缓存
                    if (cache) {
                        //以完整的url地址缓存此对象结果
                        Cacher[fullUrl] = response
                    }
                    completionHandler(response)
                }
            })
        }

        fun download(url: String, path: String, fileName: String, deleteIfExist: Boolean = false, showToastonResponse: Boolean = true, activity: Activity? = null, needCallBack: Boolean = false, completionHandler: (Float?, File?) -> Unit) {
            if (!File(path).exists())
                File(path).mkdirs()
            if (deleteIfExist) {
                if (File(path + "/" + fileName).exists())
                    File(path + "/" + fileName).delete()
            }

            try {
                OkHttpUtils.get().url(url).build()
                        .execute(object : FileCallBack(path, fileName) //
                        {
                            override fun onResponse(response: File?) {
                                //                            Logger.i("下载成功,保存在" + response?.absolutePath)
                                if (activity != null && activity is BaseActivity) {
                                    activity.cancelLoading()
                                }
                                if (showToastonResponse)
                                    activity?.toast("下载成功,保存在" + response?.absolutePath)
                                completionHandler.invoke(1f, response)
                            }

                            override fun onError(call: Call?, e: Exception?) {
                                if (activity != null && activity is BaseActivity) {
                                    activity.cancelLoading()
                                }
                                activity?.toast(e?.message.toString())
                                if (activity != null && activity is BaseActivity) {
                                    activity.cancelLoading()
                                }
                                if (needCallBack) {
                                    completionHandler.invoke(-1f, null)
                                }
                            }

                            override fun inProgress(progress: Float) {
                                completionHandler.invoke(progress, null)
                            }

                        })
            } catch(e: Exception) {
                if (activity != null && activity is BaseActivity) {
                    activity.cancelLoading()
                }
                activity?.toast("下载失败")
                if (needCallBack) {
                    completionHandler.invoke(-1f, null)
                }
            }
        }

        fun getFullURL(url: String, params: Map<String, Any>): String {
            var fullUrl = url
            //组装url
            if (params.isNotEmpty()) {
                var str: String = "?"
                for (param in params) {
                    str += "${param.key}=${param.value}&"
                }
                str = str.substring(0, str.length - 1)
                fullUrl = url + str
            }

            return fullUrl
        }

        fun <T> checkResult(response: T, activity: Activity?): Boolean {
            var check: Boolean = false
            if (response is HMModel) {
                check = response.hm_valid
                if (!check) {
                    parse.onError(response.hm_message)
                    activity?.toast(response.hm_message)
                    if (activity != null && activity is BaseActivity) {
                        activity.cancelLoading()
                    }
                }
            }
            return check
        }

    }

}