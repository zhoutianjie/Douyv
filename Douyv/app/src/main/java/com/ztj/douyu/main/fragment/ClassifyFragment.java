package com.ztj.douyu.main.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.ztj.douyu.R;
import com.ztj.douyu.bean.GameType;
import com.ztj.douyu.main.adapter.GameTypesAdapter;
import com.ztj.douyu.main.presenter.ClassifyPresenter;
import com.ztj.douyu.main.view.onClassifyView;

import java.util.List;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;


public class ClassifyFragment extends Fragment implements onClassifyView {

    private View rootView;
    private ClassifyPresenter presenter;
    private RecyclerView recyclerView;
    private GameTypesAdapter adapter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new ClassifyPresenter();
        presenter.attachView(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //防止onCreateView被重复调用
        if(rootView == null){
            rootView = inflater.inflate(R.layout.fragment_classify,null);
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

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }




    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.getAllGamesTypes();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public void onGetAllGamesSuccess(final List<GameType> list) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.setDatas(list);
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onGetAllGamesFailed(String str) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {

            }
        });
    }

    private void initView() {
        recyclerView = rootView.findViewById(R.id.recycler_view);
        GridLayoutManager manager = new GridLayoutManager(getContext(),4);

        adapter = new GameTypesAdapter(null,getActivity());
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);

    }

    private void registerListener() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
               if(newState == SCROLL_STATE_IDLE){
                   Glide.with(getActivity()).resumeRequests();//停止状态下恢复加载
               }else{
                   Glide.with(getActivity()).pauseRequests();//滚动状态下取消加载(这么操作也不会出现图片错位显示的情形)
               }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        adapter.setOnItemClickListener(new GameTypesAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                if(adapter==null)return;
                GameType gameType = adapter.getItem(position);
                if(gameType==null)return;
                presenter.updateGameType(gameType);

            }
        });
    }
}
