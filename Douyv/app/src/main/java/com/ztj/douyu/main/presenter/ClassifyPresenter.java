package com.ztj.douyu.main.presenter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ztj.douyu.base.mvp.BasePresenter;
import com.ztj.douyu.bean.GameAllTypes;
import com.ztj.douyu.bean.GameType;
import com.ztj.douyu.main.view.onClassifyView;
import com.ztj.douyu.utils.OkhttpUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;

import static com.ztj.douyu.bean.constant.DouYvUrl.DOUYV_ROOMAPI_URL;

/**
 * Created by zhoutianjie on 2018/7/5.
 */

public class ClassifyPresenter extends BasePresenter<onClassifyView> {


    public ClassifyPresenter() {
    }

    /**
     * 请求所有游戏类型
     */
    public void getAllGamesTypes(){

        String url = DOUYV_ROOMAPI_URL+"game";
        OkhttpUtil.getInstance().getAsyncResponse(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if(isViewAttached()){
                    getView().onGetAllGamesFailed(e.getMessage());
                }

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){
                    ResponseBody body = response.body();
                    if(body!=null){
                        String gameAllTypesJson = body.string();
                        GameAllTypes gameAllTypes = new Gson().fromJson(gameAllTypesJson,GameAllTypes.class);
                        List<GameType> result = getResult(gameAllTypes);
                        if(isViewAttached()){
                            getView().onGetAllGamesSuccess(result);
                        }
                    }else{
                        if(isViewAttached()){
                            getView().onGetAllGamesFailed(response.message());
                        }
                    }
                }else{
                    if(isViewAttached()){
                        getView().onGetAllGamesFailed(response.message());
                    }
                }
            }
        });
    }

    private List<GameType> getResult(GameAllTypes gameAllTypes) {
        if(gameAllTypes == null){
            return null;
        }
        List<GameType> result = new ArrayList<>();
        result.addAll(gameAllTypes.getData());
        return result;
    }
}
