package com.ztj.douyu.utils;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * 单例数据缓存
 * Created by zhoutianjie on 2018/8/2.
 */

public class DataHolder {

    private Map<String,WeakReference<Object>> data;

    private DataHolder(){
        data = new ConcurrentHashMap<>();
    }

    private static  class Inner{
        private static  DataHolder INSTANCE = new DataHolder();
    }

    public static DataHolder getInstance(){
        return Inner.INSTANCE;
    }

    public void save(String id,Object o){
        data.put(id,new WeakReference<Object>(o));
    }

    public Object acquire(String id){
        WeakReference<Object> weakReferenceO = data.get(id);
        return weakReferenceO.get();
    }
}
