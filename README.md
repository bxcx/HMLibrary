# README

tags： HMLibrary


## Configuration ##

1. kotlin config   
![](https://github.com/bxcx/HMLibrary/blob/master/config%20kotlin.jpg)

2. build.gradle

        dependencies {
            compile 'com.github.bxcx:HMLibrary:v0.0.1'
        }


3. Application/AndroidManifest

        <application android:name="com.hm.library.app.HMApp" .../>
        
    or

        class DemoApp : HMApp() {}
        <application android:name="your.package.DemoApp" .../>

## Usage ##
 - [HMRequest][3]

        HMRequest.go<DealListModel>(url = url, params = params, method = Method.GET, headers = headers, activity = act, cache = true, needCallBack = false) {
            //it即解析成功的实体类
            showToast("返回数据共${it?.data?.size}条")
        }
        
        //可选传参数
        HMRequest.go<DealListModel>(url) {}
        HMRequest.go<DealListModel>(url, params) {}
        HMRequest.go<DealListModel>(url, params, cache = true) {}
 - [BaseMainActivity][1]   
![](https://github.com/bxcx/HMLibrary/blob/master/md/baseMainActivity.gif)   
 - [BaseActivity][2]   
![](https://github.com/bxcx/HMLibrary/blob/master/md/baseActivity.gif)   




  [1]: https://github.com/bxcx/HMLibrary/blob/master/md/BaseMainActivity.md
  [2]: https://github.com/bxcx/HMLibrary/blob/master/md/BaseActivity.md
  [3]: https://github.com/bxcx/HMLibrary/blob/master/md/HMRequest.md
