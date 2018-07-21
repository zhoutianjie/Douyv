package com.ztj.douyu.utils;

import android.view.Gravity;
import android.widget.Toast;

import com.ztj.douyu.main.App;

/**
 * Created by zhoutianjie on 2018/7/21.
 */

public class ToastObject {

    private Toast mToast;

    private volatile static ToastObject toastObject;

    public ToastObject() {
        mToast = Toast.makeText(App.getContext(),"",Toast.LENGTH_SHORT);
    }

    public static ToastObject getInstance(){
        if(toastObject==null){
            synchronized (ToastObject.class){
                if(toastObject==null){
                    toastObject = new ToastObject();
                }
            }
        }
        return toastObject;
    }

    public void show(String text){
        mToast.setText(text);
        mToast.setGravity(Gravity.CENTER,0,0);
        mToast.show();
    }
}
