package com.hm.library.expansion

import android.text.Editable
import java.util.regex.Pattern

/**
 * ExtString
 *
 * himi on 2016-03-25 14:47
 * version V1.0
 */
val String.toZN: CharSequence
    get() = this.replace(",", "，")

val String.isEmail: Boolean
    get() {
        val check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$"
        val regex = Pattern.compile(check)
        val matcher = regex.matcher(this)
        return matcher.matches()
    }

fun String.length(min: Int, max: Int): Boolean
        = this.length >= min && this.length <= max

val Editable.isEmail: Boolean
    get() = this.toString().isEmail

fun Editable.length(min: Int, max: Int): Boolean
        = this.toString().length(min, max)