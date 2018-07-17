package com.ztj.douyu.main.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ztj.douyu.db.GameTypeInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhoutianjie on 2018/7/17.
 */

public class ContentFragmentPagerAdapter extends FragmentPagerAdapter{

    private List<Fragment> fragments;
    public ContentFragmentPagerAdapter(FragmentManager fm,List<Fragment> fragments) {
        super(fm);
        if(fragments == null){
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
}
