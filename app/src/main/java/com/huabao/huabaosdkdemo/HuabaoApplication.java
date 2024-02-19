package com.huabao.huabaosdkdemo;

import android.app.Application;
import android.os.Environment;
import android.widget.Toast;

import cn.baos.watch.sdk.BaosWatchSdk;
import cn.baos.watch.sdk.bluetooth.bt.CbtManager;
import cn.baos.watch.sdk.utils.LogUtil;
import cn.baos.watch.sdk.utils.SharePreferenceUtils;


public class HuabaoApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化SDK
        BaosWatchSdk.initSdk(this);
        BaosWatchSdk.setLogEnable(true);
        BaosWatchSdk.setLogDir(getExternalFilesDir("HBSDK").getAbsolutePath());

    }

}
