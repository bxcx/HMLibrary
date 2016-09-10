package com.hm.library.base

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.hm.library.R
import com.hm.library.http.HMRequest
import com.hm.library.resource.recyclerview.PullRefreshLoadRecyclerView
import com.hm.library.resource.recyclerview.PullRefreshLoadRecyclerView.SwipeType
import com.hm.library.resource.recyclerview.WrapperAdapter
import com.hm.library.resource.recyclerview.headfoot.LoadMoreView
import com.hm.library.resource.recyclerview.headfoot.RefreshView
import com.hm.library.resource.recyclerview.swipe.ItemSlideHelper
import com.hm.library.util.ViewBindUtil
import org.jetbrains.anko.find
import org.jetbrains.anko.internals.AnkoInternals
import org.jetbrains.anko.support.v4.toast
import java.util.*
import kotlin.properties.Delegates


/**
 * BaseListFragment
 *
 * himi on 2016-03-17 22:17
 * version V1.0
 */
enum class LoadAction {
    LoadNew, LoadMore
}

interface BaseListView<H> {
    fun getView(parent: ViewGroup?, position: Int): H
}

interface onMoveAndSwipedListener {
    fun onItemMove(fromPosition: Int, toPosition: Int): Boolean
    fun onItemDismiss(position: Int)
}

interface onStateChangedListener {
    fun onItemSelected()
    fun onItemClear()
}

class SimpleItemTouchHelperCallback : ItemTouchHelper.Callback() {

    var mAdapter: onMoveAndSwipedListener? = null
    var canSwipe: Boolean = true
    var swipeType: SwipeType = SwipeType.Delete

    /**这个方法是用来设置我们拖动的方向以及侧滑的方向的 */
    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
        //如果是ListView样式的RecyclerView
        if (recyclerView.layoutManager is LinearLayoutManager) {
            //设置拖拽方向为上下
            val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
            //设置侧滑方向为从左到右和从右到左都可以
            val swipeFlags = if (canSwipe && swipeType == SwipeType.Delete) ItemTouchHelper.START or ItemTouchHelper.END else 0
            //将方向参数设置进去
            return makeMovementFlags(dragFlags, swipeFlags)
        } else {//如果是GridView样式的RecyclerView
            //设置拖拽方向为上下左右
            val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN or
                    ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
            //不支持侧滑
            val swipeFlags = 0
            return makeMovementFlags(dragFlags, swipeFlags)
        }
    }

    /**当我们拖动item时会回调此方法 */
    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        //如果两个item不是一个类型的，我们让他不可以拖拽
        if (viewHolder.itemViewType != target.itemViewType) {
            return false
        }
        //回调adapter中的onItemMove方法
        mAdapter?.onItemMove(viewHolder.adapterPosition, target.adapterPosition)
        return true
    }

    /**当我们侧滑item时会回调此方法 */
    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        //回调adapter中的onItemDismiss方法
        mAdapter?.onItemDismiss(viewHolder.adapterPosition)
    }

//    /**当状态改变时回调此方法 */
//    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder, actionState: Int) {
//        if (viewHolder != null) {
//            //当前状态不是idel（空闲）状态时，说明当前正在拖拽或者侧滑
//            if (actionState !== ItemTouchHelper.ACTION_STATE_IDLE) {
//                //看看这个viewHolder是否实现了onStateChangedListener接口
//                if (viewHolder is onStateChangedListener) {
//                    //回调ItemViewHolder中的onItemSelected方法来改变item的背景颜色
//                    viewHolder.onItemSelected()
//                }
//            }
//            super.onSelectedChanged(viewHolder, actionState)
//        }
//    }

    /**当用户拖拽完或者侧滑完一个item时回调此方法，用来清除施加在item上的一些状态 */
    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        super.clearView(recyclerView, viewHolder)
        if (viewHolder is onStateChangedListener) {
            viewHolder.onItemClear()
        }
    }

    /**这个方法可以判断当前是拖拽还是侧滑 */
    override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            //根据侧滑的位移来修改item的透明度
            val alpha = 1.0f - Math.abs(dX) / viewHolder.itemView.width.toFloat()
            viewHolder.itemView.alpha = alpha
            viewHolder.itemView.translationX = dX
        }
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }
}


abstract class BaseViewHolder<T : Any> : RecyclerView.ViewHolder, onStateChangedListener {

    lateinit var context: Context
    lateinit var adapter: BaseListFragment.BaseListAdapter<T, BaseViewHolder<T>>
    var handleView: View? = null
    var data: T by Delegates.notNull()

    constructor(itemView: View) : super(itemView) {
        handleView = itemView.findViewById(R.id.handleView)
        ViewBindUtil.bindViews(this, itemView)
    }

    abstract fun setContent(position: Int)

    override fun onItemSelected() {
        //设置item的背景颜色为浅灰色
        itemView?.setBackgroundColor(Color.LTGRAY)
    }

    override fun onItemClear() {
        //恢复item的背景颜色
        itemView?.setBackgroundColor(0)
    }

    inline fun <reified T : Activity> Context.startActivity(vararg params: Pair<String, Any>) {
        AnkoInternals.internalStartActivity(this, T::class.java, params)
    }

    inline fun <reified T : Any> Context.intentFor(vararg params: Pair<String, Any>): Intent {
        return AnkoInternals.createIntent(this, T::class.java, params)
    }
}

abstract class BaseListFragment<T : Any, H : BaseViewHolder<T>> : BaseFragment(), BaseListView<H>, PullRefreshLoadRecyclerView.LoadRefreshListener, PullRefreshLoadRecyclerView.onStartDragListener {

    override fun startDrag(viewHolder: RecyclerView.ViewHolder?) {
        mItemTouchHelper?.startDrag(viewHolder)
    }

    override var layoutResID: Int = -1
    open var itemResID: Int = -1

    fun getItemView(parent: ViewGroup?): View = LayoutInflater.from(context).inflate(itemResID, parent, false)

    /// 动作标识
    var action: LoadAction = LoadAction.LoadNew

    /// 当前页
    var page: Int = 1

    /// 数据源集合
    var dataList: ArrayList<T> = ArrayList()

    var row: Int = 1

    var recyclerView: PullRefreshLoadRecyclerView? = null
    var adapter: BaseListFragment.BaseListAdapter<T, H>? = null
    var list_activity: BaseListActivity<T, H>? = null

    var mItemTouchHelper: ItemTouchHelper? = null

    var refreshView: RefreshView? = null
    var loadMoreView: LoadMoreView? = null

    var activity: BaseActivity? = null

    open var canRefesh: Boolean = true
    open var canLoadmore: Boolean = true
    open var canDrag: Boolean = false
    open var canSwipe: Boolean = false
    open var swipeType = SwipeType.Delete

    //从第几页开始
    open var default_pageIndex = 1
    // 每页加载多少条
    open var default_pageSize = 10
    open var default_params_page = "page"
    open var default_params_pageSize = "pageSize"

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setUIParams()
        page = default_pageIndex

        if (rootView == null) {

            if (layoutResID == -1) {
                recyclerView = PullRefreshLoadRecyclerView(context)
                recyclerView?.setBackgroundColor(0xF5F5F5)
                rootView = recyclerView
            } else {
                rootView = inflater?.inflate(layoutResID, container, false)
                recyclerView = rootView!!.find(android.R.id.list)
            }
            recyclerView?.initLoadRefesh(canRefesh, canLoadmore)
            refreshView?.scrollBarStyle = View.OVER_SCROLL_IF_CONTENT_SCROLLS
            isInit = true
        }
        // 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
        if (rootView!!.parent != null) {
            val parent = rootView!!.parent as ViewGroup
            parent.removeView(rootView)
        }
        return rootView
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        if (isInit) {
            isInit = false
            if (checkParams()) {
                initUI()
                loadData()
                list_activity?.onViewCreated()
            }
        }

    }

    open fun setHeaderView() {
        list_activity?.setHeaderView()
    }

    open fun setFootView() {
        list_activity?.setFootView()
    }

    override fun initUI() {
        if (row > 1) {
            recyclerView!!.recyclerView.layoutManager = GridLayoutManager(context, row)
        }
        recyclerView!!.setLoadRefreshListener(this)

        initAdapter()
        activity?.initUI()
    }

    fun initAdapter() {
        setHeaderView()
        setFootView()

        if (adapter == null)
            adapter = BaseListAdapter(this, context)

        if (canDrag) {
            //关联ItemTouchHelper和RecyclerView
            val callback = SimpleItemTouchHelperCallback()
            callback.mAdapter = adapter
            callback.canSwipe = canSwipe
            callback.swipeType = swipeType
            mItemTouchHelper = ItemTouchHelper(callback)
            mItemTouchHelper!!.attachToRecyclerView(recyclerView!!.recyclerView)
            adapter!!.mDragStartListener = this
        }
        recyclerView!!.setAdapter(adapter)

        initComplete()
    }

    override fun loadData() {
        list_activity?.loadData()
    }

    /**
    执行刷新
     */
    open fun loadRefresh() {
        recyclerView?.recyclerView?.scrollToPosition(0)
        page = default_pageIndex
        action = LoadAction.LoadNew
        loadData()
    }

    /**
    执行加载更多
     */
    open fun loadMore() {
        if (dataList.size == 0)
            page = 1
        else {
            page++
            action = LoadAction.LoadMore
        }
        loadData()

    }

    fun loadCompleted(list: List<T>?) {
        loadCompleted(list as ArrayList<T>)
    }

    /**
    加载完成
     */
    fun loadCompleted(list: ArrayList<T>?) {
        if (action == LoadAction.LoadNew) {
            this.dataList.clear()
            refreshView?.state = LoadMoreView.STATE_NORMAL
            loadMoreView?.state = LoadMoreView.STATE_NORMAL
        } else {
            loadMoreView?.state = LoadMoreView.STATE_NORMAL
        }

        recyclerView?.hideLable()

        //list为nil时表示请求失败，需要page--
        if (list != null && list.size > 0) {
            for (data in list) {
                this.dataList.add(data)
            }
            adapter!!.notifyDataSetChanged(dataList)

            if (list.size < default_pageSize)
                loadMoreView?.state = LoadMoreView.STATE_NO_MORE

        } else {
            if (page == default_pageIndex && (list == null || list.size == 0)) {
                adapter!!.notifyDataSetChanged(ArrayList())
                recyclerView?.showLabel()
                loadMoreView?.state = LoadMoreView.STATE_EMPTY_RELOAD
            } else {
                if (action == LoadAction.LoadMore) {
                    if (page > 2)
                        toast("没有更多啦")
                    loadMoreView?.state = LoadMoreView.STATE_NO_MORE
                }
                if (list == null) {
                    loadMoreView?.state = LoadMoreView.STATE_LOAD_FAIL
                }
            }

            page--
            if (page <= default_pageIndex) page = default_pageIndex
        }


    }

    val listParams: HashMap<String, Any>
        get() {
            var params = HMRequest.params.clone() as HashMap<String, Any>
            if (canLoadmore && canRefesh) {
                params.put(default_params_page, page)
                params.put(default_params_pageSize, default_pageSize)
            }
            return params
        }

    override fun onRefresh(pullRefreshLoadRecyclerView: PullRefreshLoadRecyclerView, refreshView: RefreshView) {
        runDelayed({
            this.refreshView = refreshView
            loadRefresh()
        }, 500)

    }

    override fun onLoadMore(pullRefreshLoadRecyclerView: PullRefreshLoadRecyclerView, loadMoreView: LoadMoreView) {
        this.loadMoreView = loadMoreView
        loadMore()
    }

    var onItemMove: ((Int, Int) -> Unit)? = null
    var onItemDismiss: ((Int) -> Unit)? = null

    open class BaseListAdapter<T : Any, H : BaseViewHolder<T>> : WrapperAdapter<H>, onMoveAndSwipedListener, ItemSlideHelper.Callback {

        override fun getHorizontalRange(holder: RecyclerView.ViewHolder): Int {
            if (holder.itemView is LinearLayout) {
                val viewGroup = holder.itemView as ViewGroup
                if (viewGroup.childCount === 2) {
                    return viewGroup.getChildAt(1).layoutParams.width
                }
            }
            return 0
        }

        override fun getChildViewHolder(childView: View?): RecyclerView.ViewHolder? {
            return mRecyclerView?.getChildViewHolder(childView)
        }

        override fun findTargetView(x: Float, y: Float): View? {
            return mRecyclerView?.findChildViewUnder(x, y)
        }


        var data = ArrayList<T>()
        var mDragStartListener: PullRefreshLoadRecyclerView.onStartDragListener? = null
        var mRecyclerView: RecyclerView? = null
        override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
            super.onAttachedToRecyclerView(recyclerView)

            mRecyclerView = recyclerView
            if (baseListFragment.canSwipe && baseListFragment.swipeType == SwipeType.Layout)
                mRecyclerView!!.addOnItemTouchListener(ItemSlideHelper(mRecyclerView!!.context, this))
        }

        override fun onItemMove(fromPosition: Int, toPosition: Int): Boolean {
            //交换mItems数据的位置
            Collections.swap(data, fromPosition, toPosition)
            //交换RecyclerView列表中item的位置
            notifyItemMoved(fromPosition, toPosition)
            baseListFragment.onItemMove?.invoke(fromPosition, toPosition)
            return true
        }

        override fun onItemDismiss(position: Int) {
            //删除mItems数据
            data.removeAt(position);
            //删除RecyclerView列表对应item
            notifyItemRemoved(position)
            baseListFragment.onItemDismiss?.invoke(position)
        }

        lateinit var baseListFragment: BaseListFragment<T, H>
        lateinit var context: Context

        constructor(baseListFragment: BaseListFragment<T, H>, context: Context) {
            this.baseListFragment = baseListFragment
            this.context = context
        }

        override fun getItemCount(): Int = data.size

        override fun onCreateViewHolder(parent: ViewGroup?, position: Int): H = baseListFragment.getView(parent, position)

        override fun onBindViewHolder(holder: H, position: Int) {
            holder.context = context
            holder.adapter = this as BaseListAdapter<T, BaseViewHolder<T>>
            if (this.data.size > position) {
                holder.data = this.data[position]
                holder.setContent(position)

                if (mDragStartListener != null)
                    holder.handleView?.setOnTouchListener { view, motionEvent ->
                        if (motionEvent.action == MotionEvent.ACTION_DOWN) {
                            holder.onItemSelected()
                            mDragStartListener!!.startDrag(holder)
                        }
                        false
                    }
            }
        }

        fun notifyDataSetChanged(data: ArrayList<T>) {
            this.data = data

            if (wrapperAdapter != null)
                wrapperAdapter.notifyDataSetChanged()
            else
                notifyDataSetChanged()
        }

    }


}