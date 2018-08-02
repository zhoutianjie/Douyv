package com.ztj.douyu.main.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.ztj.douyu.R;
import com.ztj.douyu.main.presenter.PlayLivePresenter;
import com.ztj.douyu.main.view.onPlayLiveView;
import com.ztj.douyu.widgt.VerticalViewPager;

import tv.danmaku.ijk.media.player.IjkMediaPlayer;

/**
 * 构想：布局是一个垂直滑动的viewPager，初始显示的是一个竖图，同时没有播放前显示的是模糊的照片
 * Created by zhoutianjie on 2018/8/1.
 */

public class VerticalPlayLiveUI extends AppCompatActivity{


    private VerticalViewPager verticalViewPager;

    private String roomId;
    private String mPlayUrl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
        setContentView(R.layout.activity_vertical_play);
        initExtras();
        initComponet();
        initView();
        registerListeners();

    }

    private void initExtras(){
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle!=null){
            roomId = bundle.getString("roomId");
            mPlayUrl = bundle.getString("play_url");
        }
    }

    private void initComponet(){
        IjkMediaPlayer.loadLibrariesOnce(null);
        IjkMediaPlayer.native_profileBegin("libijkplayer.so");
    }

    private void initView(){
        verticalViewPager = findViewById(R.id.vertical_play_vp);

    }

    private void registerListeners(){

    }




}
