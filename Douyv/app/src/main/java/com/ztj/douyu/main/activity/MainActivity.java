package com.ztj.douyu.main.activity;


import android.content.Intent;
import android.support.v4.app.FragmentTabHost;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import android.widget.TextView;

import com.ztj.douyu.R;
import com.ztj.douyu.main.fragment.ClassifyFragment;
import com.ztj.douyu.main.fragment.FavourFragment;
import com.ztj.douyu.main.fragment.HomeFragment;
import com.ztj.douyu.main.fragment.MineFragment;

public class MainActivity extends AppCompatActivity {

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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
