package com.jake.huntkey.core.activitys;


import android.os.Bundle;

import com.jake.huntkey.core.R;
import com.jake.huntkey.core.delegates.MainDelegate;
import com.jake.huntkey.core.delegates.basedelegate.MainBackPressDelegate;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;


public class MainActivity extends BaseActivity implements MainBackPressDelegate.OnBackToFirstListener {

    MainDelegate mainDelegate;

    @Override
    public void onBackPressedSupport() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            pop();
        } else {
            ActivityCompat.finishAfterTransition(this);
        }
    }

    @Override
    public void onBackToFirstFragment() {
        mainDelegate.viewPagerDelegate.goToFirstPage();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainDelegate = findFragment(MainDelegate.class);
        if (mainDelegate == null) {
            mainDelegate = MainDelegate.newInstance();
        }
        loadRootFragment(R.id.fl_container, mainDelegate);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected int setLayoutId() {
        return R.layout.main_activity_layout;
    }
}
