package com.ztj.douyu.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.ztj.douyu.main.App;

/**
 * Created by zhoutianjie on 2018/7/25.
 */

public class ScreenUtils {

    public static int[] getScreenSize(){
        WindowManager windowManager = (WindowManager) App.getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;//屏幕宽度，像素
        int height = dm.heightPixels;//屏幕高度，像素
        float density = dm.density; //屏幕密度

        int screenWidth = (int) (width/density);//屏幕宽度 dp
        int screenHeight = (int) (height/density);//屏幕高度 dp

        int[] size = new int[2];
        size[0] = screenWidth;
        size[1] = screenHeight;

        return size;
    }
}
