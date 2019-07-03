package com.jake.huntkey;


import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.jake.huntkey.core.activitys.BaseActivity;
import com.jake.huntkey.core.delegates.basedelegate.MainBackPressDelegate;
import com.jake.huntkey.core.delegates.MainDelegate;

import me.yokeyword.fragmentation.SupportFragment;


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
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.main_activity_layout);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        mainDelegate = findFragment(MainDelegate.class);
        if (mainDelegate == null) {
            mainDelegate = MainDelegate.newInstance();
        }
        loadRootFragment(R.id.fl_container, mainDelegate);
    }
}
