package com.ztj.douyu.main.presenter;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.raizlabs.android.dbflow.sql.language.Condition;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.ztj.douyu.base.mvp.BasePresenter;
import com.ztj.douyu.bean.GameAllTypes;
import com.ztj.douyu.bean.GameType;
import com.ztj.douyu.db.GameTypeInfo;
import com.ztj.douyu.db.GameTypeInfo_Table;
import com.ztj.douyu.main.view.onClassifyView;
import com.ztj.douyu.utils.OkhttpUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;

import static com.ztj.douyu.bean.constant.DouYvUrl.DOUYV_ROOMAPI_URL;

/**
 * Created by zhoutianjie on 2018/7/5.
 */

public class ClassifyPresenter extends BasePresenter<onClassifyView> {

    private static ExecutorService cachePool = Executors.newCachedThreadPool();

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

    /**
     * 更新gameType
     */
    public void updateGameType(final GameType gameType){
        cachePool.execute(new Runnable() {
            @Override
            public void run() {
                GameTypeInfo gameTypeInfo = new Select()
                        .from(GameTypeInfo.class)
                        .where(GameTypeInfo_Table.gameTypeName.eq(gameType.getGameName()))
                        .querySingle();
                if(gameTypeInfo!=null){
                    gameTypeInfo.selectCount +=2;
                    gameTypeInfo.update();
                }
            }
        });
    }
}
