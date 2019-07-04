package com.jake.huntkey.core.delegates.EChartsDelegate;

import android.os.Bundle;
import android.view.View;

import android.widget.FrameLayout;
import android.widget.TextView;


import com.google.android.material.tabs.TabLayout;
import com.jake.huntkey.core.R;
import com.jake.huntkey.core.R2;
import com.jake.huntkey.core.delegates.DebugPagerFragment;
import com.jake.huntkey.core.delegates.basedelegate.BaseBackDelegate;
import com.xuexiang.xui.widget.textview.marqueen.MarqueeFactory;
import com.xuexiang.xui.widget.textview.marqueen.MarqueeView;
import com.xuexiang.xui.widget.textview.MarqueeTextView;
import com.xuexiang.xui.widget.textview.marqueen.SimpleNoticeMF;


import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import me.yokeyword.fragmentation.SupportFragment;

public class EChartsBoardDelegate extends BaseBackDelegate {
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
    @BindView(R2.id.id_marqueeview)
    MarqueeView marqueeTextView;
    @BindView(R2.id.id_material_number_tv)
    TextView material_number_tv;
    @BindView(R2.id.id_upm_tv)
    TextView upm_tv;


    private int mCurrentFragmentPostion;

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
        MarqueeFactory<TextView, String> marqueeFactory = new SimpleNoticeMF(getContext());
        String[] data = {"WBJ7941279124, WBJ53823902, WBJ8085340, WBJ9829202"};
        marqueeFactory.setData(Arrays.asList(data));
        marqueeTextView.setMarqueeFactory(marqueeFactory);
        marqueeTextView.startFlipping();
        SupportFragment firstFragment = findFragment(EChartZhiTongLvDelegate.class);
        mCurrentFragmentPostion = FIRST;
        if (firstFragment == null) {
            mFragments[FIRST] = EChartZhiTongLvDelegate.newInstance("");
            mFragments[SECOND] = EChartDaChengLvDelegate.newInstance("");
            mFragments[THIRD] = EChartSheBieJiaDongLvDelegate.newInstance("");
            mFragments[FOURTH] = EChartChuQinLvDelegate.newInstance("");
            mFragments[FIVE] = EChart_WIP_Tj_Delegate.newInstance("");

            loadMultipleRootFragment(R.id.fl_container, FIRST,
                    mFragments[FIRST],
                    mFragments[SECOND],
                    mFragments[THIRD],
                    mFragments[FOURTH], mFragments[FIVE]);
        } else {
            // 这里库已经做了Fragment恢复,所有不需要额外的处理了, 不会出现重叠问题这里我们需要拿到mFragments的引用
            mFragments[FIRST] = firstFragment;
            mFragments[SECOND] = findFragment(EChartDaChengLvDelegate.class);
            mFragments[THIRD] = findFragment(EChartSheBieJiaDongLvDelegate.class);
            mFragments[FOURTH] = findFragment(EChartChuQinLvDelegate.class);
            mFragments[FIVE] = findFragment(EChart_WIP_Tj_Delegate.class);
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
        tabLayout.addTab(tabLayout.newTab().setText("稼动率"));
        tabLayout.addTab(tabLayout.newTab().setText("出勤率"));
        tabLayout.addTab(tabLayout.newTab().setText("WIP统计"));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                if (tab.getPosition() != mCurrentFragmentPostion) {
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
