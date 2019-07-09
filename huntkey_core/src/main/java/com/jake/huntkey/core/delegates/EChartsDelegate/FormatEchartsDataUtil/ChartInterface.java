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
import com.github.abel533.echarts.json.GsonOption;
import com.github.abel533.echarts.series.Bar;
import com.github.abel533.echarts.series.Gauge;
import com.github.abel533.echarts.series.SeriesFactory;
import com.github.abel533.echarts.series.gauge.Detail;

import java.util.ArrayList;

/**
 * 注入到JS里的对象接口
 */
public class ChartInterface {


    @JavascriptInterface
    public String getZhiTongLvLvOptions1(String rate) {
        return GetChartsOptionString.getZhiTongLvGauge1(rate);
    }

    @JavascriptInterface
    public String getZhiTongLvLvOptions2(ArrayList<String> axis, ArrayList<String> data) {
        return GetChartsOptionString.getZhiTongLvBarOptions2(axis, data);
    }

    @JavascriptInterface
    public String getZhiTongLvLvOptions3(ArrayList<String> axis, ArrayList<String> data) {
        return GetChartsOptionString.getZhiTongLvBarOptions3(axis, data);
    }

    @JavascriptInterface
    public String getZhiTongLvLvOptions4(ArrayList<String> axis, ArrayList<String> data) {
        return GetChartsOptionString.getZhiTongLvBarOptions4(axis, data);
    }

    @JavascriptInterface
    public String getDaChengLvOptions1() {
        return GetChartsOptionString.getDaChengLvGaugeChartOptions();
    }


    @JavascriptInterface
    public String getDaChengLvOptions2() {
        return GetChartsOptionString.getDaChengLvDoubleAxisBarLineOptions();
    }

    @JavascriptInterface
    public String getDaChengLvOptions3() {
        return GetChartsOptionString.getDaChengLvBarChartOptions();
    }

    @JavascriptInterface
    public String getJiaDongLvOptions1() {
        return GetChartsOptionString.getJiaDOngLvGaugeOptions1();
    }

    @JavascriptInterface
    public String getJiaDongLvOptions2() {
        return GetChartsOptionString.getJiaDOngLvBarOptions2();
    }

    @JavascriptInterface
    public String getJiaDongLvOptions3() {
        return GetChartsOptionString.getJiaDOngLvBarOptions3();
    }

    @JavascriptInterface
    public String getJiaDongLvOptions4() {
        return GetChartsOptionString.getJiaDOngLvBarOptions4();
    }

    @JavascriptInterface
    public String getChuQinLvOptions1() {
        return GetChartsOptionString.getChuQinLvGaugeOptions1();
    }

    @JavascriptInterface
    public String getChuQinLvOptions2() {
        return GetChartsOptionString.getChuQinLvBarOptions2();
    }

    @JavascriptInterface
    public String getChuQinLvOptions3() {
        return GetChartsOptionString.getChuQinLvBarOptions3();
    }

    @JavascriptInterface
    public String getChuQinLvOptions4() {
        return GetChartsOptionString.getChuQinLvBarOptions4();
    }
}