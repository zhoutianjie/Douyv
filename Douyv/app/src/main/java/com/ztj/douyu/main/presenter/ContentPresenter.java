package com.ztj.douyu.main.presenter;

import android.util.Log;

import com.ztj.douyu.base.mvp.BasePresenter;
import com.ztj.douyu.bean.constant.DouYvUrl;
import com.ztj.douyu.main.view.onContentView;
import com.ztj.douyu.utils.OkhttpUtil;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by zhoutianjie on 2018/7/18.
 */

public class ContentPresenter extends BasePresenter<onContentView> {

    private static ExecutorService contentSinglePool = Executors.newSingleThreadExecutor();
    private long lastLodingTime = 0;
    public ContentPresenter() {

    }

    /**
     * 获取对应游戏类型的房间列表信息
     * @param gameName
     */
    public void getSubChannelRoomInfos(String gameName){
        Log.e("TAG",gameName);
        //加载间隔30s以内不重复加载
       if(true/*System.currentTimeMillis()-lastLodingTime>30*1000*/){
           String url = DouYvUrl.getDouyuSubChannelBaseTag21(gameName);
           OkhttpUtil.getInstance().getAsyncResponse(url, new Callback() {
               @Override
               public void onFailure(Call call, IOException e) {

               }

               @Override
               public void onResponse(Call call, Response response) throws IOException {
                    String str = response.body().string();
                   Log.e("TAG",str);
               }
           });


           lastLodingTime = System.currentTimeMillis();
       }

    }


}
