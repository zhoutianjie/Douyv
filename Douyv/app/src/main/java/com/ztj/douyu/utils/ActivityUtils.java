package com.ztj.douyu.utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;


/**
 * Created by zhoutianjie on 2018/7/23.
 */

public class ActivityUtils {

    public static void openActivity(Activity activity, Class<?> pClass, Bundle pBundle){
        if(activity == null) return;
        Intent intent = new Intent(activity,pClass);
        if(pBundle!=null){
            intent.putExtras(pBundle);
        }
        activity.startActivity(intent);
    }
}
