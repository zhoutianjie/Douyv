package com.ztj.douyu.widgt;

import android.app.Service;
import android.content.Context;
import android.graphics.PixelFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;

import com.ztj.douyu.R;
import com.ztj.douyu.utils.SystemUtils;

/**
 * Created by zhoutianjie on 2018/7/25.
 */

public class FloatWindow {

    private Service mHostService;
    private Context mContext;
    private WindowManager.LayoutParams wmParams;
    private WindowManager mWindowManager;
    private View mFloatLayout;

    public FloatWindow(Service mHostService) {
        this.mHostService = mHostService;
        mContext = mHostService.getApplicationContext();
    }

    public void createFloatView(){
        Log.e("FloatWindow","createFloatView");
        wmParams = new WindowManager.LayoutParams();
        mWindowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        //TYPE_SYSTEM_OVERLAY 出现无法点击响应的情况
        //wmParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY;
//        wmParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR;
        wmParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        wmParams.format = PixelFormat.RGBA_8888;
        //悬浮窗自己处理点击事件
        wmParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;

        wmParams.gravity = Gravity.RIGHT|Gravity.CENTER_VERTICAL;

        int[] size = SystemUtils.getScreenSize();

        wmParams.width = size[0]/3;
        wmParams.height = (wmParams.width/4)*3;

        LayoutInflater inflater = LayoutInflater.from(mContext);
        mFloatLayout = inflater.inflate(R.layout.window_play,null);
        mWindowManager.addView(mFloatLayout,wmParams);


        //设置点击事件相关
        ImageButton imageButton = mFloatLayout.findViewById(R.id.play_close);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("FloatWindow","onClick");
                mHostService.stopSelf();
            }
        });

    }

    //播放相关
    public void play(){

    }

    //关闭悬浮窗相关
    public void remove(){

    }

    public void detory(){
        Log.e("FloatWindow","detory");
        //关闭ijkplayer
        if(mFloatLayout!=null){
            mWindowManager.removeView(mFloatLayout);
        }
    }
}
