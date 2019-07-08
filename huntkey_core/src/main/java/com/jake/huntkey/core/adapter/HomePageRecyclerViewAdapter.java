package com.jake.huntkey.core.adapter;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jake.huntkey.core.R;
import com.jake.huntkey.core.entity.HomePageItemEntity;

import java.util.List;

public class HomePageRecyclerViewAdapter extends BaseQuickAdapter<HomePageItemEntity, BaseViewHolder> {


    public HomePageRecyclerViewAdapter(int layoutResId, @Nullable List<HomePageItemEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HomePageItemEntity homePageItemEntity) {
        helper.setText(R.id.item_icon, homePageItemEntity.icon);
        helper.setText(R.id.item_name, homePageItemEntity.name);


    }
}
