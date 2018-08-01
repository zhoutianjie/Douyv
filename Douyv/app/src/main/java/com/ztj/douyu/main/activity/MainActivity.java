package com.ztj.douyu.main.activity;



import android.content.Intent;
import android.support.v4.app.FragmentTabHost;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import android.widget.TextView;

import com.ztj.douyu.R;
import com.ztj.douyu.bean.MessageEvent;
import com.ztj.douyu.bean.constant.RequestAndResultCode;
import com.ztj.douyu.main.App;
import com.ztj.douyu.main.fragment.ClassifyFragment;
import com.ztj.douyu.main.fragment.FavourFragment;
import com.ztj.douyu.main.fragment.HomeFragment;
import com.ztj.douyu.main.fragment.MineFragment;
import com.ztj.douyu.main.service.FloatWindowService;
import com.ztj.douyu.utils.ActivityUtils;
import com.ztj.douyu.utils.StringUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class MainActivity extends AppCompatActivity {


    //是否支持小窗口播放
    private boolean isSupportFloatWindow = true;
    private FragmentTabHost mTabHost;
    private final String mTabSpec[] = {"head", "classify", "heart", "search"};

    private final Class mFragmentsClass[] = {
            HomeFragment.class,
            ClassifyFragment.class,
            FavourFragment.class,
            MineFragment.class
    };

    private final String mIndicator[] = {"首页", "分类", "喜欢", "搜索"};

    private final int mTabImage[] = {R.drawable.tab_home_item,
            R.drawable.tab_classify_item,
            R.drawable.tab_heart_item,
            R.drawable.tab_user_item};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EventBus.getDefault().register(this);
        initView();

    }

    private void initView() {
        mTabHost = findViewById(android.R.id.tabhost);
        mTabHost.setup(this,getSupportFragmentManager(),R.id.real_tab_content);
        for(int i=0;i<mTabSpec.length;++i){
            mTabHost.addTab(mTabHost.newTabSpec(mTabSpec[i]).setIndicator(getTabView(i))
            ,mFragmentsClass[i],null);
        }
    }

    private View getTabView(int position){
        View view = LayoutInflater.from(this).inflate(R.layout.tab_item,null);
        ImageView imageView = view.findViewById(R.id.tab_image);
        TextView textView = view.findViewById(R.id.tab_title);
        imageView.setImageResource(mTabImage[position]);
        textView.setText(mIndicator[position]);
        return view;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopFloatWindowService();
        EventBus.getDefault().unregister(this);
    }


    @Subscribe
    public void onEvent(MessageEvent event){
        String url = event.getUrl();
        if(StringUtils.isNull(url))return;
        startFloatWindowService(url);
    }

    private void startFloatWindowService(String url){
        Log.e("TAG","startFloatWindowService "+url);
        if(!isSupportFloatWindow)return;
        if(!ActivityUtils.hasPermissionFloatWin(App.getContext()))return;
        Intent intent = new Intent(MainActivity.this,FloatWindowService.class);
        Bundle bundle = new Bundle();
        bundle.putString("url",url);
        intent.putExtra(FloatWindowService.ACTION_PLAY,bundle);
        startService(intent);
    }

    public void stopFloatWindowService(){
        if(FloatWindowService.mIsFloatWindowShown){
            Intent intent = new Intent(MainActivity.this,FloatWindowService.class);
            stopService(intent);
        }
    }


}
