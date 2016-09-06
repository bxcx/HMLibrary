package com.hm.demo


import com.hm.demo.DemoBaseListFragment.DealListModel
import com.hm.library.base.BaseFragment
import com.hm.library.http.HMRequest
import com.hm.library.http.Method
import kotlinx.android.synthetic.main.fragment_request.*
import org.jetbrains.anko.onClick
import org.jetbrains.anko.support.v4.act


class RequestFragment(override var layoutResID: Int = R.layout.fragment_request) : BaseFragment() {

    //百度糯米API http://apistore.baidu.com/apiworks/servicedetail/508.html
    val url = "http://apis.baidu.com/baidunuomi/openapi/searchdeals"

    //{"errno":0,"msg":"success","data":{"total":75613,"deals":[{"deal_id":11265335,"image":"http:\/\/timg.baidu.com\/timg?lbstsm&ref=http%3a%2f%2fbj.nuomi.com&quality=100&size=8&sec=1473087273&di=46646edf3d4e67bacf8ad343fb76fc5d&src=http:\/\/e.hiphotos.baidu.com\/bainuo\/crop=27,0,1146,694;w=719;q=79\/sign=bd3cd194bf4543a9e154a08c2325bdae\/500fd9f9d72a60592b08ee7a2034349b033bbab1.jpg","tiny_image":"http:\/\/timg.baidu.com\/timg?lbstsm&ref=http%3a%2f%2fbj.nuomi.com&quality=100&size=8&sec=1473087273&di=c3f616e77661bca369f5a9d52705ff12&src=http:\/\/e.hiphotos.baidu.com\/bainuo\/crop=27,0,1146,694;w=230;q=79\/sign=b96079f98a26cffc7d65e5f284337dbc\/500fd9f9d72a60592b08ee7a2034349b033bbab1.jpg","title":"\u516d\u4e45\u89c6\u89c9","min_title":"\u516d\u4e45\u89c6\u89c9\u4e2a\u4eba\u5199\u771f","description":"\u671b\u4eac\u5e97\u4e2a\u4eba\u5199\u771f\uff01\u8282\u5047\u65e5\u901a\u7528\uff01\u63d0\u524d2\u5929\u9884\u7ea6\uff01","market_price":368800,"current_price":39900,"promotion_price":39900,"sale_num":789,"score":5,"comment_num":152,"publish_time":1458576000,"purchase_deadline":1498924799,"is_reservation_required":true,"distance":-1,"shop_num":3,"deal_url":"https:\/\/www.nuomi.com\/deal\/ahuslrkx.html","deal_murl":"https:\/\/m.nuomi.com\/deal\/view?tinyurl=ahuslrkx","shops":[{"shop_id":3219529,"longitude":116.476619,"latitude":39.999185,"distance":-1,"shop_url":"https:\/\/www.nuomi.com\/shop\/3219529","shop_murl":"https:\/\/m.nuomi.com\/merchant\/3219529"}]}]}}

    override fun initUI() {
        super.initUI()

        tv_url.text = "url:$url"

        btn_go.onClick {
            val params = HMRequest.params
            val header = HMRequest.header

            params.put("string", "value")
            params.put("int", 1)

            //url 接口地址
            //params 参数 选传 默认为空
            //method 请求方式 选传 默认为GET
            //header 请求头 选传 默认为空
            //activity 请求所在的Activity, 用于请求失败时给出Toast错误提示 选传 默认为空
            //cache 是否缓存到本地 选传 默认为默认为false
            //needCallBack 请求失败时是否执行回调 选传 默认为false

            HMRequest.go<DealListModel>(url = url, params = params, method = Method.GET, header = header, activity = act, cache = true, needCallBack = false) {
                //it即解析成功的实体类
//                showToast("返回数据共${it?.data?.size}条")
            }

            HMRequest.go<DealListModel>(url) {}
            HMRequest.go<DealListModel>(url, params) {}
            HMRequest.go<DealListModel>(url, params, cache = true) {}
        }
    }



}
