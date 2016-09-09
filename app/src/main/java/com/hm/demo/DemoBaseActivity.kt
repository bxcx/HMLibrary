package com.hm.demo

import com.hm.library.base.BaseActivity
import com.hm.library.resource.view.TipsToast
import kotlinx.android.synthetic.main.activity_demo_base_activity.*
import org.jetbrains.anko.onClick

//如果只需要设置UIParams中的少量参数,可以直接在这里override,用逗号隔开
class DemoBaseActivity(override var layoutResID: Int = R.layout.activity_demo_base_activity) : BaseActivity() {

    override fun setUIParams() {
        layoutResID = R.layout.activity_demo_base_activity    //activity布局id
        menuRes = R.menu.menu_save              //toolBar的MenuID
        needBind = false                        //是否需要bindView,默认为false,java类需要设置为true,kotlin类不需要

        swipeBack = true                        //是否开启手势右滑返回,默认为true
        hideActionBar = false                    //是否隐藏toolBar,默认为false
        displayHome = false                     //是否显示toolBar返回箭头,默认为true
    }

    override fun initUI() {
        super.initUI()

        tv_hello.text = "Hello!"

        btn_toast.onClick { showToast("Welcome to HMLibrary") }
        btn_tips.onClick { showTips(TipsToast.TipType.Smile, "Thanks for using") }

    }

}

