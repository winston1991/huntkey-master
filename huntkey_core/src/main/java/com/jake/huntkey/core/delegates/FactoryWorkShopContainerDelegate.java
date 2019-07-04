package com.jake.huntkey.core.delegates;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.jake.huntkey.core.R;
import com.jake.huntkey.core.delegates.basedelegate.BaseBackDelegate;
import com.jake.huntkey.core.delegates.basedelegate.MainBackPressDelegate;

public class FactoryWorkShopContainerDelegate extends BaseBackDelegate {

    public  FactoryWorkshopDelegate factoryWorkshopDelegate;

    public static FactoryWorkShopContainerDelegate newInstance() {
        FactoryWorkShopContainerDelegate fragment = new FactoryWorkShopContainerDelegate();
        return fragment;
    }

    @Override
    public Object setLayout() {
        return R.layout.factory_workshop_container_delegate_layout;
    }


    @Override
    protected void onBindView(Bundle savedInstanceState, View rootView) {

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (findChildFragment(FactoryWorkshopDelegate.class) == null) {
            factoryWorkshopDelegate = FactoryWorkshopDelegate.newInstance("河源消费");
            loadRootFragment(R.id.fl_container, factoryWorkshopDelegate);
        }
    }


}
