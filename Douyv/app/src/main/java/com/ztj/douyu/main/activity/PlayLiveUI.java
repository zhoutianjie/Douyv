package com.ztj.douyu.main.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ztj.douyu.R;
import com.ztj.douyu.main.presenter.PlayLivePresenter;
import com.ztj.douyu.main.view.onPlayLiveView;
import com.ztj.douyu.utils.StringUtils;
import com.ztj.douyu.widgt.media.IjkVideoView;

import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

/**
 *直播播放页面(普通游戏的非全屏的播放页面，颜值和全屏的播放页面实现后面再研究)
 */
public class PlayLiveUI extends AppCompatActivity implements onPlayLiveView {

    private String roomId;
    private IjkVideoView mVideoView;
    private PlayLivePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        presenter = new PlayLivePresenter();
        presenter.attachView(this);
        presenter.getLiveRoomUrl(roomId);
    }

    private void initView() {
        mVideoView = findViewById(R.id.ijkplayView);
        IjkMediaPlayer.loadLibrariesOnce(null);
        IjkMediaPlayer.native_profileBegin("libijkplayer.so");
    }

    private void register() {

    }


    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
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
}
