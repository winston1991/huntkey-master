package com.jake.huntkey.core.delegates;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.jake.huntkey.core.R;
import com.jake.huntkey.core.delegates.basedelegate.BaseBackDelegate;
import com.jake.huntkey.core.delegates.basedelegate.MainBackPressDelegate;

public class FactoryWorkShopContainerDelegate extends BaseBackDelegate {

    public  FactoryWorkshopDelegate factoryWorkshopDelegate;
    private static final String ARG_TYPE = "arg_type";
    private String mTitle;

    public static FactoryWorkShopContainerDelegate newInstance(String title) {
        Bundle args = new Bundle();
        args.putString(ARG_TYPE, title);
        FactoryWorkShopContainerDelegate fragment = new FactoryWorkShopContainerDelegate();
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public Object setLayout() {
        return R.layout.factory_workshop_container_delegate_layout;
    }


    @Override
    protected void onBindView(Bundle savedInstanceState, View rootView) {

        mTitle = getArguments().getString(ARG_TYPE);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (findChildFragment(FactoryWorkshopDelegate.class) == null) {
            factoryWorkshopDelegate = FactoryWorkshopDelegate.newInstance(mTitle);
            loadRootFragment(R.id.fl_container, factoryWorkshopDelegate);
        }
    }


}
