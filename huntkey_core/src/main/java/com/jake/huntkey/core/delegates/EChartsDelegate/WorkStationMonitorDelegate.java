package com.jake.huntkey.core.delegates.EChartsDelegate;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;

import com.bin.david.form.core.SmartTable;
import com.bin.david.form.core.TableConfig;
import com.bin.david.form.data.CellInfo;
import com.bin.david.form.data.CellRange;
import com.bin.david.form.data.column.Column;
import com.bin.david.form.data.format.bg.BaseBackgroundFormat;
import com.bin.david.form.data.format.draw.TextDrawFormat;
import com.bin.david.form.data.style.FontStyle;
import com.bin.david.form.data.table.ArrayTableData;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.jake.huntkey.core.R;
import com.jake.huntkey.core.R2;
import com.jake.huntkey.core.app.ConfigKeys;
import com.jake.huntkey.core.app.HkEngine;
import com.jake.huntkey.core.delegates.basedelegate.BaseBackDelegate;
import com.jake.huntkey.core.entity.HomePageItemEntity;
import com.jake.huntkey.core.net.WebApiServices;
import com.jake.huntkey.core.net.callback.dealTokenExpire;
import com.jake.huntkey.core.netbean.Get20BdJianKongInfoResponse;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.core.ApiTransformer;
import com.vise.xsnow.http.subscriber.ApiCallbackSubscriber;
import com.xuexiang.xui.widget.statelayout.StatefulLayout;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import me.yokeyword.eventbusactivityscope.EventBusActivityScope;


public class WorkStationMonitorDelegate extends BaseBackDelegate {
    private static final String ARG_lineID = "lineId";
    @BindView(R2.id.id_smart_table)
    SmartTable idSmartTable;
    @BindView(R2.id.id_state_layout)
    StatefulLayout idStateLayout;
    @BindView(R2.id.id_smart_refresh_layout)
    SmartRefreshLayout idSmartRefreshLayout;
    @BindView(R2.id.id_scrollview)
    NestedScrollView idScrollview;
    private String lineId;
    private String sid;
    private String accid;
    private String colortcrred;
    private String colorfpyred;
    private String colorfpyyellow;

    public static WorkStationMonitorDelegate newInstance(String lineId) {
        Bundle args = new Bundle();
        args.putString(ARG_lineID, lineId);
        WorkStationMonitorDelegate fragment = new WorkStationMonitorDelegate();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Object setLayout() {
        return R.layout.workstations_monitor_delegate_layout;
    }

    @Override
    protected void onBindView(Bundle savedInstanceState, View rootView) {
        initView(rootView);
    }

    protected void initView(View view) {
        HashMap<String, Float> colorRange = (HashMap<String, Float>) HkEngine.getConfiguration(ConfigKeys.GAUGE_COLOR_RANGE);
        if (colorRange != null) {
            colortcrred = Float.toString(colorRange.get("tcr_yellow_end") * 100);
            colorfpyred = Float.toString(colorRange.get("fpy_red") * 100);
            colorfpyyellow = Float.toString(colorRange.get("fpy_yellow_end") * 100);
        }
        Bundle bundle = getArguments();
        if (bundle != null) {
            lineId = bundle.getString(ARG_lineID);
        }
        initTableFormat();
        load20BdJianKongInfoData();




    }

    private void load20BdJianKongInfoData() {
        ApiCallbackSubscriber disposable = new ApiCallbackSubscriber<>(new dealTokenExpire<Get20BdJianKongInfoResponse>(_mActivity) {
            @Override
            public void onSuccess(Get20BdJianKongInfoResponse data) {
                super.onSuccess(data);
                if (data != null && data.getContent() != null && data.getContent().size() > 0) {
                    showJianKongData(data);
                }
            }

            private void showJianKongData(Get20BdJianKongInfoResponse data) {
                String[] titles = data.getContent().get(0).getData().getTitles();//表头
                final int itemSize = data.getContent().get(0).getData().getData().size() + 1;//每个工单制令数
                formatDate(titles);
                List<List<String>> tableDatas = new ArrayList();
                List<String> list;
                List<CellRange> cellRanges = new ArrayList<>();  //工单行的单元格合并区间集合
                CellRange cellRange;
                for (int i = 0; i < data.getContent().size(); i++) {
                    list = new ArrayList<>();
                    list.add(data.getContent().get(i).getLaytName());
                    for (int j = 0; j < titles.length - 1; j++) {
                        list.add("");
                    }
                    tableDatas.add(list);
                    tableDatas.addAll(data.getContent().get(i).getData().getData());
                    cellRange = new CellRange(i * itemSize, i * itemSize, 0, titles.length); //每个合并区间
                    cellRanges.add(cellRange);
                }
                String[][] td = new String[tableDatas.size()][tableDatas.get(0).size()];
                for (int i = 0; i < tableDatas.size(); i++) {
                    for (int j = 0; j < tableDatas.get(i).size(); j++) {
                        if (tableDatas.get(i).get(j).equals("-1")) {
                            td[i][j] = "1";
                        } else {
                            td[i][j] = tableDatas.get(i).get(j);
                        }
                    }
                }

                TextDrawFormat<String> textDrawFormat = new TextDrawFormat<String>() {
                    @Override
                    protected void drawText(Canvas c, String value, Rect rect, Paint paint) {
                        paint.setColor(ContextCompat.getColor(_mActivity, R.color.black));
                        super.drawText(c, value, rect, paint);
                    }

                    @Override
                    public void draw(Canvas c, Rect rect, CellInfo<String> cellInfo, TableConfig config) {
                        Paint paint = config.getPaint();
                        paint.setStyle(Paint.Style.FILL);
                        if ((cellInfo.row % itemSize) == 0) {
                            config.getContentStyle().setAlign(Paint.Align.LEFT);  //工单行居左
                            paint.setColor(ContextCompat.getColor(_mActivity, R.color.table_divide));
                            c.drawRect(rect.left, rect.top, rect.right, rect.bottom, paint);
                            rect.left += config.getHorizontalPadding();
                            rect.right -= config.getHorizontalPadding(); //设置文字左边的padding 因为文字是根据rect来定位的
                        } else {
                            config.getContentStyle().setAlign(Paint.Align.CENTER);//其他行居右
                            if ((cellInfo.row % itemSize) == 8 && cellInfo.col != 0) //节拍达成率
                            {
                                if (cellInfo.value.equals("1")) {
                                    paint.setColor(ContextCompat.getColor(_mActivity, R.color.table_cell_green));
                                } else {
                                    paint.setColor(ContextCompat.getColor(_mActivity, R.color.table_cell_red));
                                }
                                c.drawRect(rect.left, rect.top, rect.right, rect.bottom, paint);
                            }
//                            else if ((cellInfo.row % itemSize) == 7 && cellInfo.col != 0) //目标达成率
//                            {
//                                if (cellInfo.value.compareTo(colortcrred) < 0) {
//                                    paint.setColor(ContextCompat.getColor(_mActivity, R.color.table_cell_red));
//                                    c.drawRect(rect.left, rect.top, rect.right, rect.bottom, paint);
//                                }
//                            } else if ((cellInfo.row % itemSize) == 6 && cellInfo.col != 0) //一次良率
//                            {
//                                if (cellInfo.value.compareTo(colorfpyred) < 0) {
//                                    paint.setColor(ContextCompat.getColor(_mActivity, R.color.table_cell_red));
//                                    c.drawRect(rect.left, rect.top, rect.right, rect.bottom, paint);
//                                } else if (cellInfo.value.compareTo(colorfpyyellow) < 0) {
//                                    paint.setColor(ContextCompat.getColor(_mActivity, R.color.yellow));
//                                    c.drawRect(rect.left, rect.top, rect.right, rect.bottom, paint);
//                                }
//                            }
                        }
                        super.draw(c, rect, cellInfo, config);
                    }

                    @Override
                    public int measureHeight(Column<String> column, int position, TableConfig config) {
                        return super.measureHeight(column, position, config);
                    }
                };
                ArrayTableData<String> arrayTableData = ArrayTableData.create("", titles, ArrayTableData.transformColumnArray(td), textDrawFormat);
                idSmartTable.setTableData(arrayTableData);
                ((Column) idSmartTable.getTableData().getColumns().get(0)).setFixed(true);
                idSmartTable.getTableData().setUserCellRange(cellRanges);
                //如果没有设置  idSmartTable.getConfig().setHorizontalPadding()默认是用ColumnTitleHorizontalPadding
                idSmartTable.getConfig().setColumnTitleHorizontalPadding(ConvertUtils.dp2px(8));

            }

            @Override
            public void onFail(int errCode, String errMsg) {
                ToastUtils.showShort(errMsg);
            }
        });
        ViseHttp.RETROFIT()
                .create(WebApiServices.class)
                .Get20BdJianKongInfo(sid, lineId, accid)
                .compose(ApiTransformer.<Get20BdJianKongInfoResponse>norTransformer())
                .subscribe(disposable);
        ViseHttp.addDisposable("Get20BdJianKongInfo", disposable);
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
        idSmartTable.getConfig().setFixedTitle(true);
        idSmartTable.getConfig().setShowXSequence(false);
        idSmartTable.getConfig().setShowYSequence(false);
        idSmartTable.getConfig().setShowTableTitle(false);
        FontStyle fontStyle = new FontStyle();
        fontStyle.setTextColor(Color.WHITE);
        idSmartTable.getConfig().setColumnTitleBackground(new BaseBackgroundFormat(Color.rgb(0, 152, 217)));
        idSmartTable.getConfig().setColumnTitleStyle(fontStyle);
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
