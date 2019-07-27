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

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.android.material.tabs.TabLayout;
import com.jake.huntkey.core.R;
import com.jake.huntkey.core.R2;
import com.jake.huntkey.core.app.Configurator;
import com.jake.huntkey.core.app.Consts;
import com.jake.huntkey.core.delegates.basedelegate.BaseBackDelegate;
import com.jake.huntkey.core.entity.HomePageItemEntity;
import com.jake.huntkey.core.net.WebApiServices;
import com.jake.huntkey.core.netbean.GetEmpRateResponse;
import com.jake.huntkey.core.netbean.GetJdRateResponse;
import com.jake.huntkey.core.netbean.GetNbrInfoResponse;
import com.jake.huntkey.core.netbean.GetQueryWarnResponse;
import com.jake.huntkey.core.netbean.GetTcrRateResponse;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.vise.xsnow.http.core.ApiTransformer;
import com.vise.xsnow.http.subscriber.ApiCallbackSubscriber;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import me.yokeyword.eventbusactivityscope.EventBusActivityScope;
import me.yokeyword.fragmentation.SupportFragment;

public class EChartsBoardDelegate extends BaseBackDelegate {

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
    private SupportFragment mEchartDelegate;
    private SupportFragment mWIPDelegate;
    private SupportFragment mJiePaiDelegate;
    private SupportFragment mWorkStationMonitorDelegate;
    private SupportFragment mCurrentDelegate;
    private String lineId; //线体id
    private String accid; //工厂id
    private String sid;// 服务器id
    private String deptCode;  //部门code
    private GetJdRateResponse mGetJdRateResponse;  //稼动率数据
    private GetEmpRateResponse mGetEmpRateResponse; //出勤率数据
    private GetTcrRateResponse mGetTcrRateResponse; //达成率数据


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
        getBoardData();
        ConcatRequest();
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        mCurrentDelegate = mEchartDelegate = EChartContainerDelegate.newInstance(lineId, deptCode);
        mWIPDelegate = EChart_WIP_Tj_Delegate.newInstance(lineId);
        mJiePaiDelegate = JiePaiDelegate.newInstance(lineId);
        mWorkStationMonitorDelegate = WorkStationMonitorDelegate.newInstance(lineId);
        //加载chart图表和wip统计.节拍三个fragment
        loadMultipleRootFragment(R.id.fl_container, 0, mEchartDelegate, mWIPDelegate, mJiePaiDelegate, mWorkStationMonitorDelegate);

    }

    private void getBoardData() {
        ApiCallbackSubscriber disposable = new ApiCallbackSubscriber<>(new ACallback<GetNbrInfoResponse>() {
            @Override
            public void onSuccess(GetNbrInfoResponse data) {
                if (data != null && data.getContent() != null && data.getStatus().equals("OK") && data.getContent().size() > 0) {
                    marqueeTextView.setText(data.getContent().get(0).getOtpt_wo_nbr());//设置工单
                    material_number_marqueenview.setText(data.getContent().get(0).getOtpt_part());//设置料号
                    idUpmTv.setText("UPM: " + data.getContent().get(0).getUpm());
                }
            }

            @Override
            public void onFail(int errCode, String errMsg) {
                ToastUtils.showShort(errMsg);
            }
        });
        ViseHttp.RETROFIT()
                .create(WebApiServices.class)
                .GetNbrInfo(sid, lineId, accid)
                .compose(ApiTransformer.<GetNbrInfoResponse>norTransformer())
                .subscribe(disposable);
        ViseHttp.addDisposable("GetNbrInfo", disposable);
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
        tabLayout.addTab(tabLayout.newTab().setText("节拍"));
        tabLayout.addTab(tabLayout.newTab().setText("工站监控"));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                if (tab.getPosition() != mCurrentFragmentPostion) {
                    switch (tab.getPosition()) {
                        case 0:
                            showHideFragment(mEchartDelegate, mCurrentDelegate);
                            ((EChartContainerDelegate) mEchartDelegate).loadZhiTongLvChart();
                            mCurrentDelegate = mEchartDelegate;
                            break;
                        case 1:
                            showHideFragment(mEchartDelegate, mCurrentDelegate);
                            ((EChartContainerDelegate) mEchartDelegate).loadDaChengLvChart(mGetTcrRateResponse);
                            mCurrentDelegate = mEchartDelegate;
                            break;
                        case 2:
                            showHideFragment(mEchartDelegate, mCurrentDelegate);
                            ((EChartContainerDelegate) mEchartDelegate).loadJiaDongLvChart(mGetJdRateResponse);
                            mCurrentDelegate = mEchartDelegate;
                            break;
                        case 3:
                            showHideFragment(mEchartDelegate, mCurrentDelegate);
                            ((EChartContainerDelegate) mEchartDelegate).loadChuQinLvChart(mGetEmpRateResponse);
                            mCurrentDelegate = mEchartDelegate;
                            break;
                        case 4:
                            showHideFragment(mWIPDelegate, mCurrentDelegate);
                            mCurrentDelegate = mWIPDelegate;
                            break;
                        case 5:
                            showHideFragment(mJiePaiDelegate, mCurrentDelegate);
                            mCurrentDelegate = mJiePaiDelegate;
                            break;
                        case 6:
                            showHideFragment(mWorkStationMonitorDelegate, mCurrentDelegate);
                            mCurrentDelegate = mWorkStationMonitorDelegate;
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


    /**
     * 接收从HomePagerDelegate发送来的信息
     *
     * @param homePageItemEntity
     */
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
        ViseHttp.cancelTag("ConcatRequest");
        ViseHttp.cancelTag("GetNbrInfo");
    }


    /**
     * 合并四个网络请求  达成率  稼动率  出勤率  仪表盘颜色区间值
     */
    private void ConcatRequest() {
        ApiCallbackSubscriber disposable = new ApiCallbackSubscriber<>(new ACallback<Object>() {
            @Override
            public void onSuccess(Object object) {
                if (object instanceof GetJdRateResponse) {
                    mGetJdRateResponse = (GetJdRateResponse) object;
                } else if (object instanceof GetEmpRateResponse) {
                    mGetEmpRateResponse = (GetEmpRateResponse) object;
                } else if (object instanceof GetTcrRateResponse) {
                    mGetTcrRateResponse = (GetTcrRateResponse) object;
                }
            }

            @Override
            public void onFail(int errCode, String errMsg) {
            }
        });
        String deptCodes = SPUtils.getInstance(Consts.SP_INSTANT_NAME).getString(Consts.SP_ITEM_DEPTCODE_NAME);
        Observable<GetTcrRateResponse> observable2 = ViseHttp.RETROFIT().create(WebApiServices.class).GetTcrRate(sid, lineId, accid).subscribeOn(Schedulers.io()); //达成率
        Observable<GetJdRateResponse> observable3 = ViseHttp.RETROFIT().create(WebApiServices.class).GetJdRate(sid, lineId, accid).subscribeOn(Schedulers.io()); // 稼动率
        Observable<GetEmpRateResponse> observable4 = ViseHttp.RETROFIT().create(WebApiServices.class).GetEmpRate(deptCode, deptCodes).subscribeOn(Schedulers.io()); //出勤率
        Observable.concat( observable2, observable3, observable4)
                .subscribe(disposable);

        ViseHttp.addDisposable("ConcatRequest", disposable);
    }
}
