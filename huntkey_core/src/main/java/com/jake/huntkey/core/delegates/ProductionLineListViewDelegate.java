package com.jake.huntkey.core.delegates;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ToastUtils;
import com.jake.huntkey.core.R;
import com.jake.huntkey.core.R2;
import com.jake.huntkey.core.adapter.ProductionLineListViewAdapter;
import com.jake.huntkey.core.delegates.basedelegate.BaseBackDelegate;
import com.jake.huntkey.core.entity.ProductionLineEntity;

import java.util.ArrayList;

import butterknife.BindView;


public class ProductionLineListViewDelegate extends BaseBackDelegate {
    private static final String ARG_TYPE = "arg_type";

    @BindView(R2.id.id_listview)
    protected ListView listView;

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

        ProductionLineListViewAdapter adapter = new ProductionLineListViewAdapter(getContext(), getdatas());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ToastUtils.showShort("" + position);
            }
        });
    }

    private ArrayList<ArrayList<ProductionLineEntity>> getdatas() {

        ArrayList<ArrayList<ProductionLineEntity>> datas = new ArrayList<>();
        ArrayList<ProductionLineEntity> data;
        ProductionLineEntity productionLineEntity;
        data = new ArrayList<>();
        datas.add(getLinedata(data, "E1线"));
        data = new ArrayList<>();
        datas.add(getLinedata(data, "E2线"));
        return datas;
    }

    private ArrayList<ProductionLineEntity> getLinedata(ArrayList<ProductionLineEntity> data, String name) {
        ProductionLineEntity productionLineEntity;
        for (int i = 0; i < 3; i++) {
            productionLineEntity = new ProductionLineEntity();
            productionLineEntity.setLinebody(name);
            if (i == 0) {
                productionLineEntity.setQuota("直通率");
            } else if (i == 1) {
                productionLineEntity.setQuota("损失率");
            } else if (i == 2) {
                productionLineEntity.setQuota("合格率");
            }
            productionLineEntity.setItem1("9" + i);
            productionLineEntity.setItem2("9" + i);
            productionLineEntity.setItem3("9" + i);
            productionLineEntity.setItem4("8" + i);
            productionLineEntity.setItem5("9" + i);
            productionLineEntity.setItem6("8" + i);
            productionLineEntity.setItem7("9" + i);
            data.add(productionLineEntity);
        }
        return data;
    }
}
