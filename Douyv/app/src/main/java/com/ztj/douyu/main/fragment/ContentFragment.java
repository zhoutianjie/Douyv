package com.ztj.douyu.main.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.ztj.douyu.R;
import com.ztj.douyu.bean.RoomInfo;
import com.ztj.douyu.main.presenter.ContentPresenter;
import com.ztj.douyu.main.view.onContentView;

import java.util.List;


/**
 * Created by zhoutianjie on 2018/7/17.
 */

public class ContentFragment extends Fragment implements onContentView {

    private View rootView;
    private SmartRefreshLayout smartRefreshLayout;
    private RecyclerView recyclerView;
    private ContentPresenter presenter;
    private String gameName;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new ContentPresenter();
        presenter.attachView(this);


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // 这里如果不做处理onCreateView还是会被重复调用
        if(rootView==null){
            rootView = inflater.inflate(R.layout.fragment_content,null);
            initExtras();
            initView();
            registerListener();

        }else{
           ViewGroup parent = (ViewGroup) rootView.getParent();
           if(parent!=null){
               parent.removeView(rootView);
           }
        }

        return rootView;
    }

    private void initExtras(){
        Bundle bundle = getArguments();
        if(bundle!=null){
            gameName = bundle.getString("gameName");
        }
    }
    private void initView() {
        smartRefreshLayout = rootView.findViewById(R.id.content_srl);
        recyclerView = rootView.findViewById(R.id.content_rlr);
    }

    private void registerListener(){

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }



    @Override
    public void onResume() {
        super.onResume();
        presenter.getSubChannelRoomInfos(gameName);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public void onGetLiveRoomInfosSuccess(List<RoomInfo> roomInfoList) {

//        Log.e(gameName,"size "+roomInfoList.size());
//        for(RoomInfo roomInfo:roomInfoList){
//            Log.e(gameName,roomInfo.toString());
//        }

        Activity activity = getActivity();
        if(activity==null)return;
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {

            }
        });
    }

    @Override
    public void onGetLiveRoomInfosFailed(String message) {
        Activity activity = getActivity();
        if(activity==null)return;
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {

            }
        });
    }
}
