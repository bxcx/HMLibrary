package com.hm.library.resource.tabindicator.internal;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

import com.hm.library.R;
import com.hm.library.resource.view.SViewPager;


public abstract class TabPageIndicatorBase<T extends TabViewBase> extends TabIndicatorBase<T> implements ViewPager.OnPageChangeListener {

    /**
     * 是否渐变切换
     */
    protected boolean mIsGradualChange = true;
    /**
     * 用于ViewPager会渐变颜色
     */
    private SViewPager mViewPager;

    public TabPageIndicatorBase(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void handleStyledAttributes(TypedArray a) {
        super.handleStyledAttributes(a);
        mIsGradualChange = a.getBoolean(R.styleable.TabIndicator_tabGradualChange, true);
    }

    /**
     * 设置是否渐变渐变切换
     *
     * @param isGradualChange
     */
    public void setIsGradualChange(boolean isGradualChange) {
        this.mIsGradualChange = isGradualChange;
    }


    /**
     * @param isCanScroll 是否可以滑动
     * @param isGradualChange 是否开启渐变
     */
    public void setStyle(boolean isCanScroll, boolean isGradualChange) {
        this.mIsGradualChange = isGradualChange;
        mViewPager.setCanScroll(isCanScroll);
    }

    /**
     * 获取是否渐变切换
     *
     * @return
     */
    public boolean getIsGradualChange() {
        return mIsGradualChange;
    }

    public void setViewPager(SViewPager viewPager) {
        this.mViewPager = viewPager;
        mViewPager.addOnPageChangeListener(this);
    }

    @Override
    public void onPageScrollStateChanged(int position) {
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (mIsGradualChange && positionOffset > 0) {
            T left = mCheckedList.get(position);
            T right = mCheckedList.get(position + 1);

            left.setIconAlpha(1 - positionOffset);
            right.setIconAlpha(positionOffset);
        }
    }

    @Override
    public void onPageSelected(int position) {
        if (!mIsGradualChange) {
            setTabsDisplay(position);
        }
    }
}
