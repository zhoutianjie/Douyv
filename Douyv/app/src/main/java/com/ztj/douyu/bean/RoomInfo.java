package com.ztj.douyu.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by zhoutianjie on 2018/7/18.
 */

public class RoomInfo {

    @SerializedName("room_id")
    private String roomId;

    @SerializedName("room_src")
    private String roomSrc;

    @SerializedName("vertical_src")
    private String verticalSrc;

    private int isVertical;

    @SerializedName("cate_id")
    private int gameId;

    @SerializedName("room_name")
    private String roomName;

    @SerializedName("show_status")
    private String showStatus;

    private String subject;

    @SerializedName("show_time")
    private String showTime;

    @SerializedName("owner_uid")
    private String ownerUid;

    @SerializedName("specific_catalog")
    private String specificCatalog;

    @SerializedName("specific_status")
    private String specificStatus;

    @SerializedName("vod_quality")
    private String vodQuality;

    @SerializedName("nickname")
    private String nickName;

    private long online;
    private long hn;

    private String url;

    @SerializedName("game_url")
    private String gameUrl;

    @SerializedName("game_name")
    private String gameName;

    @SerializedName("child_id")
    private int childId;

    private String avatar;

    @SerializedName("avatar_mid")
    private String avatarMid;

    @SerializedName("avatar_small")
    private String avatarSmall;

    private String jumpUrl;
    private String fans;
    private int ranktype;

    @SerializedName("is_noble_rec")
    private int isNobleRec;

    @SerializedName("anchor_city")
    private String anchorCity;

    public String getRoomId() {
        return roomId;
    }

    public String getRoomSrc() {
        return roomSrc;
    }

    public String getVerticalSrc() {
        return verticalSrc;
    }

    public int getIsVertical() {
        return isVertical;
    }

    public int getGameId() {
        return gameId;
    }

    public String getRoomName() {
        return roomName;
    }

    public String getShowStatus() {
        return showStatus;
    }

    public String getSubject() {
        return subject;
    }

    public String getShowTime() {
        return showTime;
    }

    public String getOwnerUid() {
        return ownerUid;
    }

    public String getSpecificCatalog() {
        return specificCatalog;
    }

    public String getSpecificStatus() {
        return specificStatus;
    }

    public String getVodQuality() {
        return vodQuality;
    }

    public String getNickName() {
        return nickName;
    }

    public long getOnline() {
        return online;
    }

    public long getHn() {
        return hn;
    }

    public String getUrl() {
        return url;
    }

    public String getGameUrl() {
        return gameUrl;
    }

    public String getGameName() {
        return gameName;
    }

    public int getChildId() {
        return childId;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getAvatarMid() {
        return avatarMid;
    }

    public String getAvatarSmall() {
        return avatarSmall;
    }

    public String getJumpUrl() {
        return jumpUrl;
    }

    public String getFans() {
        return fans;
    }

    public int getRanktype() {
        return ranktype;
    }

    public int getIsNobleRec() {
        return isNobleRec;
    }

    public String getAnchorCity() {
        return anchorCity;
    }

    @Override
    public String toString() {
        return "RoomInfo{" +
                "roomId='" + roomId + '\'' +
                ", roomSrc='" + roomSrc + '\'' +
                ", verticalSrc='" + verticalSrc + '\'' +
                ", isVertical=" + isVertical +
                ", gameId=" + gameId +
                ", roomName='" + roomName + '\'' +
                ", showStatus='" + showStatus + '\'' +
                ", subject='" + subject + '\'' +
                ", showTime='" + showTime + '\'' +
                ", ownerUid='" + ownerUid + '\'' +
                ", specificCatalog='" + specificCatalog + '\'' +
                ", specificStatus='" + specificStatus + '\'' +
                ", vodQuality='" + vodQuality + '\'' +
                ", nickName='" + nickName + '\'' +
                ", online=" + online +
                ", hn=" + hn +
                ", url='" + url + '\'' +
                ", gameUrl='" + gameUrl + '\'' +
                ", gameName='" + gameName + '\'' +
                ", childId=" + childId +
                ", avatar='" + avatar + '\'' +
                ", avatarMid='" + avatarMid + '\'' +
                ", avatarSmall='" + avatarSmall + '\'' +
                ", jumpUrl='" + jumpUrl + '\'' +
                ", fans='" + fans + '\'' +
                ", ranktype=" + ranktype +
                ", isNobleRec=" + isNobleRec +
                ", anchorCity='" + anchorCity + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj){
            return true;
        }
        if(obj == null){
            return false;
        }

        if(getClass() !=obj.getClass()){
            return false;
        }

        RoomInfo other = (RoomInfo) obj;
        return this.roomId.equals(other.roomId);
    }
}
