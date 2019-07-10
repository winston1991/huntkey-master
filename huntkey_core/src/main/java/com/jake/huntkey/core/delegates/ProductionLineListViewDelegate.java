package com.jake.huntkey.core.delegates;

import android.graphics.Color;
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
        fontStyle.setTextColor(Color.WHITE);
        idSmartTable.getConfig().setColumnTitleStyle(fontStyle);
        idSmartTable.getConfig().setColumnTitleBackground(new BaseBackgroundFormat(Color.rgb(0, 152, 217)));
        loadNetData();

//        getTableColums(getdatas().getContent().get(0).getTitles());
//        try {
//            getTableData(getdatas().getContent().get(0).getData());
//        } catch (NoSuchFieldException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }
//        TableData<ProductionLineEntity> tableData = new TableData<ProductionLineEntity>("", tableDatas, colums);
//        idSmartTable.setTableData(tableData);

    }

    private void loadNetData() {
        DialogLoaderManager.showLoading(_mActivity);
        String deptCodes = SPUtils.getInstance(Consts.SP_INSTANT_NAME).getString(Consts.SP_ITEM_DEPTCODE_NAME);
        ViseHttp.RETROFIT()
                .create(WebApiServices.class)
                .Get20Be31Data(sid, deptCodes, accid)
                .compose(ApiTransformer.<Get20Be31DataResponse>norTransformer())
                .subscribe(new ApiCallbackSubscriber<>(new ACallback<Get20Be31DataResponse>() {
                    @Override
                    public void onSuccess(Get20Be31DataResponse data) {
                        if (data.getStatus().equals("OK") && data.getContent().size() > 0) {
                            getTableColums(data.getContent().get(0).getTitles());
                            getTableData(data.getContent().get(0).getData());
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
                    String lineId, lineName , deptCode;
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
                    ToastUtils.showShort(lineName);
                    ((SupportFragment) getParentFragment()).start(EChartsBoardDelegate.newInstance(mTitle + lineName + "看板", lineId, deptCode));
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
                }
                colums.add(item);
            }
        }
    }


    private Get20Be31DataResponse getdatas() {
        String jsonS = "{\n" +
                "\t\"ErrorMsg\": null,\n" +
                "\t\"Status\": \"OK\",\n" +
                "\t\"Content\": [{\n" +
                "\t\t\"Titles\": [\"线体\", \"指标\", \"2019-02\", \"2019-03\", \"2019-04\", \"2019-05\", \"2019-06\", \"2019-07\", \"27周\", \"28周\", \"07月08\"],\n" +
                "\t\t\"Data\": [\n" +
                "\t\t\t[\"E2线\", \"直通率\", \"97.43\", \"97.68\", \"98.22\", \"96.88\", \"97.06\", \"96.22\", \"96.23\", \"20.83\", \"83.33\"],\n" +
                "\t\t\t[\"E2线\", \"损失率\", \"18.15\", \"24.76\", \"17.32\", \"8.66\", \"9.73\", \"8.53\", \"8.35\", \"99.50\", \"95.65\"],\n" +
                "\t\t\t[\"E2线\", \"合格率\", \"79.75\", \"73.49\", \"81.21\", \"88.49\", \"87.62\", \"88.02\", \"88.19\", \"0.10\", \"3.62\"]\n" +
                "\t\t]\n" +
                "\t}]\n" +
                "}";
        Gson gson = new Gson();

        Get20Be31DataResponse get20Be31DataResponse = gson.fromJson(jsonS, new TypeToken<Get20Be31DataResponse>() {
        }.getType());

        return get20Be31DataResponse;
    }


    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEvent(HomePageItemEntity homePageItemEntity) {
        ToastUtils.showShort(homePageItemEntity.sid);
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
