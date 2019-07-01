package com.jake.huntkey.core.delegates.EChartsDelegate;

import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.webkit.JavascriptInterface;
import android.widget.FrameLayout;

import com.github.abel533.echarts.DataZoom;
import com.github.abel533.echarts.Legend;
import com.github.abel533.echarts.Title;
import com.github.abel533.echarts.Tooltip;
import com.github.abel533.echarts.axis.AxisLabel;
import com.github.abel533.echarts.axis.CategoryAxis;
import com.github.abel533.echarts.axis.ValueAxis;
import com.github.abel533.echarts.code.AxisType;
import com.github.abel533.echarts.code.DataZoomType;
import com.github.abel533.echarts.code.Trigger;
import com.github.abel533.echarts.data.Data;
import com.github.abel533.echarts.data.PieData;
import com.github.abel533.echarts.json.GsonOption;
import com.github.abel533.echarts.series.Bar;
import com.github.abel533.echarts.series.Gauge;
import com.github.abel533.echarts.series.Line;
import com.github.abel533.echarts.series.Pie;
import com.github.abel533.echarts.series.gauge.Detail;
import com.jake.huntkey.core.R;
import com.jake.huntkey.core.R2;
import com.jake.huntkey.core.delegates.EChartsDelegate.FormatEchartsDataUtil.GetChartsOptionString;

import butterknife.BindView;

public class EChartChuQinLvDelegate extends BaseWebViewDelegate implements WebViewCreater.OnPageLoadFinishedListener {

    private static final String ARG_TITLE = "arg_type";
    @BindView(R2.id.fl_container)
    public FrameLayout flContainer;

    private ChartInterface mChartInterface;

    public static EChartChuQinLvDelegate newInstance(String title) {

        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        EChartChuQinLvDelegate fragment = new EChartChuQinLvDelegate();
        fragment.setArguments(args);
        return fragment;
    }

    protected void initViews(View rootview) {

        mAgentWeb = new WebViewCreater(this).createAgentWeb(this, flContainer);
//        //注入接口,供JS调用

        mAgentWeb.getJsInterfaceHolder().addJavaObject("Android", mChartInterface = new ChartInterface());

        Bundle bundle = getArguments();
        if (bundle != null) {
            String mTitle = bundle.getString(ARG_TITLE);
            //super.mToolbar.setTitle(mTitle);
        }

        rootview.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                initBarChart();
                initGaugeChart();
                initLineChart();
                initPieChart();
            }
        });

    }


    private void initPieChart() {
        mAgentWeb.getJsAccessEntrace().quickCallJs("loadChartView", "chart1", mChartInterface.makeGaugeChartOptions());
    }

    private void initBarChart() {
        mAgentWeb.getJsAccessEntrace().quickCallJs("loadChartView", "chart2", mChartInterface.getDoubleAxisBarLineOptions());
    }

    private void initLineChart() {
        mAgentWeb.getJsAccessEntrace().quickCallJs("loadChartView", "chart3", mChartInterface.makeLineChartOptions());
    }

    private void initGaugeChart() {
        mAgentWeb.getJsAccessEntrace().quickCallJs("loadChartView", "chart4", mChartInterface.makePieChartOptions());
    }

    @Override
    public Object setLayout() {
        return R.layout.echarts_chuqinlv_delegate_layout;
    }


    @Override
    protected void onBindView(Bundle savedInstanceState, View rootView) {
        initViews(rootView);
    }

    @Override
    public void onPageLoadFinished() {

    }

    /**
     * 注入到JS里的对象接口
     */
    public class ChartInterface {
        @JavascriptInterface
        public String makePieChartOptions() {
            GsonOption option = new GsonOption();
            option.tooltip().trigger(Trigger.item).formatter("{a} <br/>{b} : {c} ({d}%)");
            option.legend().data("直接", "邮件", "联盟", "视频", "搜索");

            Pie pie = new Pie("访问来源").data(
                    new PieData("直接", 335),
                    new PieData("邮件", 310),
                    new PieData("联盟", 274),
                    new PieData("视频", 235),
                    new PieData("搜索", 400)
            ).center("50%", "45%").radius("50%");
            pie.label().normal().show(true).formatter("{b}{c}({d}%)");
            option.series(pie);
            return option.toString();
        }

        @JavascriptInterface
        public String makeBarChartOptions() {
            GsonOption option = new GsonOption();
            option.setTitle(new Title().text("柱状图"));
            option.setLegend(new Legend().data("销量"));
            option.setTooltip(new Tooltip().formatter("{a} <br/>{b} : {c}"));
            AxisLabel axisLabel = new AxisLabel();
            axisLabel.setInterval(0);
            axisLabel.setRotate(45);
            option.xAxis(new CategoryAxis().data("产品外观检查", "包装扫描", "工单投入入", "插件AOI测试", "共模测试", "超声波", "条码转换", "条码绑定", "功能终测", "条码替换", "AOI测试", "高压测试", "镭雕外观检测", "功能初测", "预功能测试").axisLabel(axisLabel));
            CategoryAxis categoryAxis = new CategoryAxis();
            option.yAxis(categoryAxis.type(AxisType.value));
            DataZoom dataZoom = new DataZoom();
            dataZoom.setType(DataZoomType.slider);
            dataZoom.setStart(0);
            dataZoom.setEnd(100);
            option.dataZoom(dataZoom);
            Bar bar = new Bar("销量");
            bar.data(5, 20, 36, 10, 10, 20,5, 20, 36, 10, 10, 20,2,8,10);
            option.series(bar);
            return option.toString();
        }


        @JavascriptInterface
        public String makeLineChartOptions() {
            GsonOption option = new GsonOption();
            option.legend("高度(km)与气温(°C)变化关系");
            option.toolbox().show(false);
            option.calculable(true);
            option.tooltip().trigger(Trigger.axis).formatter("Temperature : <br/>{b}km : {c}°C");

            ValueAxis valueAxis = new ValueAxis();
            valueAxis.axisLabel().formatter("{value} °C");
            option.xAxis(valueAxis);

            CategoryAxis categoryAxis = new CategoryAxis();
            categoryAxis.axisLine().onZero(false);
            categoryAxis.axisLabel().formatter("{value} km");
            categoryAxis.boundaryGap(false);
            categoryAxis.data(0, 10, 20, 30, 40, 50, 60, 70, 80);
            option.yAxis(categoryAxis);

            Line line = new Line();
            line.smooth(true).name("高度(km)与气温(°C)变化关系").data(15, -50, -56.5, -46.5, -22.1, -2.5, -27.7, -55.7, -76.5).itemStyle().normal().lineStyle().shadowColor("rgba(0,0,0,0.4)");
            option.series(line);
            return option.toString();
        }

        @JavascriptInterface
        public String getDoubleAxisBarLineOptions() {
            return GetChartsOptionString.getDoubleAxisBarLineOptions();
        }

        @JavascriptInterface
        public String makeGaugeChartOptions() {
            GsonOption option = new GsonOption();
            option.setTooltip(new Tooltip().formatter("{a} <br/>{b} : {c}%"));
            Gauge gauge = new Gauge();
            gauge.name("出勤率");
            gauge.detail(new Detail().formatter("{value}%"));
            gauge.data(new Data().setValue(100).setName("出勤率"));
            option.series(gauge);
            return option.toString();
        }

    }

}
