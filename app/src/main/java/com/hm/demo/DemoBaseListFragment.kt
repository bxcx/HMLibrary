package com.hm.demo


import android.view.View
import android.view.ViewGroup
import com.hm.demo.DemoBaseListFragment.CookHolder
import com.hm.demo.DemoBaseListFragment.CookListModel.CookModel
import com.hm.library.base.BaseListFragment
import com.hm.library.base.BaseViewHolder
import com.hm.library.expansion.show
import com.hm.library.http.HMModel
import com.hm.library.http.HMRequest
import com.hm.library.resource.recyclerview.PullRefreshLoadRecyclerView
import kotlinx.android.synthetic.main.item_cook.view.*
import java.util.*


class DemoBaseListFragment : BaseListFragment<CookModel, CookHolder>() {

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

        itemResID = R.layout.item_cook
    }

    override fun loadData() {
        val url = "http://www.tngou.net/api/cook/list"
        val params = createParams

        HMRequest.go<CookListModel>(url, params, cache = true, needCallBack = true) {
            loadCompleted(it?.tngou)
        }
    }


    override fun getView(parent: ViewGroup?, position: Int): CookHolder = CookHolder(getItemView(parent))

    class CookListModel(errno: Int, error: String) : BaseModel(errno, error) {

        var tngou: ArrayList<CookModel>? = null

        class CookModel(var id: Long, var name: String, var description: String, var img: String)
    }

    class CookHolder(itemView: View) : BaseViewHolder<CookModel>(itemView) {

        override fun setContent(position: Int) {
            itemView.tv_name.text = data.name
            itemView.tv_desc.text = data.description
            itemView.iv_pic.show("http://tnfs.tngou.net/img" + data.img)
        }

    }

}

open class BaseModel(var errno: Int, var msg: String) : HMModel() {

    override var valid: Boolean = false
        get() = errno == 0

    override var message: String = ""
        get() = msg

}

