package com.hm.library.resource.tabindicator;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import com.hm.library.R;
import com.hm.library.resource.tabindicator.internal.TabIndicatorBase;

public class TabIndicator extends TabIndicatorBase<TabView> {

	private int[] mSelectedDrawableIds;
	private int[] mUnselectedDrawableIds;

	public TabIndicator(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void handleStyledAttributes(TypedArray a) {
		// 读取布局中，各个tab使用的图标
		int selectedIconsResId = a.getResourceId(R.styleable.TabIndicator_tabSelectedIcons, 0);
		TypedArray ta = getContext().getResources().obtainTypedArray(selectedIconsResId);
		int len = ta.length();
		mSelectedDrawableIds = new int[len];
		for(int i = 0; i < len; i++) {
			mSelectedDrawableIds[i] = ta.getResourceId(i, 0);
		}

		int unselectedIconsResId = a.getResourceId(R.styleable.TabIndicator_tabUnselectedIcons, 0);
		ta = getContext().getResources().obtainTypedArray(unselectedIconsResId);
		len = ta.length();
		mUnselectedDrawableIds = new int[len];
		for(int i = 0; i < len; i++) {
			mUnselectedDrawableIds[i] = ta.getResourceId(i, 0);
		}

		ta.recycle();
	}

	@Override
	protected TabView createTabView() {
		return new TabView(getContext());
	}

	@Override
	protected void setProperties(TabView tabView, int index) {
		tabView.setSelectedIcon(mSelectedDrawableIds[index]);
		tabView.setUnselectedIcon(mUnselectedDrawableIds[index]);
	}

	@Override
	protected int getTabSize() {
		return mSelectedDrawableIds.length;
	}
}
