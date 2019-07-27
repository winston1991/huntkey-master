package com.jake.huntkey.core.delegates.EChartsDelegate.FormatEchartsDataUtil;

import android.webkit.JavascriptInterface;
import android.widget.FrameLayout;

import androidx.fragment.app.FragmentActivity;

import com.blankj.utilcode.util.ScreenUtils;

import com.just.agentweb.core.AgentWeb;

import java.util.HashMap;
import java.util.List;

/**
 * 注入到JS里的对象接口
 */
public class ChartInterface {

    protected FragmentActivity mActivity = null;
    protected AgentWeb mAgentWeb;

    public ChartInterface(FragmentActivity activity, AgentWeb agentWeb) {
        this.mActivity = activity;
        this.mAgentWeb = agentWeb;
    }

    @JavascriptInterface
    public String getZhiTongLvLvOptions1(String rate, HashMap<String,Float> hashMap) {
        return GetChartsOptionString.getZhiTongLvGauge1(rate, hashMap);
    }

    @JavascriptInterface
    public String getZhiTongLvLvOptions2(List<String> axis, List<String> data) {
        return GetChartsOptionString.getZhiTongLvBarOptions2(axis, data);
    }

    @JavascriptInterface
    public String getZhiTongLvLvOptions3(List<String> axis, List<String> data) {
        return GetChartsOptionString.getZhiTongLvBarOptions3(axis, data);
    }

    @JavascriptInterface
    public String getZhiTongLvLvOptions4(List<String> axis, List<String> data) {
        return GetChartsOptionString.getZhiTongLvBarOptions4(axis, data);
    }

    @JavascriptInterface
    public String getDaChengLvOptions1(String rate, HashMap<String, Float> hashmap) {
        return GetChartsOptionString.getDaChengLvGaugeChartOptions(rate, hashmap);
    }


    @JavascriptInterface
    public String getDaChengLvOptions2(List<String> axis, List<String> list1, List<String> list2, List<String> list3, List<String> list4, List<String> list5) {
        return GetChartsOptionString.getDaChengLvDoubleAxisBarLineOptions(axis, list1, list2, list3, list4, list5);
    }

    @JavascriptInterface
    public String getDaChengLvOptions3(List<String> axis, List<String> list1, List<String> list2) {
        return GetChartsOptionString.getDaChengLvBarChartOptions(axis, list1, list2);
    }

    @JavascriptInterface
    public String getJiaDongLvOptions1(String rate) {
        return GetChartsOptionString.getJiaDongLvGaugeOptions1(rate);
    }

    @JavascriptInterface
    public String getJiaDongLvOptions2(List<String> axisX, List<String> axisY) {
        return GetChartsOptionString.getJiaDOngLvBarOptions2(axisX, axisY);
    }

    @JavascriptInterface
    public String getJiaDongLvOptions3(List<String> axisX, List<String> axisY) {
        return GetChartsOptionString.getJiaDOngLvBarOptions3(axisX, axisY);
    }

    @JavascriptInterface
    public String getJiaDongLvOptions4(List<String> axisX, List<String> axisY) {
        return GetChartsOptionString.getJiaDOngLvBarOptions4(axisX, axisY);
    }

    @JavascriptInterface
    public String getChuQinLvOptions1(String rate) {
        return GetChartsOptionString.getChuQinLvGaugeOptions1(rate);
    }

    @JavascriptInterface
    public String getChuQinLvOptions2(List<String>... list) {
        return GetChartsOptionString.getChuQinLvBarOptions2(list);
    }

    @JavascriptInterface
    public String getChuQinLvOptions3(List<String>... lists) {
        return GetChartsOptionString.getChuQinLvBarOptions3(lists);
    }

    @JavascriptInterface
    public String getChuQinLvOptions4(List<String>... lists) {
        return GetChartsOptionString.getChuQinLvBarOptions4(lists);
    }



    @JavascriptInterface
    public void resize(final float height) {
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mAgentWeb.getWebCreator().getWebView().setLayoutParams(new FrameLayout.LayoutParams(ScreenUtils.getScreenWidth(), (int) (height * ScreenUtils.getScreenDensity())));
            }
        });

    }

}