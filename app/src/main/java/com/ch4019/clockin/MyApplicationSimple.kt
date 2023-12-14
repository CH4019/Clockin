package com.ch4019.clockin

import android.app.Application
import cn.leancloud.LCLogger
import cn.leancloud.LeanCloud

//  请修改AndroidManifest.xml中的application部分的android:name
//  请使用自己的leanCloud的appID和appKey以及serverURL来配置数据库
class MyApplicationSimple: Application() {
    override fun onCreate() {
        super.onCreate()
        LeanCloud.setLogLevel(LCLogger.Level.DEBUG)
        LeanCloud.initialize(
            this,
            "appId",
            "appKey",
            "serverURL"
        )
    }
}