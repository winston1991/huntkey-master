package com.jake.huntkey.core.delegates.EChartsDelegate;

import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

import com.blankj.utilcode.util.ToastUtils;
import com.jake.huntkey.core.R;
import com.jake.huntkey.core.R2;
import com.jake.huntkey.core.delegates.EChartsDelegate.FormatEchartsDataUtil.ChartInterface;

import butterknife.BindView;

public class EChartContainerDelegate extends BaseWebViewDelegate implements WebViewCreater.OnPageLoadFinishedListener {

    private static final String ARG_TITLE = "arg_type";
    @BindView(R2.id.fl_container)
    public FrameLayout flContainer;
    private ChartInterface mChartInterface;


    public static EChartContainerDelegate newInstance(String title) {

        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
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
            String mTitle = bundle.getString(ARG_TITLE);
        }

        rootview.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                ToastUtils.showShort("ggg");
            }
        });
    }


    public void loadZhiTongLvChart() {
        mAgentWeb.getJsAccessEntrace().quickCallJs("clearChart");
        mAgentWeb.getJsAccessEntrace().quickCallJs("showDiv");
        mAgentWeb.getJsAccessEntrace().quickCallJs("loadChartView", "chart4", mChartInterface.getZhiTongLvLvOptions4());
        mAgentWeb.getJsAccessEntrace().quickCallJs("loadChartView", "chart2", mChartInterface.getZhiTongLvLvOptions2());
        mAgentWeb.getJsAccessEntrace().quickCallJs("loadChartView", "chart3", mChartInterface.getZhiTongLvLvOptions3());
        mAgentWeb.getJsAccessEntrace().quickCallJs("loadChartView", "chart1", mChartInterface.getZhiTongLvLvOptions1());


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
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    protected void onBindView(Bundle savedInstanceState, View rootView) {
        initViews(rootView);
    }

    @Override
    public void onPageLoadFinished() {
        loadZhiTongLvChart();
    }


}
