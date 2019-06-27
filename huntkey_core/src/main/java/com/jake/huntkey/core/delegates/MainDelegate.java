package com.jake.huntkey.core.delegates;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.jake.huntkey.core.R;

public class MainDelegate extends MainBackPressDelegate  {

    public static MainDelegate newInstance() {
        Bundle args = new Bundle();
        MainDelegate fragment = new MainDelegate();
        fragment.setArguments(args);
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
            loadRootFragment(R.id.fl_container, ViewPagerDelegate.newInstance());
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