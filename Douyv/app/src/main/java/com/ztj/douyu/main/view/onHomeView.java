package com.ztj.douyu.main.view;

import com.ztj.douyu.base.mvp.BaseView;
import com.ztj.douyu.bean.GameType;
import com.ztj.douyu.db.GameTypeInfo;

import java.util.List;

/**
 * Created by zhoutianjie on 2018/7/13.
 */

public interface onHomeView extends BaseView {

     void GetFrequentGameTypes(List<GameTypeInfo> gameTypeList);
}
