package com.hm.library.resource.material;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.TextView;

import com.hm.library.R;
import com.hm.library.resource.ViewTool;


public class MaterialTextView extends TextView implements MaterialBackgroundDetector.Callback {
	private MaterialBackgroundDetector mDetector;

	public MaterialTextView(Context context) {
		super(context);
		init(context, null, 0);
	}

	public MaterialTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs, 0);
	}

	public MaterialTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context, attrs, defStyle);
	}

	private void init(Context context, AttributeSet attrs, int defStyle) {
		final TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.MaterialView, defStyle, 0);
		int color = a.getColor(R.styleable.MaterialView_maskColor, MaterialBackgroundDetector.DEFAULT_COLOR);
		boolean padding = a.getBoolean(R.styleable.MaterialView_maskPadding, true);
		a.recycle();
		mDetector = new MaterialBackgroundDetector(getContext(), this, this, color);
		if (padding) {
			setPadding(ViewTool.dip2px(context, 10), ViewTool.dip2px(context, 5), ViewTool.dip2px(context, 10), ViewTool.dip2px(context, 5));
		}
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		mDetector.onSizeChanged(w, h);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		boolean superResult = super.onTouchEvent(event);
		return mDetector.onTouchEvent(event, superResult);
	}

	public void cancelAnimator() {
		mDetector.cancelAnimator();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (isInEditMode()) {
			return;
		}
		mDetector.draw(canvas);
	}

	/**
	 * When the view performClick, we should ensure the background animation
	 * appears. So we will handle the action in mDetector;
	 * 
	 * @return
	 */
	@Override
	public boolean performClick() {
		return mDetector.handlePerformClick();
	}

	/**
	 * When the view performClick, we should ensure the background animation
	 * appears. So we will handle the action in mDetector;
	 * 
	 * @return
	 */
	@Override
	public boolean performLongClick() {
		return mDetector.handlePerformLongClick();
	}

	@Override
	public void performClickAfterAnimation() {
		super.performClick();
	}

	@Override
	public void performLongClickAfterAnimation() {
		super.performLongClick();
	}
}
