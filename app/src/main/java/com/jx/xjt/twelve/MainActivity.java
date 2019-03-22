package com.jx.xjt.twelve;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;


import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.jx.xjt.twelve.activity.MVPActivity;
import com.jx.xjt.twelve.fragment.HomeFragment;
import com.jx.xjt.twelve.presenter.MainPresenter;
import com.jx.xjt.twelve.view.IMainView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends MVPActivity<MainPresenter> implements IMainView, ViewPager.OnPageChangeListener, BottomNavigationBar.OnTabSelectedListener {

  @BindView(R.id.bottom_navigation_bar_container)
          ViewPager viewPager;
  @BindView(R.id.bottom_navigation_bar)
          BottomNavigationBar bottomNavigationBar;


    List<Fragment> fragmentList;
    private static final String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       ButterKnife.bind(this);
        init();

    }

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter(this);
    }

        @Override
    protected void initToolbar(Toolbar toolbar) {
            toolbar.setTitle("M10模块演示系统");

      super.initToolbar(toolbar);
    }


    private void init() {

        fragmentList = new ArrayList<>();
        fragmentList.add(new HomeFragment());
        fragmentList.add(new HomeFragment());
        fragmentList.add(new HomeFragment());
        Log.e(TAG, "init: "+viewPager );
        viewPager.setAdapter(new SectionsPageAdapter(getSupportFragmentManager(),fragmentList));
        viewPager.addOnPageChangeListener(this);
        viewPager.setCurrentItem(0);

        bottomNavigationBar.setTabSelectedListener(this);
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        bottomNavigationBar.setActiveColor(R.color.colorPrimary);
        bottomNavigationBar.setInActiveColor(R.color.colorPrimaryDark);
        bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        bottomNavigationBar.addItem(new BottomNavigationItem(R.drawable.icon_juji, "主页"))
                .addItem(new BottomNavigationItem(R.drawable.icon_set_nor, "设置"))
                .addItem(new BottomNavigationItem(R.drawable.icon_yuanchuang, "我的"))
                .setFirstSelectedPosition(0)
                .initialise();
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        bottomNavigationBar.selectTab(i);
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }


    @Override
    public void onTabSelected(int position) {
viewPager.setCurrentItem(position);
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }


    private class SectionsPageAdapter extends FragmentPagerAdapter {
        List<Fragment> fragments;
        public SectionsPageAdapter(FragmentManager fm, List<Fragment> fragments){
            super(fm);
            this.fragments = fragments;
        }
        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public Fragment getItem(int i) {
            return fragments.get(i);
        }


    }
}
