package com.hm.library.util

import android.text.TextUtils

/**
 * StringUtil
 *
 * himi on 2016-07-28 18:33
 * version V1.0
 */
object StringUtil {

    fun isEmpty(vararg arr: String?): Boolean {
        var empty: Boolean = false
        for (str in arr) {
            if (TextUtils.isEmpty(str)) {
                empty = true
                break
            }
        }

        return empty
    }

}
