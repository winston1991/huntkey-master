package com.jake.huntkey.core.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jake.huntkey.core.R;
import com.jake.huntkey.core.entity.HomePageEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomePageRecyclerViewAdapter extends BaseQuickAdapter<HomePageEntity, BaseViewHolder> {


    public HomePageRecyclerViewAdapter(int layoutResId, @Nullable List<HomePageEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HomePageEntity homePageEntity) {
        helper.setText(R.id.item_icon, homePageEntity.text);
        helper.setText(R.id.item_name, homePageEntity.name);

    }
}
