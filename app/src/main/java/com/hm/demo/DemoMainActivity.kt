package com.hm.demo

import com.hm.library.base.BaseMainActivity
import kotlinx.android.synthetic.main.activity_demo_main.*

class DemoMainActivity(override var layoutResID: Int = R.layout.activity_demo_main) : BaseMainActivity() {


    val titles = arrayOf("妙笔", "纯音", "自然", "爱听")


    override fun setUIParams() {
        titles.forEach { mTabs.add(BlankFragment.newInstance(it)) }
//        titles.forEachIndexed { index, title ->
//            if (index == 0) {
//                mTabs.add(DemoBaseListFragment())
//            } else {
//                mTabs.add(BlankFragment.newInstance(title))
//            }
//        }
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
