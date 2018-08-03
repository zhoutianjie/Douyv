package com.ztj.douyu.main.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.ztj.douyu.R;
import com.ztj.douyu.bean.MessageEvent;
import com.ztj.douyu.main.presenter.PlayLivePresenter;
import com.ztj.douyu.main.view.onPlayLiveView;
import com.ztj.douyu.utils.StringUtils;
import com.ztj.douyu.widgt.media.IjkVideoView;

import org.greenrobot.eventbus.EventBus;

import java.lang.ref.WeakReference;

import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

/**
 *直播播放页面(普通游戏的非全屏的播放页面，颜值和全屏的播放页面实现后面再研究)
 */
public class HorizontalPlayLiveUI extends AppCompatActivity implements onPlayLiveView {

    private static final int HIDE_VIEWS_MSG = 0;
    private String roomId;
    private IjkVideoView mVideoView;
    private PlayLivePresenter presenter;
    private ImageView mBackImg;
    private ImageView mFullScreenImg;
    private RelativeLayout mPlayRl;
    private boolean isViewsShow;
    private ControlHandler mHandler;
    private boolean isBackPressed = false;
    private String mPlayUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
        setContentView(R.layout.activity_live);

        initExtras();
        initView();
        initComponet();
        register();
    }


    private void initExtras() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle!=null){
            roomId = bundle.getString("roomId");
            mPlayUrl = bundle.getString("play_url");
        }
    }

    private void initComponet() {
        mHandler = new ControlHandler(this);
        presenter = new PlayLivePresenter();
        presenter.attachView(this);
        if(!StringUtils.isNull(roomId)){
            presenter.getLiveRoomUrl(roomId);
        }else if(!StringUtils.isNull(mPlayUrl)){
            mVideoView.setVideoURI(Uri.parse(mPlayUrl));
            mVideoView.setOnPreparedListener(new IMediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(IMediaPlayer mp) {
                    mVideoView.start();
                }
            });
        }
    }

    private void initView() {

        mVideoView = findViewById(R.id.ijkplayView);
        mPlayRl = findViewById(R.id.play_rl);
        IjkMediaPlayer.loadLibrariesOnce(null);
        IjkMediaPlayer.native_profileBegin("libijkplayer.so");

        mBackImg = findViewById(R.id.back_iv);
        mFullScreenImg = findViewById(R.id.full_screen_iv);
    }

    private void register() {

        mBackImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBusSendMessageEvent();
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
        EventBusSendMessageEvent();
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
        mPlayUrl = liveUrl;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //初始化播放器
//                mVideoView.setRender(IjkVideoView.RENDER_TEXTURE_VIEW);
                mVideoView.setVideoURI(Uri.parse(liveUrl));
                mVideoView.setOnPreparedListener(new IMediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(IMediaPlayer mp) {
                        Log.e("onPrepared","start");
                        mVideoView.start();
                    }
                });
                mVideoView.setOnInfoListener(new IMediaPlayer.OnInfoListener() {
                    @Override
                    public boolean onInfo(IMediaPlayer mp, int what, int extra) {
                        Log.e("onInfo",""+what);
                        return false;
                    }
                });
                mVideoView.setOnErrorListener(new IMediaPlayer.OnErrorListener() {
                    @Override
                    public boolean onError(IMediaPlayer mp, int what, int extra) {
                        Log.e("onError",""+what);
                        return false;
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
        set.setDuration(500).start();
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

    //启动主界面的悬浮框
    private void EventBusSendMessageEvent(){
        MessageEvent messageEvent = new MessageEvent();
        messageEvent.setUrl(mPlayUrl);
        EventBus.getDefault().post(messageEvent);
    }


     static class ControlHandler extends Handler{
        private WeakReference<HorizontalPlayLiveUI> weakReference;

         public ControlHandler(HorizontalPlayLiveUI activity) {
             weakReference = new WeakReference<HorizontalPlayLiveUI>(activity);
         }

         @Override
         public void handleMessage(Message msg) {
             HorizontalPlayLiveUI activity = weakReference.get();
             if(activity!=null){
                 if(msg.what== HIDE_VIEWS_MSG){
                     activity.hideViews();
                 }
             }
         }
     }

}
