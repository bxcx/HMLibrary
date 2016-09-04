package com.hm.library.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import com.hm.library.resource.tabindicator.TabPageIndicatorEx
import com.hm.library.resource.view.SViewPager
import com.jude.swipbackhelper.SwipeBackHelper
import java.util.*

/**
 * Created by himi on 16/3/2.
 *
 *
 *
 * xml:<p>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
xmlns:hm="http://schemas.android.com/apk/res-auto"
android:layout_width="match_parent"
android:layout_height="match_parent"
android:orientation="vertical">

<com.hm.library.resource.view.SViewPager
android:id="@+id/activiy_base_viewpager"
android:layout_width="fill_parent"
android:layout_height="0dp"
android:layout_weight="1"/>

<FrameLayout
android:layout_width="match_parent"
android:layout_height="wrap_content"
android:layout_gravity="bottom">

<com.hm.library.resource.tabindicator.TabPageIndicatorEx
android:id="@+id/activiy_base_tabpage"
android:layout_width="match_parent"
android:layout_height="60dp"
android:background="@drawable/tabbg"
hm:tabTextSize="12sp"
hm:tabIcons="@array/bottom_bar_icons"
hm:tabLabels="@array/bottom_bar_labels"
hm:tabSelectedColor="#3F51B5"
hm:tabUnselectedColor="#555555"
hm:tabItemPadding="8dp"/>/>
</FrameLayout>

</LinearLayout>
 * </p>
 */
abstract class BaseMainActivity : BaseActivity() {

    override var layoutResID = -1

    //
    var mViewPager: SViewPager? = null
    var mTabPageIndicator: TabPageIndicatorEx? = null
    abstract val mTabs: ArrayList<Fragment>

    private var mAdapter: FragmentPagerAdapter? = null
    private var isGradualChange: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SwipeBackHelper.getCurrentPage(this).setSwipeBackEnable(false)
    }

    fun initWithParams(viewPager: SViewPager, tabPageIndicator: TabPageIndicatorEx) {
        mViewPager = viewPager
        mTabPageIndicator = tabPageIndicator
    }

    override fun checkParams(): Boolean {
        return false
    }

    override fun initUI() {
        initTabs()
    }

    private fun initTabs() {
        mAdapter = object : FragmentPagerAdapter(supportFragmentManager) {

            override fun getCount(): Int {
                return mTabs.size
            }

            override fun getItem(position: Int): Fragment {
                return mTabs[position]
            }
        }
        mViewPager!!.adapter = mAdapter
        mTabPageIndicator!!.setViewPager(mViewPager!!)
        mTabPageIndicator!!.setOnTabSelectedListener { index -> mViewPager!!.setCurrentItem(index, false) }

    }
}