
# HMLibrary



## 配置 ##

kotlin config
![](http://ww1.sinaimg.cn/large/72f96cbagw1f7hxdqd213j20cc05pdg9.jpg)

build.gradle

    dependencies {
        compile 'com.github.bxcx:HMLibrary:v0.0.1'
    }
styles.xml

    <resources>

        <!-- Base application theme. -->
        <style name="AppTheme" parent="Base.Theme.AppCompat.Light.DarkActionBar">
            <!-- Customize your theme here. -->
            <item name="colorPrimary">@color/colorPrimary</item>
            <item name="colorPrimaryDark">@color/colorPrimaryDark</item>
            <item name="colorAccent">@color/colorAccent</item>
    
            <item name="android:textColorPrimary">@android:color/white</item>
    
            //背景透明，不设滑动关闭时背景就是黑的。
            <item name="android:windowIsTranslucent">true</item>
            //Activity右滑进出的动画，觉得这个不好看随便换成自己的
            <item name="android:windowAnimationStyle">@style/SlideRightAnimation</item>
    
            <item name="windowActionBar">false</item>
            <item name="windowNoTitle">true</item>
        </style>

    </resources>

AndroidManifest.xml

    <application 
        ... 
        tools:replace="android:icon,android:theme"
        .../>

## 使用方法 ##
