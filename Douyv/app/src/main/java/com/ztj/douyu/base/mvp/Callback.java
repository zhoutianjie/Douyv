package com.ztj.douyu.base.mvp;

/**
 * Presenter 与 model 交互的Callback
 * Created by zhoutianjie on 2018/7/4.
 */

public interface Callback<T> {
    /**
     * 数据请求成功
     * @param data
     */
    void onSuccess(T data);

    /**
     * 数据请求失败
     * @param msg
     */
    void onFailure(String msg);

    /**
     * 数据请求完成
     */
    void onComplete();
}
