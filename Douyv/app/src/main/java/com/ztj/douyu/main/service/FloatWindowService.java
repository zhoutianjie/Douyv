package com.ztj.douyu.main.service;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.ztj.douyu.widgt.FloatWindow;

public class FloatWindowService extends Service {

    public static boolean mIsFloatWindowShown = false;
    public static final String ACTION_EXIT = "action_exit";
    public static final String ACTION_PLAY = "action_play";

    private FloatWindow mFloatWindow;

    public FloatWindowService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("FloatService","onCreate");
        mFloatWindow = new FloatWindow(this);
        mFloatWindow.createFloatView();
        mIsFloatWindowShown = true;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("FloatService","onStartCommand");
        if(intent==null || intent.hasExtra(ACTION_EXIT)){
            stopSelf();
        }else{
            Bundle bundle = intent.getBundleExtra(ACTION_PLAY);
            if(bundle!=null && mFloatWindow!=null){
                String url = bundle.getString("url");
                Log.e("FloatService",url);
                mFloatWindow.play(url);
            }
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("FloatService","onDestroy");
        if(mFloatWindow!=null){
            mFloatWindow.detory();
        }
        mIsFloatWindowShown = false;
    }

}
