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
        //初始化通用缓存
        Cacher.init(this)

        //初始化图片加载
        val config = ImageLoaderConfiguration.Builder(this).threadPriority(Thread.NORM_PRIORITY - 2).denyCacheImageMultipleSizesInMemory().discCacheSize(50 * 1024 * 1024)
                //.diskCache(UnlimitedDiskCache(File(PathUtil.PHOTOCACHEPIC))).discCacheFileNameGenerator(Md5FileNameGenerator())
                .writeDebugLogs().tasksProcessingOrder(QueueProcessingType.LIFO).build()
        ImageLoader.getInstance().init(config)

        //初始化日志输出
        Logger.init(packageName)                 // default PRETTYLOGGER or use just init()
                .setMethodCount(2)                 // default 2
                .hideThreadInfo()               // default shown
                .setLogLevel(LogLevel.FULL)        // default LogLevel.FULL
                .setMethodOffset(0)                // default 0

        //设置网络请求超时时限
        OkHttpUtils.getInstance().setConnectTimeout(10, TimeUnit.SECONDS)
    }
}
