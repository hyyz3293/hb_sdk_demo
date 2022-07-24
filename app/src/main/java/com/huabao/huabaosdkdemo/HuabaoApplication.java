package com.huabao.huabaosdkdemo;

import android.app.Application;
import android.os.Environment;

import cn.baos.watch.sdk.BaosWatchSdk;


public class HuabaoApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化SDK
        BaosWatchSdk.initSdk(this);
        BaosWatchSdk.setLogEnable(true);
        BaosWatchSdk.setLogDir(this.getCacheDir().getAbsolutePath());
    }

}
