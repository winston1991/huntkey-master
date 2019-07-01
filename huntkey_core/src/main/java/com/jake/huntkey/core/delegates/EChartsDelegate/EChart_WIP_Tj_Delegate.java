package com.jake.huntkey.core.delegates.EChartsDelegate;

import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.webkit.JavascriptInterface;
import android.widget.FrameLayout;

import com.bin.david.form.core.SmartTable;
import com.github.abel533.echarts.Legend;
import com.github.abel533.echarts.Title;
import com.github.abel533.echarts.Tooltip;
import com.github.abel533.echarts.axis.CategoryAxis;
import com.github.abel533.echarts.axis.ValueAxis;
import com.github.abel533.echarts.code.AxisType;
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
import com.jake.huntkey.core.delegates.basedelegate.CheckPermissionDelegate;
import com.jake.huntkey.core.entity.StationSheetEntity;
import com.jake.huntkey.core.entity.WIPEntity;

import java.util.ArrayList;

import butterknife.BindView;

public class EChart_WIP_Tj_Delegate extends CheckPermissionDelegate {

    private static final String ARG_TITLE = "arg_type";
    @BindView(R2.id.id_smart_table1)
    SmartTable<WIPEntity> smartTable1;

    @BindView(R2.id.id_smart_table2)
    SmartTable<StationSheetEntity> smartTable2;

    public static EChart_WIP_Tj_Delegate newInstance(String title) {

        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        EChart_WIP_Tj_Delegate fragment = new EChart_WIP_Tj_Delegate();
        fragment.setArguments(args);
        return fragment;
    }

    protected void initViews(View rootview) {


        Bundle bundle = getArguments();
        if (bundle != null) {
            String mTitle = bundle.getString(ARG_TITLE);
            //super.mToolbar.setTitle(mTitle);
        }
        smartTable1.getConfig().setFixedTitle(true);
        smartTable1.getConfig().setShowXSequence(false);
        smartTable1.getConfig().setShowYSequence(false);
        smartTable1.setData(getData());

        smartTable2.getConfig().setFixedTitle(true);
        smartTable2.getConfig().setShowXSequence(false);
        smartTable2.getConfig().setShowYSequence(false);
        smartTable2.setData(getData2());

    }

    @Override
    public Object setLayout() {
        return R.layout.echarts_wip_tj_delegate_layout;
    }


    @Override
    protected void onBindView(Bundle savedInstanceState, View rootView) {
        initViews(rootView);
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


    private ArrayList<StationSheetEntity> getData2() {
        ArrayList datas = new ArrayList<StationSheetEntity>();
        StationSheetEntity stationSheetEntity;
        for (int i = 0; i < 12; i++) {
            stationSheetEntity = new StationSheetEntity();
            stationSheetEntity.setStationSheet("WBJ332"+i);
            stationSheetEntity.setAoiTest("23");
            stationSheetEntity.setBarCodeBinding("23" + i);
            stationSheetEntity.setFunctionFinalTest("" + i);
            stationSheetEntity.setPackingScan("2" + i);
            stationSheetEntity.setHeightPressureTest("1" + i);
            stationSheetEntity.setBarCodeTransformation("23" + i);
            datas.add(stationSheetEntity);
        }
        return datas;
    }
}
