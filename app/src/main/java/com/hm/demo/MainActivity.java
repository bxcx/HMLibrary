package com.hm.demo;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hm.library.base.BaseListActivity;
import com.hm.library.base.BaseViewHolder;
import com.hm.library.http.HMModel;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseListActivity<TestData, TestHolder> {

    @Override
    public void setListParams() {
        setCanLoadmore(false);
        setCanDrag(true);
        setCanSwipe(true);
    }

    @Override
    public int getItemResID() {
        return R.layout.item_test;
    }

    @Override
    public TestHolder getView(@Nullable ViewGroup parent, int position) {
        return new TestHolder(getItemView(parent));
    }

    @Override
    public void loadData() {

        List<TestData> list = new ArrayList();
        for (int i = 0; i < 20; i++) {
            TestData data = new TestData("ok", "ok");
            data.name = i + "";
            list.add(data);
        }
        loadCompleted(list);
    }
}

class TestHolder extends BaseViewHolder<TestData> {

    TextView tv_name;
    View layout;

    public TestHolder(@NotNull View itemView) {
        super(itemView);
    }

    @Override
    public void setContent(int position) {
        tv_name.setText(getData().name);
        layout.setBackgroundColor(position % 2 == 0 ? Color.WHITE : Color.GRAY);
    }
}

class TestData extends HMModel {
    public String name;

    public TestData(@NotNull String status, @NotNull String error) {
        super(status, error);
    }
}

