package com.jake.huntkey.core.activitys;


import android.os.Bundle;

import com.blankj.utilcode.util.ToastUtils;
import com.jake.huntkey.core.R;
import com.jake.huntkey.core.delegates.MainDelegate;
import com.jake.huntkey.core.delegates.basedelegate.MainBackPressDelegate;
import com.jake.huntkey.core.netbean.LoginResponse;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import me.yokeyword.eventbusactivityscope.EventBusActivityScope;


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
        EventBus.getDefault().register(this);
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

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    /**
     * 接收从LoginActivity发送来的信息
     *
     * @param factorys
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEvent(List<LoginResponse.Factorys> factorys) {

        EventBusActivityScope.getDefault(MainActivity.this).postSticky(factorys);
    }



}
