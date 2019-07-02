package com.jake.huntkey.core.adapter;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.jake.huntkey.core.delegates.DebugPagerFragment;
import com.jake.huntkey.core.delegates.HomePageDelegate;
import com.jake.huntkey.core.delegates.ProductionLineListViewDelegate;

import me.yokeyword.fragmentation.SupportFragment;


public class FactoryViewPagerFragmentAdapter extends FragmentPagerAdapter {

    String[] items;

    public FactoryViewPagerFragmentAdapter(FragmentManager fm, String[] items) {
        super(fm);
        this.items = items;
    }

    @Override
    public SupportFragment getItem(int position) {
        if (position == 0) {
            return ProductionLineListViewDelegate.newInstance("");
        } else {
            return DebugPagerFragment.newInstance(items[position]);
        }

    }

    @Override
    public int getCount() {
        return items.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return items[position];
    }
}
