package com.hm.library.resource.recyclerview;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.hm.library.R;
import com.hm.library.resource.recyclerview.headfoot.LoadMoreView;
import com.hm.library.resource.recyclerview.headfoot.RefreshView;
import com.hm.library.resource.recyclerview.headfoot.impl.DefaultLoadMoreView;
import com.hm.library.resource.recyclerview.headfoot.impl.DefaultRefreshView;
import com.hm.library.resource.recyclerview.overscroll.OverScrollGridManager;
import com.hm.library.resource.recyclerview.overscroll.OverScrollImpl;
import com.hm.library.resource.recyclerview.overscroll.OverScrollLinearLayoutManager;


public class PullRefreshLoadRecyclerView extends FrameLayout implements RefreshView.StateListener, LoadMoreView.StateListener {
    TextView textView;
    WrapRecyclerView recyclerView;
    LoadMoreView loadMoreView;
    RefreshView refreshView;
    LoadRefreshListener loadRefreshListener;

    SwipeType swipeType = SwipeType.None;

    public enum SwipeType {None, Delete, Layout}

    public interface onStartDragListener {
        void startDrag(RecyclerView.ViewHolder viewHolder);
    }

    public PullRefreshLoadRecyclerView(Context context) {
        super(context);
        init();
    }

    public PullRefreshLoadRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PullRefreshLoadRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public PullRefreshLoadRecyclerView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        recyclerView = new InnerRecyclerView(getContext());
        recyclerView.setLayoutManager(new OverScrollLinearLayoutManager(recyclerView));

        textView = new TextView(getContext());
        textView.setBackground(recyclerView.getBackground());
        textView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if (loadMoreView != null) {
                        loadMoreView.setState(LoadMoreView.STATE_LOADING);
                        textView.setVisibility(View.GONE);
                    }
                }
                return true;
            }
        });
        textView.setGravity(Gravity.CENTER);
        textView.setVisibility(View.GONE);
        addView(recyclerView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        addView(textView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    public void initLoadRefesh(boolean canRefesh, boolean canLoadMore) {
        if (canRefesh)
            setRefreshView(new DefaultRefreshView(getContext()));
        if (canLoadMore)
            setLoadMoreView(new DefaultLoadMoreView(getContext()));
    }

    public void hideLable() {
        textView.setVisibility(View.GONE);
    }

    public void showLabel() {
        textView.setText(R.string.loadmore_state_empty_reload);
        textView.setVisibility(View.VISIBLE);
    }

    public void showLabel(String label) {
        textView.setText(label);
        textView.setVisibility(View.VISIBLE);
    }

    public void setLoadMoreView(LoadMoreView loadMoreView) {
        if (this.loadMoreView != null) {
            this.loadMoreView.setStateListener(null);
            this.loadMoreView.unBind();
            removeView(this.loadMoreView);
        }
        this.loadMoreView = loadMoreView;

        if (loadMoreView != null) {
            addView(loadMoreView, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL));
            loadMoreView.bindWith(recyclerView);
            loadMoreView.setStateListener(this);
        }
    }

    public void setRefreshView(RefreshView refreshView) {
        if (this.refreshView != null) {
            this.refreshView.setStateListener(null);
            this.refreshView.unBind();
            removeView(this.refreshView);
        }
        this.refreshView = refreshView;
        if (refreshView != null) {
            addView(refreshView, new FrameLayout.LayoutParams(-1, -2, Gravity.TOP));
            refreshView.bindWith(recyclerView);
            refreshView.setStateListener(this);
        }
    }

    public WrapRecyclerView getRecyclerView() {
        return recyclerView;
    }

    public LoadMoreView getLoadMoreView() {
        return loadMoreView;
    }

    public RefreshView getRefreshView() {
        return refreshView;
    }

    public void addHeaderView(View view) {
        if (view == null)
            return;
        recyclerView.addHeaderView(view);
    }

    public void addFootView(View view) {
        if (view == null)
            return;
        recyclerView.addFootView(view);
    }

    public boolean isLoading() {
        return (loadMoreView != null && loadMoreView.getState() == LoadMoreView.STATE_LOADING) ||
                (refreshView != null && refreshView.getState() == RefreshView.STATE_LOADING);
    }

    @Override
    public boolean interceptStateChange(RefreshView refreshView, int state, int oldState) {
        if (state != RefreshView.STATE_NORMAL &&
                loadMoreView != null && loadMoreView.getState() == LoadMoreView.STATE_LOADING) {
            return true;
        }
        return false;
    }

    @Override
    public void onStateChange(RefreshView refreshView, int state) {
        if (loadRefreshListener != null && state == RefreshView.STATE_LOADING) {
            loadRefreshListener.onRefresh(this, refreshView);

            if (loadMoreView != null && loadMoreView.getState() == LoadMoreView.STATE_LOAD_FAIL) {
                loadMoreView.setState(LoadMoreView.STATE_NORMAL);
            }
        }
    }

    @Override
    public boolean interceptStateChange(LoadMoreView loadMoreView, int state, int oldState) {
        if (state == LoadMoreView.STATE_LOADING &&
                refreshView != null && refreshView.getState() == RefreshView.STATE_LOADING) {
            return true;
        }
        return false;
    }

    @Override
    public void onStateChange(LoadMoreView loadMoreView, int state) {
        if (loadRefreshListener != null && state == LoadMoreView.STATE_LOADING) {
            loadRefreshListener.onLoadMore(this, loadMoreView);
        }
    }

    public void setLoadRefreshListener(LoadRefreshListener loadRefreshListener) {
        this.loadRefreshListener = loadRefreshListener;
    }

    //    common void setAdapter(RecyclerView.Adapter adapter) {
    //        recyclerView.setAdapter(adapter);
    //    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        recyclerView.setAdapter(adapter);
        if (adapter instanceof LoadRefreshListener) {
            setLoadRefreshListener((LoadRefreshListener) adapter);
        }
    }

    public static abstract class LoadRefreshAdapter<T extends RecyclerView.ViewHolder>
            extends RecyclerView.Adapter<T> implements LoadRefreshListener {
    }

    public interface LoadRefreshListener {
        void onRefresh(PullRefreshLoadRecyclerView pullRefreshLoadRecyclerView, RefreshView refreshView);

        void onLoadMore(PullRefreshLoadRecyclerView pullRefreshLoadRecyclerView, LoadMoreView loadMoreView);
    }

    private static class InnerRecyclerView extends WrapRecyclerView {
        public InnerRecyclerView(Context context) {
            super(context);
        }

        @Override
        public void setLayoutManager(LayoutManager layout) {
            if (layout != null && !(layout instanceof OverScrollImpl.OverScrollLayoutManager)) {
                if (layout.getClass().equals(GridLayoutManager.class)) {
                    GridLayoutManager grid = (GridLayoutManager) layout;
                    layout = new OverScrollGridManager(this, grid.getSpanCount(), grid.getOrientation(), grid.getReverseLayout());
                } else if (layout.getClass().equals(LinearLayoutManager.class)) {
                    LinearLayoutManager linear = (LinearLayoutManager) layout;
                    layout = new OverScrollLinearLayoutManager(this, linear.getOrientation(), linear.getReverseLayout());
                } else {
                    throw new IllegalArgumentException("LayoutManager " + layout +
                            " should be subclass of: " + OverScrollLinearLayoutManager.class.getName());
                }
            }
            super.setLayoutManager(layout);
        }

        @Override
        public int computeVerticalScrollOffset() {
            int overScrollDistance = 0;
            LayoutManager layoutManager = getLayoutManager();
            if (layoutManager instanceof OverScrollImpl.OverScrollLayoutManager) {
                overScrollDistance = ((OverScrollImpl.OverScrollLayoutManager) layoutManager).getOverScrollDistance();
            }
            return super.computeVerticalScrollOffset() - overScrollDistance;
        }
    }
}
