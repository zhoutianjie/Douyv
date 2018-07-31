package com.ztj.douyu.widgt;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.ztj.douyu.R;
import com.ztj.douyu.widgt.media.IjkVideoView;

/**
 * Created by zhoutianjie on 2018/7/31.
 */

public class FloatWindowView extends FrameLayout {

    private View rootView;
    private IjkVideoView mIjkVideoView;
    private GestureDetector mGestureDetector;



    private OnDoubleClickListener listener;
    public interface OnDoubleClickListener{
        void OnDoubleClick();
    }

    public FloatWindowView(@NonNull Context context) {
        super(context);
        initView(context);
    }

    public FloatWindowView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public FloatWindowView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }


    private void initView(Context context) {
        rootView = LayoutInflater.from(context).inflate(R.layout.window_play,this);
        mIjkVideoView = findViewById(R.id.ijkplay_window);
        mGestureDetector = new GestureDetector(new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                if(listener!=null){
                    listener.OnDoubleClick();
                }
                return super.onDoubleTap(e);
            }
        });

    }

    public void setDoubleClickListener(OnDoubleClickListener listener) {
        this.listener = listener;
    }

    public IjkVideoView getmIjkVideoView(){
        return mIjkVideoView;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }

}
