package com.jake.huntkey.core.delegates.EChartsDelegate;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.bin.david.form.core.SmartTable;
import com.bin.david.form.core.TableConfig;
import com.bin.david.form.data.CellInfo;
import com.bin.david.form.data.column.Column;
import com.bin.david.form.data.format.bg.BaseBackgroundFormat;
import com.bin.david.form.data.format.draw.IDrawFormat;
import com.bin.david.form.data.style.FontStyle;
import com.bin.david.form.data.table.ArrayTableData;
import com.bin.david.form.utils.DensityUtils;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.ToastUtils;

import com.jake.huntkey.core.R;
import com.jake.huntkey.core.R2;
import com.jake.huntkey.core.delegates.basedelegate.CheckPermissionDelegate;
import com.jake.huntkey.core.entity.HomePageItemEntity;
import com.jake.huntkey.core.entity.ProductionLineEntity;
import com.jake.huntkey.core.entity.WIPEntity;
import com.jake.huntkey.core.net.WebApiServices;
import com.jake.huntkey.core.netbean.GetSampleResponse;
import com.jake.huntkey.core.netbean.GetWipDataResponse;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.vise.xsnow.http.core.ApiTransformer;
import com.vise.xsnow.http.subscriber.ApiCallbackSubscriber;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import io.reactivex.disposables.Disposable;
import me.yokeyword.eventbusactivityscope.EventBusActivityScope;

public class EChart_WIP_Tj_Delegate extends CheckPermissionDelegate {

    private static final String ARG_lineID = "lineId";
    @BindView(R2.id.id_smart_table1)
    SmartTable<WIPEntity> smartTable1;

    @BindView(R2.id.id_smart_table2)
    SmartTable<String> smartTable2;
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
        FontStyle fontStyle = new FontStyle();
        fontStyle.setTextColor(Color.WHITE);
        smartTable1.getConfig().setColumnTitleBackground(new BaseBackgroundFormat(Color.rgb(0, 152, 217)));
        smartTable1.getConfig().setColumnTitleStyle(fontStyle);

        smartTable2.getConfig().setFixedTitle(true);
        smartTable2.getConfig().setShowXSequence(false);
        smartTable2.getConfig().setShowYSequence(false);
        smartTable2.getConfig().setShowTableTitle(false);
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

        ApiCallbackSubscriber disposable = new ApiCallbackSubscriber<>(new ACallback<GetWipDataResponse>() {
            @Override
            public void onSuccess(GetWipDataResponse data) {
                if (data != null && data.getContent() != null && data.getStatus().equals("OK") && data.getContent().size() > 0) {
                    String[] titles = data.getContent().get(0).getTitles();
                    String[][] datas = data.getContent().get(0).getData();
                    ArrayTableData<String> arrayTableData = ArrayTableData.create("", titles, transformMatrix(datas), new IDrawFormat<String>() {
                        @Override
                        public int measureWidth(Column<String> column, int position, TableConfig config) {
                            return DensityUtils.dp2px(_mActivity, 60);
                        }

                        @Override
                        public int measureHeight(Column<String> column, int position, TableConfig config) {
                            return DensityUtils.dp2px(_mActivity, 20);
                        }

                        @Override
                        public void draw(Canvas c, Rect rect, CellInfo<String> cellInfo, TableConfig config) {
                            Paint paint = config.getPaint();
                            paint.setStyle(Paint.Style.FILL);
                            paint.setColor(ContextCompat.getColor(_mActivity, R.color.transparent));
                            c.drawRect(rect.left + 3, rect.top + 3, rect.right - 3, rect.bottom - 3, paint);
                            paint.setColor(ContextCompat.getColor(_mActivity, R.color.table_content_font_color));
                            paint.setTextSize(ConvertUtils.dp2px(14));
                            Paint.FontMetricsInt fontMetrics = paint.getFontMetricsInt();
                            int baseline = (rect.bottom + rect.top - fontMetrics.bottom - fontMetrics.top) / 2;
                            c.drawText(cellInfo.data, rect.centerX(), baseline, paint);
                        }
                    });
                    smartTable2.setTableData(arrayTableData);
                    smartTable2.getTableData().getColumns().get(0).setFixed(true);
                }
            }

            @Override
            public void onFail(int errCode, String errMsg) {

            }


        });
        ViseHttp.RETROFIT()
                .create(WebApiServices.class)
                .GetWipData(sid, lineId, accid)
                .compose(ApiTransformer.<GetWipDataResponse>norTransformer())
                .subscribe(disposable);
        ViseHttp.addDisposable("GetWipData", disposable);
    }


    // 将二维数组矩阵转置
    public static String[][] transformMatrix(String matrix[][]) {
        String a[][] = new String[matrix[0].length][matrix.length];
        for (int i = 0; i < matrix[0].length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                a[i][j] = matrix[j][i];
            }
        }
        return a;
    }


    //获取抽样表数据
    private void getSampleTableData() {
        ApiCallbackSubscriber disposable;
        disposable = new ApiCallbackSubscriber<>(new ACallback<GetSampleResponse>() {
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
                        wipEntity.setnGInfo(sapm_remark);
                        samples.add(wipEntity);
                    }
                    smartTable1.setData(samples);
                }
            }

            @Override
            public void onFail(int errCode, String errMsg) {
                ToastUtils.showShort(errMsg);
            }
        });
        ViseHttp.RETROFIT()
                .create(WebApiServices.class)
                .GetSample(sid, lineId, accid)
                .compose(ApiTransformer.<GetSampleResponse>norTransformer())
                .subscribe(disposable);
        ViseHttp.addDisposable("GetSample", disposable);
    }

    @Override
    public Object setLayout() {
        return R.layout.echarts_wip_tj_delegate_layout;
    }

    @Override
    protected void onBindView(Bundle savedInstanceState, View rootView) {
        initViews(rootView);
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
        ViseHttp.cancelTag("GetSample");
        ViseHttp.cancelTag("GetWipData");
    }
}
