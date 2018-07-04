package com.ztj.douyu.base.mvp;

import java.lang.ref.WeakReference;

/**
 * presenter 基类
 * Created by zhoutianjie on 2018/7/4.
 */

public class BasePresenter<V extends BaseView> {

    /**
     * 绑定的View
     */
    private WeakReference<V> mViewref;

    /**
     * 与view绑定，一般在初始化的时候调用
     * @param mMvpView
     */
    public void attachView(V mMvpView){
        mViewref = new WeakReference<V>(mMvpView);
    }

    /**
     * 断开view，在onDestory中调用
     */
    public void detachView(){
        if(mViewref!=null){
            mViewref.clear();
            mViewref = null;
        }
    }

    /**
     * 是否与view建立连接，每次调用业务请求时要进行判断
     * @return
     */
    public boolean isViewAttached(){
        return mViewref!=null && mViewref.get()!=null;
    }

    /**
     * 获取连接的view
     * @return
     */
    public V getView(){
        return mViewref.get();
    }

}
