package com.jake.huntkey.core.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.jake.huntkey.core.delegates.DebugPagerFragment;
import com.jake.huntkey.core.delegates.HomePageDelegate;

import me.yokeyword.fragmentation.SupportFragment;


public class ViewPagerFragmentAdapter extends FragmentPagerAdapter {
    private String[] mTitles;

    public ViewPagerFragmentAdapter(FragmentManager fm, String... titles) {
        super(fm);
        mTitles = titles;
    }

    @Override
    public SupportFragment getItem(int position) {
        if (position == 0) {
            return HomePageDelegate.newInstance();
        } else {
            return DebugPagerFragment.newInstance(mTitles[position]);
        }

    }

    @Override
    public int getCount() {
        return mTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}
