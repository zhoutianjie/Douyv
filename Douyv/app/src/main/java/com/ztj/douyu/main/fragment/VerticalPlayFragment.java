package com.ztj.douyu.main.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.ztj.douyu.R;
import com.ztj.douyu.bean.RoomInfo;
import com.ztj.douyu.utils.StringUtils;

import jp.wasabeef.glide.transformations.BlurTransformation;

/**
 * Created by zhoutianjie on 2018/8/2.
 */

public class VerticalPlayFragment extends Fragment{

    private RoomInfo mRoomInfo;
    private View mRootView;
    private ImageView mRoomImage;

    public static VerticalPlayFragment newInstance(RoomInfo info){
        Log.e("VerticalPlayFragment","new");
        VerticalPlayFragment fragment = new VerticalPlayFragment();
        String jsonInfo = new Gson().toJson(info);
        Log.e("VerticalPlayFragment",jsonInfo);
        Bundle bundle = new Bundle();
        bundle.putString("room_info",jsonInfo);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_vertical_play,null);
        mRoomImage = mRootView.findViewById(R.id.vertical_Image);
        Bundle bundle = getArguments();
        if(bundle!=null){
            String jsonInfo = bundle.getString("room_info");
            mRoomInfo = new Gson().fromJson(jsonInfo,RoomInfo.class);
        }
        if(mRoomInfo!=null){
            Activity activity = getActivity();
            if(activity!=null){
                RequestOptions options = new RequestOptions().centerCrop();
                Glide.with(this)
                        .load(mRoomInfo.getVerticalSrc())
                        .apply(options)
                        //先加载小图，再加载大图
                        .thumbnail(0.1f)
                        //这个模糊图片的效果很可能导致界面的卡顿,这个模糊效果先不要了
//                        .apply(RequestOptions.bitmapTransform(new BlurTransformation(20)))
                        .into(mRoomImage);
            }
        }

        return mRootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}
