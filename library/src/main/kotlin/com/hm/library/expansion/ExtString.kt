package com.hm.library.expansion

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
