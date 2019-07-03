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
        viewPager.setAdapter(new ViewPagerFragmentAdapter(getChildFragmentManager()));
    }

    public void goToFirstPage() {
        viewPager.setCurrentItem(0);
    }

    public boolean isFirstPage() {
        return (viewPager.getCurrentItem() == 0);
    }
}
