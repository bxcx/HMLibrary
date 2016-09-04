package com.hm.library.http

/**
 * Created by himi on 16/3/1.
 */
enum class Method {
    OPTIONS, GET, HEAD, POST, PUT, PATCH, DELETE, TRACE, CONNECT
}

object HttpResult {
    var OK = 0
}