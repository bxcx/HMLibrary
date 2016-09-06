package com.hm.demo


import android.view.View
import android.view.ViewGroup
import com.hm.demo.DemoApp.BaseModel
import com.hm.demo.DemoBaseListFragment.DealHolder
import com.hm.demo.DemoBaseListFragment.DealListModel.DealModel
import com.hm.library.base.BaseListFragment
import com.hm.library.base.BaseViewHolder
import com.hm.library.expansion.show
import com.hm.library.http.HMRequest
import com.hm.library.resource.recyclerview.PullRefreshLoadRecyclerView
import com.hm.library.resource.view.CustomToast
import kotlinx.android.synthetic.main.item_deal.view.*
import org.jetbrains.anko.onClick
import org.jetbrains.anko.support.v4.act
import java.util.*


class DemoBaseListFragment : BaseListFragment<DealModel, DealHolder>() {

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
    }


    override fun getView(parent: ViewGroup?, position: Int): DealHolder = DealHolder(getItemView(parent))

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
            itemView.handleView.show(data.image)

            itemView.layout_top.onClick { CustomToast.makeText(context, "Top clicked", 0).show() }
            itemView.layout_delete.onClick {
                adapter.onItemDismiss(position)
                CustomToast.makeText(context, "Delete clicked", 0).show()
            }
        }

    }

}



