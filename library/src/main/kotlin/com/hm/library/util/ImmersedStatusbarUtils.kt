package com.hm.library.util

import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.os.Build
import android.view.View
import android.view.Window
import android.view.WindowManager

/**
 * ImmersedStatusbarUtils
 *
 *
 * himi on 2016-08-21 10:38
 * version V1.0
 */
object ImmersedStatusbarUtils {
    /**
     * 在[Activity.setContentView]之后调用

     * @param activity
     * *            要实现的沉浸式状态栏的Activity
     * *
     * @param titleViewGroup
     * *            头部控件的ViewGroup,若为null,整个界面将和状态栏重叠
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    fun initAfterSetContentView(activity: Activity?,
                                titleViewGroup: View?) {
        if (activity == null)
            return
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val window = activity.window
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }
        if (titleViewGroup == null)
            return
        // 设置头部控件ViewGroup的PaddingTop,防止界面与状态栏重叠
        val statusBarHeight = getStatusBarHeight(activity)
        titleViewGroup.setPadding(0, statusBarHeight, 0, 0)
    }

    /**
     * 获取状态栏高度

     * @param context
     * *
     * @return
     */
    private fun getStatusBarHeight(context: Context): Int {
        var result = 0
        val resourceId = context.resources.getIdentifier(
                "status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = context.resources.getDimensionPixelSize(resourceId)
        }
        return result
    }
}
