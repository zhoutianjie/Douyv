package com.ztj.douyu.main.view;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.ztj.douyu.R;

public class MainActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private RadioGroup mRadioGroup;
    private RadioButton mRadioButtonHome;
    private RadioButton mRadioButtonClassify;
    private RadioButton mRadioButtonlike;
    private RadioButton mRadioButtonMine;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //initView();
    }
}
