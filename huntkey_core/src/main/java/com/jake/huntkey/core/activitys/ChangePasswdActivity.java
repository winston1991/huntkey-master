package com.jake.huntkey.core.activitys;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.Toolbar;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.DeviceUtils;
import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.jake.huntkey.core.R;
import com.jake.huntkey.core.R2;
import com.jake.huntkey.core.app.Consts;
import com.jake.huntkey.core.net.WebApiServices;
import com.jake.huntkey.core.netbean.ChangePasswordResponse;
import com.jake.huntkey.core.netbean.LoginResponse;
import com.jake.huntkey.core.ui.icon.Loading.DialogLoaderManager;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.vise.xsnow.http.core.ApiTransformer;
import com.vise.xsnow.http.subscriber.ApiCallbackSubscriber;

import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class ChangePasswdActivity extends BaseActivity {
    @BindView(R2.id.toolbar)
    Toolbar toolbar;
    @BindView(R2.id.id_edt_original_passwd)
    AppCompatEditText idEdtOriginalPasswd;
    @BindView(R2.id.id_edt_new_passwd)
    AppCompatEditText idEdtNewPasswd;
    @BindView(R2.id.id_edt_renew_passwd)
    AppCompatEditText idEdtRenewPasswd;
    @BindView(R2.id.id_btn_commit)
    Button idBtnCommit;


    @Override
    protected void initView() {
        toolbar.setTitle("修改密码");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    protected int setLayoutId() {
        return R.layout.change_passwd_activity_layout;
    }


    @OnClick(R2.id.id_btn_commit)
    public void onViewClicked() {
        changePwd();

    }

    private void changePwd() {
        HashMap<String, String> map = new HashMap<>();
        map.put("emp", SPUtils.getInstance(Consts.SP_INSTANT_NAME).getString(Consts.SP_ITEM_USER_JOB_NUMBER));
        map.put("pwd", idEdtOriginalPasswd.getText().toString());
        map.put("npwd", idEdtNewPasswd.getText().toString());
        JSONObject jsonObject = new JSONObject(map);
        RequestBody requestBody =
                RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString());
        DialogLoaderManager.showLoading(this);
        ViseHttp.RETROFIT().create(WebApiServices.class).ChangePassword(requestBody)
                .compose(ApiTransformer.<ChangePasswordResponse>norTransformer()).subscribe(new ApiCallbackSubscriber<>(new ACallback<ChangePasswordResponse>() {
            @Override
            public void onSuccess(ChangePasswordResponse data) {
                if (data != null && data.getContent() != null && data.getStatus().equals("OK")) {
                    ToastUtils.showShort(data.getContent().get(0));
                    ActivityUtils.startActivity(LoginActivity.class);
                    ChangePasswdActivity.this.finish();
                } else {
                    ToastUtils.showShort("修改密码失败!");
                }
                DialogLoaderManager.stopLoading();
            }

            @Override
            public void onFail(int errCode, String errMsg) {
                ToastUtils.showShort(errMsg);
                DialogLoaderManager.stopLoading();
            }
        }));
    }
}
