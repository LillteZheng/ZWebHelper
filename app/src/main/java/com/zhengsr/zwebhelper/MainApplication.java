package com.zhengsr.zwebhelper;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

/**
 * Created by zhengshaorui
 * Time on 2019/2/23
 */

public class MainApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
    }
}
