package com.ztj.douyu.main.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ztj.douyu.R;
import com.ztj.douyu.bean.GameType;
import com.ztj.douyu.main.presenter.ClassifyPresenter;
import com.ztj.douyu.main.view.onClassifyView;

import java.util.List;

public class ClassifyFragment extends Fragment implements onClassifyView {

    private View rootView;
    private ClassifyPresenter presenter;
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
                Log.e("TAG",list.toString());
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
}
