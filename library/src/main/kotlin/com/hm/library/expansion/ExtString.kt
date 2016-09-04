package com.hm.library.expansion

/**
 * ExtString
 *
 * himi on 2016-03-25 14:47
 * version V1.0
 */
var String.toZN : CharSequence
    get() = this.replace(",","，")
    set(str) {}