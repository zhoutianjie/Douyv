package com.ztj.douyu.main.fragment;

import android.app.Activity;
import android.net.Uri;
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
import com.ztj.douyu.main.presenter.PlayLivePresenter;
import com.ztj.douyu.main.view.onPlayLiveView;
import com.ztj.douyu.utils.StringUtils;
import com.ztj.douyu.widgt.media.IjkVideoView;

import jp.wasabeef.glide.transformations.BlurTransformation;
import tv.danmaku.ijk.media.player.IMediaPlayer;

/**
 * Created by zhoutianjie on 2018/8/2.
 */

public class VerticalPlayFragment extends Fragment implements onPlayLiveView {

    private RoomInfo mRoomInfo;
    private View mRootView;
    private ImageView mRoomImage;
    private IjkVideoView mVideoView;
    private PlayLivePresenter presenter;

    private boolean isInitView = false;
    private boolean isLoadSuccess=false;

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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new PlayLivePresenter();
        presenter.attachView(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_vertical_play,null);
        mRoomImage = mRootView.findViewById(R.id.vertical_Image);
        mVideoView = mRootView.findViewById(R.id.fragment_vertical_play);
        Bundle bundle = getArguments();
        if(bundle!=null){
            String jsonInfo = bundle.getString("room_info");
            mRoomInfo = new Gson().fromJson(jsonInfo,RoomInfo.class);
        }
        if(mRoomInfo!=null){
            Activity activity = getActivity();
            if(activity!=null){
                RequestOptions options = new RequestOptions().centerCrop().placeholder(R.mipmap.yz_default);
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
        isInitView = true;
        isCanRequestData();

        return mRootView;
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isCanRequestData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isInitView = false;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    /**
     * 请求播放源
     */
    private void isCanRequestData() {
        //尚未初始化界面，则不请求
        if(!isInitView){
            return;
        }
        if(getUserVisibleHint()){
            Log.e("FragmentV",mRoomInfo.getNickName()+" request url");
            lazyLoad();
        }else{
           if(mVideoView!=null && mVideoView.isPlaying()){
               Log.e("FragmentV",mRoomInfo.getNickName()+" release");
               mVideoView.release(true);

           }
        }
    }


    /**
     * 懒加载请求数据
     */
    private void lazyLoad() {
        presenter.getLiveRoomUrl(mRoomInfo.getRoomId());
    }


    @Override
    public void onGetLiveRoomUrlInfo(final String liveUrl) {
        Log.e("FragmentV",mRoomInfo.getNickName()+" liveUrl"+liveUrl);
        if(StringUtils.isNull(liveUrl))return;

        Log.e("FragmentV","liveUrl is not null");
        mRootView.post(new Runnable() {
            @Override
            public void run() {
                Log.e("FragmentV","setOnPreparedListener");
                mVideoView.setVideoURI(Uri.parse(liveUrl));
                mVideoView.setOnPreparedListener(new IMediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(IMediaPlayer mp) {
                        Log.e("FragmentV",mRoomInfo.getNickName()+"start");
                        mVideoView.start();
                        mRoomImage.setVisibility(View.GONE);
                    }
                });
            }
        });
    }

    @Override
    public void onGetLiveRoomUrlInfoFail(String message) {

    }
}
