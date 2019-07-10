package com.jake.huntkey.core.delegates.EChartsDelegate;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.blankj.utilcode.util.ToastUtils;
import com.google.android.material.tabs.TabLayout;
import com.jake.huntkey.core.R;
import com.jake.huntkey.core.R2;
import com.jake.huntkey.core.delegates.basedelegate.BaseBackDelegate;
import com.jake.huntkey.core.entity.HomePageItemEntity;
import com.jake.huntkey.core.net.WebApiServices;
import com.jake.huntkey.core.netbean.GetNbrInfoResponse;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.vise.xsnow.http.core.ApiTransformer;
import com.vise.xsnow.http.subscriber.ApiCallbackSubscriber;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import me.yokeyword.eventbusactivityscope.EventBusActivityScope;
import me.yokeyword.fragmentation.SupportFragment;

public class EChartsBoardDelegate extends BaseBackDelegate {
    public static final int FIRST = 0;

    private static final String ARG_TITLE = "arg_type";
    private static final String ARG_LineID = "lineId";
    private static final String ARG_DeptCode = "DeptCode";
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
    TextView idUpmTv;


    private int mCurrentFragmentPostion = 0;
    private SupportFragment mCurrentFragment;
    private SupportFragment mFragment;
    private String lineId; //线体id
    private String accid; //工厂id
    private String sid;// 服务器id
    private String deptCode;  //部门code


    public static EChartsBoardDelegate newInstance(String title, String lineId, String deptCode) {

        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        args.putString(ARG_LineID, lineId);
        args.putString(ARG_DeptCode, deptCode);
        EChartsBoardDelegate fragment = new EChartsBoardDelegate();
        fragment.setArguments(args);
        return fragment;
    }

    protected void initViews(View rootview) {
        super.initView(rootview);
        Bundle bundle = getArguments();
        if (bundle != null) {
            String mTitle = bundle.getString(ARG_TITLE);
            lineId = bundle.getString(ARG_LineID);
            deptCode = bundle.getString(ARG_DeptCode);
            super.mToolbar.setTitle(mTitle);
        }
        getData();
        mCurrentFragment = EChartContainerDelegate.newInstance(lineId, deptCode);
        mFragment = EChart_WIP_Tj_Delegate.newInstance(lineId);
        //加载chart图表和wip统计两个fragment
        loadMultipleRootFragment(R.id.fl_container, 0, mCurrentFragment, mFragment);

    }

    private void getData() {
        ViseHttp.RETROFIT()
                .create(WebApiServices.class)
                .GetNbrInfo(sid, lineId, accid)
                .compose(ApiTransformer.<GetNbrInfoResponse>norTransformer())
                .subscribe(new ApiCallbackSubscriber<>(new ACallback<GetNbrInfoResponse>() {
                    @Override
                    public void onSuccess(GetNbrInfoResponse data) {
                        if (data != null && data.getContent() != null && data.getStatus().equals("OK") && data.getContent().size() > 0) {
                            marqueeTextView.setText(data.getContent().get(0).getOtpt_wo_nbr());
                            material_number_marqueenview.setText(data.getContent().get(0).getOtpt_part());
                            idUpmTv.setText("UPM: " + data.getContent().get(0).getUpm());
                        }
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {
                        ToastUtils.showShort(errMsg);
                    }
                }));
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


    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEvent(HomePageItemEntity homePageItemEntity) {
        sid = homePageItemEntity.sid;
        accid = homePageItemEntity.accid;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        EventBusActivityScope.getDefault(_mActivity).register(this);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        EventBusActivityScope.getDefault(_mActivity).unregister(this);
        super.onDestroyView();
    }
}
