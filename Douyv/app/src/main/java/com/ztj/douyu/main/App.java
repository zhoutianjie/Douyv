package com.ztj.douyu.main;

import android.app.Application;

import com.raizlabs.android.dbflow.config.FlowManager;

/**
 * Created by zhoutianjie on 2018/7/5.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FlowManager.init(this);
    }
}
