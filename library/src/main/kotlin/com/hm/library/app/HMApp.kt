package com.hm.library.app

import android.app.Application
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration
import com.nostra13.universalimageloader.core.assist.QueueProcessingType
import com.orhanobut.logger.LogLevel
import com.orhanobut.logger.Logger
import com.zhy.http.okhttp.OkHttpUtils
import java.util.concurrent.TimeUnit

/**
 * Created by himi on 16/2/25.
 */
open class HMApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Cacher.init(this)


        val config = ImageLoaderConfiguration.Builder(this).threadPriority(Thread.NORM_PRIORITY - 2).denyCacheImageMultipleSizesInMemory().discCacheSize(50 * 1024 * 1024)
                //.diskCache(UnlimitedDiskCache(File(PathUtil.PHOTOCACHEPIC))).discCacheFileNameGenerator(Md5FileNameGenerator())
                .writeDebugLogs().tasksProcessingOrder(QueueProcessingType.LIFO).build()
        ImageLoader.getInstance().init(config)

        Logger.init(packageName)                 // default PRETTYLOGGER or use just init()
                .setMethodCount(3)                 // default 2
                .hideThreadInfo()               // default shown
                .setLogLevel(LogLevel.FULL)        // default LogLevel.FULL
                .setMethodOffset(2)                // default 0

        OkHttpUtils.getInstance().setConnectTimeout(2000, TimeUnit.SECONDS)
    }
}
