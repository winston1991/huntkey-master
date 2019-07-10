package com.jake.huntkey.core.delegates.EChartsDelegate;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.jake.huntkey.core.R;
import com.jake.huntkey.core.R2;
import com.jake.huntkey.core.app.Consts;
import com.jake.huntkey.core.delegates.EChartsDelegate.FormatEchartsDataUtil.ChartInterface;
import com.jake.huntkey.core.entity.HomePageItemEntity;
import com.jake.huntkey.core.net.WebApiServices;
import com.jake.huntkey.core.netbean.GetEmpRateResponse;
import com.jake.huntkey.core.netbean.GetFpyRateResponse;
import com.jake.huntkey.core.netbean.GetJdRateResponse;
import com.jake.huntkey.core.netbean.GetTcrRateResponse;
import com.jake.huntkey.core.ui.icon.Loading.DialogLoaderManager;
import com.vise.log.ViseLog;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.vise.xsnow.http.core.ApiTransformer;
import com.vise.xsnow.http.subscriber.ApiCallbackSubscriber;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import me.yokeyword.eventbusactivityscope.EventBusActivityScope;

public class EChartContainerDelegate extends BaseWebViewDelegate implements WebViewCreater.OnPageLoadFinishedListener {

    private static final String ARG_LineId = "lineId";
    private static final String ARG_DeptCode = "DeptCode";
    @BindView(R2.id.fl_container)
    public FrameLayout flContainer;
    private ChartInterface mChartInterface;
    private String lineId;  //线体id
    private String sid; //服务器id
    private String accid; //工厂id
    private String deptCode; //部门code;


    public static EChartContainerDelegate newInstance(String lineId, String deptCode) {

        Bundle args = new Bundle();
        args.putString(ARG_LineId, lineId);
        args.putString(ARG_DeptCode, deptCode);
        EChartContainerDelegate fragment = new EChartContainerDelegate();
        fragment.setArguments(args);
        return fragment;
    }

    protected void initViews(View rootview) {

        mAgentWeb = new WebViewCreater(this).createAgentWeb(this, flContainer);
        //注入接口,供JS调用
        mAgentWeb.getJsInterfaceHolder().addJavaObject("Android", mChartInterface = new ChartInterface());
        Bundle bundle = getArguments();
        if (bundle != null) {
            lineId = bundle.getString(ARG_LineId);
            deptCode = bundle.getString(ARG_DeptCode);
        }
        rootview.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
            }
        });
    }


    public void loadZhiTongLvChart() {
        mAgentWeb.getJsAccessEntrace().quickCallJs("clearChart");
        mAgentWeb.getJsAccessEntrace().quickCallJs("showDiv");
        getZhiTongLvData();


    }

    private void getZhiTongLvData() {
        DialogLoaderManager.showLoading(_mActivity);
        ViseHttp.RETROFIT()
                .create(WebApiServices.class)
                .GetFpyRate(sid, lineId, accid)
                .compose(ApiTransformer.<GetFpyRateResponse>norTransformer())
                .subscribe(new ApiCallbackSubscriber<>(new ACallback<GetFpyRateResponse>() {
                    @Override
                    public void onSuccess(GetFpyRateResponse getFpyRateResponse) {
                        if (getFpyRateResponse.getStatus().equals("OK") && getFpyRateResponse.getContent().size() > 0) {

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
                        DialogLoaderManager.stopLoading();
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {
                        ToastUtils.showShort(errMsg);
                        DialogLoaderManager.stopLoading();
                    }
                }));

    }


    public void loadDaChengLvChart() {
        mAgentWeb.getJsAccessEntrace().quickCallJs("clearChart");
        mAgentWeb.getJsAccessEntrace().quickCallJs("hideDiv");
        getDaChengLvData();

    }

    private void getDaChengLvData() {
        DialogLoaderManager.showLoading(_mActivity);
        ViseHttp.RETROFIT()
                .create(WebApiServices.class)
                .GetTcrRate(sid, lineId, accid)
                .compose(ApiTransformer.<GetTcrRateResponse>norTransformer())
                .subscribe(new ApiCallbackSubscriber<>(new ACallback<GetTcrRateResponse>() {
                    @Override
                    public void onSuccess(GetTcrRateResponse data) {
                        if (data.getStatus().equals("OK") && data.getContent().size() > 0) {

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
                        DialogLoaderManager.stopLoading();
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {
                        DialogLoaderManager.stopLoading();
                        ToastUtils.showShort(errMsg);
                    }
                }));
    }

    public void loadJiaDongLvChart() {
        mAgentWeb.getJsAccessEntrace().quickCallJs("clearChart");
        mAgentWeb.getJsAccessEntrace().quickCallJs("showDiv");
        getJiaDongLvData();

    }

    private void getJiaDongLvData() {
        DialogLoaderManager.showLoading(_mActivity);
        ViseHttp.RETROFIT()
                .create(WebApiServices.class)
                .GetJdRate(sid, lineId, accid)
                .compose(ApiTransformer.<GetJdRateResponse>norTransformer())
                .subscribe(new ApiCallbackSubscriber<>(new ACallback<GetJdRateResponse>() {
                    @Override
                    public void onSuccess(GetJdRateResponse data) {
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
                        DialogLoaderManager.stopLoading();

                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {
                        DialogLoaderManager.stopLoading();
                        ToastUtils.showShort(errMsg);
                    }
                }));
    }

    public void loadChuQinLvChart() {
        mAgentWeb.getJsAccessEntrace().quickCallJs("clearChart");
        mAgentWeb.getJsAccessEntrace().quickCallJs("showDiv");
        getChuQinLvData();

    }

    private void getChuQinLvData() {

        DialogLoaderManager.showLoading(_mActivity);
        String deptCodes = SPUtils.getInstance(Consts.SP_INSTANT_NAME).getString(Consts.SP_ITEM_DEPTCODE_NAME);
        ViseHttp.RETROFIT()
                .create(WebApiServices.class)
                .GetEmpRate(deptCode, deptCodes)
                .compose(ApiTransformer.<GetEmpRateResponse>norTransformer())
                .subscribe(new ApiCallbackSubscriber<>(new ACallback<GetEmpRateResponse>() {
                    @Override
                    public void onSuccess(GetEmpRateResponse data) {

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
                        DialogLoaderManager.stopLoading();
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {
                        DialogLoaderManager.stopLoading();
                    }
                }));

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
