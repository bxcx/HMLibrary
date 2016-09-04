package com.hm.library.base

import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hm.library.resource.view.TipsToast

/**
 * BaseFragment
 *
 * himi on 2016-03-17 22:19
 * version V1.0
 */
abstract class BaseFragment : Fragment() {

    abstract var layoutResID: Int
    var rootView: View? = null// 缓存Fragment view
    var isInit: Boolean = true

    protected val uiHandler: Handler = Handler()

    var initCompleteRunnable: () -> Unit = {}

    /**
    检查调用本类必需传递的参数条件是否满足
    默认返回true，在需要的类中重写此方法即可

    - returns: true为满足
     */
    open fun checkParams(): Boolean {
        return true
    }

    /**
    加载数据，请求接口或者读取本地
    子类可不重写，默认调用初始化界面方法
     */
    open fun loadData() {
        initUI()
    }

    /**
    初始化界面，在这里可以分为几个方法函数来调用
     */
    open fun initUI() {
    }

    /**
    初始化界面完成
     */
    open fun initComplete() {
        if (initCompleteRunnable != null)
            initCompleteRunnable.invoke()
    }

    fun runDelayed(runnable: () -> Unit, delayMillis: Long) {
        uiHandler.postDelayed(runnable, delayMillis)
    }

    fun runOnUIThread(runnable: () -> Unit, delayMillis: Long = 0) {
        if (delayMillis == 0.toLong())
            uiHandler.post(runnable)
        else
            uiHandler.postDelayed(runnable, delayMillis)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        if (rootView == null) {
            rootView = inflater!!.inflate(layoutResID, null)
            isInit = true

        }
        // 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
        if (rootView!!.parent != null) {
            val parent = rootView!!.parent as ViewGroup
            parent?.removeView(rootView)
        }
        return rootView
    }


    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (isInit) {
            isInit = false
            if (checkParams()) {
                loadData()
            }
            initComplete()
        }
    }

    fun getBaseActivity(): BaseActivity {
        return activity as BaseActivity
    }

    fun showLoading(msg: CharSequence? = "加载中") {
        getBaseActivity().showLoading(msg)
    }

    fun showToast(content: String?) {
        getBaseActivity().showToast(content)
    }

    fun showTips(tipType: TipsToast.TipType, msg: String) {
        getBaseActivity().showTips(tipType, msg)
    }

    fun cancelLoading() {
        getBaseActivity().cancelLoading()
    }

}