package com.jake.huntkey.core.delegates.EChartsDelegate;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.bin.david.form.core.SmartTable;
import com.bin.david.form.core.TableConfig;
import com.bin.david.form.data.CellInfo;
import com.bin.david.form.data.format.bg.BaseBackgroundFormat;
import com.bin.david.form.data.format.draw.TextDrawFormat;
import com.bin.david.form.data.style.FontStyle;
import com.bin.david.form.data.table.ArrayTableData;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.jake.huntkey.core.R;
import com.jake.huntkey.core.R2;
import com.jake.huntkey.core.delegates.basedelegate.BaseBackDelegate;
import com.jake.huntkey.core.entity.HomePageItemEntity;
import com.jake.huntkey.core.net.WebApiServices;
import com.jake.huntkey.core.net.callback.dealTokenExpire;
import com.jake.huntkey.core.netbean.GetJiePaiResponse;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.core.ApiTransformer;
import com.vise.xsnow.http.subscriber.ApiCallbackSubscriber;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import me.yokeyword.eventbusactivityscope.EventBusActivityScope;


public class JiePaiDelegate extends BaseBackDelegate {
    private static final String ARG_lineID = "lineId";
    @BindView(R2.id.id_smart_table)
    SmartTable idSmartTable;
    @BindView(R2.id.id_smart_refresh_layout)
    SmartRefreshLayout idSmartRefreshLayout;

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
        idSmartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                loadJiePaiData();
            }
        });
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
                idSmartRefreshLayout.finishRefresh();
            }

            @Override
            public void onFail(int errCode, String errMsg) {
                idSmartRefreshLayout.finishRefresh();
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
                } else if (tabledatas[i][j].equals("-1")) {
                    tabledatas[i][j] = "1";
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
                    config.getContentStyle().setAlign(Paint.Align.CENTER);
                    if (cellInfo.value.equals("1")) {
                        paint.setColor(ContextCompat.getColor(_mActivity, R.color.table_cell_green));
                    } else if (cellInfo.value.equals("-")) {
                        paint.setColor(ContextCompat.getColor(_mActivity, R.color.white));
                    } else {
                        paint.setColor(ContextCompat.getColor(_mActivity, R.color.table_cell_red));
                    }
                } else {
                    config.getContentStyle().setAlign(Paint.Align.LEFT);
                    paint.setColor(ContextCompat.getColor(_mActivity, R.color.white));
                }
                c.drawRect(rect.left, rect.top, rect.right, rect.bottom, paint);
                rect.left += config.getHorizontalPadding();
                rect.right -= config.getHorizontalPadding();
                super.draw(c, rect, cellInfo, config);
            }
        });
        idSmartTable.setTableData(arrayTableData);
        idSmartTable.getConfig().setColumnTitleHorizontalPadding(ConvertUtils.dp2px(4));
        idSmartTable.getConfig().setHorizontalPadding(ConvertUtils.dp2px(4));
        idSmartTable.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ScreenUtils.getScreenHeight()));

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
