package com.hm.library.resource.tabindicator.internal;

public interface ITabView {
    /**
     * 设置文字
     * @param id
     */
    void setText(int id);

    /**
     * 设置文字
     * @param text
     */
    void setText(CharSequence text);

    /**
     * 设置选中时颜色
     * @param selectedColor
     */
    void setSelectedColor(int selectedColor);

    /**
     * 设置未选中时颜色
     * @param unselectedColor
     */
    void setUnselectedColor(int unselectedColor);

    /**
     * 设置文本大小
     * @param textSize
     */
    void setTextSize(int textSize);

    /**
     * 设置指示点大小
     * @param indicatorSize
     */
    void setIndicatorSize(int indicatorSize);

    /**
     * 设置是否选中
     * @param isSelected
     */
    void setSelected(boolean isSelected);
}
