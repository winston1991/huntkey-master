package com.jake.huntkey.core.delegates.EChartsDelegate;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.webkit.JavascriptInterface;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bin.david.form.core.SmartTable;
import com.bin.david.form.data.column.Column;
import com.bin.david.form.data.format.bg.BaseBackgroundFormat;
import com.bin.david.form.data.style.FontStyle;
import com.bin.david.form.data.table.TableData;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.ToastUtils;
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
import com.jake.huntkey.core.entity.HomePageItemEntity;
import com.jake.huntkey.core.entity.ProductionLineEntity;
import com.jake.huntkey.core.entity.StationSheetEntity;
import com.jake.huntkey.core.entity.WIPEntity;
import com.jake.huntkey.core.net.WebApiServices;
import com.jake.huntkey.core.netbean.GetFpyRateResponse;
import com.jake.huntkey.core.netbean.GetSampleResponse;
import com.jake.huntkey.core.netbean.GetWipDataResponse;
import com.jake.huntkey.core.ui.icon.Loading.DialogLoaderManager;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.vise.xsnow.http.core.ApiTransformer;
import com.vise.xsnow.http.subscriber.ApiCallbackSubscriber;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import me.yokeyword.eventbusactivityscope.EventBusActivityScope;

public class EChart_WIP_Tj_Delegate extends CheckPermissionDelegate {

    private static final String ARG_lineID = "lineId";
    @BindView(R2.id.id_smart_table1)
    SmartTable<WIPEntity> smartTable1;

    @BindView(R2.id.id_smart_table2)
    SmartTable<ProductionLineEntity> smartTable2;
    ArrayList<Column> colums;
    ArrayList<ProductionLineEntity> tableDatas;

    private String lineId;
    private String sid;
    private String accid;

    public static EChart_WIP_Tj_Delegate newInstance(String lineId) {

        Bundle args = new Bundle();
        args.putString(ARG_lineID, lineId);
        EChart_WIP_Tj_Delegate fragment = new EChart_WIP_Tj_Delegate();
        fragment.setArguments(args);
        return fragment;
    }

    protected void initViews(View rootview) {


        Bundle bundle = getArguments();
        if (bundle != null) {
            lineId = bundle.getString(ARG_lineID);
        }
        smartTable1.getConfig().setFixedTitle(true);
        smartTable1.getConfig().setShowXSequence(false);
        smartTable1.getConfig().setShowYSequence(false);
        smartTable1.getConfig().setShowTableTitle(false);
        // smartTable1.setData(getData());
        FontStyle fontStyle = new FontStyle();
        fontStyle.setTextColor(Color.WHITE);
        smartTable1.getConfig().setColumnTitleBackground(new BaseBackgroundFormat(Color.rgb(0, 152, 217)));
        smartTable1.getConfig().setColumnTitleStyle(fontStyle);


        smartTable2.getConfig().setFixedTitle(true);
        smartTable2.getConfig().setShowXSequence(false);
        smartTable2.getConfig().setShowYSequence(false);
        smartTable2.getConfig().setShowTableTitle(false);
//        smartTable2.setData(getData2());
        smartTable2.getConfig().setColumnTitleBackground(new BaseBackgroundFormat(Color.rgb(0, 152, 217)));
        smartTable2.getConfig().setColumnTitleStyle(fontStyle);

        fontStyle = new FontStyle();
        fontStyle.setTextColor(Color.GRAY);
        fontStyle.setTextSize(ConvertUtils.sp2px(20));
        smartTable1.getConfig().setTableTitleStyle(fontStyle);
        smartTable2.getConfig().setTableTitleStyle(fontStyle);

        getSampleTableData();
        getWipTableData();
    }

    private void getWipTableData() {
        ViseHttp.RETROFIT()
                .create(WebApiServices.class)
                .GetWipData(sid, lineId, accid)
                .compose(ApiTransformer.<GetWipDataResponse>norTransformer())
                .subscribe(new ApiCallbackSubscriber<>(new ACallback<GetWipDataResponse>() {
                    @Override
                    public void onSuccess(GetWipDataResponse data) {
                        if (data != null && data.getContent() != null && data.getStatus().equals("OK") && data.getContent().size() > 0) {
                            getTableColums(data.getContent().get(0).getTitles());
                            getTableData(data.getContent().get(0).getData());
                        }

                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {

                    }
                }));
    }

    private void getTableColums(List<String> Titles) {
        //创建表实体
        if (Titles.size() > 0) {
            colums = new ArrayList<>();
            Column item = null;
            for (int i = 1; i <= Titles.size(); i++) {
                String tmp = "item" + (i);
                item = new Column(Titles.get(i - 1), tmp);
                colums.add(item);
            }
        }
    }


    private void getTableData(final List<List<String>> data) {
        if (data != null && data.size() > 0) {
            tableDatas = new ArrayList<>();
            ProductionLineEntity productionLineEntity;
            for (int i = 0; i < data.size(); i++) {
                productionLineEntity = new ProductionLineEntity();
                for (int j = 1; j <= data.get(i).size(); j++) {
                    try {
                        Field field = productionLineEntity.getClass().getDeclaredField("item" + j);
                        field.setAccessible(true);
                        field.set(productionLineEntity, data.get(i).get(j - 1));
                    } catch (Exception e) {
                        ToastUtils.showShort(e.toString());
                    }
                }
                tableDatas.add(productionLineEntity);
            }
            if (tableDatas != null && colums != null) {
                TableData<ProductionLineEntity> tableData = new TableData<ProductionLineEntity>("", tableDatas, colums);
                smartTable2.setTableData(tableData);
            }

        }


    }

    //获取抽样表数据
    private void getSampleTableData() {
        ViseHttp.RETROFIT()
                .create(WebApiServices.class)
                .GetSample(sid, lineId, accid)
                .compose(ApiTransformer.<GetSampleResponse>norTransformer())
                .subscribe(new ApiCallbackSubscriber<>(new ACallback<GetSampleResponse>() {
                    @Override
                    public void onSuccess(GetSampleResponse data) {
                        if (data != null && data.getContent() != null && data.getStatus().equals("OK") && data.getContent().size() > 0) {
                            ArrayList<WIPEntity> samples = new ArrayList<>();
                            WIPEntity wipEntity;
                            for (int i = 0; i < data.getContent().size(); i++) {
                                wipEntity = new WIPEntity();
                                wipEntity.setId("" + (i + 1));
                                wipEntity.setSamplingResult(data.getContent().get(i).getCode_name());
                                wipEntity.setSamplingNumber(data.getContent().get(i).getSapm_lot());
                                wipEntity.setQuantity(data.getContent().get(i).getSapm_act_qty());
                                wipEntity.setMakeFlowNumber(data.getContent().get(i).getSapm_wo_nbr());
                                String sapm_remark = data.getContent().get(i).getSapm_remark();
                                wipEntity.setnGInfo((sapm_remark == null) ? "-" : sapm_remark);
                                samples.add(wipEntity);
                            }
                            smartTable1.setData(samples);
                        }

                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {
                        ToastUtils.showShort(errMsg);
                    }
                }));
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
        for (int i = 0; i < 22; i++) {
            stationSheetEntity = new StationSheetEntity();
            stationSheetEntity.setStationSheet("WBJ332" + i);
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


    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEvent(HomePageItemEntity homePageItemEntity) {
        sid = homePageItemEntity.sid;
        accid = homePageItemEntity.accid;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        EventBusActivityScope.getDefault(_mActivity).register(this);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        EventBusActivityScope.getDefault(_mActivity).unregister(this);
        super.onDestroyView();
    }
}
