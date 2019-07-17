package com.jake.huntkey.core.activitys;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.Toolbar;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.jake.huntkey.core.R;
import com.jake.huntkey.core.R2;
import com.jake.huntkey.core.app.Consts;
import com.jake.huntkey.core.net.WebApiServices;
import com.jake.huntkey.core.net.callback.dealTokenExpire;
import com.jake.huntkey.core.netbean.PostValidateCodeResponse;
import com.jake.huntkey.core.netbean.ResetPasswordResponse;
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

public class FindPasswordActivity extends BaseActivity {
    @BindView(R2.id.toolbar)
    Toolbar toolbar;
    @BindView(R2.id.id_edt_new_passwd)
    AppCompatEditText idEdtNewPasswd;
    @BindView(R2.id.id_edt_renew_passwd)
    AppCompatEditText idEdtRenewPasswd;
    @BindView(R2.id.id_btn_commit)
    Button idBtnCommit;
    private String token;

    @Override
    protected void initView() {
        toolbar.setTitle("找回密码");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        token = getIntent().getStringExtra("token");
    }

    @Override
    protected int setLayoutId() {
        return R.layout.forget_passwd_activity_layout;
    }

    @OnClick(R2.id.id_btn_commit)
    public void onViewClicked() {
        if (checkForm()) {
            HashMap<String, String> map = new HashMap<>();
            map.put("emp", SPUtils.getInstance(Consts.SP_INSTANT_NAME).getString(Consts.SP_ITEM_USER_JOB_NUMBER));
            map.put("pwd", idEdtRenewPasswd.getText().toString().trim());
            JSONObject jsonObject = new JSONObject(map);
            RequestBody requestBody =
                    RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString());
            DialogLoaderManager.showLoading(this);
            ViseHttp
                    .RETROFIT().addHeader("Authorization", "Bearer " + token)
                    .addHeader("Content-Type", "application/json;charset=utf-8")
                    .create(WebApiServices.class).ResetPassword(requestBody)
                    .compose(ApiTransformer.<ResetPasswordResponse>norTransformer())
                    .subscribe(new ApiCallbackSubscriber<>(new ACallback<ResetPasswordResponse>() {
                        @Override
                        public void onSuccess(ResetPasswordResponse data) {
                            if (data.getContent() != null && data.getContent().size() > 0) {
                                ToastUtils.showShort(data.getContent().get(0));
                                ActivityUtils.startActivity(LoginActivity.class);
                                finish();
                            } else {
                                ToastUtils.showShort(data.getErrorMsg());
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

    private boolean checkForm() {
        boolean flag = true;
        String np = idEdtNewPasswd.getText().toString().trim();
        String rnp = idEdtRenewPasswd.getText().toString().trim();
        if (np.isEmpty()) {
            flag = false;
            idEdtNewPasswd.setError("密码不能为空!");
        }
        if (rnp.isEmpty()) {
            flag = false;
            idEdtRenewPasswd.setError("新密码不能为空!");
        }
        if (!np.equals(rnp)) {
            flag = false;
            idEdtRenewPasswd.setError("两次输入的密码不相同!");
        }
        return flag;
    }
}
