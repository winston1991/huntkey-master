package com.jake.huntkey.core.delegates.EChartsDelegate.FormatEchartsDataUtil;

import com.github.abel533.echarts.AxisPointer;
import com.github.abel533.echarts.DataZoom;
import com.github.abel533.echarts.Grid;
import com.github.abel533.echarts.Legend;
import com.github.abel533.echarts.Title;
import com.github.abel533.echarts.Tooltip;
import com.github.abel533.echarts.axis.AxisTick;
import com.github.abel533.echarts.axis.CategoryAxis;
import com.github.abel533.echarts.code.AxisType;
import com.github.abel533.echarts.code.DataZoomType;
import com.github.abel533.echarts.code.PointerType;
import com.github.abel533.echarts.code.Position;
import com.github.abel533.echarts.code.Trigger;
import com.github.abel533.echarts.data.SeriesData;
import com.github.abel533.echarts.json.GsonOption;
import com.github.abel533.echarts.series.Bar;
import com.github.abel533.echarts.series.Line;
import com.github.abel533.echarts.series.Series;
import com.github.abel533.echarts.series.SeriesFactory;

public class GetChartsOptionString {

    public static String getDoubleAxisBarLineOptions() {
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


    public static String getChuQinLvDoubleAxisBarLineOptions() {
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
}
