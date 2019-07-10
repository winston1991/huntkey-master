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
import com.jake.huntkey.core.app.Consts;
import com.jake.huntkey.core.delegates.basedelegate.CheckPermissionDelegate;
import com.jake.huntkey.core.entity.HomePageItemEntity;
import com.jake.huntkey.core.net.WebApiServices;
import com.jake.huntkey.core.netbean.Get7DayFpyRateResponse;
import com.jake.huntkey.core.netbean.GetFpyRateResponse;
import com.jake.huntkey.core.netbean.LoginResponse;
import com.joanzapata.iconify.widget.IconTextView;
import com.vise.log.ViseLog;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.vise.xsnow.http.core.ApiTransformer;
import com.vise.xsnow.http.subscriber.ApiCallbackSubscriber;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import me.yokeyword.eventbusactivityscope.EventBusActivityScope;
import me.yokeyword.fragmentation.SupportFragment;


public class HomePageDelegate extends CheckPermissionDelegate implements BaseQuickAdapter.OnItemClickListener {


    @BindView(R2.id.id_recyclerView)
    RecyclerView mRecyclerView;
    HomePageRecyclerViewAdapter mHomePageRecyclerViewAdapter;
    ArrayList<HomePageItemEntity> entityList;

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
        toolbar.setTitle("选择工厂/车间");

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
        IconTextView iconTextView = view.findViewById(R.id.item_icon);
        TextView textView = view.findViewById(R.id.item_name);
        ((SupportFragment) getParentFragment()).start(FactoryWorkShopContainerDelegate.newInstance(((List<HomePageItemEntity>) adapter.getData()).get(position).name));
        ToastUtils.showShort(((List<HomePageItemEntity>) adapter.getData()).get(position).toString());
        EventBusActivityScope.getDefault(_mActivity).postSticky(((List<HomePageItemEntity>) adapter.getData()).get(position));

    }

    private void test() {

        ViseHttp.RETROFIT()
                .create(WebApiServices.class)
                .GetFpyRate("5", "431", "1")
                .compose(ApiTransformer.<GetFpyRateResponse>norTransformer())
                .subscribe(new ApiCallbackSubscriber<>(new ACallback<GetFpyRateResponse>() {
                    @Override
                    public void onSuccess(GetFpyRateResponse getFpyRateResponse) {
                        ViseLog.i("request onSuccess!");
                        if (getFpyRateResponse == null) {
                            return;
                        } else {
                            ToastUtils.showShort(getFpyRateResponse.getContent().toString());
                        }
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {
                        ToastUtils.showShort(errMsg);
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
