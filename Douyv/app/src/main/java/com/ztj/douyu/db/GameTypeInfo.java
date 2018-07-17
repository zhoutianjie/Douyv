package com.ztj.douyu.db;


import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ModelContainer;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by zhoutianjie on 2018/7/13.
 */
@ModelContainer
@Table(database = APPDataBase.class)
public class GameTypeInfo extends BaseModel {

    @Column
    @PrimaryKey(autoincrement = true)
    public long id;

    @Column
    public int gameId;

    @Column
    public String gameTypeName;

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

    public String getGameIcon() {
        return gameIcon;
    }

    public void setGameIcon(String gameIcon) {
        this.gameIcon = gameIcon;
    }

    public String getGameSrc() {
        return gameSrc;
    }

    public void setGameSrc(String gameSrc) {
        this.gameSrc = gameSrc;
    }

    @Column
    public String gameShortName;

    @Column
    public String gameUrl;//游戏分类网址

    @Column
    public String gameIcon;

    @Column
    public String gameSrc;//游戏封面图片

    @Column
    public int selectCount;

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public String getGameTypeName() {
        return gameTypeName;
    }

    public void setGameTypeName(String gameTypeName) {
        this.gameTypeName = gameTypeName;
    }

    public int getSelectCount() {
        return selectCount;
    }

    public void setSelectCount(int selectCount) {
        this.selectCount = selectCount;
    }

    @Override
    public String toString() {
        return "GameTypeInfo{" +
                "id=" + id +
                ", gameId=" + gameId +
                ", gameTypeName='" + gameTypeName + '\'' +
                ", gameShortName='" + gameShortName + '\'' +
                ", gameUrl='" + gameUrl + '\'' +
                ", gameIcon='" + gameIcon + '\'' +
                ", gameSrc='" + gameSrc + '\'' +
                ", selectCount=" + selectCount +
                '}';
    }
}
