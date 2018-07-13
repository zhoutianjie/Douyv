package com.ztj.douyu.main.presenter;

import com.ztj.douyu.base.mvp.BasePresenter;
import com.ztj.douyu.main.view.onHomeView;

/**
 * Created by zhoutianjie on 2018/7/13.
 */

public class HomePresenter extends BasePresenter<onHomeView> {

    public HomePresenter() {
    }

    public void getFrequentGameType(){
        //去本地数据库读以前观看过的前8个游戏类型返回界面作为tablayout的显示。

    }
}
