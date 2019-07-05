package com.jake.huntkey.core.delegates.EChartsDelegate;

import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.webkit.JavascriptInterface;
import android.widget.FrameLayout;

import com.github.abel533.echarts.AxisPointer;
import com.github.abel533.echarts.DataZoom;
import com.github.abel533.echarts.Legend;
import com.github.abel533.echarts.Title;
import com.github.abel533.echarts.Tooltip;
import com.github.abel533.echarts.axis.AxisLabel;
import com.github.abel533.echarts.axis.AxisTick;
import com.github.abel533.echarts.axis.CategoryAxis;
import com.github.abel533.echarts.axis.ValueAxis;
import com.github.abel533.echarts.code.AxisType;
import com.github.abel533.echarts.code.DataZoomType;
import com.github.abel533.echarts.code.PointerType;
import com.github.abel533.echarts.code.Position;
import com.github.abel533.echarts.code.Trigger;
import com.github.abel533.echarts.data.Data;
import com.github.abel533.echarts.data.PieData;
import com.github.abel533.echarts.json.GsonOption;
import com.github.abel533.echarts.series.Bar;
import com.github.abel533.echarts.series.Gauge;
import com.github.abel533.echarts.series.Line;
import com.github.abel533.echarts.series.Pie;
import com.github.abel533.echarts.series.SeriesFactory;
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


    private void initGaugeChart() {
        mAgentWeb.getJsAccessEntrace().quickCallJs("loadChartView", "chart1", mChartInterface.makeGaugeChartOptions());
    }

    private void initBarChart() {
        mAgentWeb.getJsAccessEntrace().quickCallJs("loadChartView", "chart2", mChartInterface.getDoubleAxisBarLineOptions());
    }

    private void initLineChart() {
        mAgentWeb.getJsAccessEntrace().quickCallJs("loadChartView", "chart3", mChartInterface.makeLineChartOptions());
    }

    private void initPieChart() {
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
            Tooltip tooltip = new Tooltip();
            tooltip.setTrigger(Trigger.axis);
            AxisPointer axisPointer = new AxisPointer();
            axisPointer.setType(PointerType.cross);
            tooltip.axisPointer(axisPointer);
            option.setTooltip(tooltip);
            option.legend().data("在职人数", "实际出勤人数", "实际出勤率");

            CategoryAxis categoryAxisX = new CategoryAxis();
            categoryAxisX.setAxisTick(new AxisTick().show(true));
            categoryAxisX.data("航嘉股份.IT.BG.ITBG制造部.IT制造处A班", "航嘉股份.IT.BG.ITBG制造部.IT制造处B班");
            option.xAxis(categoryAxisX);

            //Y轴
            CategoryAxis categoryAxisY1 = new CategoryAxis();
            categoryAxisY1.setType(AxisType.value);
            categoryAxisY1.name("数量").position(Position.left).min(0);
            CategoryAxis categoryAxisY2 = new CategoryAxis();
            categoryAxisY2.setType(AxisType.value);
            categoryAxisY2.name("出勤率").position(Position.right).min(0);
            categoryAxisY2.axisLabel().setFormatter("{value} %");
            option.yAxis(categoryAxisY1, categoryAxisY2);

            DataZoom dataZoom = new DataZoom();
            dataZoom.setType(DataZoomType.slider);
            dataZoom.start(0).end(100).bottom("10%");
            option.dataZoom(dataZoom);

            SeriesFactory seriesFactory = new SeriesFactory();
            Bar bar1 = SeriesFactory.newBar("在职人数");
            bar1.yAxisIndex(0).label().normal().show(true).position(Position.top);
            bar1.data(890, 845);
            Bar bar2 = SeriesFactory.newBar("实际出勤人数");
            bar2.yAxisIndex(0).label().normal().show(true).position(Position.top);
            bar2.data(390, 345);

            Line line1 = seriesFactory.newLine("实际出勤率");
            line1.yAxisIndex(1).label().normal().show(true).position(Position.top);
            line1.data(70, 80);

            option.grid().top("20%").containLabel(false);
            option.series(bar1, bar2, line1);
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
            bar.data(5, 20, 36, 10, 10, 20, 5, 20, 36, 10, 10, 20, 2, 8, 10);
            option.series(bar);
            return option.toString();
        }


        @JavascriptInterface
        public String makeLineChartOptions() {
            GsonOption option = new GsonOption();
            Tooltip tooltip = new Tooltip();
            tooltip.setTrigger(Trigger.axis);
            AxisPointer axisPointer = new AxisPointer();
            axisPointer.setType(PointerType.cross);
            tooltip.axisPointer(axisPointer);
            option.setTooltip(tooltip);
            option.legend().data("在职人数", "A班出勤人数", "B班出勤人数", "A班出勤率", "B班出勤率");

            CategoryAxis categoryAxisX = new CategoryAxis();
            categoryAxisX.setAxisTick(new AxisTick().show(true));
            categoryAxisX.data("上周", "本周");
            option.xAxis(categoryAxisX);

            //Y轴
            CategoryAxis categoryAxisY1 = new CategoryAxis();
            categoryAxisY1.setType(AxisType.value);
            categoryAxisY1.name("数量").position(Position.left).min(0);
            CategoryAxis categoryAxisY2 = new CategoryAxis();
            categoryAxisY2.setType(AxisType.value);
            categoryAxisY2.name("出勤率").position(Position.right).min(0);
            categoryAxisY2.axisLabel().setFormatter("{value} %");
            option.yAxis(categoryAxisY1, categoryAxisY2);

            DataZoom dataZoom = new DataZoom();
            dataZoom.setType(DataZoomType.slider);
            dataZoom.start(0).end(100).bottom("10%");
            option.dataZoom(dataZoom);

            SeriesFactory seriesFactory = new SeriesFactory();
            Bar bar1 = SeriesFactory.newBar("在职人数");
            bar1.yAxisIndex(0).label().normal().show(true).position(Position.top);
            bar1.data(890, 845);
            Bar bar2 = SeriesFactory.newBar("A班出勤人数");
            bar2.yAxisIndex(0).label().normal().show(true).position(Position.top);
            bar2.data(390, 345);
            Bar bar3 = SeriesFactory.newBar("B班出勤人数");
            bar3.yAxisIndex(0).label().normal().show(true).position(Position.top);
            bar3.data(500, 500);
            Line line1 = seriesFactory.newLine("A班出勤率");
            line1.yAxisIndex(1).label().normal().show(true).position(Position.top);
            line1.data(70, 80);
            Line line2 = seriesFactory.newLine("B班出勤率");
            line2.yAxisIndex(1).label().normal().show(true).position(Position.top);
            line2.data(80, 90);
            option.grid().top("20%").containLabel(false);
            option.series(bar1, bar2, bar3, line1, line2);
            return option.toString();
        }

        @JavascriptInterface
        public String getDoubleAxisBarLineOptions() {
            return GetChartsOptionString.getChuQinLvDoubleAxisBarLineOptions();
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
