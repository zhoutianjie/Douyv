package com.ztj.douyu.main.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.ztj.douyu.R;
import com.ztj.douyu.bean.RoomInfo;
import com.ztj.douyu.bean.constant.DouYvUrl;
import com.ztj.douyu.main.adapter.VerticalOpenPagerAdapter;
import com.ztj.douyu.main.presenter.PlayLivePresenter;
import com.ztj.douyu.main.view.onPlayLiveView;
import com.ztj.douyu.utils.DataHolder;
import com.ztj.douyu.utils.StringUtils;
import com.ztj.douyu.widgt.VerticalViewPager;

import java.util.ArrayList;
import java.util.List;

import tv.danmaku.ijk.media.player.IjkMediaPlayer;

/**
 * 构想：布局是一个垂直滑动的viewPager，初始显示的是一个竖图，同时没有播放前显示的是模糊的照片
 * Created by zhoutianjie on 2018/8/1.
 */

public class VerticalPlayLiveUI extends AppCompatActivity{


    private VerticalViewPager verticalViewPager;
    private List<RoomInfo> roomInfos;
    private VerticalOpenPagerAdapter adapter;

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
        roomInfos = (List<RoomInfo>) DataHolder.getInstance().acquire("room_info_list");
        List<RoomInfo> result = GetVerticalYZinfos(roomInfos);
        if (roomInfos==null || roomInfos.size()==0)return;
        adapter = new VerticalOpenPagerAdapter(getSupportFragmentManager(),result);
        verticalViewPager.setAdapter(adapter);
        //定位到当前的item 根据roomId对应的position获得
        verticalViewPager.setCurrentItem(GetPositionByRoomId(roomId,result));

    }

    private void registerListeners(){
        verticalViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    @Override
    protected void onStop() {
        super.onStop();
        IjkMediaPlayer.native_profileEnd();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    //确保所有颜值频道的播放格式都是竖屏播放
    private List<RoomInfo> GetVerticalYZinfos(List<RoomInfo> infos){
        List<RoomInfo> result = new ArrayList<>();
        for(RoomInfo info:infos){
            if(info.getIsVertical()==DouYvUrl.vertical_screen_play){
                result.add(info);
            }
        }
        return result;
    }

    /**
     * 根据rooId 找到集合中对应的位置
     * @param roomId
     * @return
     */
    private int GetPositionByRoomId(String roomId,List<RoomInfo> result){
        if(StringUtils.isNull(roomId))return 0;
        for(RoomInfo info:result){
            if(StringUtils.equals(info.getRoomId(),roomId)){
                return result.indexOf(info);
            }
        }
        return 0;
    }

}
