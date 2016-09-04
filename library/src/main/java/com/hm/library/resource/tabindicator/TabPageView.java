package com.hm.library.resource.tabindicator;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;

import com.hm.library.resource.tabindicator.internal.TabViewBase;


public class TabPageView extends TabViewBase {

	private Paint mPaint = new Paint();
	/** 透明度 0.0-1.0 */
	private float mAlpha = 0f;
	/** 图标 */
	private Bitmap mSelectedIconBitmap;
	private Bitmap mUnselectedIconBitmap;

	public TabPageView(Context context) {
		super(context);
	}

	public TabPageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		int alpha = (int) Math.ceil((255 * mAlpha));
		drawSourceBitmap(canvas, alpha);
		drawTargetBitmap(canvas, alpha);
		if(null != mText) {
			drawSourceText(canvas, alpha);
			drawTargetText(canvas, alpha);
		}
		drawIndicator(canvas);
	}

	private void drawSourceBitmap(Canvas canvas, int alpha) {
		mPaint.setAntiAlias(true);
		mPaint.setDither(true);
		mPaint.setAlpha(255 - alpha);
		canvas.drawBitmap(mUnselectedIconBitmap, null, mIconRect, mPaint);
	}

	private void drawTargetBitmap(Canvas canvas, int alpha) {
		mPaint.setAntiAlias(true);
		mPaint.setDither(true);
		mPaint.setAlpha(alpha);
		canvas.drawBitmap(mSelectedIconBitmap, null, mIconRect, mPaint);
	}

	/**
	 * 画未选中文字
	 * @param canvas
	 * @param alpha
	 */
	private void drawSourceText(Canvas canvas, int alpha) {
		mTextPaint.setTextSize(mTextSize);
		mTextPaint.setColor(mUnselectedColor);
		mTextPaint.setAlpha(255 - alpha);
		canvas.drawText(mText, mIconRect.left + mIconRect.width() / 2
						- mTextBound.width() / 2,
				mIconRect.bottom + mTextBound.height(), mTextPaint);
	}

	/**
	 * 画选中文字
	 * @param canvas
	 * @param alpha
	 */
	private void drawTargetText(Canvas canvas, int alpha) {
		mTextPaint.setColor(mSelectedColor);
		mTextPaint.setAlpha(alpha);
		canvas.drawText(mText, mIconRect.left + mIconRect.width() / 2
						- mTextBound.width() / 2,
				mIconRect.bottom + mTextBound.height(), mTextPaint);
	}


	@Override
	public void setSelected(boolean selected) {
		if(selected) {
			setIconAlpha(1.0f);
		} else {
			setIconAlpha(0f);
		}
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

	public void setIconAlpha(float alpha) {
		this.mAlpha = alpha;
		invalidateView();
	}

}
