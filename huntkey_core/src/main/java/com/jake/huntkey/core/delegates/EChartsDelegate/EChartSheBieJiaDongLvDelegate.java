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

import butterknife.BindView;

public class EChartSheBieJiaDongLvDelegate extends BaseWebViewDelegate implements WebViewCreater.OnPageLoadFinishedListener {

    private static final String ARG_TITLE = "arg_type";
    @BindView(R2.id.fl_container)
    public FrameLayout flContainer;

    private ChartInterface mChartInterface;

    public static EChartSheBieJiaDongLvDelegate newInstance(String title) {

        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        EChartSheBieJiaDongLvDelegate fragment = new EChartSheBieJiaDongLvDelegate();
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
        mAgentWeb.getJsAccessEntrace().quickCallJs("loadChartView", "chart2", mChartInterface.makeLineChartOptions());
    }

    private void initLineChart() {
        mAgentWeb.getJsAccessEntrace().quickCallJs("loadChartView", "chart3", mChartInterface.makeBarChartOptions());
    }

    private void initPieChart() {
        mAgentWeb.getJsAccessEntrace().quickCallJs("loadChartView", "chart4", mChartInterface.makePieChartOptions());
    }

    @Override
    public Object setLayout() {
        return R.layout.echarts_shebiejiadong_delegate_layout;
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
            option.setLegend(new Legend().data("停工线时"));
            option.setTooltip(new Tooltip().formatter("{a} <br/>{b} : {c}h"));
            option.xAxis(new CategoryAxis().data("包装扫描", "条码转换", "AOI测试", "条码绑定", "超声波"));
            CategoryAxis categoryAxis = new CategoryAxis();
            option.yAxis(categoryAxis.type(AxisType.value));
            DataZoom dataZoom = new DataZoom();
            dataZoom.setType(DataZoomType.slider);
            dataZoom.setStart(0);
            dataZoom.setEnd(100);
            option.dataZoom(dataZoom);
            Bar bar = new Bar("停工线时");
            bar.data(2, 2, 3.6, 1, 0.8);
            option.series(bar);
            return option.toString();
        }

        @JavascriptInterface
        public String makeBarChartOptions() {
            GsonOption option = new GsonOption();
            option.setLegend(new Legend().data("稼动率Top5"));
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
            Bar bar = new Bar("稼动率Top5");
            bar.data(5, 20, 36, 10, 10, 20, 5, 20, 36, 10, 10, 20, 2, 8, 10);
            option.series(bar);
            return option.toString();
        }


        @JavascriptInterface
        public String makeLineChartOptions() {
            GsonOption option = new GsonOption();
            option.setLegend(new Legend().data("近7天稼动率"));
            option.setTooltip(new Tooltip().formatter("{a} <br/>{b} : {c}"));
            option.xAxis(new CategoryAxis().data("06-29", "06-30", "06-31", "07-1", "07-2", "07-3", "07-4"));
            CategoryAxis categoryAxis = new CategoryAxis();
            option.yAxis(categoryAxis.type(AxisType.value));
            DataZoom dataZoom = new DataZoom();
            dataZoom.setType(DataZoomType.slider);
            dataZoom.setStart(0);
            dataZoom.setEnd(100);
            option.dataZoom(dataZoom);
            Bar bar = new Bar("近7天稼动率");
            bar.data(5, 20, 36, 10, 10, 20, 5);
            option.series(bar);
            return option.toString();
        }

        @JavascriptInterface
        public String makeGaugeChartOptions() {
            GsonOption option = new GsonOption();
            option.setTooltip(new Tooltip().formatter("{a} <br/>{b} : {c}%"));
            Gauge gauge = new Gauge();
            gauge.name("设备稼动率");
            gauge.detail(new Detail().formatter("{value}%"));
            gauge.data(new Data().setValue(90).setName("稼动率"));
            option.series(gauge);
            return option.toString();
        }

    }

}
