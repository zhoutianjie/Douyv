package com.ztj.douyu.main.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.ztj.douyu.R;
import com.ztj.douyu.main.App;
import com.ztj.douyu.main.presenter.PlayLivePresenter;
import com.ztj.douyu.main.service.FloatWindowService;
import com.ztj.douyu.main.view.onPlayLiveView;
import com.ztj.douyu.utils.ActivityUtils;
import com.ztj.douyu.utils.StringUtils;
import com.ztj.douyu.widgt.FloatWindow;
import com.ztj.douyu.widgt.media.IjkVideoView;

import java.lang.ref.WeakReference;

import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

/**
 *直播播放页面(普通游戏的非全屏的播放页面，颜值和全屏的播放页面实现后面再研究)
 */
public class PlayLiveUI extends AppCompatActivity implements onPlayLiveView {

    private static final int HIDE_VIEWS_MSG = 0;
    private String roomId;
    private IjkVideoView mVideoView;
    private PlayLivePresenter presenter;
    private ImageView mBackImg;
    private ImageView mFullScreenImg;
    private RelativeLayout mPlayRl;
    private boolean isViewsShow;
    private ControlHandler mHandler;
    private boolean isSupportFloatWindow = true;
    private boolean isBackPressed = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_live);

        initExtras();
        initComponet();
        initView();
        register();
    }


    private void initExtras() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle!=null){
            roomId = bundle.getString("roomId");
        }
    }

    private void initComponet() {
        mHandler = new ControlHandler(this);
        presenter = new PlayLivePresenter();
        presenter.attachView(this);
        presenter.getLiveRoomUrl(roomId);
    }

    private void initView() {

        mVideoView = findViewById(R.id.ijkplayView);
        mPlayRl = findViewById(R.id.play_rl);
        IjkMediaPlayer.loadLibrariesOnce(null);
        IjkMediaPlayer.native_profileBegin("libijkplayer.so");

        mBackImg = findViewById(R.id.back_iv);
        mFullScreenImg = findViewById(R.id.full_screen_iv);
//        showViews();
//        startAutoHide();

    }

    private void register() {

        mBackImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startFloatWindowService();
                finish();
            }
        });

        mFullScreenImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getRequestedOrientation()!= ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE){
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                }else{
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                }
            }
        });

        mPlayRl.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                showViews();
                startAutoHide();
                return true;
            }
        });

    }


    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onBackPressed() {
        isBackPressed = true;
        super.onBackPressed();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(isBackPressed || !mVideoView.isBackgroundPlayEnabled()){
            mVideoView.stopPlayback();
            mVideoView.release(true);
            mVideoView.stopBackgroundPlay();
        }else{
            mVideoView.enterBackground();
        }
        IjkMediaPlayer.native_profileEnd();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
        mHandler.removeCallbacksAndMessages(null);
    }

    @Override
    public void onGetLiveRoomUrlInfo(final String liveUrl) {
        if(StringUtils.isNull(liveUrl))return;

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //初始化播放器
                mVideoView.setVideoURI(Uri.parse(liveUrl));
                mVideoView.setOnPreparedListener(new IMediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(IMediaPlayer mp) {
                        mVideoView.start();
                    }
                });
            }
        });
    }

    @Override
    public void onGetLiveRoomUrlInfoFail(String message) {

    }


    //隐藏相关布局
    public void hideViews(){

        isViewsShow = false;

        AnimatorSet set = new AnimatorSet();
        set.playTogether(
                ObjectAnimator.ofFloat(mBackImg,"alpha",1f,0f),
                ObjectAnimator.ofFloat(mFullScreenImg,"alpha",1f,0f)
        );
        set.setDuration(1000).start();
        mBackImg.setVisibility(View.GONE);
        mFullScreenImg.setVisibility(View.GONE);

//        mBackImg.startAnimation(AnimationUtils.loadAnimation(this,R.anim.alpha_hide));
//        mBackImg.setVisibility(View.GONE);
//        mFullScreenImg.startAnimation(AnimationUtils.loadAnimation(this,R.anim.alpha_hide));
//        mFullScreenImg.setVisibility(View.GONE);
    }

    //显示相关布局
    public void showViews(){

        isViewsShow = true;

        AnimatorSet set = new AnimatorSet();
        set.playTogether(
                ObjectAnimator.ofFloat(mBackImg,"alpha",0f,1f),
                ObjectAnimator.ofFloat(mFullScreenImg,"alpha",0f,1f)
        );
        set.setDuration(1000).start();
        mBackImg.setVisibility(View.VISIBLE);
        mFullScreenImg.setVisibility(View.VISIBLE);
        if(getRequestedOrientation()!= ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE){
            mFullScreenImg.setImageResource(R.mipmap.ic_fullscreen_white_24dp);
        }else{
            mFullScreenImg.setImageResource(R.mipmap.ic_fullscreen_exit_white_24dp);
        }
//        mBackImg.startAnimation(AnimationUtils.loadAnimation(this,R.anim.alpha_show));
//        mBackImg.setVisibility(View.VISIBLE);
//        mFullScreenImg.startAnimation(AnimationUtils.loadAnimation(this,R.anim.alpha_show));
//        mFullScreenImg.setVisibility(View.VISIBLE);
    }

    private void startAutoHide(){
        if(isViewsShow){
            mHandler.removeMessages(HIDE_VIEWS_MSG);
            Message message = Message.obtain();
            message.what = HIDE_VIEWS_MSG;
            mHandler.sendMessageDelayed(message,3000);
        }
    }


    private void startFloatWindowService(){

        //是否开启支持悬浮窗,展示默认支持
        isSupportFloatWindow = true;
        if(!isSupportFloatWindow)return;
        //是否开启悬浮窗权限
        if(!ActivityUtils.hasPermissionFloatWin(App.getContext()))return;

        Intent intent = new Intent(PlayLiveUI.this,FloatWindowService.class);
        Bundle bundle = new Bundle();
        bundle.putString("url","");
        intent.putExtra(FloatWindowService.ACTION_PLAY,bundle);
        startService(intent);
    }

     static class ControlHandler extends Handler{
        private WeakReference<PlayLiveUI> weakReference;

         public ControlHandler(PlayLiveUI activity) {
             weakReference = new WeakReference<PlayLiveUI>(activity);
         }

         @Override
         public void handleMessage(Message msg) {
             PlayLiveUI activity = weakReference.get();
             if(activity!=null){
                 if(msg.what== HIDE_VIEWS_MSG){
                     activity.hideViews();
                 }
             }
         }
     }

}
