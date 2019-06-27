package com.jake.huntkey.core.delegates;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.jake.huntkey.core.R;
import com.jake.huntkey.core.R2;
import com.jake.huntkey.core.adapter.ViewPagerFragmentAdapter;
import com.jake.huntkey.core.delegates.basedelegate.CheckPermissionDelegate;

import butterknife.BindView;

public class ViewPagerDelegate extends CheckPermissionDelegate {

    @BindView(R2.id.view_pager)
    ViewPager viewPager;
    @BindView(R2.id.navigation)
    BottomNavigationView navigation;

    public static ViewPagerDelegate newInstance() {
        ViewPagerDelegate fragment = new ViewPagerDelegate();
        return fragment;
    }

    @Override
    public Object setLayout() {
        return R.layout.viewpager_delegate_layout;
    }


    @Override
    protected void onBindView(Bundle savedInstanceState, View rootView) {
        initView(rootView);
    }

    private void initView(View view) {
        viewPager.setAdapter(new ViewPagerFragmentAdapter(getChildFragmentManager(),
                "首页", "发现", "流程", "我的 "));
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                //这里设置为：当点击到某子项，ViewPager就滑动到对应位置
                int i = item.getItemId();
                if (i == R.id.navigation_home) {
                    viewPager.setCurrentItem(0);
                    return true;
                } else if (i == R.id.navigation_dashboard) {
                    viewPager.setCurrentItem(1);
                    return true;
                } else if (i == R.id.navigation_notifications) {
                    viewPager.setCurrentItem(2);
                    return true;
                } else if (i == R.id.navigation_setting) {
                    viewPager.setCurrentItem(3);
                    return true;
                }
                return false;
            }
        });


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {
                navigation.getMenu().getItem(position).setChecked(true);
            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
