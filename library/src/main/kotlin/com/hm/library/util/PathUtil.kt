package com.hm.library.util

import android.os.Environment

/**
 * PathUtil
 *
 * himi on 2016-03-17 14:40
 * version V1.0
 */
/**
 * 标识各种路径
 * <p/>
 * himi on 2015-09-15 15:22
 * version V1.0
 */
object PathUtil {

    val PHOTOCACHEPIC = "HMLibrary/"

    /**
     * 获取内置SD卡路径
     * @return
     */
    fun SDCardPath(): String {
        return Environment.getExternalStorageDirectory().absolutePath
    }
}
