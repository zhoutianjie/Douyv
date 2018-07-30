package com.ztj.douyu.widgt;

import android.app.Service;
import android.content.Context;
import android.graphics.PixelFormat;
import android.util.Log;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.widget.ImageButton;

import com.ztj.douyu.R;
import com.ztj.douyu.main.activity.MainActivity;
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

    private int mTouchStartX;
    private int mTouchStartY;

    private int mTouchCurrentX;
    private int mTouchCurrentY;

    private int mStartX;
    private int mStartY;

    private int mStopX;
    private int mStopY;

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
        wmParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL|WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;

        //为什么设置Gravity.END 以后水平方向就不能滑动悬浮框?
        wmParams.gravity = Gravity.CENTER_VERTICAL|Gravity.START;

        int[] size = SystemUtils.getScreenSize();

        wmParams.width = size[0]/3;
        wmParams.height = size[0]/9*2;


        LayoutInflater inflater = LayoutInflater.from(mContext);
        mFloatLayout = inflater.inflate(R.layout.window_play,null);
        mWindowManager.addView(mFloatLayout,wmParams);

        mFloatLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        mTouchStartX = (int) event.getRawX();
                        mTouchStartY = (int) event.getRawY();

                        mStartX = (int) event.getX();
                        mStartY = (int) event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        mTouchCurrentX = (int) event.getRawX();
                        mTouchCurrentY = (int) event.getRawY();
                        wmParams.x+= mTouchCurrentX-mTouchStartX;
                        wmParams.y+= mTouchCurrentY-mTouchStartY;

                        mWindowManager.updateViewLayout(mFloatLayout,wmParams);

                        mTouchStartX = mTouchCurrentX;
                        mTouchStartY = mTouchCurrentY;

                        break;
                    case MotionEvent.ACTION_UP:
                        mStopX = (int) event.getX();
                        mStopY = (int) event.getY();
                        int mTouchSlop = ViewConfiguration.get(mContext).getScaledTouchSlop();
                        if(Math.abs(mStartX-mStopX)>mTouchSlop || Math.abs(mStartY-mStopY)>mTouchSlop){
                            return true;
                        }
                        break;
                }

                return false;
            }
        });

        mFloatLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //取消小窗口播放，跳转到PlayUI界面
            }
        });

       mFloatLayout.setOnLongClickListener(new View.OnLongClickListener() {
           @Override
           public boolean onLongClick(View v) {
               mHostService.stopSelf();
               return false;
           }
       });


    }

    //播放相关
    public void play(String url){

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
