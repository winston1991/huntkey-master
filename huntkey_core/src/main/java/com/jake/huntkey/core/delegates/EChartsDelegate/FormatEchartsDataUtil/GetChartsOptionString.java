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

public class GetChartsOptionString {

    public static String getZhiTongLvGauge1() {
        GsonOption option = new GsonOption();
        option.setTooltip(new Tooltip().formatter("{a} <br/>{b} : {c}%"));
        Gauge gauge = new Gauge();
        gauge.name("直通率");
        gauge.detail(new Detail().formatter("{value}%"));
        gauge.data(new Data().setValue(99).setName("直通率"));
        option.series(gauge);
        return option.toString();
    }

    public static String getZhiTongLvBarOptions2() {
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

    public static String getZhiTongLvBarOptions3() {
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


    public static String getZhiTongLvBarOptions4() {
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


    public static String getDaChengLvGaugeChartOptions() {
        GsonOption option = new GsonOption();
        option.setTooltip(new Tooltip().formatter("{a} <br/>{b} : {c}%"));
        Gauge gauge = new Gauge();
        gauge.name("达成率");
        gauge.detail(new Detail().formatter("{value}%"));
        gauge.data(new Data().setValue(89).setName("达成率"));
        option.series(gauge);
        return option.toString();
    }

    public static String getDaChengLvBarChartOptions() {
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

    //
    public static String getDaChengLvDoubleAxisBarLineOptions() {
        GsonOption option = new GsonOption();
        Tooltip tooltip = new Tooltip();
        tooltip.setTrigger(Trigger.axis);
        AxisPointer axisPointer = new AxisPointer();
        axisPointer.setType(PointerType.cross);
        tooltip.axisPointer(axisPointer);
        option.setTooltip(tooltip);
        option.legend().data("计划数量", "A班完成数量", "B班完成数量", "A班达成率", "B班达成率");

        CategoryAxis categoryAxisX = new CategoryAxis();
        categoryAxisX.setAxisTick(new AxisTick().show(true));
        categoryAxisX.data("06月24", "06月25", "06月26", "06月27", "06月28");
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
        dataZoom.start(0).end(100).bottom("10%");
        option.dataZoom(dataZoom);

        SeriesFactory seriesFactory = new SeriesFactory();
        Bar bar1 = SeriesFactory.newBar("计划数量");
        bar1.yAxisIndex(0).label().normal().show(true).position(Position.top);
        bar1.data(12390, 12345, 23949, 10023, 9898);
        Bar bar2 = SeriesFactory.newBar("A班完成数量");
        bar2.yAxisIndex(0).label().normal().show(true).position(Position.top);
        bar2.data(2390, 2345, 3949, 1023, 898);
        Bar bar3 = SeriesFactory.newBar("B班完成数量");
        bar3.yAxisIndex(0).label().normal().show(true).position(Position.top);
        bar3.data(10390, 10345, 20949, 10000, 7000);


        Line line1 = seriesFactory.newLine("A班达成率");
        line1.yAxisIndex(1).label().normal().show(true).position(Position.top);
        line1.data(70, 80, 90, 78, 94);

        Line line2 = seriesFactory.newLine("B班达成率");
        line2.yAxisIndex(1).label().normal().show(true).position(Position.top);
        line2.data(80, 90, 70, 98, 99);

        option.grid().top("20%").containLabel(false);
        option.series(bar1, bar2, bar3, line1, line2);


        return option.toString();
    }


    @JavascriptInterface
    public static String getJiaDOngLvGaugeOptions1() {
        GsonOption option = new GsonOption();
        option.setTooltip(new Tooltip().formatter("{a} <br/>{b} : {c}%"));
        Gauge gauge = new Gauge();
        gauge.name("设备稼动率");
        gauge.detail(new Detail().formatter("{value}%"));
        gauge.data(new Data().setValue(90).setName("稼动率"));
        option.series(gauge);
        return option.toString();
    }

    @JavascriptInterface
    public static String getJiaDOngLvBarOptions2() {
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
    public static String getJiaDOngLvBarOptions3() {
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
    public static String getJiaDOngLvBarOptions4() {
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
    public static String getChuQinLvGaugeOptions1() {
        GsonOption option = new GsonOption();
        option.setTooltip(new Tooltip().formatter("{a} <br/>{b} : {c}%"));
        Gauge gauge = new Gauge();
        gauge.name("出勤率");
        gauge.detail(new Detail().formatter("{value}%"));
        gauge.data(new Data().setValue(100).setName("出勤率"));
        option.series(gauge);
        return option.toString();
    }


    @JavascriptInterface
    public static String getChuQinLvBarOptions2() {
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
        categoryAxisX.data("06月24", "06月25", "06月26", "06月27", "06月28", "06月29", "06月30");
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
        bar1.data(890, 845, 890, 890, 890, 890, 890);
        Bar bar2 = SeriesFactory.newBar("A班出勤人数");
        bar2.yAxisIndex(0).label().normal().show(true).position(Position.top);
        bar2.data(390, 345, 349, 123, 298, 400, 320);
        Bar bar3 = SeriesFactory.newBar("B班出勤人数");
        bar3.yAxisIndex(0).label().normal().show(true).position(Position.top);
        bar3.data(500, 500, 249, 730, 560, 590, 540);


        Line line1 = seriesFactory.newLine("A班出勤率");
        line1.yAxisIndex(1).label().normal().show(true).position(Position.top);
        line1.data(70, 80, 90, 78, 94, 80, 70);

        Line line2 = seriesFactory.newLine("B班出勤率");
        line2.yAxisIndex(1).label().normal().show(true).position(Position.top);
        line2.data(80, 90, 70, 98, 99, 89, 99);

        option.grid().top("20%").containLabel(false);
        option.series(bar1, bar2, bar3, line1, line2);
        return option.toString();
    }


    @JavascriptInterface
    public static String getChuQinLvBarOptions3() {
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
    public static String getChuQinLvBarOptions4() {
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


}
