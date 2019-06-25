package com.jake.huntkey;


import android.os.Bundle;

import com.jake.huntkey.core.activitys.BaseActivity;
import com.jake.huntkey.core.delegates.MainDelegate;

import me.yokeyword.fragmentation.SupportFragment;


public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_delegate_layout);
        SupportFragment mainDelegate = findFragment(MainDelegate.class);
        if(mainDelegate ==null)
        {
               mainDelegate = MainDelegate.newInstance();
        }
        loadRootFragment(R.id.fl_container, mainDelegate);
    }


    @Override
    public void onBackPressedSupport() {
        super.onBackPressedSupport();
    }
}
