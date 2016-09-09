package com.hm.library.base

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import com.hm.library.R
import com.hm.library.resource.tabindicator.TabPageIndicatorEx
import com.hm.library.resource.view.SViewPager
import org.jetbrains.anko.find
import org.jetbrains.anko.support.v4.onPageChangeListener
import java.util.*

/**
 * Created by himi on 16/3/2.
 *
 *
 *
 * xml:<p>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
xmlns:att="http://schemas.android.com/apk/res-auto"
android:layout_width="match_parent"
android:layout_height="match_parent"
android:background="#ffffff"
android:orientation="vertical">

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
 * </p>
 */
abstract class BaseMainActivity : BaseActivity() {

    override var layoutResID = -1
    override var swipeBack = false

    //
    var mViewPager: SViewPager? = null
    var mTabPageIndicator: TabPageIndicatorEx? = null
    val mTabs: ArrayList<Fragment> = ArrayList()

    private var mAdapter: FragmentPagerAdapter? = null
    private var isGradualChange: Boolean = false

    fun initWithParams(viewPager: SViewPager, tabPageIndicator: TabPageIndicatorEx) {
        mViewPager = viewPager
        mTabPageIndicator = tabPageIndicator
    }

    override fun initUI() {
        initTabs()
        super.initUI()
    }

    private fun initTabs() {

        if (mViewPager == null)
            mViewPager = find(R.id.main_viewpager)
        if (mTabPageIndicator == null)
            mTabPageIndicator = find(R.id.main_tabpage)

        mAdapter = object : FragmentPagerAdapter(supportFragmentManager) {

            override fun getCount(): Int {
                return mTabs.size
            }

            override fun getItem(position: Int): Fragment {
                return mTabs[position]
            }
        }
        mViewPager!!.onPageChangeListener {

            onPageSelected {
                onTabSelected(it)
            }
        }
        mViewPager!!.adapter = mAdapter
        mTabPageIndicator!!.setViewPager(mViewPager!!)
        mTabPageIndicator!!.setOnTabSelectedListener { mViewPager!!.setCurrentItem(it, false) }
    }

    open fun onTabSelected(index: Int) {

    }

}