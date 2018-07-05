package com.ztj.douyu.bean;

import com.google.gson.annotations.SerializedName;

/**
 * 游戏类型
 * Created by zhoutianjie on 2018/7/5.
 */

public class GameType {

    @SerializedName("cate_id")
    private int gameId; //游戏分类id

    @SerializedName("game_name")
    private String gameName;//游戏名称

    @SerializedName("short_name")
    private String gameShortName;//游戏分类别名

    @SerializedName("game_url")
    private String gameUrl;//游戏分类网址

    @SerializedName("game_src")
    private String gameSrc;//游戏封面图片

    @SerializedName("game_icon")
    private String gameIcon;//游戏分类小图标

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getGameShortName() {
        return gameShortName;
    }

    public void setGameShortName(String gameShortName) {
        this.gameShortName = gameShortName;
    }

    public String getGameUrl() {
        return gameUrl;
    }

    public void setGameUrl(String gameUrl) {
        this.gameUrl = gameUrl;
    }

    public String getGameSrc() {
        return gameSrc;
    }

    public void setGameSrc(String gameSrc) {
        this.gameSrc = gameSrc;
    }

    public String getGameIcon() {
        return gameIcon;
    }

    public void setGameIcon(String gameIcon) {
        this.gameIcon = gameIcon;
    }

    @Override
    public String toString() {
        return "GameType{" +
                "gameId=" + gameId +
                ", gameName='" + gameName + '\'' +
                ", gameShortName='" + gameShortName + '\'' +
                ", gameUrl='" + gameUrl + '\'' +
                ", gameSrc='" + gameSrc + '\'' +
                ", gameIcon='" + gameIcon + '\'' +
                '}';
    }
}
