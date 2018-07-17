package com.ztj.douyu.main.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ztj.douyu.R;
import com.ztj.douyu.db.GameTypeInfo;
import com.ztj.douyu.main.adapter.ContentFragmentPagerAdapter;
import com.ztj.douyu.main.presenter.HomePresenter;
import com.ztj.douyu.main.view.onHomeView;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements onHomeView {

    private View rootView;
    private TabLayout tabLayout;
    private ViewPager contentViewPager;
    private HomePresenter presenter;
    private ContentFragmentPagerAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new HomePresenter();
        presenter.attachView(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if(rootView == null){
            rootView = inflater.inflate(R.layout.fragment_home,null,false);
            initView();
            presenter.getFrequentGameType();

        }else{
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if(parent!=null){
                parent.removeView(rootView);
            }
        }
        return rootView;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    private void initView() {
        tabLayout = rootView.findViewById(R.id.tab);
        contentViewPager = rootView.findViewById(R.id.content_vp);
    }

    @Override
    public void GetFrequentGameTypes(final List<GameTypeInfo> gameTypeList) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
               if(gameTypeList ==null || gameTypeList.size()==0)return;
                List<Fragment> contentFragments = new ArrayList<>();
               for(GameTypeInfo gameTypeInfo:gameTypeList){
                   tabLayout.addTab(tabLayout.newTab());
                   ContentFragment fragment = new ContentFragment();
                   Bundle bundle = new Bundle();
                   bundle.putString("gameName",gameTypeInfo.getGameTypeName());
                   fragment.setArguments(bundle);
                   contentFragments.add(fragment);
               }
                adapter = new ContentFragmentPagerAdapter(getFragmentManager(),contentFragments);
                contentViewPager.setAdapter(adapter);
                tabLayout.setupWithViewPager(contentViewPager);
                //不在setupWithViewpager之后设置title会导致tab为空title
                for(int i=0;i<tabLayout.getTabCount();++i){
                   tabLayout.getTabAt(i).setText(gameTypeList.get(i).getGameTypeName());
               }
            }
        });
    }

}
