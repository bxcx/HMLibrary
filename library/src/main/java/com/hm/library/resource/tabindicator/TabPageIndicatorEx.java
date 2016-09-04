package com.hm.library.resource.tabindicator;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;

import com.hm.library.R;
import com.hm.library.resource.tabindicator.internal.TabPageIndicatorBase;

public class TabPageIndicatorEx extends TabPageIndicatorBase<TabPageViewEx> implements OnPageChangeListener {

	/** 底部菜单图标数组 */
	private int[] mDrawableIds;

	public TabPageIndicatorEx(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void handleStyledAttributes(TypedArray a) {
		// 读取布局中，各个tab使用的图标
		int iconsResId = a.getResourceId(R.styleable.TabIndicator_tabIcons, 0);
		TypedArray ta = getContext().getResources().obtainTypedArray(iconsResId);
		int len = ta.length();
		mDrawableIds = new int[len];
		for(int i = 0; i < len; i++) {
			mDrawableIds[i] = ta.getResourceId(i, 0);
		}
		ta.recycle();
	}

	@Override
	protected TabPageViewEx createTabView() {
		return new TabPageViewEx(getContext());
	}

	@Override
	protected void setProperties(TabPageViewEx tabPageView, int index) {
		tabPageView.setIcon(mDrawableIds[index]);
	}

	@Override
	protected int getTabSize() {
		return mDrawableIds.length;
	}
}
