package com.hm.library.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hm.library.R
import com.hm.library.resource.recyclerview.PullRefreshLoadRecyclerView
import com.hm.library.util.FragmentUtil
import java.util.*


/**
 * BaseListActivity
 *
 * himi on 2016-03-16 14:21
 * version V1.0
 */

abstract class BaseListActivity<T : Any, H : BaseViewHolder<T>> : BaseActivity(), BaseListView<H> {

    override var layoutResID = R.layout.activity_base
    open var itemResID: Int = -1

    open var canRefesh: Boolean = true
    open var canLoadmore: Boolean = true
    open var canDrag: Boolean = false
    open var canSwipe: Boolean = false
    open var swipeType = PullRefreshLoadRecyclerView.SwipeType.Delete

    var row: Int = 1

    open var fragment: BaseListFragment<T, H>? = null
    var recyclerView: PullRefreshLoadRecyclerView? = null
    lateinit var adapter: BaseListFragment.BaseListAdapter<T, H>

    protected var item: H? = null

    open fun setListParams() {
    }

    fun getItemView(parent: ViewGroup?): View = LayoutInflater.from(this).inflate(itemResID, parent, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        setListParams()

        super.onCreate(savedInstanceState)

        if (fragment == null) {
            fragment = BLF<T, H>()
            fragment!!.activity = this
            fragment!!.canRefesh = canRefesh
            fragment!!.canLoadmore = canLoadmore
            fragment!!.canDrag = canDrag
            fragment!!.canSwipe = canSwipe
            fragment!!.swipeType = swipeType
            fragment!!.itemResID = itemResID
            fragment!!.list_activity = this
            fragment!!.row = row
        }

        FragmentUtil.replace(supportFragmentManager, R.id.layout_content, fragment!!, false, false)

        fragment!!.initCompleteRunnable = {
            onViewCreated()
        }
    }

    open fun onViewCreated() {
        recyclerView = fragment!!.recyclerView
        adapter = fragment!!.adapter!!
        loadData()
        initComplete()
    }


    override fun checkParams(): Boolean {
        return false
    }

    open fun setHeaderView() {
    }

    open fun setFootView() {
    }

    fun loadCompleted(list: ArrayList<T>?) {
        fragment!!.loadCompleted(list)
    }

    fun loadCompleted(list: List<T>?) {
        fragment!!.loadCompleted(list)
    }

    val createParams: HashMap<String, Any>
        get() = fragment!!.listParams
}


class BLF<T : Any, H : BaseViewHolder<T>> : BaseListFragment<T, H>() {

    override fun getView(parent: ViewGroup?, position: Int): H {
        val act = parent!!.context as BaseListActivity<T, H>
        return act.getView(parent, position)
    }

}
