package com.ztj.douyu.bean.constant;

/**
 * Created by zhoutianjie on 2018/7/17.
 */

public class DouYvUrl {

    public static final String DOUYV_ROOMAPI_URL = "http://open.douyucdn.cn/api/RoomApi/";
    public static final String DOUYU_API = "http://capi.douyucdn.cn/api/v1/";
    //2.1平台api
    public static final String DOUYU_API_2_1 = "http://api.douyutv.com/api/v1/";

    //各种游戏类型对应的直播列表
    public static String getDouyuSubChannelBaseTag(int channelTag) {
        return DOUYU_API + "live/" + channelTag + "?&limit=20";
    }

    public static String getDouyuSubChannelBaseTag(String gameTypeName){
        return DOUYU_API + "live/" + gameTypeName + "?&limit=20";
    }

    //2.1平台
    public static String getDouyuSubChannelBaseTag21(int channelTag) {
        return DOUYU_API_2_1 + "live/" + channelTag + "?&limit=20";
    }

    public static String getDouyuSubChannelBaseTag21(String gameTypeName){
        return DOUYU_API_2_1 + "live/" + gameTypeName + "?&limit=20";
    }

}
