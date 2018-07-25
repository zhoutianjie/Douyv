package com.ztj.douyu.utils;

import android.app.Activity;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import java.lang.reflect.Method;


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

    public static boolean hasPermissionFloatWin(Context context){
        Log.e("TAG","hasAuthorFloatWin SDK INT"+ Build.VERSION.SDK_INT);
        if(Build.VERSION.SDK_INT<19){
            return true;
        }
        try{
            AppOpsManager appOpsManager = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
            Class c = appOpsManager.getClass();
            Class[] cArg = new Class[3];
            cArg[0] = int.class;
            cArg[1] = int.class;
            cArg[2] = String.class;
            Method lMethod = c.getDeclaredMethod("checkOp",cArg);
            return (AppOpsManager.MODE_ALLOWED ==(Integer)lMethod.invoke(appOpsManager,24, Binder.getCallingUid(),context.getPackageName()));
        }catch (Exception e){
            return false;
        }

    }
}
