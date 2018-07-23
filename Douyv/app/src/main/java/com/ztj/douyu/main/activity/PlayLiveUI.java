package com.ztj.douyu.main.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ztj.douyu.R;
import com.ztj.douyu.main.presenter.PlayLivePresenter;
import com.ztj.douyu.main.view.onPlayLiveView;
import com.ztj.douyu.utils.StringUtils;

/**
 *直播播放页面(普通游戏的非全屏的播放页面，颜值和全屏的播放页面实现后面再研究)
 */
public class PlayLiveUI extends AppCompatActivity implements onPlayLiveView {

    private String roomId;
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
    public void onGetLiveRoomUrlInfo(String liveUrl) {
        if(StringUtils.isNull(liveUrl))return;

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //初始化播放器
            }
        });
    }

    @Override
    public void onGetLiveRoomUrlInfoFail(String message) {

    }
}
