package com.jake.huntkey.core.delegates;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jake.huntkey.core.R;
import com.jake.huntkey.core.R2;
import com.jake.huntkey.core.adapter.HomePageRecyclerViewAdapter;
import com.jake.huntkey.core.app.Configurator;
import com.jake.huntkey.core.app.Consts;
import com.jake.huntkey.core.delegates.basedelegate.CheckPermissionDelegate;
import com.jake.huntkey.core.entity.HomePageItemEntity;
import com.jake.huntkey.core.net.WebApiServices;
import com.jake.huntkey.core.net.callback.dealTokenExpire;
import com.jake.huntkey.core.netbean.GetFpyRateResponse;
import com.jake.huntkey.core.netbean.GetQueryWarnResponse;
import com.jake.huntkey.core.netbean.GetSampleResponse;
import com.jake.huntkey.core.netbean.LoginResponse;

import com.vise.log.ViseLog;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.vise.xsnow.http.core.ApiTransformer;
import com.vise.xsnow.http.subscriber.ApiCallbackSubscriber;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import me.yokeyword.eventbusactivityscope.EventBusActivityScope;
import me.yokeyword.fragmentation.SupportFragment;


public class HomePageDelegate extends CheckPermissionDelegate implements BaseQuickAdapter.OnItemClickListener {


    @BindView(R2.id.id_recyclerView)
    RecyclerView mRecyclerView;
    HomePageRecyclerViewAdapter mHomePageRecyclerViewAdapter;
    ArrayList<HomePageItemEntity> entityList;
    HashMap<String, Float> gaugeColorRange;  //仪表盘颜色区间值
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
        mRecyclerView.setAdapter(mHomePageRecyclerViewAdapter);
    }

    private void initToobar(View rootView) {
        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.select_factory_workshop);

    }

    private void initEntityList(List<LoginResponse.Factorys> factorys) {
        entityList = new ArrayList();
        HomePageItemEntity entity = null;
        for (int i = 0; i < factorys.size(); i++) {
            entity = new HomePageItemEntity();
            entity.icon = Consts.homeItems.get(0);
            entity.name = factorys.get(i).getName();
            entity.sid = factorys.get(i).getSid();
            entity.accid = factorys.get(i).getAcctId();
            entityList.add(entity);
        }

    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        ((SupportFragment) getParentFragment()).start(FactoryWorkShopContainerDelegate.newInstance(((List<HomePageItemEntity>) adapter.getData()).get(position).name));
        HomePageItemEntity homePageItemEntity=  ((List<HomePageItemEntity>) adapter.getData()).get(position);
        EventBusActivityScope.getDefault(_mActivity).postSticky(homePageItemEntity);
        getQueryWarn(homePageItemEntity.sid);


    }

    private void getQueryWarn(String sid) {
        ViseHttp.RETROFIT().create(WebApiServices.class).GetQueryWarn(sid) .compose(ApiTransformer.<GetQueryWarnResponse>norTransformer()).subscribe(new ApiCallbackSubscriber<>(new dealTokenExpire<GetQueryWarnResponse>(_mActivity) {
            @Override
            public void onSuccess(GetQueryWarnResponse data) {
                super.onSuccess(data);
                if (data != null && data.getContent() != null && data.getStatus().equals("OK") && data.getContent().size() > 0) {
                    gaugeColorRange = new HashMap<>();
                    Float f = Float.parseFloat(data.getContent().get(0).getFpy_red()) / 100;
                    gaugeColorRange.put("fpy_red", f);
                    f = Float.parseFloat(data.getContent().get(0).getFpy_yellow_begin()) / 100;
                    gaugeColorRange.put("fpy_yellow_begin", f);
                    f = Float.parseFloat(data.getContent().get(0).getFpy_yellow_end()) / 100;
                    gaugeColorRange.put("fpy_yellow_end", f);
                    f = Float.parseFloat(data.getContent().get(0).getTcr_yellow_begin()) / 100;
                    gaugeColorRange.put("tcr_yellow_begin", f);
                    f = Float.parseFloat(data.getContent().get(0).getTcr_yellow_end()) / 100;
                    gaugeColorRange.put("tcr_yellow_end", f);
                    Configurator.getInstance().withGagueColorRange(gaugeColorRange);
                }
            }
            @Override
            public void onFail(int errCode, String errMsg) {

            }
        }));

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

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEvent(List<LoginResponse.Factorys> factorys) {
        initEntityList(factorys);
        mHomePageRecyclerViewAdapter = new HomePageRecyclerViewAdapter(R.layout.homepage_delegate_recycler_item_layout, entityList);
        mHomePageRecyclerViewAdapter.setOnItemClickListener(this);
    }
}
