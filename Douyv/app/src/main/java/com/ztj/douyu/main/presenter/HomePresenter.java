package com.ztj.douyu.main.presenter;

import android.util.Log;

import com.google.gson.Gson;
import com.raizlabs.android.dbflow.runtime.transaction.process.ProcessModelInfo;
import com.raizlabs.android.dbflow.runtime.transaction.process.SaveModelTransaction;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.ztj.douyu.base.mvp.BasePresenter;
import com.ztj.douyu.bean.GameAllTypes;
import com.ztj.douyu.bean.GameType;
import com.ztj.douyu.bean.constant.DouYvUrl;
import com.ztj.douyu.db.GameTypeInfo;
import com.ztj.douyu.main.view.onHomeView;
import com.ztj.douyu.utils.AlgorithmUtil;
import com.ztj.douyu.utils.OkhttpUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by zhoutianjie on 2018/7/13.
 */

public class HomePresenter extends BasePresenter<onHomeView> {

    private static ExecutorService singlePool = Executors.newSingleThreadExecutor();
    private static final int common_game_types_num = 8;

    public HomePresenter() {

    }

    /**
     * 本地数据库读取经常观看的前K个游戏直播类型，作为tablayout的标题
     * 如果本地数据库为空，或者本地数据库保存的游戏类型没有达到k个直接请求网络
     *
     */
    public void getFrequentGameType(){
        singlePool.execute(new Runnable() {
            @Override
            public void run() {
                List<GameTypeInfo> result = new Select().from(GameTypeInfo.class).queryList();
                if(result==null || result.isEmpty()){
                    result = getGameTypeInfoFormNetWork();
                }
                if(result!=null && result.size()>0){

                    result = AlgorithmUtil.getKthofGameTypeInfo(result,common_game_types_num);
                    if(isViewAttached() && result!=null){
                        getView().GetFrequentGameTypes(result);
                    }
                }

            }
        });
    }

    /**
     * 请求网络数据
     * @return
     */
    private List<GameTypeInfo> getGameTypeInfoFormNetWork() {
        List<GameTypeInfo> gameTypeInfos =null;
        String url = DouYvUrl.DOUYV_ROOMAPI_URL+"game";
        Response response = OkhttpUtil.getInstance().getSyncResponse(url);
        if(response!=null && response.isSuccessful()){
            ResponseBody body = response.body();
            if(body!=null){
                try {
                    String gameAllTypesJson = body.string();
                    GameAllTypes gameAllTypes = new Gson().fromJson(gameAllTypesJson,GameAllTypes.class);
                    gameTypeInfos = convertToGameTypeInfos(gameAllTypes);
                    //保存至本地数据库
                    new SaveModelTransaction<GameTypeInfo>(ProcessModelInfo.withModels(gameTypeInfos)).onExecute();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return gameTypeInfos;
    }

    /**
     * 转换数据类型
     * @param gameAllTypes
     * @return
     */
    private List<GameTypeInfo> convertToGameTypeInfos(GameAllTypes gameAllTypes) {
        List<GameTypeInfo> result = new ArrayList<>();
        List<GameType> data = gameAllTypes.getData();
        for(GameType gameType:data){
            GameTypeInfo info = new GameTypeInfo();
            info.setGameId(gameType.getGameId());
            info.setGameTypeName(gameType.getGameName());
            info.setGameIcon(gameType.getGameIcon());
            info.setGameShortName(gameType.getGameShortName());
            info.setGameSrc(gameType.getGameSrc());
            info.setGameUrl(gameType.getGameUrl());
            info.setSelectCount(0);
            result.add(info);
        }
        return result;
    }


}
