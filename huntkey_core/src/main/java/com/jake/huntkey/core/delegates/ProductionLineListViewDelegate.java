package com.jake.huntkey.core.delegates;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bin.david.form.data.column.Column;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.jake.huntkey.core.R;
import com.jake.huntkey.core.R2;
import com.jake.huntkey.core.adapter.ProductionLineListViewAdapter;
import com.jake.huntkey.core.app.Consts;
import com.jake.huntkey.core.delegates.basedelegate.BaseBackDelegate;
import com.jake.huntkey.core.entity.HomePageItemEntity;
import com.jake.huntkey.core.entity.ProductionLineEntity;
import com.jake.huntkey.core.net.WebApiServices;
import com.jake.huntkey.core.netbean.Get20Be31DataResponse;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.vise.xsnow.http.core.ApiTransformer;
import com.vise.xsnow.http.subscriber.ApiCallbackSubscriber;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import butterknife.BindView;
import me.yokeyword.eventbusactivityscope.EventBusActivityScope;


public class ProductionLineListViewDelegate extends BaseBackDelegate {
    private static final String ARG_TYPE = "arg_type";

    @BindView(R2.id.id_listview)
    protected ListView listView;

    private String sid;//服务器id
    private String accid; //工厂id
    ArrayList<Column> arrayListColum;
    private ArrayList<ProductionLineEntity> productionLineEntityArrayList;
    private ArrayList<ArrayList<ProductionLineEntity>> pEArrayLists = new ArrayList<>();

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
        loadNetData();

    }

    private void loadNetData() {
        String deptCode = SPUtils.getInstance(Consts.SP_INSTANT_NAME).getString(Consts.SP_ITEM_DEPTCODE_NAME);
        ViseHttp.RETROFIT()
                .create(WebApiServices.class)
                .Get20Be31Data(sid, deptCode, accid)
                .compose(ApiTransformer.<Get20Be31DataResponse>norTransformer())
                .subscribe(new ApiCallbackSubscriber<>(new ACallback<Get20Be31DataResponse>() {
                    @Override
                    public void onSuccess(Get20Be31DataResponse data) {

                            bindData(data.getContent());
                            ProductionLineListViewAdapter adapter = new ProductionLineListViewAdapter(ProductionLineListViewDelegate.this, getdatas(), arrayListColum);
                            listView.setAdapter(adapter);

                        ToastUtils.showShort(data.toString());
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {
                        ToastUtils.showShort(errCode);
                    }
                }));
    }

    private void bindData(List<Get20Be31DataResponse.Content> data)  {
        //创建表实体
        arrayListColum = new ArrayList<>();
        Column item1 = new Column("线体", "linebody");
        item1.setAutoMerge(true);
        arrayListColum.add(item1);
        item1 = new Column("指标", "quota");
        arrayListColum.add(item1);
        for(int i = 1; i <= 9; i++)
        {
            String tmp = "item"+i;
            item1 = new Column(data.get(i-1).getTmrm_month(), tmp);
            arrayListColum.add(item1);
        }



//        //遍历所有实体，通过线体名称把实体分类
//        HashSet<String> lines = new HashSet<>();
//        for (Get20Be31DataResponse.Content content : data) {
//            lines.add(content.getLayt_name());
//        }
//        {"ErrorMsg":null,"Status":"OK","Content":[{"Titles":["线体","指标","2019-02","2019-03","2019-04","2019-05","2019-06","2019-07","27周","28周","07月08"],"Data":[["E2线","直通率","97.43","97.68","98.22","96.88","97.06","96.22","96.23","20.83","83.33"],["E2线","损失率","18.15","24.76","17.32","8.66","9.73","8.53","8.35","99.50","95.65"],["E2线","合格率","79.75","73.49","81.21","88.49","87.62","88.02","88.19","0.10","3.62"]]}]}
//        String[] linesName =  lines.toString().split(",");
//
//        List<Get20Be31DataResponse.Content> line;
//
//        HashMap<String, List<Get20Be31DataResponse.Content>> hashMap = new HashMap<>();
//        for (int i = 0; i < linesName.length; i++) {
//            line = new ArrayList<>();
//            for (Get20Be31DataResponse.Content content : data) {
//                if (content.getLayt_name().equals(linesName[i])) ;
//                line.add(content);
//            }
//            hashMap.put(linesName[i], line);
//        }
//        getFormatData(hashMap);

    }


    private void getFormatData(HashMap<String, List<Get20Be31DataResponse.Content>> map) throws NoSuchFieldException, IllegalAccessException {
        for (String key : map.keySet()) {
            List<Get20Be31DataResponse.Content> data = map.get(key);
            ProductionLineEntity productionLineEntity = new ProductionLineEntity();
            ProductionLineEntity productionLineEntity2 = new ProductionLineEntity();
            ProductionLineEntity productionLineEntity3 = new ProductionLineEntity();
            for (int i = 1; i <= data.size(); i++) {
                productionLineEntity.quota = "直通率";
                Field field = productionLineEntity.getClass().getDeclaredField("item" + i);
                field.setAccessible(true);
                field.set(productionLineEntity, data.get(i).getTmrm_rate());
                productionLineEntity.linebody = key;
                productionLineEntity2.quota = "损失率";
                field = productionLineEntity2.getClass().getDeclaredField("item" + i);
                field.setAccessible(true);
                field.set(productionLineEntity2, data.get(i).getTmrm_loss_rate());
                productionLineEntity2.linebody = key;
                productionLineEntity3.quota = "合格率";
                field = productionLineEntity3.getClass().getDeclaredField("item" + i);
                field.setAccessible(true);
                field.set(productionLineEntity3, data.get(i).getTmrm_synthesize_rate());
                productionLineEntity3.linebody = key;
            }
            productionLineEntityArrayList = new ArrayList<>();
            productionLineEntityArrayList.add(productionLineEntity);
            productionLineEntityArrayList.add(productionLineEntity2);
            productionLineEntityArrayList.add(productionLineEntity3);
            pEArrayLists.add(productionLineEntityArrayList);
        }
    }


    private ArrayList<ArrayList<ProductionLineEntity>> getdatas() {

        ArrayList<ArrayList<ProductionLineEntity>> datas = new ArrayList<>();
        ArrayList<ProductionLineEntity> data;
        ProductionLineEntity productionLineEntity;
        data = new ArrayList<>();
        datas.add(getLinedata(data, "E1线"));
        data = new ArrayList<>();
        datas.add(getLinedata(data, "E2线"));
        data = new ArrayList<>();
        datas.add(getLinedata(data, "E3线"));
        data = new ArrayList<>();
        datas.add(getLinedata(data, "E4线"));
        return datas;
    }

    private ArrayList<ProductionLineEntity> getLinedata(ArrayList<ProductionLineEntity> data, String name) {
        ProductionLineEntity productionLineEntity;
        for (int i = 0; i < 3; i++) {
            productionLineEntity = new ProductionLineEntity();
            productionLineEntity.linebody = name;
            if (i == 0) {
                productionLineEntity.quota = "直通率";
            } else if (i == 1) {
                productionLineEntity.quota= "损失率";
            } else if (i == 2) {
                productionLineEntity.quota= "合格率";
            }
            productionLineEntity.item1= "9"+i;
            productionLineEntity.item2= "9"+i;
            productionLineEntity.item3= "9"+i;
            productionLineEntity.item4= "8"+i;
            productionLineEntity.item5= "9"+i;
            productionLineEntity.item6= "8"+i;
            productionLineEntity.item7= "9"+i;
            productionLineEntity.item8= "8"+i;
            productionLineEntity.item9= "9"+i;
            data.add(productionLineEntity);
        }
        return data;
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
