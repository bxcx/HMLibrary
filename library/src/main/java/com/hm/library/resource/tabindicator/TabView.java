package com.hm.library.resource.tabindicator;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;

import com.hm.library.resource.tabindicator.internal.TabViewBase;


public class TabView extends TabViewBase {

	/** 图标 */
	private Bitmap mSelectedIconBitmap;
	private Bitmap mUnselectedIconBitmap;

	public TabView(Context context) {
		super(context);
	}

	public TabView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		setupTargetBitmap(canvas);
		drawIndicator(canvas);
	}

	private void setupTargetBitmap(Canvas canvas) {
		canvas.drawBitmap(isSelected ? mSelectedIconBitmap : mUnselectedIconBitmap, null, mIconRect, null);
	}

	@Override
	public void setSelected(boolean selected) {
		this.isSelected = selected;
		invalidateView();
	}

	public void setSelectedIcon(int resId) {
		this.mSelectedIconBitmap = BitmapFactory.decodeResource(getResources(), resId);
		if (mIconRect != null)
			invalidateView();
	}

	public void setSelectedIcon(Bitmap iconBitmap) {
		this.mSelectedIconBitmap = iconBitmap;
		if (mIconRect != null)
			invalidateView();
	}

	public void setUnselectedIcon(int resId) {
		this.mUnselectedIconBitmap = BitmapFactory.decodeResource(getResources(), resId);
		if (mIconRect != null)
			invalidateView();
	}

	public void setUnselectedIcon(Bitmap iconBitmap) {
		this.mUnselectedIconBitmap = iconBitmap;
		if (mIconRect != null)
			invalidateView();
	}
}
