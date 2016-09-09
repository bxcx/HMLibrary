package com.hm.library.resource;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.hm.library.R;

/**
 * LoadingProgressView
 * <p>
 * himi on 2016-09-09 10:20
 * version V1.0
 */
public class LoadingProgressView extends FrameLayout {

    View loadingProgressView;
    ImageView iv_progress;
    TextView tv_message;
    AnimationDrawable animationDrawable;//

    public LoadingProgressView(Context context) {
        super(context);
        init();
    }

    public LoadingProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LoadingProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    public void init() {
        loadingProgressView = LayoutInflater.from(getContext()).inflate(R.layout.include_progress_view, null);
        iv_progress = (ImageView) loadingProgressView.findViewById(R.id.iv_progress);
        tv_message = (TextView) loadingProgressView.findViewById(R.id.tv_message);
        animationDrawable = (AnimationDrawable) iv_progress.getDrawable();
        addView(loadingProgressView);
    }

    public void showLoadProgerss(CharSequence label) {
        animationDrawable.start();
        tv_message.setText(label);
        setVisibility(View.VISIBLE);
    }

    public void hidden() {
        setVisibility(View.GONE);
    }

}
