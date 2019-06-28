package com.jake.huntkey.core.delegates.EChartsDelegate;

import android.os.Bundle;
import android.view.View;

import android.widget.FrameLayout;


import com.blankj.utilcode.util.ToastUtils;
import com.google.android.material.tabs.TabLayout;
import com.jake.huntkey.core.R;
import com.jake.huntkey.core.R2;
import com.jake.huntkey.core.delegates.DebugPagerFragment;
import com.jake.huntkey.core.delegates.basedelegate.BaseBackDelegate;


import butterknife.BindView;
import me.yokeyword.fragmentation.SupportFragment;

public class EChartsAndroidDelegate extends BaseBackDelegate {
    public static final int FIRST = 0;
    public static final int SECOND = 1;
    public static final int THIRD = 2;
    public static final int FOURTH = 3;
    public static final int FIVE = 4;
    private SupportFragment[] mFragments = new SupportFragment[5];


    private static final String ARG_TITLE = "arg_type";
    @BindView(R2.id.fl_container)
    public FrameLayout flContainer;
    @BindView(R2.id.id_tablayout)
    TabLayout tabLayout;

    private int mCurrentFragmentPostion;

    public static EChartsAndroidDelegate newInstance(String title) {

        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        EChartsAndroidDelegate fragment = new EChartsAndroidDelegate();
        fragment.setArguments(args);
        return fragment;
    }

    protected void initViews(View rootview) {

        super.initView(rootview);
        Bundle bundle = getArguments();
        if (bundle != null) {
            String mTitle = bundle.getString(ARG_TITLE);
            super.mToolbar.setTitle(mTitle);
        }
        SupportFragment firstFragment = findFragment(EChartZhiTongLvDelegate.class);
        mCurrentFragmentPostion = FIRST;
        if (firstFragment == null) {
            mFragments[FIRST] = EChartZhiTongLvDelegate.newInstance("");
            mFragments[SECOND] = EChartDaChengLvDelegate.newInstance("");
            mFragments[THIRD] = DebugPagerFragment.newInstance("2");
            mFragments[FOURTH] = DebugPagerFragment.newInstance("3");
            mFragments[FIVE] = DebugPagerFragment.newInstance("4");

            loadMultipleRootFragment(R.id.fl_container, FIRST,
                    mFragments[FIRST],
                    mFragments[SECOND],
                    mFragments[THIRD],
                    mFragments[FOURTH], mFragments[FIVE]);
        } else {
            // 这里库已经做了Fragment恢复,所有不需要额外的处理了, 不会出现重叠问题这里我们需要拿到mFragments的引用
            mFragments[FIRST] = firstFragment;
            mFragments[SECOND] = findFragment(EChartDaChengLvDelegate.class);
            mFragments[THIRD] = findFragment(DebugPagerFragment.class);
            mFragments[FOURTH] = findFragment(DebugPagerFragment.class);
            mFragments[FIVE] = findFragment(DebugPagerFragment.class);
        }
    }

    @Override
    public Object setLayout() {
        return R.layout.echarts_delegate_layout;
    }


    @Override
    protected void onBindView(Bundle savedInstanceState, View rootView) {
        initViews(rootView);
        tabLayout.addTab(tabLayout.newTab().setText("直通率"));
        tabLayout.addTab(tabLayout.newTab().setText("达成率"));
        tabLayout.addTab(tabLayout.newTab().setText("嫁动率"));
        tabLayout.addTab(tabLayout.newTab().setText("出勤率"));
        tabLayout.addTab(tabLayout.newTab().setText("WIP统计"));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                if (tab.getPosition() != mCurrentFragmentPostion) {
                    if (tab.getPosition() == 1) {
                       // ((EChartDaChengLvDelegate) mFragments[tab.getPosition()]).button.performClick();
                    }
                    showHideFragment(mFragments[tab.getPosition()], mFragments[mCurrentFragmentPostion]);
                    mCurrentFragmentPostion = tab.getPosition();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }


}
