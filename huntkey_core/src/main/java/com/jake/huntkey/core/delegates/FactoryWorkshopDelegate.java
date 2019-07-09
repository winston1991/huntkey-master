package com.jake.huntkey.core.delegates;

import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.jake.huntkey.core.R;
import com.jake.huntkey.core.R2;
import com.jake.huntkey.core.adapter.FactoryViewPagerFragmentAdapter;
import com.jake.huntkey.core.delegates.basedelegate.BaseBackDelegate;
import com.jake.huntkey.core.delegates.basedelegate.MainBackPressDelegate;
import com.jake.huntkey.core.ui.icon.HKIcons;
import com.joanzapata.iconify.IconDrawable;

import butterknife.BindView;


public class FactoryWorkshopDelegate extends BaseBackDelegate {
    private static final String ARG_TYPE = "arg_type";

    private String mTitle;
    @BindView(R2.id.id_vipager)
    protected ViewPager viewPager;
    @BindView(R2.id.id_tablayout)
    protected TabLayout tabLayout;
    FactoryViewPagerFragmentAdapter factoryViewPagerFragmentAdapter;

    public static FactoryWorkshopDelegate newInstance(String title) {

        Bundle args = new Bundle();
        args.putString(ARG_TYPE, title);
        FactoryWorkshopDelegate fragment = new FactoryWorkshopDelegate();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTitle = getArguments().getString(ARG_TYPE);

    }

    @Override
    public Object setLayout() {
        return R.layout.factory_workshop_layout;
    }


    @Override
    protected void onBindView(Bundle savedInstanceState, View rootView) {
        initView(rootView);
    }

    protected void initView(View view) {
        super.initView(view);
        mToolbar.setTitle(mTitle);
        factoryViewPagerFragmentAdapter = new FactoryViewPagerFragmentAdapter(getFragmentManager(), new String[]{"看板", "预警", "我的"}, mTitle);
        viewPager.setAdapter(factoryViewPagerFragmentAdapter);
        tabLayout.setupWithViewPager(viewPager);//将TabLayout与Viewpager联动起来
        //图片状态选择器
        StateListDrawable boardDrawable = new StateListDrawable();
        boardDrawable.addState(new int[]{android.R.attr.state_selected}, new IconDrawable(getContext(), HKIcons.icon_board).colorRes(R.color.colorPrimary));
        boardDrawable.addState(new int[]{}, new IconDrawable(getContext(), HKIcons.icon_board));
        tabLayout.getTabAt(0).setIcon(boardDrawable);
        StateListDrawable prewaringDrawable = new StateListDrawable();
        prewaringDrawable.addState(new int[]{android.R.attr.state_selected}, new IconDrawable(getContext(), HKIcons.icon_prewarning).colorRes(R.color.colorPrimary));
        prewaringDrawable.addState(new int[]{}, new IconDrawable(getContext(), HKIcons.icon_prewarning));
        tabLayout.getTabAt(1).setIcon(prewaringDrawable);
        StateListDrawable mineDrawable = new StateListDrawable();
        mineDrawable.addState(new int[]{android.R.attr.state_selected}, new IconDrawable(getContext(), HKIcons.icon_mine).colorRes(R.color.colorPrimary));
        mineDrawable.addState(new int[]{}, new IconDrawable(getContext(), HKIcons.icon_mine));
        tabLayout.getTabAt(2).setIcon(mineDrawable);


    }
}
