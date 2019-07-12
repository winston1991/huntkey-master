package com.jake.huntkey.core.delegates.EChartsDelegate.FormatEchartsDataUtil;

import android.webkit.JavascriptInterface;

import com.github.abel533.echarts.AxisPointer;
import com.github.abel533.echarts.DataZoom;
import com.github.abel533.echarts.Legend;
import com.github.abel533.echarts.Tooltip;
import com.github.abel533.echarts.axis.AxisLabel;
import com.github.abel533.echarts.axis.AxisTick;
import com.github.abel533.echarts.axis.CategoryAxis;
import com.github.abel533.echarts.code.AxisType;
import com.github.abel533.echarts.code.DataZoomType;
import com.github.abel533.echarts.code.PointerType;
import com.github.abel533.echarts.code.Position;
import com.github.abel533.echarts.code.Trigger;
import com.github.abel533.echarts.data.Data;
import com.github.abel533.echarts.data.SeriesData;
import com.github.abel533.echarts.json.GsonOption;
import com.github.abel533.echarts.series.Bar;
import com.github.abel533.echarts.series.Gauge;
import com.github.abel533.echarts.series.Line;
import com.github.abel533.echarts.series.Series;
import com.github.abel533.echarts.series.SeriesFactory;
import com.github.abel533.echarts.series.gauge.Detail;

import java.util.ArrayList;
import java.util.List;

public class GetChartsOptionString {

    public static String getZhiTongLvGauge1(String rate) {
        GsonOption option = new GsonOption();
        option.setTooltip(new Tooltip().formatter("{a} <br/>{b} : {c}%"));
        Gauge gauge = new Gauge();
        gauge.name("直通率");
        gauge.detail(new Detail().formatter("{value}%"));
        gauge.data(new Data().setValue(rate).setName("直通率"));
        option.series(gauge);
        return option.toString();
    }

    public static String getZhiTongLvBarOptions2(List<String> axis, List<String> data) {
        GsonOption option = new GsonOption();
        option.setLegend(new Legend().data("近7天直通率").top("5%"));
        option.setTooltip(new Tooltip().formatter("{a} <br/>{b} : {c}%"));
        AxisLabel axisLabel = new AxisLabel();
        axisLabel.setInterval(0);
        axisLabel.setRotate(45);
        option.xAxis(new CategoryAxis().data(axis.toArray()).axisLabel(axisLabel));
        CategoryAxis categoryAxis = new CategoryAxis();
        option.yAxis(categoryAxis.type(AxisType.value));
        Bar bar = new Bar("近7天直通率");
        bar.data(data.toArray());
        bar.label().normal().show(true).position(Position.inside);
        option.series(bar);
        return option.toString();
    }

    public static String getZhiTongLvBarOptions3(List<String> axis, List<String> data) {
        GsonOption option = new GsonOption();
        option.setLegend(new Legend().data("直通率Tops5").top("10%"));
        option.setTooltip(new Tooltip().formatter("{a} <br/>{b} : {c}%"));
        AxisLabel axisLabel = new AxisLabel();
        axisLabel.setInterval(0);
        axisLabel.setRotate(45);
        option.xAxis(new CategoryAxis().data(axis.toArray()).axisLabel(axisLabel));
        CategoryAxis categoryAxis = new CategoryAxis();
        option.yAxis(categoryAxis.type(AxisType.value));
        Bar bar = new Bar("直通率Tops5");
        bar.data(data.toArray()).itemStyle().normal().color("#E6B600");
        bar.label().normal().show(true).position(Position.inside);
        option.series(bar);
        return option.toString();
    }


    public static String getZhiTongLvBarOptions4(List<String> axis, List<String> data) {
        GsonOption option = new GsonOption();
        option.setLegend(new Legend().data("损失率Top5").top("10%"));
        option.setTooltip(new Tooltip().formatter("{a} <br/>{b} : {c}%"));
        AxisLabel axisLabel = new AxisLabel();
        axisLabel.setInterval(0);
        axisLabel.setRotate(45);
        option.xAxis(new CategoryAxis().data(axis.toArray()).axisLabel(axisLabel));
        CategoryAxis categoryAxis = new CategoryAxis();
        option.yAxis(categoryAxis.type(AxisType.value));
        Bar bar = new Bar("损失率Top5");
        bar.data(data.toArray()).itemStyle().normal().color("#0098D9");
        bar.label().normal().show(true).position(Position.inside);
        option.series(bar);
        return option.toString();
    }


    public static String getDaChengLvGaugeChartOptions(String rate) {
        GsonOption option = new GsonOption();
        option.setTooltip(new Tooltip().formatter("{a} <br/>{b} : {c}%"));
        Gauge gauge = new Gauge();
        gauge.name("达成率");
        gauge.detail(new Detail().formatter("{value}%"));
        gauge.data(new Data().setValue(rate).setName("达成率"));
        option.series(gauge);
        return option.toString();
    }

    /**
     * @param axis
     * @param list1 计划产能
     * @param list2 实际产能
     * @return
     */
    public static String getDaChengLvBarChartOptions(List<String> axis, List<String> list1, List<String> list2) {
        GsonOption option = new GsonOption();
        Tooltip tooltip = new Tooltip();
        tooltip.setTrigger(Trigger.axis);
        AxisPointer axisPointer = new AxisPointer();
        axisPointer.setType(PointerType.cross);
        tooltip.axisPointer(axisPointer);
        option.setTooltip(tooltip);
        option.legend().data("计划产能", "实际产能").top("10%");

        CategoryAxis categoryAxisX = new CategoryAxis();
        categoryAxisX.setAxisTick(new AxisTick().show(true));
        categoryAxisX.data(axis.toArray());
        option.xAxis(categoryAxisX);

        //Y轴
        CategoryAxis categoryAxisY1 = new CategoryAxis();
        categoryAxisY1.setType(AxisType.value);
        categoryAxisY1.name("数量").position(Position.left).min(0);
        option.yAxis(categoryAxisY1);


        DataZoom dataZoom = new DataZoom();
        dataZoom.setType(DataZoomType.slider);
        dataZoom.start(0).end(100).bottom("2%");
        option.dataZoom(dataZoom);

        SeriesFactory seriesFactory = new SeriesFactory();
        Bar bar1 = SeriesFactory.newBar("计划产能");
        bar1.yAxisIndex(0).label().normal().show(true).position(Position.top);
        bar1.data(list1.toArray());
        Bar bar2 = SeriesFactory.newBar("实际产能");
        bar2.yAxisIndex(0).label().normal().show(true).position(Position.top);
        bar2.data(list2.toArray());
        option.grid().top("20%").containLabel(false);
        option.series(bar1, bar2);
        return option.toString();
    }

    /**
     * @param axis  x抽坐标
     * @param list1 计划数量
     * @param list2 A班完成
     * @param list3 A班达成率
     * @param list4 B班完成
     * @param list5 B班达成率
     * @return
     */
    public static String getDaChengLvDoubleAxisBarLineOptions(List<String> axis, List<String> list1, List<String> list2, List<String> list3, List<String> list4, List<String> list5) {
        GsonOption option = new GsonOption();
        Tooltip tooltip = new Tooltip();
        tooltip.setTrigger(Trigger.axis);
        AxisPointer axisPointer = new AxisPointer();
        axisPointer.setType(PointerType.cross);
        tooltip.axisPointer(axisPointer);
        option.setTooltip(tooltip);
        option.legend().data("计划数量", "A班完成数", "A班达成率", "B班完成数", "B班达成率");

        CategoryAxis categoryAxisX = new CategoryAxis();
        categoryAxisX.setAxisTick(new AxisTick().show(true));
        categoryAxisX.data(axis.toArray());
        option.xAxis(categoryAxisX);

        //Y轴
        CategoryAxis categoryAxisY1 = new CategoryAxis();
        categoryAxisY1.setType(AxisType.value);
        categoryAxisY1.name("数量").position(Position.left).min(0);
        CategoryAxis categoryAxisY2 = new CategoryAxis();
        categoryAxisY2.setType(AxisType.value);
        categoryAxisY2.name("达成率").position(Position.right).min(0);
        categoryAxisY2.axisLabel().setFormatter("{value} %");
        option.yAxis(categoryAxisY1, categoryAxisY2);


        DataZoom dataZoom = new DataZoom();
        dataZoom.setType(DataZoomType.slider);
        dataZoom.start(0).end(100).bottom("2%");
        option.dataZoom(dataZoom);

        SeriesFactory seriesFactory = new SeriesFactory();
        Bar bar1 = SeriesFactory.newBar("计划数量");
        bar1.yAxisIndex(0).label().normal().show(true).position(Position.top);
        bar1.data(list1.toArray());
        Bar bar2 = SeriesFactory.newBar("A班完成数量");
        bar2.yAxisIndex(0).label().normal().show(true).position(Position.top);
        bar2.data(list2.toArray());
        Bar bar3 = SeriesFactory.newBar("B班完成数量");
        bar3.yAxisIndex(0).label().normal().show(true).position(Position.top);
        bar3.data(list4.toArray());


        Line line1 = seriesFactory.newLine("A班达成率");
        line1.yAxisIndex(1).label().normal().show(true).position(Position.top);
        line1.data(list3.toArray());

        Line line2 = seriesFactory.newLine("B班达成率");
        line2.yAxisIndex(1).label().normal().show(true).position(Position.top);
        line2.data(list5.toArray());

        option.grid().top("20%").containLabel(false);
        option.series(bar1, bar2, bar3, line1, line2);


        return option.toString();
    }


    @JavascriptInterface
    public static String getJiaDOngLvGaugeOptions1(String rate) {
        GsonOption option = new GsonOption();
        option.setTooltip(new Tooltip().formatter("{a} <br/>{b} : {c}%"));
        Gauge gauge = new Gauge();
        gauge.name("设备稼动率");
        gauge.detail(new Detail().formatter("{value}%"));
        gauge.data(new Data().setValue(rate).setName("稼动率"));
        option.series(gauge);
        return option.toString();
    }

    @JavascriptInterface
    public static String getJiaDOngLvBarOptions2(List<String> axisX, List<String> axisY) {
        GsonOption option = new GsonOption();
        option.setLegend(new Legend().data("近7天稼动率").top("10%"));
        option.setTooltip(new Tooltip().formatter("{a} <br/>{b} : {c}"));
        option.xAxis(new CategoryAxis().data(axisX.toArray()));
        CategoryAxis categoryAxis = new CategoryAxis();
        option.yAxis(categoryAxis.type(AxisType.value));
        DataZoom dataZoom = new DataZoom();
        dataZoom.setType(DataZoomType.slider);
        dataZoom.setStart(0);
        dataZoom.setEnd(100);
        option.dataZoom(dataZoom);
        Bar bar = new Bar("近7天稼动率");
        bar.data(axisY.toArray());
        option.series(bar);
        return option.toString();
    }

    @JavascriptInterface
    public static String getJiaDOngLvBarOptions3(List<String> axisX, List<String> axisY) {
        GsonOption option = new GsonOption();
        option.setLegend(new Legend().data("稼动率Top5").top("10%"));
        option.setTooltip(new Tooltip().formatter("{a} <br/>{b} : {c}"));
        AxisLabel axisLabel = new AxisLabel();
        axisLabel.setInterval(0);
        axisLabel.setRotate(45);
        option.xAxis(new CategoryAxis().data(axisX.toArray()).axisLabel(axisLabel));
        CategoryAxis categoryAxis = new CategoryAxis();
        option.yAxis(categoryAxis.type(AxisType.value));
        DataZoom dataZoom = new DataZoom();
        dataZoom.setType(DataZoomType.slider);
        dataZoom.setStart(0);
        dataZoom.setEnd(100);
        option.dataZoom(dataZoom);
        Bar bar = new Bar("稼动率Top5");
        bar.data(axisY.toArray());
        option.series(bar);
        return option.toString();
    }

    @JavascriptInterface
    public static String getJiaDOngLvBarOptions4(List<String> axisX, List<String> axisY) {
        GsonOption option = new GsonOption();
        option.setLegend(new Legend().data("停工线时").top("10%"));
        option.setTooltip(new Tooltip().formatter("{a} <br/>{b} : {c}h"));
        option.xAxis(new CategoryAxis().data(axisX.toArray()));
        CategoryAxis categoryAxis = new CategoryAxis();
        option.yAxis(categoryAxis.type(AxisType.value));
        DataZoom dataZoom = new DataZoom();
        dataZoom.setType(DataZoomType.slider);
        dataZoom.setStart(0);
        dataZoom.setEnd(100);
        option.dataZoom(dataZoom);
        Bar bar = new Bar("停工线时");
        bar.data(axisY.toArray());
        option.series(bar);
        return option.toString();
    }

    @JavascriptInterface
    public static String getChuQinLvGaugeOptions1(String rate) {
        GsonOption option = new GsonOption();
        option.setTooltip(new Tooltip().formatter("{a} <br/>{b} : {c}%"));
        Gauge gauge = new Gauge();
        gauge.name("出勤率");
        gauge.detail(new Detail().formatter("{value}%"));
        gauge.data(new Data().setValue(rate).setName("出勤率"));
        option.series(gauge);
        return option.toString();
    }


    @JavascriptInterface
    public static String getChuQinLvBarOptions2(List<String>... list) {
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
        categoryAxisX.data(list[0].toArray());
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
        dataZoom.start(0).end(100).bottom("2%");
        option.dataZoom(dataZoom);

        SeriesFactory seriesFactory = new SeriesFactory();
        Bar bar1 = SeriesFactory.newBar("在职人数");
        bar1.yAxisIndex(0).label().normal().show(true).position(Position.top);
        bar1.data(list[1].toArray());
        Bar bar2 = SeriesFactory.newBar("A班出勤人数");
        bar2.yAxisIndex(0).label().normal().show(true).position(Position.top);
        bar2.data(list[2].toArray());
        Bar bar3 = SeriesFactory.newBar("B班出勤人数");
        bar3.yAxisIndex(0).label().normal().show(true).position(Position.top);
        bar3.data(list[4].toArray());


        Line line1 = seriesFactory.newLine("A班出勤率");
        line1.yAxisIndex(1).label().normal().show(true).position(Position.top);
        line1.data(list[3].toArray());

        Line line2 = seriesFactory.newLine("B班出勤率");
        line2.yAxisIndex(1).label().normal().show(true).position(Position.top);
        line2.data(list[5].toArray());

        option.grid().top("20%").containLabel(false);
        option.series(bar1, bar2, bar3, line1, line2);
        return option.toString();
    }


    @JavascriptInterface
    public static String getChuQinLvBarOptions3(List<String>... lists) {
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
        categoryAxisX.data(lists[0].toArray());
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
        dataZoom.start(0).end(100).bottom("2%");
        option.dataZoom(dataZoom);

        SeriesFactory seriesFactory = new SeriesFactory();
        Bar bar1 = SeriesFactory.newBar("在职人数");
        bar1.yAxisIndex(0).label().normal().show(true).position(Position.top);
        bar1.data(lists[1].toArray());
        Bar bar2 = SeriesFactory.newBar("A班出勤人数");
        bar2.yAxisIndex(0).label().normal().show(true).position(Position.top);
        bar2.data(lists[2].toArray());
        Bar bar3 = SeriesFactory.newBar("B班出勤人数");
        bar3.yAxisIndex(0).label().normal().show(true).position(Position.top);
        bar3.data(lists[4].toArray());
        Line line1 = seriesFactory.newLine("A班出勤率");
        line1.yAxisIndex(1).label().normal().show(true).position(Position.top);
        line1.data(lists[3].toArray());
        Line line2 = seriesFactory.newLine("B班出勤率");
        line2.yAxisIndex(1).label().normal().show(true).position(Position.top);
        line2.data(lists[5].toArray());
        option.grid().top("20%").containLabel(false);
        option.series(bar1, bar2, bar3, line1, line2);
        return option.toString();
    }


    @JavascriptInterface
    public static String getChuQinLvBarOptions4(List<String>... lists) {
        GsonOption option = new GsonOption();
        Tooltip tooltip = new Tooltip();

        tooltip.position(new String[]{"25%", "25%"});
        tooltip.setTrigger(Trigger.axis);
        AxisPointer axisPointer = new AxisPointer();
        axisPointer.setType(PointerType.cross);
        tooltip.axisPointer(axisPointer);
        option.setTooltip(tooltip);
        option.legend().data("在职人数", "实际出勤人数", "实际出勤率").top("5%");

        CategoryAxis categoryAxisX = new CategoryAxis();
        categoryAxisX.setAxisTick(new AxisTick().show(true));
        categoryAxisX.data(lists[0].toArray());
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
        dataZoom.start(0).end(100).bottom("2%");
        option.dataZoom(dataZoom);

        SeriesFactory seriesFactory = new SeriesFactory();
        Bar bar1 = SeriesFactory.newBar("在职人数");
        bar1.yAxisIndex(0).label().normal().show(true).position(Position.top);
        bar1.data(lists[1].toArray());
        Bar bar2 = SeriesFactory.newBar("实际出勤人数");
        bar2.yAxisIndex(0).label().normal().show(true).position(Position.top);
        bar2.data(lists[2].toArray());

        Line line1 = seriesFactory.newLine("实际出勤率");
        line1.yAxisIndex(1).label().normal().show(true).position(Position.top);
        line1.data(lists[3].toArray());

        option.grid().top("20%").containLabel(false);
        option.series(bar1, bar2, line1);
        return option.toString();
    }


}
