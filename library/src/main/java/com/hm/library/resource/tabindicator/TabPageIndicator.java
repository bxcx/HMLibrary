package com.hm.library.resource.tabindicator;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;

import com.hm.library.R;
import com.hm.library.resource.tabindicator.internal.TabPageIndicatorBase;

public class TabPageIndicator extends TabPageIndicatorBase<TabPageView> implements OnPageChangeListener {

	/** 底部菜单图标数组 */
	private int[] mSelectedDrawableIds;
	private int[] mUnselectedDrawableIds;

	public TabPageIndicator(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void handleStyledAttributes(TypedArray a) {
		// 读取布局中，各个tab使用的图标
		int iconsResId = a.getResourceId(R.styleable.TabIndicator_tabSelectedIcons, 0);
		TypedArray ta = getContext().getResources().obtainTypedArray(iconsResId);
		int len = ta.length();
		mSelectedDrawableIds = new int[len];
		for(int i = 0; i < len; i++) {
			mSelectedDrawableIds[i] = ta.getResourceId(i, 0);
		}

		iconsResId = a.getResourceId(R.styleable.TabIndicator_tabUnselectedIcons, 0);
		ta = getContext().getResources().obtainTypedArray(iconsResId);
		len = ta.length();
		mUnselectedDrawableIds = new int[len];
		for(int i = 0; i < len; i++) {
			mUnselectedDrawableIds[i] = ta.getResourceId(i, 0);
		}

		ta.recycle();
	}

	@Override
	protected TabPageView createTabView() {
		return new TabPageView(getContext());
	}

	@Override
	protected void setProperties(TabPageView tabPageView, int index) {
		tabPageView.setSelectedIcon(mSelectedDrawableIds[index]);
		tabPageView.setUnselectedIcon(mUnselectedDrawableIds[index]);
	}

	@Override
	protected int getTabSize() {
		return mSelectedDrawableIds.length;
	}
}
