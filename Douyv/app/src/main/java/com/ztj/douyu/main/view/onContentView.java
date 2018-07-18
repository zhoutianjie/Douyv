package com.ztj.douyu.main.view;

import com.ztj.douyu.base.mvp.BaseView;
import com.ztj.douyu.bean.RoomInfo;

import java.util.List;

/**
 * Created by zhoutianjie on 2018/7/17.
 */

public interface onContentView extends BaseView {

    void onGetLiveRoomInfosSuccess(List<RoomInfo> roomInfoList);
    void onGetLiveRoomInfosFailed(String message);
}
