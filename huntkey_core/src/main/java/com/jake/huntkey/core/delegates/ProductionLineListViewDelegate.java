package com.jake.huntkey.core.delegates;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bin.david.form.core.SmartTable;
import com.bin.david.form.data.column.Column;
import com.bin.david.form.data.format.bg.BaseBackgroundFormat;
import com.bin.david.form.data.style.FontStyle;
import com.bin.david.form.data.table.TableData;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.JsonUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.jake.huntkey.core.R;
import com.jake.huntkey.core.R2;
import com.jake.huntkey.core.adapter.HomePageRecyclerViewAdapter;
import com.jake.huntkey.core.app.Consts;
import com.jake.huntkey.core.delegates.EChartsDelegate.EChartsBoardDelegate;
import com.jake.huntkey.core.delegates.basedelegate.BaseBackDelegate;
import com.jake.huntkey.core.entity.HomePageItemEntity;
import com.jake.huntkey.core.entity.ProductionLineEntity;
import com.jake.huntkey.core.net.WebApiServices;
import com.jake.huntkey.core.net.callback.dealTokenExpire;
import com.jake.huntkey.core.netbean.BaseResponse;
import com.jake.huntkey.core.netbean.Get20Be31DataResponse;
import com.jake.huntkey.core.netbean.LoginResponse;
import com.jake.huntkey.core.ui.icon.Loading.DialogLoaderManager;
import com.vise.utils.assist.JSONUtil;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.vise.xsnow.http.core.ApiTransformer;
import com.vise.xsnow.http.subscriber.ApiCallbackSubscriber;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import butterknife.BindView;
import me.yokeyword.eventbusactivityscope.EventBusActivityScope;
import me.yokeyword.fragmentation.SupportFragment;


public class ProductionLineListViewDelegate extends BaseBackDelegate {
    private static final String ARG_TYPE = "arg_type";
    @BindView(R2.id.id_smart_table)
    SmartTable idSmartTable;


    private String sid;//服务器id
    private String accid; //工厂id
    ArrayList<Column> colums;
    ArrayList<ProductionLineEntity> tableDatas;
    private String mTitle;  //工厂名

    public static ProductionLineListViewDelegate newInstance(String title) {
        Bundle args = new Bundle();
        args.putString(ARG_TYPE, title);
        ProductionLineListViewDelegate fragment = new ProductionLineListViewDelegate();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public Object setLayout() {
        return R.layout.production_line_layout;
    }


    @Override
    protected void onBindView(Bundle savedInstanceState, View rootView) {
        initView(rootView);
    }

    protected void initView(View view) {

        mTitle = getArguments().getString(ARG_TYPE);
        idSmartTable.getConfig().setShowYSequence(false);
        idSmartTable.getConfig().setShowTableTitle(false);
        idSmartTable.getConfig().setShowXSequence(false);
        idSmartTable.getConfig().setHorizontalPadding(16);
        idSmartTable.getConfig().setColumnTitleHorizontalPadding(17);
        idSmartTable.getConfig().setMinTableWidth(ScreenUtils.getScreenWidth());
        FontStyle fontStyle = new FontStyle();
        fontStyle.setTextSize(ConvertUtils.sp2px(getResources().getDimension(R.dimen.table_colum)));
        fontStyle.setTextColor(Color.rgb(27, 124, 226));
        //fontStyle.setAlign(Paint.Align.RIGHT);  //设置文字居右
        idSmartTable.getConfig().setColumnTitleStyle(fontStyle);
        idSmartTable.getConfig().setColumnTitleBackground(new BaseBackgroundFormat(Color.rgb(213, 213, 213)));
        fontStyle = new FontStyle();
        fontStyle.setAlign(Paint.Align.RIGHT);
        fontStyle.setTextSize(ConvertUtils.sp2px(getResources().getDimension(R.dimen.table_content)));
        idSmartTable.getConfig().setContentStyle(fontStyle);
        idSmartTable.getConfig().setVerticalPadding(ConvertUtils.dp2px(6));
        loadNetData();

    }

    private void loadNetData() {
        DialogLoaderManager.showLoading(_mActivity);
        String deptCodes = SPUtils.getInstance(Consts.SP_INSTANT_NAME).getString(Consts.SP_ITEM_DEPTCODE_NAME);
        ViseHttp.RETROFIT()
                .create(WebApiServices.class)
                .Get20Be31Data(sid, deptCodes, accid)
                .compose(ApiTransformer.<Get20Be31DataResponse>norTransformer())
                .subscribe(new ApiCallbackSubscriber<>(new dealTokenExpire<Get20Be31DataResponse>(_mActivity) {
                    @Override
                    public void onSuccess(Get20Be31DataResponse data) {
                        super.onSuccess(data);
                        if (data.getStatus().equals("OK") && data.getContent().size() > 0) {
                            getTableColums(data.getContent().get(0).getTitles()); //表头
                            getTableData(data.getContent().get(0).getData());  //表格数据
                        }
                        DialogLoaderManager.stopLoading();
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {
                        ToastUtils.showShort(errCode);
                        DialogLoaderManager.stopLoading();
                    }
                }));
    }


    private void getTableData(final List<List<String>> data) {
        if (data.size() > 0) {
            tableDatas = new ArrayList<>();
            ProductionLineEntity productionLineEntity;
            for (int i = 1; i <= data.size(); i++) {
                productionLineEntity = new ProductionLineEntity();
                for (int j = 3; j <= data.get(i - 1).size(); j++) {
                    try {
                        Field field = productionLineEntity.getClass().getDeclaredField("item" + (j - 2));
                        field.setAccessible(true);
                        field.set(productionLineEntity, data.get(i - 1).get(j - 1));
                    } catch (Exception e) {
                        ToastUtils.showShort(e.toString());
                    }
                }
                tableDatas.add(productionLineEntity);
            }
            TableData<ProductionLineEntity> tableData = new TableData<ProductionLineEntity>("", tableDatas, colums);
            idSmartTable.setTableData(tableData);
            idSmartTable.getTableData().setOnItemClickListener(new TableData.OnItemClickListener() {
                @Override
                public void onClick(Column column, String value, Object o, int col, int row) {
                    int tmp = 1;
                    String lineId, lineName, deptCode;
                    //判断点击的是哪一个产线
                    if (col == 0) {
                        tmp = tmp * row;
                        lineId = data.get(tmp).get(0);
                        deptCode = data.get(tmp).get(1);
                        lineName = data.get(tmp).get(2);

                    } else {
                        tmp = row / 3;
                        lineId = data.get(tmp * 3).get(0);
                        deptCode = data.get(tmp * 3).get(1);
                        lineName = data.get(tmp * 3).get(2);
                    }
                    ((SupportFragment) getParentFragment()).start(EChartsBoardDelegate.newInstance(mTitle + "-" + lineName + "看板", lineId, deptCode));
                }
            });
        }
    }

    private void getTableColums(List<String> Titles) {
        //创建表实体
        if (Titles.size() > 0) {
            colums = new ArrayList<>();
            Column item = null;
            for (int i = 3; i <= Titles.size(); i++) {
                String tmp = "item" + (i - 2);
                item = new Column(Titles.get(i - 1), tmp);
                if (i == 3) {
                    item.setAutoMerge(true);
                    item.setFixed(true);  //固定第一列
                    item.setTextAlign(Paint.Align.CENTER);  //设置第一列文字居中
                } else if (i == 4) {
                    item.setFixed(true); //固定第二列
                    item.setTextAlign(Paint.Align.CENTER); //设置第二列文字居中
                }
                colums.add(item);
            }
        }
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
