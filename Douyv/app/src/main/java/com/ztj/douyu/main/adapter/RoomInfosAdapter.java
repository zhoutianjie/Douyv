package com.ztj.douyu.main.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ztj.douyu.R;
import com.ztj.douyu.bean.RoomInfo;
import com.ztj.douyu.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhoutianjie on 2018/7/19.
 */

public class RoomInfosAdapter extends RecyclerView.Adapter<RoomInfosAdapter.RoomInfoViewHolder> {

    private List<RoomInfo> mData;
    private Context mContext;

    private OnItemClickListener listener;



    public interface OnItemClickListener{
        void onClick(int position);
    }

    public RoomInfosAdapter(List<RoomInfo> mData, Context mContext) {
        if(mData==null){
            mData = new ArrayList<>();
        }
        this.mData = mData;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RoomInfoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_room,null);
        RoomInfoViewHolder holder = new RoomInfoViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RoomInfoViewHolder holder, final int i) {
        RoomInfo roomInfo = mData.get(i);
        if(roomInfo==null)return;
        String roomName = roomInfo.getRoomName();
        if(!StringUtils.isNull(roomName)){
            holder.roomName.setText(roomName);
        }
        String online = StringUtils.onlineNumConverToString(roomInfo.getOnline());
        if(!StringUtils.isNull(online)){
            holder.online.setText(online);
        }
        String nickName = roomInfo.getNickName();
        if(!StringUtils.isNull(nickName)){
            holder.nickName.setText(nickName);
        }

        String src_url = roomInfo.getRoomSrc();
        if(!StringUtils.isNull(src_url)){
            Glide.with(mContext).load(src_url)
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .into(holder.roomImg);
        }
        if(listener!=null){
            holder.roomLL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onClick(i);
                }
            });
        }


    }

    @Override
    public int getItemCount() {
        if(mData==null || mData.size()==0){
            return 0;
        }
        return mData.size();
    }

    public class RoomInfoViewHolder extends RecyclerView.ViewHolder{

        public ImageView roomImg;
        public TextView  online;
        public TextView  nickName;
        public TextView  roomName;

        public LinearLayout roomLL;

        public RoomInfoViewHolder(@NonNull View itemView) {
            super(itemView);
            roomImg = itemView.findViewById(R.id.room_iv);
            online = itemView.findViewById(R.id.online_tv);
            nickName = itemView.findViewById(R.id.nickname_tv);
            roomName = itemView.findViewById(R.id.room_name_tv);
            roomLL = itemView.findViewById(R.id.room_ll);
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }
}
