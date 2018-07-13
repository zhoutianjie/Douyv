package com.ztj.douyu.main.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ztj.douyu.R;
import com.ztj.douyu.bean.GameType;
import com.ztj.douyu.main.presenter.HomePresenter;
import com.ztj.douyu.main.view.onHomeView;

import java.util.List;

public class HomeFragment extends Fragment implements onHomeView {

    private View rootView;
    private TabLayout tabLayout;
    private HomePresenter presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new HomePresenter();
        presenter.attachView(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if(rootView == null){
            rootView = inflater.inflate(R.layout.fragment_home,null,false);
            initView();
            presenter.getFrequentGameType();

        }else{
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if(parent!=null){
                parent.removeView(rootView);
            }
        }
        return rootView;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    private void initView() {
        tabLayout = rootView.findViewById(R.id.tab);
    }

    @Override
    public void GetFrequentGameTypes(List<GameType> gameTypeList) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //创建Tablayout 创建viewpager 开始加载数据显示 直播间
            }
        });
    }
}
