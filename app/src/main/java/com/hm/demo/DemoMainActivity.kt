package com.hm.demo

import com.hm.library.base.BaseMainActivity
import kotlinx.android.synthetic.main.activity_demo_main.*

class DemoMainActivity(override var layoutResID: Int = R.layout.activity_demo_main) : BaseMainActivity() {


    lateinit var titles: Array<String> //= arrayOf("首页", "附近", "精选", "我的")


    override fun setUIParams() {
        titles = resources.getStringArray(R.array.bottom_bar_labels)
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
