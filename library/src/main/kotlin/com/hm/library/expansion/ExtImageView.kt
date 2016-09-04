package com.hm.library.expansion

import android.text.TextUtils
import android.widget.ImageView
import com.hm.library.util.ImageUtil
import com.nostra13.universalimageloader.core.DisplayImageOptions

/**
 * ExtImageView
 *
 * himi on 2016-03-25 09:14
 * version V1.0
 */
fun ImageView.show(imageUrl: String?, options: DisplayImageOptions? = null) {
    if (TextUtils.isEmpty(imageUrl))// || imageUrl!!.contains("null"))
        return

    ImageUtil.displayImage(imageUrl!!, this, options)
}