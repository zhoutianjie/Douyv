package com.ztj.douyu.main.adapter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhoutianjie on 2018/7/17.
 */

public class ContentFragmentPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments;

    public ContentFragmentPagerAdapter(FragmentManager fm,List<Fragment> fragments) {
        super(fm);
        if(fragments==null){
            fragments = new ArrayList<>();
        }
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int i) {
        if(fragments.size()==0){
            return null;
        }
        return fragments.get(i);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

//    @Nullable
//    @Override
//    public CharSequence getPageTitle(int position) {
//        if(fragments.size()==0){
//            return "";
//        }
//        Bundle bundle = fragments.get(position).getArguments();
//        String title = bundle.getString("gameName");
//        return title;
//    }
}
