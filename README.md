[![](https://jitpack.io/v/bxcx/HMLibrary.svg)](https://jitpack.io/#bxcx/HMLibrary)
# README

tags： HMLibrary


## Configuration ##

1. kotlin config   
![](https://github.com/bxcx/HMLibrary/blob/master/config%20kotlin.jpg)

2. build.gradle

        dependencies {
            compile 'com.github.bxcx:HMLibrary:v0.0.2'
        }


3. Application/AndroidManifest

        <application android:name="com.hm.library.app.HMApp" .../>
        
    or

        class DemoApp : HMApp() {}
        <application android:name="your.package.DemoApp" .../>

## Usage ##
 - [HMRequest][1]

        HMRequest.go<DealListModel>(url = url, params = params, method = Method.GET, headers = headers, activity = act, cache = true, needCallBack = false) {
            //it即解析成功的实体类
            showToast("返回数据共${it?.data?.size}条")
        }
        
        //可选传参数
        HMRequest.go<DealListModel>(url) {}
        HMRequest.go<DealListModel>(url, params) {}
        HMRequest.go<DealListModel>(url, params, cache = true) {}
 - [BaseMainActivity][2]   

            class DemoMainActivity(override var layoutResID: Int = R.layout.activity_demo_main) : BaseMainActivity() {
        
        
                val titles = arrayOf("首页", "附近", "精选", "我的")
            
            
                override fun setUIParams() {
                    titles.forEach { mTabs.add(BlankFragment.newInstance(it)) }
                }
            
            
                override fun initComplete() {
                    //三秒后执行
                    runDelayed({
                        //模拟红点
                        main_tabpage.setIndicateDisplay(3, true)
                    }, 3000)
            
                    //是否可以滑动，滑动是否有渐变效果
                    main_tabpage.setStyle(true, true)
                }
            
                override fun onTabSelected(index: Int) {
                    tv_title.text = titles[index]
                }
        
            }

 ![](https://github.com/bxcx/HMLibrary/blob/master/md/baseMainActivity.gif) 
  
 - [BaseActivity][3]   

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
![](https://github.com/bxcx/HMLibrary/blob/master/md/baseActivity.gif) 

 - [BaseListFragment][4]

            class DemoBaseListFragment : BaseListFragment<DealModel, DealHolder>() {
    
                //不需要设置下拉刷新\上拉加载更多的事件及逻辑, 只需要一个loadData方法
                override fun loadData() {
                    //百度糯米API http://apistore.baidu.com/apiworks/servicedetail/508.html
                    val url = "http://apis.baidu.com/baidunuomi/openapi/searchdeals"
                    //BaseListFragment中内置的listParams, 带有page和pageSize, 也可自行定义
                    val params = listParams
                    params.put("city_id", 100010000)
            
                    //分页请求通常需要needCallBack = true, 在请求失败时BaseListFragment会自动page--
                    HMRequest.go<DealListModel>(url, params, activity = act, cache = false, needCallBack = true) {
                        loadCompleted(it?.data?.deals)
                    }
    
                    override fun getView(parent: ViewGroup?, position: Int): DealHolder = DealHolder(getItemView(parent))
                
                }
            }

 

    *下拉刷新, 上拉加载更多*   
    ![](https://github.com/bxcx/HMLibrary/blob/master/md/baseListFragment.gif)   
    *拖拽排序, 侧滑删除*   
    ![](https://github.com/bxcx/HMLibrary/blob/master/md/baseListFragment_drag&delete.gif)   
    *拖拽排序, 侧滑菜单*   
    ![](https://github.com/bxcx/HMLibrary/blob/master/md/baseListFragment_drag&layout.gif)   


  [1]: https://github.com/bxcx/HMLibrary/blob/master/md/HMRequest.md
  [2]: https://github.com/bxcx/HMLibrary/blob/master/md/BaseMainActivity.md
  [3]: https://github.com/bxcx/HMLibrary/blob/master/md/BaseActivity.md
  [4]: https://github.com/bxcx/HMLibrary/blob/master/md/BaseListFragment.md
