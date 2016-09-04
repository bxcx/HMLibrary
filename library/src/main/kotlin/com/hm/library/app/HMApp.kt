package com.hm.library.app

import android.app.Application
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration
import com.nostra13.universalimageloader.core.assist.QueueProcessingType
import com.orhanobut.logger.Logger
import com.zhy.http.okhttp.OkHttpUtils
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Created by himi on 16/2/25.
 */
open class HMApp : Application() {


    //USER USER
    override fun onCreate() {
        super.onCreate()
        Cacher.init(this)


        val config = ImageLoaderConfiguration.Builder(this).threadPriority(Thread.NORM_PRIORITY - 2).denyCacheImageMultipleSizesInMemory().discCacheSize(50 * 1024 * 1024)
                //.diskCache(UnlimitedDiskCache(File(PathUtil.PHOTOCACHEPIC))).discCacheFileNameGenerator(Md5FileNameGenerator())
                .writeDebugLogs().tasksProcessingOrder(QueueProcessingType.LIFO).build()
        ImageLoader.getInstance().init(config)
        Logger.init(packageName)

        OkHttpUtils.getInstance().setConnectTimeout(2000, TimeUnit.SECONDS)
    }

    companion object {

        fun createParams(): HashMap<String, Any> {
            var params: HashMap<String, Any> = HashMap<String, Any>()
//            if USER != nil {
//                params.updateValue(USER?.sessionId!, forKey: "sessionId")
//                params.updateValue(USER?.personalId!, forKey: "personalId")
//            }
            return params
        }

        fun createHeader(): HashMap<String, String> {
            var header: HashMap<String, String> = HashMap<String, String>()
            header.put("apikey", "be910c69ec688ba099d0091e19c21033")
            return header
        }
    }
}
