package com.jake.huntkey.core.delegates.EChartsDelegate;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ToastUtils;
import com.github.abel533.echarts.DataZoom;
import com.github.abel533.echarts.Legend;
import com.github.abel533.echarts.Title;
import com.github.abel533.echarts.Tooltip;
import com.github.abel533.echarts.axis.AxisLabel;
import com.github.abel533.echarts.axis.CategoryAxis;
import com.github.abel533.echarts.axis.ValueAxis;
import com.github.abel533.echarts.code.AxisType;
import com.github.abel533.echarts.code.DataZoomType;
import com.github.abel533.echarts.code.Position;
import com.github.abel533.echarts.code.Trigger;
import com.github.abel533.echarts.data.Data;
import com.github.abel533.echarts.data.PieData;
import com.github.abel533.echarts.json.GsonOption;
import com.github.abel533.echarts.series.Bar;
import com.github.abel533.echarts.series.Gauge;
import com.github.abel533.echarts.series.Line;
import com.github.abel533.echarts.series.Pie;
import com.github.abel533.echarts.series.gauge.Detail;
import com.google.android.material.tabs.TabLayout;
import com.jake.huntkey.core.R;
import com.jake.huntkey.core.R2;
import com.jake.huntkey.core.delegates.EChartsDelegate.FormatEchartsDataUtil.GetChartsOptionString;

import java.util.ArrayList;

import butterknife.BindView;

public class EChartZhiTongLvDelegate extends BaseWebViewDelegate implements WebViewCreater.OnPageLoadFinishedListener {

    private static final String ARG_TITLE = "arg_type";
    @BindView(R2.id.fl_container)
    public FrameLayout flContainer;
    private ChartInterface mChartInterface;


    public static EChartZhiTongLvDelegate newInstance(String title) {

        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        EChartZhiTongLvDelegate fragment = new EChartZhiTongLvDelegate();
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
            //super.mToolbar.setTitle(mTitle);
        }

    }

    private void initPieChart() {
        mAgentWeb.getJsAccessEntrace().quickCallJs("loadChartView", "chart1", mChartInterface.makeGaugeChartOptions());
    }

    private void initBarChart() {
        mAgentWeb.getJsAccessEntrace().quickCallJs("loadChartView", "chart2", mChartInterface.makeNearlySevendayBarChartOptions());
    }

    private void initLineChart() {
        mAgentWeb.getJsAccessEntrace().quickCallJs("loadChartView", "chart3", mChartInterface.makeTopsFiveBarChartOptions());
    }

    private void initGaugeChart() {
        mAgentWeb.getJsAccessEntrace().quickCallJs("loadChartView", "chart4", mChartInterface.makeLossRateBarChartOptions());
    }

    @Override
    public Object setLayout() {
        return R.layout.echarts_zhitonglv_delegate_layout;
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
        initBarChart();
        initGaugeChart();
        initLineChart();
        initPieChart();
    }


    /**
     * 注入到JS里的对象接口
     */
    public class ChartInterface {
        @JavascriptInterface
        public String makeLossRateBarChartOptions() {
            GsonOption option = new GsonOption();
            option.setLegend(new Legend().data("损失率Top5"));
            option.setTooltip(new Tooltip().formatter("{a} <br/>{b} : {c}%"));
            AxisLabel axisLabel = new AxisLabel();
            axisLabel.setInterval(0);
            axisLabel.setRotate(45);
            option.xAxis(new CategoryAxis().data("AOI测试", "功能测试", "条码绑定", "镭雕外观检测", "功能终测").axisLabel(axisLabel));
            CategoryAxis categoryAxis = new CategoryAxis();
            option.yAxis(categoryAxis.type(AxisType.value));
            Bar bar = new Bar("损失率Top5");
            bar.data(8.9, 9.9, 9.6, 9.52, 9.12).itemStyle().normal().color("#0033ff");
            bar.label().normal().show(true).position(Position.inside);
            option.series(bar);
            return option.toString();
        }

        @JavascriptInterface
        public String makeNearlySevendayBarChartOptions() {
            GsonOption option = new GsonOption();
            option.setLegend(new Legend().data("近7天直通率"));
            option.setTooltip(new Tooltip().formatter("{a} <br/>{b} : {c}%"));
            AxisLabel axisLabel = new AxisLabel();
            axisLabel.setInterval(0);
            axisLabel.setRotate(45);
            option.xAxis(new CategoryAxis().data("06月23", "06月24", "06月25", "06月26", "06月27", "06月28", "06月30").axisLabel(axisLabel));
            CategoryAxis categoryAxis = new CategoryAxis();
            option.yAxis(categoryAxis.type(AxisType.value));
            Bar bar = new Bar("近7天直通率");
            bar.data(89, 99, 96, 95, 91, 85, 87);
            bar.label().normal().show(true).position(Position.inside);
            option.series(bar);
            return option.toString();
        }


        @JavascriptInterface
        public String makeTopsFiveBarChartOptions() {
            GsonOption option = new GsonOption();
            option.setLegend(new Legend().data("直通率Tops5"));
            option.setTooltip(new Tooltip().formatter("{a} <br/>{b} : {c}%"));
            AxisLabel axisLabel = new AxisLabel();
            axisLabel.setInterval(0);
            axisLabel.setRotate(45);
            option.xAxis(new CategoryAxis().data("镭雕外观检测", "共模测试", "预功能测试", "高压测试", "功能测试").axisLabel(axisLabel));
            CategoryAxis categoryAxis = new CategoryAxis();
            option.yAxis(categoryAxis.type(AxisType.value));
            Bar bar = new Bar("直通率Tops5");
            bar.data(89, 96, 95, 91, 85).itemStyle().normal().color("#00ff00");
            bar.label().normal().show(true).position(Position.inside);
            option.series(bar);
            return option.toString();
        }

        //直通率仪表盘
        @JavascriptInterface
        public String makeGaugeChartOptions() {
            GsonOption option = new GsonOption();
            option.setTooltip(new Tooltip().formatter("{a} <br/>{b} : {c}%"));
            Gauge gauge = new Gauge();
            gauge.name("直通率");
            gauge.detail(new Detail().formatter("{value}%"));
            gauge.data(new Data().setValue(99).setName("直通率"));
            option.series(gauge);
            return option.toString();
        }


    }

}
