package com.ztj.douyu.main.view;

import com.ztj.douyu.base.mvp.BaseView;

/**
 * Created by zhoutianjie on 2018/7/23.
 */

public interface onPlayLiveView extends BaseView {

    void onGetLiveRoomUrlInfo(String liveUrl);
    void onGetLiveRoomUrlInfoFail(String message);
}
