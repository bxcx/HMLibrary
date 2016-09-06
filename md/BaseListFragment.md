# BaseListFragment

tags： HMLibrary

*下拉刷新, 上拉加载更多*   
![](https://github.com/bxcx/HMLibrary/blob/master/md/baseListFragment.gif)   
*拖拽排序, 侧滑删除*   
![](https://github.com/bxcx/HMLibrary/blob/master/md/baseListFragment_drag&delete.gif)   
*拖拽排序, 侧滑菜单*   
![](https://github.com/bxcx/HMLibrary/blob/master/md/baseListFragment_drag&layout.gif)   

 - Example
    
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

 - UIParams

            override fun setUIParams() {
                //是否能刷新
                canRefesh = true
                //是否能加载更多
                canLoadmore = true
                //是否能拖拽排序
                canDrag = true
                //是否能侧滑
                canSwipe = true
                //侧滑方式 支持Layout(滑动菜单) Delete(滑动删除)
                swipeType = PullRefreshLoadRecyclerView.SwipeType.Layout
        
                //item的布局文件
                itemResID = R.layout.item_deal
        
                //接口定义的分页参数
                default_params_page = "page"
                default_params_pageSize = "page_size"
                //从第几页开始, 默认为1
                default_pageIndex = 1
                //每页加载多少条数据, 默认为10
                default_pageSize = 5
            }

 - Model & Holder
        
            class DealListModel(errno: Int, msg: String) : BaseModel(errno, msg) {
        
                var data: DealData? = null
        
                class DealData(var total: Int, var deals: ArrayList<DealModel>? = null)
        
                //实体类中的字段个数可以与json中不同, 可以多, 也可以少, 满足需求即可
                class DealModel(var image: String, var title: String, var description: String)
            }
        
            class DealHolder(itemView: View) : BaseViewHolder<DealModel>(itemView) {
        
                override fun setContent(position: Int) {
                    //进到此方法后可直接使用 data对象, 即当前绑定的数据实体
                    itemView.tv_name.text = data.title
                    itemView.tv_desc.text = data.description
                    itemView.iv_pic.show(data.image)
                }
        
            }

 - Item XML
    
            <?xml version="1.0" encoding="utf-8"?>
                <!--如果需要侧滑菜单,设置为LinearLayout -->
                <LinearLayout
                    xmlns:android="http://schemas.android.com/apk/res/android"
                    xmlns:att="http://schemas.android.com/apk/res-auto"
                    android:layout_width="match_parent"
                    android:layout_height="90dp"
                    android:orientation="horizontal"
                    android:paddingLeft="10dp"
                    android:paddingRight="10dp">
                
                    <!-- 正常情况显示的Layout -->
                    <RelativeLayout
                        android:id="@+id/layout"
                        android:layout_width="match_parent"
                        android:layout_height="match_parent">
                
                        <TextView
                            android:id="@+id/tv_name"
                            android:layout_width="match_parent"
                            android:layout_height="wrap_content"
                            android:layout_alignTop="@+id/handleView"
                            android:layout_marginRight="10dp"
                            android:layout_toLeftOf="@+id/handleView"
                            android:text="name"
                            android:textColor="#212121"
                            android:textSize="16sp"/>
                
                        <TextView
                            android:id="@+id/tv_desc"
                            android:layout_width="match_parent"
                            android:layout_height="wrap_content"
                            android:layout_below="@+id/tv_name"
                            android:layout_marginRight="10dp"
                            android:layout_marginTop="5dp"
                            android:layout_toLeftOf="@+id/handleView"
                            android:maxLines="3"
                            android:text="desc"
                            android:textColor="#888888"
                            android:textSize="12sp"/>
                
                        <ImageView
                            android:id="@id/handleView"
                            android:layout_width="90dp"
                            android:layout_height="70dp"
                            android:layout_alignParentRight="true"
                            android:layout_centerVertical="true"
                            android:scaleType="centerCrop"
                            android:src="@drawable/iv_defalut_image"/>
                    </RelativeLayout>
                
                    <!-- 滑动出现的Layout -->
                    <LinearLayout
                        android:layout_width="120dp"
                        android:layout_height="match_parent"
                        android:layout_marginBottom="10dp"
                        android:layout_marginTop="10dp"
                        android:background="@color/colorPrimary"
                        android:orientation="horizontal">
                
                        <!--MaterialRippleLayout 水波效果 只是为了好看,可以不用 -->
                        <com.balysv.materialripple.MaterialRippleLayout
                            android:id="@+id/layout_top"
                            android:layout_width="0dp"
                            android:layout_height="wrap_content"
                            android:layout_gravity="center_vertical"
                            android:layout_weight="1"
                            android:padding="10dp"
                            att:mrl_rippleAlpha="0.1"
                            att:mrl_rippleColor="#000000"
                            att:mrl_rippleOverlay="true">
                
                            <ImageView
                                android:layout_width="40dp"
                                android:layout_height="40dp"
                                android:layout_gravity="center"
                                android:scaleType="fitCenter"
                                android:src="@mipmap/icon_top"/>
                        </com.balysv.materialripple.MaterialRippleLayout>
                
                        <com.balysv.materialripple.MaterialRippleLayout
                            android:id="@+id/layout_delete"
                            android:layout_width="0dp"
                            android:layout_height="wrap_content"
                            android:layout_gravity="center_vertical"
                            android:layout_weight="1"
                            android:padding="10dp"
                            att:mrl_rippleAlpha="0.1"
                            att:mrl_rippleColor="#000000"
                            att:mrl_rippleOverlay="true">
                
                            <ImageView
                                android:layout_width="40dp"
                                android:layout_height="40dp"
                                android:layout_gravity="center"
                                android:scaleType="fitCenter"
                                android:src="@mipmap/icon_delete"/>
                        </com.balysv.materialripple.MaterialRippleLayout>
                
                    </LinearLayout>
                
                </LinearLayout>

 - Event
 
        override fun initUI() {
            super.initUI()
            
            //监听Item移动事件 拖拽排序
            onItemMove = { fromPosition, toPosition ->
                showToast("onItemMove")
            }
            //监听Item删除事件 侧滑删除
            onItemDismiss = { position ->
                showToast("onItemDismiss")
            }
        }
