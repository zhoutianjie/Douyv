package com.ztj.douyu.bean;

import java.util.List;

/**
 * Created by zhoutianjie on 2018/7/18.
 */

public class RoomInfos {

    private int error;

    private List<RoomInfo> data;

    public int getError() {
        return error;
    }

    public List<RoomInfo> getData() {
        return data;
    }

    @Override
    public String toString() {
        return "RoomInfos{" +
                "error=" + error +
                ", data=" + data +
                '}';
    }
}
