package com.jake.huntkey.core.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.jake.huntkey.core.delegates.DebugPagerFragment;
import com.jake.huntkey.core.delegates.HomePageDelegate;

import me.yokeyword.fragmentation.SupportFragment;


public class ViewPagerFragmentAdapter extends FragmentPagerAdapter {
    private String[] mTitles;

    public ViewPagerFragmentAdapter(FragmentManager fm) {
        super(fm);

    }

    @Override
    public SupportFragment getItem(int position) {
            return HomePageDelegate.newInstance();
    }

    @Override
    public int getCount() {
        return 1;
    }


}
