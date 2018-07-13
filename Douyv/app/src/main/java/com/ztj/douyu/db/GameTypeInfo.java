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

    @Column
    public int selectCount;

}
