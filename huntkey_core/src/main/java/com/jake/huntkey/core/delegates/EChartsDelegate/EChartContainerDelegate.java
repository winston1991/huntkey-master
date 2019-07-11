package com.jake.huntkey.core.delegates.EChartsDelegate;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bin.david.form.core.SmartTable;
import com.bin.david.form.data.column.Column;
import com.bin.david.form.data.format.bg.BaseBackgroundFormat;
import com.bin.david.form.data.style.FontStyle;
import com.bin.david.form.data.table.TableData;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.jake.huntkey.core.R;
import com.jake.huntkey.core.R2;
import com.jake.huntkey.core.app.Consts;
import com.jake.huntkey.core.delegates.EChartsDelegate.FormatEchartsDataUtil.ChartInterface;
import com.jake.huntkey.core.entity.HomePageItemEntity;
import com.jake.huntkey.core.entity.ProductionLineEntity;
import com.jake.huntkey.core.net.WebApiServices;
import com.jake.huntkey.core.netbean.GetEmpRateResponse;
import com.jake.huntkey.core.netbean.GetFpyRateResponse;
import com.jake.huntkey.core.netbean.GetJdRateResponse;
import com.jake.huntkey.core.netbean.GetTcrRateResponse;
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

public class EChartContainerDelegate extends BaseWebViewDelegate implements WebViewCreater.OnPageLoadFinishedListener {

    private static final String ARG_LineId = "lineId";
    private static final String ARG_DeptCode = "DeptCode";
    @BindView(R2.id.fl_container)
    public FrameLayout flContainer;
    @BindView(R2.id.id_smart_table1)
    SmartTable idSmartTable1;
    private ChartInterface mChartInterface;
    private String lineId;  //线体id
    private String sid; //服务器id
    private String accid; //工厂id
    private String deptCode; //部门code;
    ArrayList<Column> colums;
    ArrayList<ProductionLineEntity> tableDatas;
    GetFpyRateResponse mGetFpyRateResponse;


    public static EChartContainerDelegate newInstance(String lineId, String deptCode) {
        Bundle args = new Bundle();
        args.putString(ARG_LineId, lineId);
        args.putString(ARG_DeptCode, deptCode);
        EChartContainerDelegate fragment = new EChartContainerDelegate();
        fragment.setArguments(args);
        return fragment;
    }

    protected void initViews(View rootview) {
        FontStyle fontStyle = new FontStyle();
        fontStyle.setTextColor(Color.WHITE);
        idSmartTable1.getConfig().setColumnTitleBackground(new BaseBackgroundFormat(Color.rgb(0, 152, 217)));
        idSmartTable1.getConfig().setColumnTitleStyle(fontStyle);
        idSmartTable1.getConfig().setShowXSequence(false);
        idSmartTable1.getConfig().setShowYSequence(false);
        idSmartTable1.getConfig().setShowTableTitle(false);
        mAgentWeb = new WebViewCreater(this).createAgentWeb(this, flContainer);
        //注入接口,供JS调用
        mAgentWeb.getJsInterfaceHolder().addJavaObject("Android", mChartInterface = new ChartInterface());
        Bundle bundle = getArguments();
        if (bundle != null) {
            lineId = bundle.getString(ARG_LineId);
            deptCode = bundle.getString(ARG_DeptCode);
        }

    }


    public void loadZhiTongLvChart( ) {
        idSmartTable1.setVisibility(View.GONE);
        mAgentWeb.getJsAccessEntrace().quickCallJs("clearChart");
        mAgentWeb.getJsAccessEntrace().quickCallJs("showDiv");
        getZhiTongLvData();
    }

    public void getZhiTongLvData( ) {
        if (mGetFpyRateResponse == null) {
            DialogLoaderManager.showLoading(_mActivity);
            ViseHttp.RETROFIT()
                    .create(WebApiServices.class)
                    .GetFpyRate(sid, lineId, accid)
                    .compose(ApiTransformer.<GetFpyRateResponse>norTransformer())
                    .subscribe(new ApiCallbackSubscriber<>(new ACallback<GetFpyRateResponse>() {
                        @Override
                        public void onSuccess(GetFpyRateResponse data) {
                            mGetFpyRateResponse = data;
                            dealGetFpyRateResponse(data);
                            DialogLoaderManager.stopLoading();
                        }
                        @Override
                        public void onFail(int errCode, String errMsg) {
                            DialogLoaderManager.stopLoading();
                        }
                    }));
        } else {
            dealGetFpyRateResponse(mGetFpyRateResponse);
        }


    }

    private void dealGetFpyRateResponse(GetFpyRateResponse getFpyRateResponse) {
        if (getFpyRateResponse.getContent() != null && getFpyRateResponse.getStatus().equals("OK") && getFpyRateResponse.getContent().size() > 0) {

            List<String> axisX = getFpyRateResponse.getContent().get(0).getPass7DayRate().getOtpt_start_time();
            List<String> data = getFpyRateResponse.getContent().get(0).getPass7DayRate().getFpy_rate();
            mAgentWeb.getJsAccessEntrace().quickCallJs("loadChartView", "chart2", mChartInterface.getZhiTongLvLvOptions2(axisX, data));

            axisX = getFpyRateResponse.getContent().get(0).getPassRateTop5().getLayt_name();
            data = getFpyRateResponse.getContent().get(0).getPassRateTop5().getRate();
            mAgentWeb.getJsAccessEntrace().quickCallJs("loadChartView", "chart3", mChartInterface.getZhiTongLvLvOptions3(axisX, data));

            axisX = getFpyRateResponse.getContent().get(0).getLossRateTop5().getLayt_name();
            data = getFpyRateResponse.getContent().get(0).getLossRateTop5().getRate();
            mAgentWeb.getJsAccessEntrace().quickCallJs("loadChartView", "chart4", mChartInterface.getZhiTongLvLvOptions4(axisX, data));

            mAgentWeb.getJsAccessEntrace().quickCallJs("loadChartView", "chart1", mChartInterface.getZhiTongLvLvOptions1(getFpyRateResponse.getContent().get(0).getRate()));
        }
    }


    public void loadDaChengLvChart(GetTcrRateResponse mGetTcrRateResponse) {
        mAgentWeb.getJsAccessEntrace().quickCallJs("clearChart");
        mAgentWeb.getJsAccessEntrace().quickCallJs("hideDiv");
        idSmartTable1.setVisibility(View.VISIBLE);
        getDaChengLvData(mGetTcrRateResponse);

    }

    public void getDaChengLvData(GetTcrRateResponse getTcrRateResponse) {

        if (getTcrRateResponse == null) {
            DialogLoaderManager.showLoading(_mActivity);
            ViseHttp.RETROFIT()
                    .create(WebApiServices.class)
                    .GetTcrRate(sid, lineId, accid)
                    .compose(ApiTransformer.<GetTcrRateResponse>norTransformer())
                    .subscribe(new ApiCallbackSubscriber<>(new ACallback<GetTcrRateResponse>() {
                        @Override
                        public void onSuccess(GetTcrRateResponse data) {
                            dealGetTcrRateResponse(data);
                            DialogLoaderManager.stopLoading();
                        }

                        @Override
                        public void onFail(int errCode, String errMsg) {
                            DialogLoaderManager.stopLoading();
                        }
                    }));
        } else {
            dealGetTcrRateResponse(getTcrRateResponse);
        }

    }

    private void dealGetTcrRateResponse(GetTcrRateResponse data) {
        if (data.getContent() != null && data.getStatus().equals("OK") && data.getContent().size() > 0) {
            getTableColums(data.getContent().get(0).getMonitorData().get(0));
            getTableDatas(data.getContent().get(0).getMonitorData());

            mAgentWeb.getJsAccessEntrace().quickCallJs("loadChartView", "chart1", mChartInterface.getDaChengLvOptions1(data.getContent().get(0).getRate()));

            List<String> axisX = data.getContent().get(0).getTcr7DayRate().getOtpt_start_time();
            List<String> legend1 = data.getContent().get(0).getTcr7DayRate().getTargetqty(); //计划数量
            List<String> legend2 = data.getContent().get(0).getTcr7DayRate().getOqty(); //A班完成数
            List<String> legend3 = data.getContent().get(0).getTcr7DayRate().getReachrate(); //A班达成率
            List<String> legend4 = data.getContent().get(0).getTcr7DayRate().getOqty1(); //B班完成数
            List<String> legend5 = data.getContent().get(0).getTcr7DayRate().getReachrate1(); //B班达成率
            mAgentWeb.getJsAccessEntrace().quickCallJs("loadChartView", "chart2", mChartInterface.getDaChengLvOptions2(axisX, legend1, legend2, legend3, legend4, legend5));

            axisX = data.getContent().get(0).getOQty().getOtpt_start_time();
            legend1 = data.getContent().get(0).getOQty().getTargetqty();//计划产能
            legend2 = data.getContent().get(0).getOQty().getOqty();//实际产能
            mAgentWeb.getJsAccessEntrace().quickCallJs("loadChartView", "chart3", mChartInterface.getDaChengLvOptions3(axisX, legend1, legend2));

        }
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

    private void getTableDatas(final List<List<String>> data) {
        if (data != null && data.size() > 0) {
            tableDatas = new ArrayList<>();
            ProductionLineEntity productionLineEntity;
            for (int i = 1; i < data.size(); i++) {
                productionLineEntity = new ProductionLineEntity();
                for (int j = 1; j <= data.get(i).size(); j++) {
                    try {
                        Field field = productionLineEntity.getClass().getDeclaredField("item" + j);
                        field.setAccessible(true);
                        field.set(productionLineEntity, data.get(i).get(j - 1));
                    } catch (Exception e) {
                    }
                }
                tableDatas.add(productionLineEntity);
            }
            if (tableDatas != null && colums != null) {
                TableData<ProductionLineEntity> tableData = new TableData<ProductionLineEntity>("", tableDatas, colums);
                idSmartTable1.setTableData(tableData);
            }
        }
    }

    public void loadJiaDongLvChart(GetJdRateResponse getJdRateResponse) {
        idSmartTable1.setVisibility(View.GONE);
        mAgentWeb.getJsAccessEntrace().quickCallJs("clearChart");
        mAgentWeb.getJsAccessEntrace().quickCallJs("showDiv");
        getJiaDongLvData(getJdRateResponse);

    }

    public void getJiaDongLvData(GetJdRateResponse getJdRateResponse) {
        if (getJdRateResponse == null) {
            DialogLoaderManager.showLoading(_mActivity);
            ViseHttp.RETROFIT()
                    .create(WebApiServices.class)
                    .GetJdRate(sid, lineId, accid)
                    .compose(ApiTransformer.<GetJdRateResponse>norTransformer())
                    .subscribe(new ApiCallbackSubscriber<>(new ACallback<GetJdRateResponse>() {
                        @Override
                        public void onSuccess(GetJdRateResponse data) {
                            dealGetJdRateResponse(data);
                            DialogLoaderManager.stopLoading();
                        }

                        @Override
                        public void onFail(int errCode, String errMsg) {
                            DialogLoaderManager.stopLoading();
                        }
                    }));
        } else {
            dealGetJdRateResponse(getJdRateResponse);
        }

    }

    private void dealGetJdRateResponse(GetJdRateResponse data) {
        if (data != null && data.getContent() != null && data.getStatus().equals("OK") && data.getContent().size() > 0) {
            mAgentWeb.getJsAccessEntrace().quickCallJs("loadChartView", "chart1", mChartInterface.getJiaDongLvOptions1(data.getContent().get(0).getRate()));
            List<String> axisX = data.getContent().get(0).getJd7DayRate().getX();
            List<String> axisY = data.getContent().get(0).getJd7DayRate().getY();
            mAgentWeb.getJsAccessEntrace().quickCallJs("loadChartView", "chart2", mChartInterface.getJiaDongLvOptions2(axisX, axisY));

            axisX = data.getContent().get(0).getRateTop5().getX();
            axisY = data.getContent().get(0).getRateTop5().getY();
            mAgentWeb.getJsAccessEntrace().quickCallJs("loadChartView", "chart3", mChartInterface.getJiaDongLvOptions3(axisX, axisY));

            axisX = data.getContent().get(0).getStopLine().getX();
            axisY = data.getContent().get(0).getStopLine().getY();
            mAgentWeb.getJsAccessEntrace().quickCallJs("loadChartView", "chart4", mChartInterface.getJiaDongLvOptions4(axisX, axisY));
        }
    }

    public void loadChuQinLvChart(GetEmpRateResponse getEmpRateResponse) {
        idSmartTable1.setVisibility(View.GONE);
        mAgentWeb.getJsAccessEntrace().quickCallJs("clearChart");
        mAgentWeb.getJsAccessEntrace().quickCallJs("showDiv");
        getChuQinLvData(getEmpRateResponse);

    }

    public void getChuQinLvData(GetEmpRateResponse getEmpRateResponse) {

        if (getEmpRateResponse == null) {
            DialogLoaderManager.showLoading(_mActivity);
            String deptCodes = SPUtils.getInstance(Consts.SP_INSTANT_NAME).getString(Consts.SP_ITEM_DEPTCODE_NAME);
            ViseHttp.RETROFIT()
                    .create(WebApiServices.class)
                    .GetEmpRate(deptCode, deptCodes)
                    .compose(ApiTransformer.<GetEmpRateResponse>norTransformer())
                    .subscribe(new ApiCallbackSubscriber<>(new ACallback<GetEmpRateResponse>() {
                        @Override
                        public void onSuccess(GetEmpRateResponse data) {
                            dealGetEmpRateResponse(data);
                            DialogLoaderManager.stopLoading();
                        }

                        @Override
                        public void onFail(int errCode, String errMsg) {
                            DialogLoaderManager.stopLoading();
                        }
                    }));
        } else {
            dealGetEmpRateResponse(getEmpRateResponse);
        }


    }

    private void dealGetEmpRateResponse(GetEmpRateResponse data) {
        if (data != null && data.getContent() != null && data.getStatus().equals("OK") && data.getContent().size() > 0) {
            String rate = data.getContent().get(0).getRate();
            mAgentWeb.getJsAccessEntrace().quickCallJs("loadChartView", "chart1", mChartInterface.getChuQinLvOptions1(rate));

            List<String> axisX = data.getContent().get(0).getEmp7DayRate().getXdate();
            List<String> emp_num = data.getContent().get(0).getEmp7DayRate().getEmp_num();//在职人数
            List<String> a_real_emp_num = data.getContent().get(0).getEmp7DayRate().getA_emp_num_real();//A班出勤人数
            List<String> a_rate = data.getContent().get(0).getEmp7DayRate().getA_rate();//A班出勤率
            List<String> b_real_emp_num = data.getContent().get(0).getEmp7DayRate().getB_emp_num_real();//B班出勤人数
            List<String> b_rate = data.getContent().get(0).getEmp7DayRate().getB_rate();//B班出勤率

            mAgentWeb.getJsAccessEntrace().quickCallJs("loadChartView", "chart2", mChartInterface.getChuQinLvOptions2(axisX, emp_num, a_real_emp_num, a_rate, b_real_emp_num, b_rate));

            axisX = data.getContent().get(0).getWeekEmpRate().getXdate();
            emp_num = data.getContent().get(0).getWeekEmpRate().getEmp_num();//在职人数
            a_real_emp_num = data.getContent().get(0).getWeekEmpRate().getA_emp_num_real();//A班出勤人数
            a_rate = data.getContent().get(0).getWeekEmpRate().getA_rate();//A班出勤率
            b_real_emp_num = data.getContent().get(0).getWeekEmpRate().getB_emp_num_real();//B班出勤人数
            b_rate = data.getContent().get(0).getWeekEmpRate().getB_rate();//B班出勤率
            mAgentWeb.getJsAccessEntrace().quickCallJs("loadChartView", "chart3", mChartInterface.getChuQinLvOptions3(axisX, emp_num, a_real_emp_num, a_rate, b_real_emp_num, b_rate));

            axisX = data.getContent().get(0).getLineEmpRate().getDept_name(); //部门名称
            emp_num = data.getContent().get(0).getLineEmpRate().getEmp_num();//在职人数
            a_real_emp_num = data.getContent().get(0).getLineEmpRate().getEmp_num_real();//实际出勤人数
            a_rate = data.getContent().get(0).getLineEmpRate().getRate(); //出勤率
            mAgentWeb.getJsAccessEntrace().quickCallJs("loadChartView", "chart4", mChartInterface.getChuQinLvOptions4(axisX, emp_num, a_real_emp_num, a_rate));

        }
    }

    @Override
    public Object setLayout() {
        return R.layout.echarts_container_delegate_layout;
    }


    @Override
    protected void onBindView(Bundle savedInstanceState, View rootView) {
        initViews(rootView);
    }

    @Override
    public void onPageLoadFinished() {
        loadZhiTongLvChart();
    }

    /**
     * 接收从HomePagerDelegate发送来的信息
     *
     * @param homePageItemEntity
     */
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
