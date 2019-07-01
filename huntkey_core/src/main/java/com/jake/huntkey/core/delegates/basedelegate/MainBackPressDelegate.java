package com.jake.huntkey.core.delegates.basedelegate;

import android.content.Context;

import com.blankj.utilcode.util.ToastUtils;
import com.jake.huntkey.core.delegates.MainDelegate;
import com.jake.huntkey.core.delegates.basedelegate.CheckPermissionDelegate;

public abstract class MainBackPressDelegate extends CheckPermissionDelegate {
    protected OnBackToFirstListener _mBackToFirstListener;
    private static final long WAIT_TIME = 2000L;
    private long TOUCH_TIME = 0;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnBackToFirstListener) {
            _mBackToFirstListener = (OnBackToFirstListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnBackToFirstListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        _mBackToFirstListener = null;
    }

    /**
     * 处理回退事件
     *
     * @return
     */
    @Override
    public boolean onBackPressedSupport() {
        if (getChildFragmentManager().getBackStackEntryCount() > 1) {
            popChild();
        } else {
            if (this instanceof MainDelegate) {
                if (!((MainDelegate) this).viewPagerDelegate.isFirstPage()) {
                    ((MainDelegate) this).viewPagerDelegate.goToFirstPage();
                }else {
                    if (System.currentTimeMillis() - TOUCH_TIME < WAIT_TIME) {
                        _mActivity.finish();           // 如果是 第一个Fragment 则退出app
                    } else {
                        TOUCH_TIME = System.currentTimeMillis();
                        ToastUtils.showShort("请双击退出应用");
                    }
                }
            }
        }
        return true;
    }

    public interface OnBackToFirstListener {
        void onBackToFirstFragment();
    }
}
