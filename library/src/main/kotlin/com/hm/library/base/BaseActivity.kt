package com.hm.library.base

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks
import com.github.ksoichiro.android.observablescrollview.ScrollState
import com.github.ksoichiro.android.observablescrollview.Scrollable
import com.hm.library.R
import com.hm.library.resource.BaseAppCompatActivity
import com.hm.library.resource.LoadingDialog
import com.hm.library.resource.view.CustomToast
import com.hm.library.resource.view.TipsToast
import com.hm.library.util.ViewBindUtil
import com.jude.swipbackhelper.SwipeBackHelper

object ActivityData {
    val URL = "URL"
    val TITLE = "TITLE"
    val ID = "ID"
}

/**
 * Created by himi on 16/3/1.
 */
abstract class BaseActivity : BaseAppCompatActivity() {

    open var layoutResID: Int = -1
    open var needBind: Boolean = false
    open var hideActionBar: Boolean = false
    open var swipeBack: Boolean = true
    open var menuRes: Int = -1

    protected var toolbar: Toolbar? = null

    protected val uiHandler: Handler = Handler()
    var initCompleteRunnable: () -> Unit = {}

    private var mProgressDialog: LoadingDialog? = null
    private var tipsToast: TipsToast? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        if (hideActionBar)
            supportActionBar?.hide()

        super.onCreate(savedInstanceState)
        if (layoutResID != -1)
            setContentView(layoutResID)

        if (needBind)
            ViewBindUtil.bindViews(this, window.decorView)

        toolbar = findViewById(R.id.toolbar) as Toolbar?
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (swipeBack) {
            SwipeBackHelper.onCreate(this)
            SwipeBackHelper.getCurrentPage(this).setScrimColor(0xff00bb9c.toInt())
        }
//        SwipeBackHelper.getCurrentPage(this)//获取当前页面
//                .setSwipeBackEnable(true)//设置是否可滑动
//                .setSwipeEdge(200)//可滑动的范围。px。200表示为左边200px的屏幕
//                .setSwipeEdgePercent(0.2f)//可滑动的范围。百分比。0.2表示为左边20%的屏幕
//                .setSwipeSensitivity(0.5f)//对横向滑动手势的敏感程度。0为迟钝 1为敏感
//                .setScrimColor(Color.BLUE)//底层阴影颜色
//                .setClosePercent(0.8f)//触发关闭Activity百分比
//                .setSwipeRelateEnable(false)//是否与下一级activity联动(微信效果)。默认关
//                .setSwipeRelateOffset(500)//activity联动时的偏移量。默认500px。
//                .setDisallowInterceptTouchEvent(true)//不抢占事件，默认关（事件将先由子View处理再由滑动关闭处理）
//                .addListener(object : SwipeListener() {//滑动监听
//
//                    fun onScroll(percent: Float, px: Int) {//滑动的百分比与距离
//                    }
//
//                    fun onEdgeTouch() {//当开始滑动
//                    }
//
//                    fun onScrollToClose() {//当滑动关闭
//                    }
//                })

        if (checkParams()) {
            loadData()
        }
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        if (swipeBack)
            SwipeBackHelper.onPostCreate(this)
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun finish() {
        super.finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (swipeBack)
            SwipeBackHelper.onDestroy(this)
    }

    override fun setTitle(title: CharSequence?) {
        super.setTitle(title)
        toolbar?.title = title
    }

    fun hideActionBarByView(view: Scrollable) {
        view.setScrollViewCallbacks(object : ObservableScrollViewCallbacks {
            override fun onScrollChanged(scrollY: Int, firstScroll: Boolean, dragging: Boolean) {
            }

            override fun onDownMotionEvent() {
            }

            override fun onUpOrCancelMotionEvent(scrollState: ScrollState) {
                if (supportActionBar == null)
                    return

                if (scrollState == ScrollState.UP) {
                    if (supportActionBar!!.isShowing) {
                        supportActionBar?.hide()
                    }
                } else if (scrollState == ScrollState.DOWN) {
                    if (!supportActionBar!!.isShowing) {
                        supportActionBar?.show()
                    }
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if (menuRes == -1)
            return super.onCreateOptionsMenu(menu)
        else {
            menuInflater.inflate(menuRes, menu)
            return true
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
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
        initComplete()
    }

    /**
    初始化界面完成
     */
    open fun initComplete() {
        if (initCompleteRunnable != null)
            initCompleteRunnable.invoke()
    }

    open fun showLoading(msg: CharSequence? = "加载中") {
        try {
            if (mProgressDialog == null) {
                mProgressDialog = LoadingDialog(this)
            }
            if (!mProgressDialog!!.isShowing) {
                mProgressDialog?.setMessage(msg)
                mProgressDialog?.setCanceledOnTouchOutside(true)
                mProgressDialog?.setCancelable(true)
                mProgressDialog?.show()
            }
            mProgressDialog?.setOnCancelListener { }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    open fun cancelLoading() {
        mProgressDialog?.cancel()
        mProgressDialog = null
    }

    fun showToast(content: String?) {
        if (content == null || content == "")
            return
        CustomToast.makeText(this, content, 0).show()
    }

    fun showTips(tipType: TipsToast.TipType, msgResId: Int) {
        showTips(tipType.value(), msgResId)
    }

    fun showTips(tipType: TipsToast.TipType, msg: String) {
        showTips(tipType.value(), msg)
    }

    fun showTips(iconResId: Int, msgResId: Int) {
        if (tipsToast != null) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                tipsToast?.cancel()
            }
        } else {
            tipsToast = TipsToast.makeText(application.baseContext, msgResId, TipsToast.LENGTH_SHORT)
        }
        tipsToast?.show()
        tipsToast?.setIcon(iconResId)
        tipsToast?.setText(msgResId)
    }

    fun showTips(iconResId: Int, msg: String) {
        if (tipsToast != null) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                tipsToast?.cancel()
            }
        } else {
            tipsToast = TipsToast.makeText(application.baseContext, msg, TipsToast.LENGTH_SHORT)
        }
        tipsToast?.show()
        tipsToast?.setIcon(iconResId)
        tipsToast?.setText(msg)
    }
}