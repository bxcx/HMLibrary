package com.hm.library.resource.recyclerview;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public abstract class WrapperAdapter<T extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<T> {
    public RecyclerView.Adapter wrapperAdapter;

}
