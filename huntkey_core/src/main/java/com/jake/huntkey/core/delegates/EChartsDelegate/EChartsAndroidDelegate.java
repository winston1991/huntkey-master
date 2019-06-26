package com.jake.huntkey.core.delegates.EChartsDelegate;

import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.widget.FrameLayout;

import com.github.abel533.echarts.Legend;
import com.github.abel533.echarts.Title;
import com.github.abel533.echarts.Toolbox;
import com.github.abel533.echarts.Tooltip;
import com.github.abel533.echarts.axis.CategoryAxis;
import com.github.abel533.echarts.axis.ValueAxis;
import com.github.abel533.echarts.code.Trigger;
import com.github.abel533.echarts.data.Data;
import com.github.abel533.echarts.data.PieData;
import com.github.abel533.echarts.feature.Feature;
import com.github.abel533.echarts.feature.Mark;
import com.github.abel533.echarts.feature.Restore;
import com.github.abel533.echarts.json.GsonOption;
import com.github.abel533.echarts.series.Bar;
import com.github.abel533.echarts.series.Gauge;
import com.github.abel533.echarts.series.Line;
import com.github.abel533.echarts.series.Pie;
import com.github.abel533.echarts.series.Series;
import com.github.abel533.echarts.series.gauge.Detail;
import com.jake.huntkey.core.R;
import com.jake.huntkey.core.R2;
import com.jake.huntkey.core.delegates.MainDelegate;
import com.xuexiang.xui.widget.tabbar.TabControlView;

import butterknife.BindView;


public class EChartsAndroidDelegate extends BaseWebViewDelegate {


    @BindView(R2.id.fl_container)
    public FrameLayout flContainer;
    private ChartInterface mChartInterface;


    public static EChartsAndroidDelegate newInstance() {



        Bundle args = new Bundle();
        EChartsAndroidDelegate fragment = new EChartsAndroidDelegate();
        fragment.setArguments(args);
        return fragment;
    }

    protected void initViews() {

        //目前Echarts-Java只支持3.x
        mAgentWeb = WebViewCreater.createAgentWeb(this, flContainer, "file:///android_asset/chart/src/template.html");

        //注入接口,供JS调用
        mAgentWeb.getJsInterfaceHolder().addJavaObject("Android", mChartInterface = new ChartInterface());

    }

    private void initPieChart() {
        mAgentWeb.getJsAccessEntrace().quickCallJs("loadChartView", "chart", mChartInterface.makePieChartOptions());
    }

    private void initBarChart() {
        mAgentWeb.getJsAccessEntrace().quickCallJs("loadChartView", "chart2", mChartInterface.makeBarChartOptions());
    }

    private void initLineChart() {
        mAgentWeb.getJsAccessEntrace().quickCallJs("loadChartView", "chart3", mChartInterface.makeLineChartOptions());
    }


    @Override
    public Object setLayout() {
        return R.layout.echarts_delegate_layout;
    }

    @Override
    protected void onBindView(Bundle savedInstanceState, View rootView) {
        initViews();
    }


    /**
     * 注入到JS里的对象接口
     */
    public class ChartInterface {
        @JavascriptInterface
        public String makePieChartOptions() {
            GsonOption option = new GsonOption();
            option.tooltip().trigger(Trigger.item).formatter("{a} <br/>{b} : {c} ({d}%)");
            option.legend().data("直接访问", "邮件营销", "联盟广告", "视频广告", "搜索引擎");

            Pie pie = new Pie("访问来源").data(
                    new PieData("直接访问", 335),
                    new PieData("邮件营销", 310),
                    new PieData("联盟广告", 274),
                    new PieData("视频广告", 235),
                    new PieData("搜索引擎", 400)
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
            option.xAxis(new CategoryAxis().data("衬衫", "羊毛衫", "雪纺衫", "裤子", "高跟鞋", "袜子"));
            option.yAxis();

            Bar bar = new Bar("销量");
            bar.data(5, 20, 36, 10, 10, 20);
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
        public String makeGaugeChartOptions() {
            GsonOption option = new GsonOption();
            option.setTitle(new Title().text("仪表盘"));
            option.setTooltip(new Tooltip().formatter("{a} <br/>{b} : {c}%"));
            Gauge gauge = new Gauge();
            gauge.name("业务指标");
            gauge.detail(new Detail().formatter("{value}%"));
            gauge.data(new Data().setValue(50).setName("完成率"));
            option.series(gauge);
            return option.toString();
        }

    }

}
