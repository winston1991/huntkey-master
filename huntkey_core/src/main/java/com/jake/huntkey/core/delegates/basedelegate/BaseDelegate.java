package com.jake.huntkey.core.delegates.basedelegate;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.jake.huntkey.core.activitys.BaseActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportFragment;

public abstract class BaseDelegate extends SupportFragment {


    /**
     * 当前界面是否呈现给用户的状态标志
     */
    protected boolean isVisible;
    //所有fragment的宿主Activity
    protected FragmentActivity _mActivity = null;
    @SuppressWarnings("SpellCheckingInspection")
    private Unbinder mUnbinder = null;

    public abstract Object setLayout();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView;
        if (setLayout() instanceof Integer) {
            rootView = inflater.inflate((int) setLayout(), container, false);
        } else if (setLayout() instanceof View) {
            rootView = (View) setLayout();
        } else {
            throw new ClassCastException("type of setLayout() must be int or View!");
        }
        mUnbinder = ButterKnife.bind(this, rootView);
        onBindView(savedInstanceState, rootView);
        return rootView;
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        _mActivity = getSupportDelegate().getActivity();

    }

    protected abstract void onBindView(Bundle savedInstanceState, View rootView);

    public final BaseActivity getBaseActivity() {
        return (BaseActivity) _mActivity;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
    }


    /**
     * 重写Fragment父类生命周期方法，在onCreate之前调用该方法，实现Fragment数据的缓加载.
     *
     * @param isVisibleToUser 当前是否已将界面显示给用户的状态
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    /**
     * 当界面呈现给用户，即设置可见时执行，进行加载数据的方法
     * 在用户可见时加载数据，而不在用户不可见的时候加载数据，是为了防止控件对象出现空指针异常
     */
    protected void onVisible() {
        loadData();
    }

    /**
     * 当界面还没呈现给用户，即设置不可见时执行
     */
    protected void onInvisible() {
    }

    /**
     * fragment可见时加载数据方法
     */
    protected void loadData() {
    }
}
