package com.hm.library.util

import android.view.View

import java.lang.reflect.Field

/**
 * ViewBindUtil
 *
 *
 * himi on 2016-09-04 10:48
 * version V1.0
 */
object ViewBindUtil {
    fun bindViews(`object`: Any?, footView: View) {
        if (`object` == null) {
            return
        }
        //获取对象中所有属性-不包含父类私有成员
        val fields = getFields(`object`)
        for (fi in fields) {
            //判断属性是否继承自view
            if (View::class.java.isAssignableFrom(fi.type)) {
                //根据属性名获取id
                val id = footView.resources.getIdentifier(fi.name, "id", footView.context.packageName)
                if (id > 0) {
                    try {
                        //查找到id时绑定控件到对应属性上
                        fi.set(`object`, footView.findViewById(id))
                    } catch (e: IllegalAccessException) {
                        e.printStackTrace()
                    }

                }
            }
        }
    }

    fun getFields(o: Any): Array<Field> {
        val f1 = o.javaClass.declaredFields
        // 设置不检查访问
        for (i in f1.indices) {
            f1[i].isAccessible = true
        }
        return f1
    }
}
