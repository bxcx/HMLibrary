package com.hm.library.resource.tabindicator.internal;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.View;

import com.hm.library.R;


public abstract class TabViewBase extends View implements ITabView {

    /** 底部文本内容 */
    protected String mText;
    /** 底部文本大小 */
    protected int mTextSize;
    /** 选中颜色 */
    protected int mSelectedColor;
    /** 未选中颜色 */
    protected int mUnselectedColor;
    /** 限制绘制icon的范围 */
    protected Rect mIconRect;
    /** 限制绘制指示点的范围 */
    private Rect mIndicatorRect;
    /** 是否显示指示点 */
    private boolean isIndicateDisplay;
    /** 指示点大小 */
    private int mIndicatorSize;

    protected boolean isSelected;

    protected Paint mTextPaint = new Paint();
    protected Rect mTextBound = new Rect();
    Bitmap mIconBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.update_hint);

    public TabViewBase(Context context) {
        super(context);
    }

    public TabViewBase(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // 得到绘制icon的宽
        int bitmapWidth = Math.min(getMeasuredWidth() - getPaddingLeft()
                - getPaddingRight(), getMeasuredHeight() - getPaddingTop()
                - getPaddingBottom() - mTextBound.height());

        int left = getMeasuredWidth() / 2 - bitmapWidth / 2;
        int top = (getMeasuredHeight() - mTextBound.height()) / 2 - bitmapWidth / 2;
        // 设置icon的绘制范围
        mIconRect = new Rect(left, top, left + bitmapWidth, top + bitmapWidth);
        // 设置指示点的范围
//        mIndicatorRect = new Rect(left + bitmapWidth * 2/3, top, left+bitmapWidth, top + bitmapWidth /3);
        int indicatorRadius = mIndicatorSize / 2;
        int tabRealHeight = bitmapWidth + mTextBound.height();
        mIndicatorRect = new Rect(left + tabRealHeight* 4/5 - indicatorRadius, top, left+tabRealHeight* 4/5 + indicatorRadius, top + mIndicatorSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(null != mText) {
            drawTargetText(canvas);
        }
    }

    private void drawTargetText(Canvas canvas) {
        mTextPaint.setColor(isSelected ? mSelectedColor : mUnselectedColor);
        canvas.drawText(mText, mIconRect.left + mIconRect.width() / 2
                        - mTextBound.width() / 2,
                mIconRect.bottom + mTextBound.height(), mTextPaint);
    }

    /**
     * 画指示点
     * @param canvas
     */
    protected void drawIndicator(Canvas canvas) {
        if(isIndicateDisplay) {
            canvas.drawBitmap(mIconBitmap, null, mIndicatorRect, null);
        }
    }

    @Override
    public void setText(int id) {
        setText(getContext().getResources().getText(id));
        measureText();
    }

    @Override
    public void setText(CharSequence text) {
        this.mText = (String) text;
        measureText();
    }

    @Override
    public void setSelectedColor(int selectedColor) {
        this.mSelectedColor = selectedColor;
        invalidateView();
    }

    @Override
    public void setUnselectedColor(int unselectedColor) {
        this.mUnselectedColor = unselectedColor;
        invalidateView();
    }

    @Override
    public void setTextSize(int textSize) {
        this.mTextSize = textSize;
        mTextPaint.setTextSize(mTextSize);
        measureText();
    }

    @Override
    public void setIndicatorSize(int indicatorSize) {
        this.mIndicatorSize = indicatorSize;
    }

    @Override
    public abstract void setSelected(boolean selected);

    /**
     * 设置切换颜色渐变
     * @param alpha
     */
    protected void setIconAlpha(float alpha){}

    /**
     * 设置指示点的显示
     *
     * @param visible
     *            是否显示，如果false，则都不显示
     */
    public void setIndicateDisplay(boolean visible) {
        this.isIndicateDisplay = visible;
        invalidateView();
    }

    // 重新测量文本绘制范围
    private void measureText(){
        mTextPaint.getTextBounds(mText, 0, mText.length(), mTextBound);
    }

    protected void invalidateView() {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            invalidate();
        } else {
            postInvalidate();
        }
    }
}
