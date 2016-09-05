# BaseActivity

Tags: HMLibrary

![](https://github.com/bxcx/HMLibrary/blob/master/md/baseActivity.gif)

##流程##

```flow
setUIParams=>start: setUIParams
onCreate=>operation: onCreate
checkParams=>operation: checkParams
cond=>condition: Yes or No?
loadData=>operation: loadData
initUI=>operation: initUI
initComplete=>operation: initComplete
finish=>operation: finish
onDestroy=>end: onDestroy

setUIParams->onCreate->checkParams->cond(right)->loadData->initUI->initComplete
cond(yes)->loadData
cond(no)->finish->onDestroy
```

## UIParams ##

        override fun setUIParams() {
            layoutResID = R.layout.activity_main    //activity布局id
            menuRes = R.menu.menu_save              //toolBar的MenuID
            needBind = false                        //是否需要bindView,默认为false,java类需要设置为true,kotlin类不需要
            swipeBack = true                        //是否开启手势右滑返回,默认为true
            hideActionBar = false                   //是否隐藏toolBar,默认为false
            displayHome = false                     //是否显示toolBar返回箭头,默认为true
        }

## Method ##

        //跟随View滑动隐藏或显示ActionBar
        hideActionBarByView(view: Scrollable)
        
        //延时执行(子线程)
        runDelayed(runnable: () -> Unit, delayMillis: Long)
        
        //延时执行(UI线程)
        runOnUIThread(runnable: () -> Unit, delayMillis: Long = 0) 
        
        //显示加载动画
        showLoading(msg: CharSequence? = "加载中")
        
        //关闭加载动画
        cancelLoading()
        
        //直接Toast,避免多次Toast长时间显示
        showToast(content: String?)
        
        //居中的带图标\文字的Tips
        showTips(tipType: TipsToast.TipType, msg: String)
        showTips(iconResId: Int, msg: String)
        
## Example ##

    //如果只需要设置UIParams中的少量参数,可以直接在这里override,用逗号隔开
    class DemoBaseActivity(override var layoutResID: Int = R.layout.activity_demo_base_activity) : BaseActivity() {
    
        override fun initUI() {
            super.initUI()
            
            //不用findView,直接使用布局中的id即控件
            tv_hello.text = "Hello!"
            btn_toast.onClick { showToast("Welcome to HMLibrary") }
            btn_tips.onClick { showTips(TipsToast.TipType.Smile, "Thanks for using") }
        }
    
    }
