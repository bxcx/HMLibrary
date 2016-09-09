package com.hm.library.resource;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hm.library.R;

/**
 * LoadingProgressView
 * <p/>
 * himi on 2016-09-09 10:20
 * version V1.0
 */
public class LoadingProgressView extends FrameLayout {

    LinearLayout loadingProgressView;
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
        iv_progress = new ImageView(getContext());
        iv_progress.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        iv_progress.setScaleType(ImageView.ScaleType.FIT_XY);
        iv_progress.setImageResource(R.drawable.anim_loading_xml);
        animationDrawable = (AnimationDrawable) iv_progress.getDrawable();

        tv_message = new TextView(getContext());
        tv_message.setTextSize(16);
        tv_message.setTextColor(0xff333333);

        loadingProgressView = new LinearLayout(getContext());
        loadingProgressView.setOrientation(LinearLayout.HORIZONTAL);
        loadingProgressView.addView(iv_progress);
        loadingProgressView.addView(tv_message);

        addView(loadingProgressView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
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
