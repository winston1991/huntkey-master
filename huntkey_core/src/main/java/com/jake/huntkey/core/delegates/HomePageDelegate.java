package com.jake.huntkey.core.delegates;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jake.huntkey.core.R;
import com.jake.huntkey.core.R2;
import com.jake.huntkey.core.adapter.HomePageRecyclerViewAdapter;
import com.jake.huntkey.core.app.Consts;
import com.jake.huntkey.core.delegates.EChartsDelegate.EChartsBoardDelegate;
import com.jake.huntkey.core.delegates.basedelegate.CheckPermissionDelegate;
import com.jake.huntkey.core.entity.HomePageItemEntity;
import com.joanzapata.iconify.widget.IconTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import me.yokeyword.fragmentation.SupportFragment;

public class HomePageDelegate extends CheckPermissionDelegate implements BaseQuickAdapter.OnItemClickListener {


    @BindView(R2.id.id_recyclerView)
    RecyclerView mRecyclerView;
    HomePageRecyclerViewAdapter mHomePageRecyclerViewAdapter;

    public static HomePageDelegate newInstance() {
        Bundle args = new Bundle();
        HomePageDelegate fragment = new HomePageDelegate();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Object setLayout() {
        return R.layout.homepage_delegate_layout;
    }

    @Override
    protected void onBindView(Bundle savedInstanceState, View rootView) {
        initToobar(rootView);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mHomePageRecyclerViewAdapter = new HomePageRecyclerViewAdapter(R.layout.homepage_delegate_recycler_item_layout, initEntityList());
        mHomePageRecyclerViewAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(mHomePageRecyclerViewAdapter);
    }

    private void initToobar(View rootView) {
        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        toolbar.setTitle("选择工厂/车间");

    }

    private List<HomePageItemEntity> initEntityList() {
        ArrayList<HomePageItemEntity> entityList = new ArrayList();
        HomePageItemEntity entity = null;
        for (int i = 0; i < Consts.items.length; i++) {
            entity = new HomePageItemEntity();
            entity.text = Consts.homeItems.get(i);
            entity.name = Consts.items[i];
            entityList.add(entity);
        }
        return entityList;
    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        IconTextView iconTextView = view.findViewById(R.id.item_icon);
        TextView textView = view.findViewById(R.id.item_name);
        if (position == 0) {
            ((SupportFragment) getParentFragment()).start(FactoryWorkShopContainerDelegate.newInstance());
        } else if (position == 1) {
            ((SupportFragment) getParentFragment()).start(DebugPagerFragment.newInstance(position + ""));
        } else {
            ((SupportFragment) getParentFragment()).start(DebugPagerFragment.newInstance(position + ""));
        }


    }
}
