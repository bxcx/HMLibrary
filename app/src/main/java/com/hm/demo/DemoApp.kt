package com.hm.demo

import com.hm.library.app.HMApp
import com.hm.library.http.HMModel
import com.hm.library.http.HMRequest
import java.util.*

/**
 * DemoApp
 *
 * himi on 2016-09-05 20:55
 * version V1.0
 */
class DemoApp : HMApp() {

    override fun onCreate() {
        super.onCreate()

        HMRequest.params = createParams
        HMRequest.header = createHeader
    }


    val createParams: HashMap<String, Any>
        get() {
            var params: HashMap<String, Any> = HashMap<String, Any>()
//            if (USER != null) {
//                params.updateValue(USER?.sessionId!, forKey: "sessionId")
//                params.updateValue(USER?.personalId!, forKey: "personalId")
//            }
            return params
        }
    val createHeader: HashMap<String, String>
        get() {
            var header: HashMap<String, String> = HashMap()
            header.put("apikey", "be910c69ec688ba099d0091e19c21033")
            return header
        }

    open class BaseModel(var errno: Int = -1, var msg: String) : HMModel() {

        override var valid: Boolean = false
            get() = errno == 0

        override var message: String = ""
            get() = msg

    }
}