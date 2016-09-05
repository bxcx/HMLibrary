package com.hm.demo

import android.os.Bundle
import com.hm.library.base.BaseFragment
import com.hm.library.util.ArgumentUtil
import kotlinx.android.synthetic.main.fragment_blank.*

class BlankFragment(override var layoutResID: Int = R.layout.fragment_blank) : BaseFragment() {

    companion object {
        fun newInstance(name: String): BlankFragment {
            val fragment = BlankFragment()
            val args = Bundle()
            args.putString(ArgumentUtil.Arg_Name, name)
            fragment.arguments = args
            return fragment
        }
    }

    var name: String? = null

    override fun checkParams(): Boolean {
        name = arguments.getString(ArgumentUtil.Arg_Name)
        return true
    }

    override fun initUI() {
        super.initUI()

        tv_name.text = name
    }

}
