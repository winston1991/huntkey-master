package com.jake.huntkey.core.delegates.EChartsDelegate;

import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.webkit.JavascriptInterface;
import android.widget.Button;
import android.widget.FrameLayout;

import com.bin.david.form.core.SmartTable;
import com.blankj.utilcode.util.ToastUtils;
import com.github.abel533.echarts.AxisPointer;
import com.github.abel533.echarts.DataZoom;
import com.github.abel533.echarts.Legend;
import com.github.abel533.echarts.Title;
import com.github.abel533.echarts.Tooltip;
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
import com.jake.huntkey.core.entity.WIPEntity;
import com.just.agentweb.core.AgentWeb;

import java.util.ArrayList;

import butterknife.BindView;

public class EChartDaChengLvDelegate extends BaseWebViewDelegate implements WebViewCreater.OnPageLoadFinishedListener {

    private static final String ARG_TITLE = "arg_type";
    @BindView(R2.id.fl_container)
    public FrameLayout flContainer;

    @BindView(R2.id.id_smart_table)
    SmartTable<WIPEntity> smartTable;
    private ChartInterface mChartInterface;

    public static EChartDaChengLvDelegate newInstance(String title) {

        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        EChartDaChengLvDelegate fragment = new EChartDaChengLvDelegate();
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
        smartTable.getConfig().setFixedTitle(true);
        smartTable.getConfig().setFixedCountRow(true);
        smartTable.getConfig().setShowXSequence(false);
        smartTable.getConfig().setShowYSequence(false);
        smartTable.setData(getData());
        rootview.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                initBarChart();
                initGaugeChart();
                initLineChart();
                mAgentWeb.getJsAccessEntrace().quickCallJs("hideDiv");
            }
        });

    }


    private void initGaugeChart() {
        mAgentWeb.getJsAccessEntrace().quickCallJs("loadChartView", "chart1", mChartInterface.makeGaugeChartOptions());
    }

    private void initBarChart() {
        mAgentWeb.getJsAccessEntrace().quickCallJs("loadChartView", "chart2", mChartInterface.makeBarChartOptions());
    }

    private void initLineChart() {
        mAgentWeb.getJsAccessEntrace().quickCallJs("loadChartView", "chart3", mChartInterface.makeLineChartOptions());
    }


    @Override
    public Object setLayout() {
        return R.layout.echarts_dachenglv_delegate_layout;
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
        public String makeBarChartOptions() {
            return GetChartsOptionString.getDoubleAxisBarLineOptions();
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
            option.legend().data("计划产能", "实际产能");

            CategoryAxis categoryAxisX = new CategoryAxis();
            categoryAxisX.setAxisTick(new AxisTick().show(true));
            categoryAxisX.data("08点", "09点", "10点", "11点", "12点", "13点", "14点", "15点", "16点");
            option.xAxis(categoryAxisX);

            //Y轴
            CategoryAxis categoryAxisY1 = new CategoryAxis();
            categoryAxisY1.setType(AxisType.value);
            categoryAxisY1.name("数量").position(Position.left).min(0);
            option.yAxis(categoryAxisY1);


            DataZoom dataZoom = new DataZoom();
            dataZoom.setType(DataZoomType.slider);
            dataZoom.start(0).end(100).bottom("10%");
            option.dataZoom(dataZoom);

            SeriesFactory seriesFactory = new SeriesFactory();
            Bar bar1 = SeriesFactory.newBar("计划产能");
            bar1.yAxisIndex(0).label().normal().show(true).position(Position.top);
            bar1.data(1239, 1234, 2394, 1003, 998, 1200, 1029, 1204);
            Bar bar2 = SeriesFactory.newBar("实际产能");
            bar2.yAxisIndex(0).label().normal().show(true).position(Position.top);
            bar2.data(1390, 1345, 949, 1023, 898, 710, 600, 300);
            option.grid().top("20%").containLabel(false);
            option.series(bar1, bar2);
            return option.toString();
        }

        @JavascriptInterface
        public String makeGaugeChartOptions() {
            GsonOption option = new GsonOption();
            option.setTooltip(new Tooltip().formatter("{a} <br/>{b} : {c}%"));
            Gauge gauge = new Gauge();
            gauge.name("达成率");
            gauge.detail(new Detail().formatter("{value}%"));
            gauge.data(new Data().setValue(89).setName("达成率"));
            option.series(gauge);
            return option.toString();
        }

    }


    private ArrayList<WIPEntity> getData() {
        ArrayList datas = new ArrayList<WIPEntity>();
        WIPEntity wipEntity;
        for (int i = 0; i < 120; i++) {
            wipEntity = new WIPEntity();
            wipEntity.setId(i++ + "");
            wipEntity.setMakeFlowNumber("WBJ33444" + i);
            wipEntity.setnGInfo("NGInfo");
            wipEntity.setQuantity(i + 100 + "");
            wipEntity.setSamplingNumber("QE2-541549856" + i);
            wipEntity.setSamplingResult("Pass");

            datas.add(wipEntity);
        }

        return datas;
    }
}
