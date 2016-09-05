# BaseMainActivity

tags： HMLibrary

---

![](https://github.com/bxcx/HMLibrary/blob/master/md/BaseMainActivity.gif)

## Example ##

 - activity

        class DemoMainActivity(override var layoutResID: Int = R.layout.activity_demo_main) : BaseMainActivity() {
    
            val titles = arrayOf("妙笔", "纯音", "自然", "爱听")
        
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

 
 - layout

        <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
                  xmlns:att="http://schemas.android.com/apk/res-auto"
                  android:layout_width="match_parent"
                  android:layout_height="match_parent"
                  android:background="#ffffff"
                  android:orientation="vertical">
    
        <RelativeLayout
            android:layout_width="match_parent"
            android:layout_height="?attr/actionBarSize"
            android:background="@color/colorPrimary">
    
            <TextView
                android:id="@+id/tv_title"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_centerInParent="true"
                android:text="@string/app_name"
                android:textColor="@color/white"
                android:textSize="18sp"/>
        </RelativeLayout>
    
        <com.hm.library.resource.view.SViewPager
            android:id="@id/main_viewpager"
            android:layout_width="match_parent"
            android:layout_height="0dp"
            android:layout_weight="1"/>
    
        <FrameLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_gravity="bottom">
    
            <com.hm.library.resource.tabindicator.TabPageIndicatorEx
                android:id="@id/main_tabpage"
                android:layout_width="match_parent"
                android:layout_height="60dp"
                android:background="@drawable/tabbg"
                att:tabIcons="@array/bottom_bar_icons"
                att:tabItemPadding="8dp"
                att:tabLabels="@array/bottom_bar_labels"
                att:tabSelectedColor="#00bb9c"
                att:tabTextSize="12sp"
                att:tabUnselectedColor="#a9b7b7"/>/>
        </FrameLayout>
    
    </LinearLayout>

 - arrays
 
    <resources>
    
        <!-- 首页导航 -->
        <string-array name="bottom_bar_labels">
            <item>"妙笔"</item>
            <item>"纯音"</item>
            <item>"自然"</item>
            <item>"爱听"</item>
        </string-array>
        <!-- 对应的图标,只需要一张灰色 -->
        <string-array name="bottom_bar_icons">
            <item>@mipmap/ic_menu_article</item>
            <item>@mipmap/ic_menu_music</item>
            <item>@mipmap/ic_menu_nature</item>
            <item>@mipmap/ic_menu_iting</item>
        </string-array>
    
    </resources>
