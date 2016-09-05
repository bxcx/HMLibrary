# HMRequest

tags： HMLibrary

网络请求类

 1. 分析接口返回的结构   
    这里用[百度糯米](http://apistore.baidu.com/apiworks/servicedetail/508.html)的API来演示

        //接口地址 
        val url = "http://apis.baidu.com/baidunuomi/openapi/searchdeals"
        
        //返回json格式
        {"errno":0,"msg":"success","data":{}}
        
    可以看出是根据errno来判断数据的正确性, msg为相应的提示

 2. 创建BaseModel   
    需要继承**HMModel**, 并重写 *valid:Boolean* 和 *message:String* 两个属性

        open class BaseModel(var errno: Int, var msg: String) : HMModel() {
            //数据是否正确
            override var valid: Boolean = false
                get() = errno == 0
            //相应提示
            override var message: String = ""
                get() = msg
        }

 3. 创建数据实体类
 
        class DealListModel(errno: Int, error: String) : BaseModel(errno, error) {

            var data: ArrayList<DealModel>? = null
    
            //字段个数可以与json中不同, 可以多, 也可以少, 满足需求即可
            class DealModel(var image: String, var title: String, var description: String)
        }
        

 4. 接口交互
 
    - 参数
        
            //url 接口地址
            //params 参数 选传 默认为空
            //method 请求方式 选传 默认为GET
            //headers 请求头 选传 默认为空
            //activity 请求所在的Activity, 用于请求失败时给出Toast错误提示 选传 默认为空
            //cache 是否缓存到本地 选传 默认为默认为false
            //needCallBack 请求失败时是否执行回调 选传 默认为false
    - 示例
        
            HMRequest.go<DealListModel>(url = url, params = params, method = Method.GET, headers = headers, activity = act, cache = true, needCallBack = false) {
                //it即解析成功的实体类
                showToast("返回数据共${it?.data?.size}条")
            }

            //可选传参数
            HMRequest.go<DealListModel>(url) {}
            HMRequest.go<DealListModel>(url, params) {}
            HMRequest.go<DealListModel>(url, params, cache = true) {}

 

