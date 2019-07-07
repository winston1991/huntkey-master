package com.jake.huntkey.core.delegates.EChartsDelegate;

import android.os.Bundle;
import android.view.View;

import android.widget.FrameLayout;
import android.widget.TextView;


import androidx.appcompat.widget.AppCompatTextView;

import com.google.android.material.tabs.TabLayout;
import com.jake.huntkey.core.R;
import com.jake.huntkey.core.R2;
import com.jake.huntkey.core.delegates.basedelegate.BaseBackDelegate;


import butterknife.BindView;
import me.yokeyword.fragmentation.SupportFragment;

public class EChartsBoardDelegate extends BaseBackDelegate {
    public static final int FIRST = 0;

    private static final String ARG_TITLE = "arg_type";
    @BindView(R2.id.fl_container)
    public FrameLayout flContainer;
    @BindView(R2.id.id_tablayout)
    TabLayout tabLayout;
    @BindView(R2.id.id_marqueeview)
    public AppCompatTextView marqueeTextView;
    @BindView(R2.id.id_marqueeview_material_number)
    public AppCompatTextView material_number_marqueenview;

    @BindView(R2.id.id_material_number_tv)
    TextView material_number_tv;
    @BindView(R2.id.id_upm_tv)
    TextView upm_tv;


    private int mCurrentFragmentPostion = 0;
    private SupportFragment mCurrentFragment;
    private SupportFragment mFragment;

    public static EChartsBoardDelegate newInstance(String title) {

        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        EChartsBoardDelegate fragment = new EChartsBoardDelegate();
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
        marqueeTextView.setText("WBJ7941279124, WBJ53823902, WBJ8085340, WBJ9829202");
        material_number_marqueenview.setText("     P23-324657M78S6, P23-324657M79S6, P23-324657M78S7");
        mCurrentFragment = EChartContainerDelegate.newInstance("");
        mFragment = EChart_WIP_Tj_Delegate.newInstance("");
        loadMultipleRootFragment(R.id.fl_container, 0, mCurrentFragment, mFragment );

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
        tabLayout.addTab(tabLayout.newTab().setText("稼动率"));
        tabLayout.addTab(tabLayout.newTab().setText("出勤率"));
        tabLayout.addTab(tabLayout.newTab().setText("WIP统计"));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                if (tab.getPosition() != mCurrentFragmentPostion) {
                    switch (tab.getPosition()) {
                        case 0:

                            showHideFragment(mCurrentFragment, mFragment);
                            ((EChartContainerDelegate) mCurrentFragment).loadZhiTongLvChart();

                            break;
                        case 1:
                            showHideFragment(mCurrentFragment, mFragment);

                            ((EChartContainerDelegate) mCurrentFragment).loadDaChengLvChart();
                            break;
                        case 2:
                            showHideFragment(mCurrentFragment, mFragment);

                            ((EChartContainerDelegate) mCurrentFragment).loadJiaDongLvChart();
                            break;
                        case 3:
                            showHideFragment(mCurrentFragment, mFragment);

                            ((EChartContainerDelegate) mCurrentFragment).loadChuQinLvChart();
                            break;
                        case 4:
                            showHideFragment(mFragment, mCurrentFragment);
                            break;

                    }
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
