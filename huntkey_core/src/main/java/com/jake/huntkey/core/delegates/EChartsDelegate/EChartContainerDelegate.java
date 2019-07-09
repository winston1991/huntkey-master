package com.jake.huntkey.core.delegates.EChartsDelegate;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ToastUtils;
import com.jake.huntkey.core.R;
import com.jake.huntkey.core.R2;
import com.jake.huntkey.core.delegates.EChartsDelegate.FormatEchartsDataUtil.ChartInterface;
import com.jake.huntkey.core.entity.HomePageItemEntity;
import com.jake.huntkey.core.net.WebApiServices;
import com.jake.huntkey.core.netbean.GetFpyRateResponse;
import com.jake.huntkey.core.ui.icon.Loading.DialogLoaderManager;
import com.vise.log.ViseLog;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.vise.xsnow.http.core.ApiTransformer;
import com.vise.xsnow.http.subscriber.ApiCallbackSubscriber;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import me.yokeyword.eventbusactivityscope.EventBusActivityScope;

public class EChartContainerDelegate extends BaseWebViewDelegate implements WebViewCreater.OnPageLoadFinishedListener {

    private static final String ARG_LineId = "lineId";
    @BindView(R2.id.fl_container)
    public FrameLayout flContainer;
    private ChartInterface mChartInterface;
    private String lineId;  //线体id
    private String sid; //服务器id
    private String accid; //工厂id


    public static EChartContainerDelegate newInstance(String lineId) {

        Bundle args = new Bundle();
        args.putString(ARG_LineId, lineId);
        EChartContainerDelegate fragment = new EChartContainerDelegate();
        fragment.setArguments(args);
        return fragment;
    }

    protected void initViews(View rootview) {

        mAgentWeb = new WebViewCreater(this).createAgentWeb(this, flContainer);
        //注入接口,供JS调用
        mAgentWeb.getJsInterfaceHolder().addJavaObject("Android", mChartInterface = new ChartInterface());
        Bundle bundle = getArguments();
        if (bundle != null) {
            lineId = bundle.getString(ARG_LineId);
        }
        rootview.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
            }
        });
    }


    public void loadZhiTongLvChart() {
        mAgentWeb.getJsAccessEntrace().quickCallJs("clearChart");
        mAgentWeb.getJsAccessEntrace().quickCallJs("showDiv");
        getZhiTongLvData();


    }

    private void getZhiTongLvData() {
        DialogLoaderManager.showLoading(_mActivity);
        ViseHttp.RETROFIT()
                .create(WebApiServices.class)
                .GetFpyRate(sid, lineId, accid)
                .compose(ApiTransformer.<GetFpyRateResponse>norTransformer())
                .subscribe(new ApiCallbackSubscriber<>(new ACallback<GetFpyRateResponse>() {
                    @Override
                    public void onSuccess(GetFpyRateResponse getFpyRateResponse) {
                        if (getFpyRateResponse.getStatus().equals("OK") && getFpyRateResponse.getContent().size() > 0) {
                            ArrayList<String> axisX;
                            ArrayList<String> data;
                            int size = getFpyRateResponse.getContent().get(0).getPass7DayRate().size();
                            axisX = new ArrayList<>();
                            data = new ArrayList<>();
                            for (int i = 0; i < size; i++) {
                                axisX.add(getFpyRateResponse.getContent().get(0).getPass7DayRate().get(i).getOtpt_start_time());
                                data.add(getFpyRateResponse.getContent().get(0).getPass7DayRate().get(i).getFpy_rate().substring(0, 2));
                            }
                            mAgentWeb.getJsAccessEntrace().quickCallJs("loadChartView", "chart2", mChartInterface.getZhiTongLvLvOptions2(axisX, data));

                            size = getFpyRateResponse.getContent().get(0).getPassRateTop5().size();
                            axisX = new ArrayList<>();
                            data = new ArrayList<>();
                            for (int i = 0; i < size; i++) {
                                axisX.add(getFpyRateResponse.getContent().get(0).getPassRateTop5().get(i).getLayt_name());
                                data.add(getFpyRateResponse.getContent().get(0).getPassRateTop5().get(i).getRate().substring(0, 2));
                            }
                            mAgentWeb.getJsAccessEntrace().quickCallJs("loadChartView", "chart3", mChartInterface.getZhiTongLvLvOptions3(axisX, data));

                            size = getFpyRateResponse.getContent().get(0).getLossRateTop5().size();
                            axisX = new ArrayList<>();
                            data = new ArrayList<>();
                            for (int i = 0; i < size; i++) {
                                axisX.add(getFpyRateResponse.getContent().get(0).getLossRateTop5().get(i).getLayt_name());
                                data.add(getFpyRateResponse.getContent().get(0).getLossRateTop5().get(i).getRate().substring(0, 2));
                            }
                            mAgentWeb.getJsAccessEntrace().quickCallJs("loadChartView", "chart4", mChartInterface.getZhiTongLvLvOptions4(axisX, data));

                            mAgentWeb.getJsAccessEntrace().quickCallJs("loadChartView", "chart1", mChartInterface.getZhiTongLvLvOptions1(getFpyRateResponse.getContent().get(0).getRate().substring(0, 5)));
                            DialogLoaderManager.stopLoading();
                        }
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {
                        ToastUtils.showShort(errMsg);
                        DialogLoaderManager.stopLoading();
                    }
                }));

    }


    public void loadDaChengLvChart() {
        mAgentWeb.getJsAccessEntrace().quickCallJs("clearChart");
        mAgentWeb.getJsAccessEntrace().quickCallJs("hideDiv");
        mAgentWeb.getJsAccessEntrace().quickCallJs("loadChartView", "chart1", mChartInterface.getDaChengLvOptions1());
        mAgentWeb.getJsAccessEntrace().quickCallJs("loadChartView", "chart2", mChartInterface.getDaChengLvOptions2());
        mAgentWeb.getJsAccessEntrace().quickCallJs("loadChartView", "chart3", mChartInterface.getDaChengLvOptions3());

    }

    public void loadJiaDongLvChart() {
        mAgentWeb.getJsAccessEntrace().quickCallJs("clearChart");
        mAgentWeb.getJsAccessEntrace().quickCallJs("showDiv");
        mAgentWeb.getJsAccessEntrace().quickCallJs("loadChartView", "chart4", mChartInterface.getJiaDongLvOptions4());
        mAgentWeb.getJsAccessEntrace().quickCallJs("loadChartView", "chart3", mChartInterface.getJiaDongLvOptions3());
        mAgentWeb.getJsAccessEntrace().quickCallJs("loadChartView", "chart1", mChartInterface.getJiaDongLvOptions1());
        mAgentWeb.getJsAccessEntrace().quickCallJs("loadChartView", "chart2", mChartInterface.getJiaDongLvOptions2());

    }

    public void loadChuQinLvChart() {
        mAgentWeb.getJsAccessEntrace().quickCallJs("clearChart");
        mAgentWeb.getJsAccessEntrace().quickCallJs("showDiv");
        mAgentWeb.getJsAccessEntrace().quickCallJs("loadChartView", "chart4", mChartInterface.getChuQinLvOptions4());
        mAgentWeb.getJsAccessEntrace().quickCallJs("loadChartView", "chart2", mChartInterface.getChuQinLvOptions2());
        mAgentWeb.getJsAccessEntrace().quickCallJs("loadChartView", "chart3", mChartInterface.getChuQinLvOptions3());
        mAgentWeb.getJsAccessEntrace().quickCallJs("loadChartView", "chart1", mChartInterface.getChuQinLvOptions1());

    }

    @Override
    public Object setLayout() {
        return R.layout.echarts_container_delegate_layout;
    }


    @Override
    protected void onBindView(Bundle savedInstanceState, View rootView) {
        initViews(rootView);
    }

    @Override
    public void onPageLoadFinished() {
        loadZhiTongLvChart();
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
