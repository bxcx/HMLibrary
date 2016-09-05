# README

tags： HMLibrary


## 配置 ##

1. kotlin config   
![](https://github.com/bxcx/HMLibrary/blob/master/config%20kotlin.jpg)

2. build.gradle

        dependencies {
            compile 'com.github.bxcx:HMLibrary:v0.0.1'
        }


3. Application/AndroidManifest

        <application android:name="com.hm.library.app.HMApp" .../>
        
    或

        class DemoApp : HMApp() {}
        <application android:name="your.package.DemoApp" .../>

