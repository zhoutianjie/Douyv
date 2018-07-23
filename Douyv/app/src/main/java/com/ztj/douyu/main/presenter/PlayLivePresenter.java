package com.ztj.douyu.main.presenter;

import android.util.Log;

import com.ztj.douyu.base.mvp.BasePresenter;
import com.ztj.douyu.bean.constant.DouYvUrl;
import com.ztj.douyu.main.view.onPlayLiveView;
import com.ztj.douyu.utils.OkhttpUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by zhoutianjie on 2018/7/23.
 */

public class PlayLivePresenter extends BasePresenter<onPlayLiveView> {


    public void getLiveRoomUrl(String roomId){
        String url = DouYvUrl.getDouyuLiveRoom(roomId);
        OkhttpUtil.getInstance().getAsyncResponse(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if(isViewAttached()){
                    getView().onGetLiveRoomUrlInfoFail(e.getMessage());
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){
                    ResponseBody body = response.body();
                    if(body!=null){
                        String json = body.string();
                        String hls_url = null;
                        try {
                            JSONObject jsonObject = new JSONObject(json);
                            JSONObject data = jsonObject.getJSONObject("data");
                            hls_url = data.getString("hls_url");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if(isViewAttached()){
                            getView().onGetLiveRoomUrlInfo(hls_url);
                        }
                    }else{
                        if(isViewAttached()){
                            getView().onGetLiveRoomUrlInfoFail(response.message());
                        }
                    }
                }else{
                    if(isViewAttached()){
                        getView().onGetLiveRoomUrlInfoFail(response.message());
                    }
                }
            }
        });
    }
}
