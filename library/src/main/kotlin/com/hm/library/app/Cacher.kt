package com.hm.library.app

import android.content.Context
import com.orhanobut.hawk.Hawk
import com.orhanobut.hawk.HawkBuilder
import com.orhanobut.hawk.LogLevel

/**
 * Created by himi on 16/2/28.
 */
object Cacher {
    fun init(context: Context) {
        Hawk.init(context)
                .setEncryptionMethod(HawkBuilder.EncryptionMethod.MEDIUM)
                .setStorage(HawkBuilder.newSqliteStorage(context))
                .setLogLevel(LogLevel.FULL)
                .build();
    }

    operator fun set(key: String, obj: Any?) {
        if (obj != null) {
            Hawk.put(key, obj)
        } else {
            Hawk.remove(key)
        }
    }

    operator fun <T> get(key: String): T? = Hawk.get(key)
    fun getString(key: String): String? = Hawk.get(key)

    fun clear() = Hawk.clear()
}