package com.ztj.douyu.main.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.bumptech.glide.Glide;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.ztj.douyu.R;
import com.ztj.douyu.bean.RoomInfo;
import com.ztj.douyu.bean.constant.DouYvUrl;
import com.ztj.douyu.main.activity.MainActivity;
import com.ztj.douyu.main.activity.HorizontalPlayLiveUI;
import com.ztj.douyu.main.activity.VerticalPlayLiveUI;
import com.ztj.douyu.main.adapter.RoomInfosAdapter;
import com.ztj.douyu.main.presenter.ContentPresenter;
import com.ztj.douyu.main.service.FloatWindowService;
import com.ztj.douyu.main.view.onContentView;
import com.ztj.douyu.utils.ActivityUtils;
import com.ztj.douyu.utils.ToastObject;

import java.util.List;



import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;


/**
 * 界面刷新和加载更多出现的几个问题
 * 1.用Glide框架在资源文件没有指定图片固定大小的情况下，会出现加载的时候图片大小不一的问题，不美观
 * 尝试在adapter里面设置默认的图片，还是不行，只和布局文件中的图片大小有关。当时我用无法固定布局文件的大小，因为我不知道图片打下
 *
 * recyclerview的adapter.notifyDataSetChanged 这个方法在加载更多的时候 会出现不流畅的感觉，不能全部加载，改用其他方法研究一下
 * Created by zhoutianjie on 2018/7/17.
 */

public class ContentFragment extends Fragment implements onContentView {

    private View rootView;
    private SmartRefreshLayout smartRefreshLayout;
    private RecyclerView recyclerView;
    private ContentPresenter presenter;
    private String gameName;
    private RoomInfosAdapter adapter;
    private GridLayoutManager gridLayoutManager;
    private int mOffset = 1;


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
            presenter.getSubChannelRoomInfos(gameName);

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
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if(newState == SCROLL_STATE_IDLE){
                    Glide.with(getActivity()).resumeRequests();//停止状态下恢复加载
                }else{
                    Glide.with(getActivity()).pauseRequests();//滚动状态下取消加载(这么操作也不会出现图片错位显示的情形)
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                presenter.refreshSubChannelRoomInfos(gameName);
            }
        });
        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

                presenter.loadMoreSubChannelRoomInfos(gameName,mOffset);

            }
        });


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }



    @Override
    public void onResume() {
        super.onResume();

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public void onGetLiveRoomInfosSuccess(final List<RoomInfo> roomInfoList) {

//        Log.e(gameName,"size "+roomInfoList.size());
//        for(RoomInfo roomInfo:roomInfoList){
//            Log.e(gameName,roomInfo.toString());
//        }

        final Activity activity = getActivity();
        if(activity==null)return;
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(adapter==null){
                    adapter = new RoomInfosAdapter(roomInfoList,getContext());
                    adapter.setOnItemClickListener(new RoomInfosAdapter.OnItemClickListener() {
                        @Override
                        public void onClick(int position, String roomId,int isVertical) {
                            if(FloatWindowService.mIsFloatWindowShown){
                                ((MainActivity)activity).stopFloatWindowService();
                            }
                            Bundle bundle = new Bundle();
                            bundle.putString("roomId",roomId);
                            if(isVertical== DouYvUrl.horizontal_screen_play){
                                ActivityUtils.openActivity(activity,HorizontalPlayLiveUI.class,bundle);
                            }else if(isVertical == DouYvUrl.vertical_screen_play){
                                //ActivityUtils.openActivity(activity,VerticalPlayLiveUI.class,bundle);
                                ActivityUtils.openActivity(activity,HorizontalPlayLiveUI.class,bundle);
                            }
                        }
                    });
                }
                if(gridLayoutManager==null){
                    gridLayoutManager = new GridLayoutManager(getContext(), 2);
                }
                recyclerView.setLayoutManager(gridLayoutManager);
                recyclerView.setAdapter(adapter);
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

    @Override
    public void onGetLiveRoomInfosRefreshSuccess(final List<RoomInfo> roomInfoList) {
        final Activity activity = getActivity();
        if(activity==null)return;
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(adapter!=null){
                    adapter.setData(roomInfoList);
                    adapter.notifyDataSetChanged();
                    smartRefreshLayout.finishRefresh();
                }
            }
        });
    }

    @Override
    public void onGetLiveRoomInfosRefreshFailed(String message) {
        final Activity activity = getActivity();
        if(activity==null)return;
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ToastObject.getInstance().show("刷新失败");
                smartRefreshLayout.finishRefresh();
            }
        });

    }

    @Override
    public void onGetLiveRoomInfoMoreSuccess(final List<RoomInfo> roomInfoList) {
        Activity activity = getActivity();
        if(activity==null)return;
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(roomInfoList==null || roomInfoList.isEmpty()){
                    smartRefreshLayout.finishLoadMoreWithNoMoreData();
                }else{
                    adapter.addData(roomInfoList);
                    adapter.notifyDataSetChanged();
                    smartRefreshLayout.finishLoadMore();
                    mOffset++;
                }


            }
        });
    }

    @Override
    public void onGetLiveRoomInfoMoreFailed(String message) {
        Activity activity = getActivity();
        if(activity==null)return;
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ToastObject.getInstance().show("加载失败");
                smartRefreshLayout.finishLoadMoreWithNoMoreData();
            }
        });
    }
}
