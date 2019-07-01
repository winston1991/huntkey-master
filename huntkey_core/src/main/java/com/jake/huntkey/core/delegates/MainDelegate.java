package com.jake.huntkey.core.delegates;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.jake.huntkey.core.R;
import com.jake.huntkey.core.delegates.basedelegate.MainBackPressDelegate;

public class MainDelegate extends MainBackPressDelegate {

    public  ViewPagerDelegate viewPagerDelegate;

    public static MainDelegate newInstance() {
        MainDelegate fragment = new MainDelegate();
        return fragment;
    }

    @Override
    public Object setLayout() {
        return R.layout.main_delegate_layout;
    }


    @Override
    protected void onBindView(Bundle savedInstanceState, View rootView) {

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (findChildFragment(ViewPagerDelegate.class) == null) {
            viewPagerDelegate = ViewPagerDelegate.newInstance();
            loadRootFragment(R.id.fl_container, viewPagerDelegate);
        }
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
    }

    @Override
    public boolean onBackPressedSupport() {
        return super.onBackPressedSupport();
    }
}
