package com.jake.huntkey.core.net.callback;

import android.app.Activity;
import android.view.accessibility.AccessibilityManager;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.jake.huntkey.core.activitys.LoginActivity;
import com.jake.huntkey.core.netbean.BaseResponse;
import com.vise.xsnow.http.callback.ACallback;

public abstract class dealTokenExpire<T extends BaseResponse> extends ACallback<T> {
    private Activity mActivity;

    public dealTokenExpire(Activity activity) {
        this.mActivity = activity;
    }

    @Override
    public void onSuccess(T data) {
        if (data != null) {
            if (data.getStatus().equals("NG") && data.getErrorMsg().contains("Token")) {
                if (mActivity != null) {
                    ToastUtils.showShort(data.getErrorMsg());
                    ActivityUtils.startActivity(LoginActivity.class);
                    mActivity.finish();
                }
            }
        }

    }

    @Override
    public abstract void onFail(int errCode, String errMsg);
}
