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
    String factoryName;

    public FactoryViewPagerFragmentAdapter(FragmentManager fm, String[] items, String factoryName) {
        super(fm);
        this.items = items;
        this.factoryName = factoryName;
    }

    @Override
    public SupportFragment getItem(int position) {
        if (position == 0) {
            return ProductionLineListViewDelegate.newInstance(factoryName);
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
