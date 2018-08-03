package com.ztj.douyu.main.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.ztj.douyu.bean.RoomInfo;
import com.ztj.douyu.main.fragment.VerticalPlayFragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * viewPager 实现动态加载和删除(不闪屏)
 * Created by zhoutianjie on 2018/8/2.
 */

public class VerticalOpenPagerAdapter extends OpenPagerAdapter<RoomInfo>{

    private List<RoomInfo> mDatas = new ArrayList<>();

    public VerticalOpenPagerAdapter(FragmentManager fm,List<RoomInfo> datas) {
        super(fm);
        mDatas.clear();
        if(datas!=null)mDatas.addAll(datas);
    }


    @Override
    public Fragment getItem(int position) {
        return VerticalPlayFragment.newInstance(mDatas.get(position));
    }

    @Override
    protected RoomInfo getItemData(int position) {
        return mDatas.get(position);
    }

    @Override
    protected boolean dataEquals(RoomInfo oldData, RoomInfo newData) {
        return oldData.equals(newData);
    }

    @Override
    protected int getDataPosition(RoomInfo data) {
        return mDatas.indexOf(data);
    }


    @Override
    public int getCount() {
        return mDatas.size();
    }

    public VerticalPlayFragment getCurrentFragmentItem(){
        return (VerticalPlayFragment) getCurrentPrimaryItem();
    }

    public void setNewData(List<RoomInfo> datas){
        mDatas.clear();
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    public void addData(RoomInfo info){
        mDatas.add(info);
        notifyDataSetChanged();
    }

    public void addData(int position,RoomInfo info){
        mDatas.add(position,info);
        notifyDataSetChanged();
    }

    public void remove(int position){
        mDatas.remove(position);
        notifyDataSetChanged();
    }

    public void moveData(int from,int to){
        if (from==to)return;
        Collections.swap(mDatas, from, to);
        notifyDataSetChanged();
    }

    public void moveDataToFirst(int from){
        RoomInfo tmp = mDatas.remove(from);
        mDatas.add(0,tmp);
        notifyDataSetChanged();
    }

    public void updateByPosition(int position,RoomInfo info){
        if(position>=0 && mDatas.size()>position){
            mDatas.set(position,info);
            VerticalPlayFragment targetF = getCachedFragmentByPosition(position);
            if(targetF!=null){
//                targetF.updateData();
            }
        }
    }

    public VerticalPlayFragment getCachedFragmentByPosition(int position){
        return (VerticalPlayFragment) getFragmentByPosition(position);
    }
}
