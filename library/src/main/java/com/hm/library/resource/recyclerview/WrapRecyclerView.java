package com.hm.library.resource.recyclerview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;

public class WrapRecyclerView extends com.github.ksoichiro.android.observablescrollview.ObservableRecyclerView {

    private ArrayList<View> mHeaderViews = new ArrayList<>();

    private ArrayList<View> mFootViews = new ArrayList<>();

    private Adapter mAdapter;

    public WrapRecyclerView(Context context) {
        super(context);
    }

    public WrapRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WrapRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void addHeaderView(View view) {
        mHeaderViews.clear();
        mHeaderViews.add(view);
        if (mAdapter != null) {
            if (!(mAdapter instanceof RecyclerWrapAdapter)) {
                mAdapter = new RecyclerWrapAdapter(mHeaderViews, mFootViews, mAdapter);
                mAdapter.notifyDataSetChanged();
            }
        }
    }

    public void addFootView(View view) {
        mFootViews.clear();
        mFootViews.add(view);
        if (mAdapter != null) {
            if (!(mAdapter instanceof RecyclerWrapAdapter)) {
                mAdapter = new RecyclerWrapAdapter(mHeaderViews, mFootViews, mAdapter);
                mAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void setAdapter(Adapter adapter) {

        if (mHeaderViews.isEmpty() && mFootViews.isEmpty()) {
            super.setAdapter(adapter);
        } else {
            RecyclerWrapAdapter wrapperAdapter = new RecyclerWrapAdapter(mHeaderViews, mFootViews, adapter);
            if (adapter instanceof WrapperAdapter) {
                ((WrapperAdapter) adapter).wrapperAdapter = wrapperAdapter;
            }
            super.setAdapter(wrapperAdapter);
        }
        mAdapter = adapter;
    }

}
