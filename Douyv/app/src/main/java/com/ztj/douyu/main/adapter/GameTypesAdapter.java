package com.ztj.douyu.main.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ztj.douyu.R;
import com.ztj.douyu.bean.GameType;
import com.ztj.douyu.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhoutianjie on 2018/7/12.
 */

public class GameTypesAdapter extends RecyclerView.Adapter<GameTypesAdapter.GameTypeViewHolder>{

    private List<GameType> mData;
    private Context mContext;
    private OnItemClickListener listener;



    public interface OnItemClickListener{
        void onClick(int position);
    }


    public GameTypesAdapter(List<GameType> mData, Context mContext) {
        if(mData==null){
            this.mData = new ArrayList<>();
        }else{
            this.mData = mData;
        }
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public GameTypeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_game_type,null,false);
        GameTypeViewHolder holder = new GameTypeViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull GameTypeViewHolder holder, int position) {
        GameType gameType = mData.get(position);
        if(gameType==null)return;
        String ganemName = gameType.getGameName();
        if(!StringUtils.isNull(ganemName)){
            holder.gameName.setText(ganemName);
        }
        String iconUrl = gameType.getGameIcon();

        if(!StringUtils.isNull(iconUrl)){
            Glide.with(mContext).load(iconUrl)
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .into(holder.gameIcon);
        }


    }


    @Override
    public int getItemCount() {
        return mData.size();
    }


    public class GameTypeViewHolder extends RecyclerView.ViewHolder{
        public RelativeLayout gameTypeRl;
        public CardView cardView;
        public ImageView gameIcon;
        public TextView gameName;

        public GameTypeViewHolder(View itemView) {
            super(itemView);
            gameTypeRl = itemView.findViewById(R.id.game_type_rl);
            cardView = itemView.findViewById(R.id.gameType_cv);
            gameIcon = itemView.findViewById(R.id.game_type_icon_iv);
            gameName = itemView.findViewById(R.id.game_type_name_tv);
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    public void setDatas(List<GameType> mData) {
        this.mData = mData;
    }
}
