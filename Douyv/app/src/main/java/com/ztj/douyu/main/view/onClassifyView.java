package com.ztj.douyu.main.view;

import com.ztj.douyu.base.mvp.BaseView;
import com.ztj.douyu.bean.GameType;

import java.util.List;

/**
 * Created by zhoutianjie on 2018/7/5.
 */

public interface onClassifyView extends BaseView {

    void onGetAllGamesSuccess(List<GameType> list);
    void onGetAllGamesFailed(String str);

}
