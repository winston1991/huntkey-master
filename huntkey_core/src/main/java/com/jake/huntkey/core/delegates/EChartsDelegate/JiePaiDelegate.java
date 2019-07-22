package com.jake.huntkey.core.delegates.EChartsDelegate;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.bin.david.form.core.SmartTable;
import com.bin.david.form.core.TableConfig;
import com.bin.david.form.data.CellInfo;
import com.bin.david.form.data.CellRange;
import com.bin.david.form.data.column.Column;
import com.bin.david.form.data.format.bg.BaseBackgroundFormat;
import com.bin.david.form.data.format.draw.IDrawFormat;
import com.bin.david.form.data.format.draw.TextDrawFormat;
import com.bin.david.form.data.style.FontStyle;
import com.bin.david.form.data.table.ArrayTableData;
import com.bin.david.form.utils.DensityUtils;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.jake.huntkey.core.R;
import com.jake.huntkey.core.R2;
import com.jake.huntkey.core.delegates.basedelegate.BaseBackDelegate;
import com.jake.huntkey.core.entity.HomePageItemEntity;
import com.jake.huntkey.core.net.WebApiServices;
import com.jake.huntkey.core.net.callback.dealTokenExpire;
import com.jake.huntkey.core.netbean.Get20BdJianKongInfoResponse;
import com.jake.huntkey.core.netbean.GetJiePaiResponse;
import com.jake.huntkey.core.netbean.GetWipDataResponse;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.vise.xsnow.http.core.ApiTransformer;
import com.vise.xsnow.http.subscriber.ApiCallbackSubscriber;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;
import me.yokeyword.eventbusactivityscope.EventBusActivityScope;


public class JiePaiDelegate extends BaseBackDelegate {
    private static final String ARG_lineID = "lineId";
    @BindView(R2.id.id_smart_table1)
    SmartTable idSmartTable1;
    @BindView(R2.id.id_smart_table2)
    SmartTable idSmartTable2;
    private String lineId;
    private String sid;
    private String accid;


    public static JiePaiDelegate newInstance(String lineId) {
        Bundle args = new Bundle();
        args.putString(ARG_lineID, lineId);
        JiePaiDelegate fragment = new JiePaiDelegate();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Object setLayout() {
        return R.layout.jiepai_delegate_layout;
    }

    @Override
    protected void onBindView(Bundle savedInstanceState, View rootView) {
        initView(rootView);
    }

    protected void initView(View view) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            lineId = bundle.getString(ARG_lineID);
        }
        initTableFormat();
        loadJiePaiData();
        load20BdJianKongInfoData();
    }

    private void load20BdJianKongInfoData() {
        ApiCallbackSubscriber disposable = new ApiCallbackSubscriber<>(new dealTokenExpire<Get20BdJianKongInfoResponse>(_mActivity) {
            @Override
            public void onSuccess(Get20BdJianKongInfoResponse data) {
                super.onSuccess(data);
                if (data != null && data.getContent() != null && data.getContent().size() > 0) {
                    String[] titles = data.getContent().get(0).getData().getTitles();
                    formatDate(titles);
                    List<List<String>> tableDatas = new ArrayList();
                    List<String> list;
                    List<CellRange> cellRanges = new ArrayList<>();
                    CellRange cellRange;
                    for (int i = 0; i < data.getContent().size(); i++) {
                        list = new ArrayList<>();
                        list.add(data.getContent().get(i).getLaytName());
                        for (int j = 0; j < titles.length - 1; j++) {
                            list.add("");
                        }
                        tableDatas.add(list);
                        tableDatas.addAll(data.getContent().get(i).getData().getData());
                        cellRange = new CellRange(i*9, i*9,0, titles.length);
                        cellRanges.add(cellRange);
                    }
                    String[][] td = new String[tableDatas.size()][tableDatas.get(0).size()];
                    for (int i = 0; i < tableDatas.size(); i++) {
                        for (int j = 0; j < tableDatas.get(i).size(); j++) {
                            td[i][j] = tableDatas.get(i).get(j);
                        }
                    }
                    ArrayTableData<String> arrayTableData = ArrayTableData.create("", titles, ArrayTableData.transformColumnArray(td), new TextDrawFormat<String>() {
                    });
                    idSmartTable2.setTableData(arrayTableData);
                    idSmartTable2.getTableData().setUserCellRange(cellRanges);
                }
            }

            @Override
            public void onFail(int errCode, String errMsg) {

            }
        });
        ViseHttp.RETROFIT()
                .create(WebApiServices.class)
                .Get20BdJianKongInfo(sid, lineId, accid)
                .compose(ApiTransformer.<Get20BdJianKongInfoResponse>norTransformer())
                .subscribe(disposable);
        ViseHttp.addDisposable("Get20BdJianKongInfo", disposable);
    }


    private void loadJiePaiData() {
        ApiCallbackSubscriber disposable = new ApiCallbackSubscriber<>(new dealTokenExpire<GetJiePaiResponse>(_mActivity) {
            @Override
            public void onSuccess(GetJiePaiResponse data) {
                super.onSuccess(data);
                if (data != null && data.getContent() != null && data.getContent().size() > 0) {
                    String[] titles = data.getContent().get(0).getTitles();
                    String[][] tabledatas = data.getContent().get(0).getData();
                    showJiePaiTable(titles, tabledatas);
                }
            }

            @Override
            public void onFail(int errCode, String errMsg) {
                ToastUtils.showShort(errMsg);
            }
        });
        ViseHttp.RETROFIT()
                .create(WebApiServices.class)
                .GetJiePai(sid, lineId, accid)
                .compose(ApiTransformer.<GetJiePaiResponse>norTransformer())
                .subscribe(disposable);
        ViseHttp.addDisposable("GetWipData", disposable);
    }

    private void showJiePaiTable(String[] titles, String[][] tabledatas) {
        formatDate(titles);
        for (int i = 0; i < tabledatas.length; i++) {
            for (int j = 0; j < tabledatas[0].length; j++) {
                if (tabledatas[i][j].isEmpty()) {
                    tabledatas[i][j] = "-";
                }
            }
        }
        ArrayTableData<String> arrayTableData = ArrayTableData.create("", titles, ArrayTableData.transformColumnArray(tabledatas), new TextDrawFormat<String>() {
            @Override
            protected void drawText(Canvas c, String value, Rect rect, Paint paint) {
                paint.setColor(ContextCompat.getColor(_mActivity, R.color.black));
                super.drawText(c, value, rect, paint);
            }
            @Override
            public void draw(Canvas c, Rect rect, CellInfo<String> cellInfo, TableConfig config) {
                Paint paint = config.getPaint();
                paint.setStyle(Paint.Style.FILL);
                if (cellInfo.col != 0) {
                    if (cellInfo.value.equals("-1")) {
                        paint.setColor(ContextCompat.getColor(_mActivity, R.color.table_cell_green));
                    } else if (cellInfo.value.equals("-")) {
                        paint.setColor(ContextCompat.getColor(_mActivity, R.color.white));
                    } else {
                        paint.setColor(ContextCompat.getColor(_mActivity, R.color.qmui_config_color_red));
                    }
                } else {
                    paint.setColor(ContextCompat.getColor(_mActivity, R.color.transparent));
                }
                c.drawRect(rect.left, rect.top, rect.right, rect.bottom, paint);

                super.draw(c, rect, cellInfo, config);
            }
        });
        idSmartTable1.setTableData(arrayTableData);
    }

    private void formatDate(String[] titles) {
        for (int i = 0; i < titles.length; i++) {
            if (i < 2) {
                continue;
            }
            titles[i] = titles[i].substring(11, 16) + "~" + titles[i].substring(31, 36);
        }
    }

    private void initTableFormat() {
        idSmartTable1.getConfig().setFixedTitle(true);
        idSmartTable1.getConfig().setShowXSequence(false);
        idSmartTable1.getConfig().setShowYSequence(false);
        idSmartTable1.getConfig().setShowTableTitle(false);
        idSmartTable2.getConfig().setFixedTitle(true);
        idSmartTable2.getConfig().setShowXSequence(false);
        idSmartTable2.getConfig().setShowYSequence(false);
        idSmartTable2.getConfig().setShowTableTitle(false);
        FontStyle fontStyle = new FontStyle();
        fontStyle.setTextColor(Color.WHITE);
        idSmartTable1.getConfig().setColumnTitleBackground(new BaseBackgroundFormat(Color.rgb(0, 152, 217)));
        idSmartTable2.getConfig().setColumnTitleBackground(new BaseBackgroundFormat(Color.rgb(0, 152, 217)));
        idSmartTable1.getConfig().setColumnTitleStyle(fontStyle);
        idSmartTable2.getConfig().setColumnTitleStyle(fontStyle);
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
        super.onDestroyView();
        EventBusActivityScope.getDefault(_mActivity).unregister(this);
        ViseHttp.cancelTag("GetWipData");
        ViseHttp.cancelTag("Get20BdJianKongInfo");

    }
}
