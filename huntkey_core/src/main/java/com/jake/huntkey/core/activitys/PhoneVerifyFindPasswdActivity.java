package com.jake.huntkey.core.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.Toolbar;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.DeviceUtils;
import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.jake.huntkey.core.R;
import com.jake.huntkey.core.R2;
import com.jake.huntkey.core.app.Consts;
import com.jake.huntkey.core.net.WebApiServices;
import com.jake.huntkey.core.net.callback.dealTokenExpire;
import com.jake.huntkey.core.netbean.ChangePasswordResponse;
import com.jake.huntkey.core.netbean.PostSendVerificationCodeResponse;
import com.jake.huntkey.core.netbean.PostValidateCodeResponse;
import com.jake.huntkey.core.ui.icon.Loading.DialogLoaderManager;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.vise.xsnow.http.core.ApiTransformer;
import com.vise.xsnow.http.subscriber.ApiCallbackSubscriber;


import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 通过手机找回密码
 */
public class PhoneVerifyFindPasswdActivity extends BaseActivity {
    @BindView(R2.id.toolbar)
    Toolbar toolbar;
    @BindView(R2.id.id_edt_job_number)
    AppCompatEditText idEdtJobNumber;
    @BindView(R2.id.id_edt_phone_number)
    AppCompatEditText idEdtPhoneNumber;
    @BindView(R2.id.id_verify_number)
    AppCompatEditText idVerifyNumber;
    @BindView(R2.id.id_btn_next)
    Button idBtnNext;
    @BindView(R2.id.id_btn_get_verifynumber)
    Button idBtnGetVerifynumber;


    @Override
    protected void initView() {
        toolbar.setTitle("获取手机验证码");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        boolean rememberPwd = SPUtils.getInstance(Consts.SP_INSTANT_NAME).getBoolean(Consts.SP_ITEM_CHECKBOX_REMEMBER_PWD);
        String jobNumber = SPUtils.getInstance(Consts.SP_INSTANT_NAME).getString(Consts.SP_ITEM_USER_JOB_NUMBER);
        String phoneNumber = SPUtils.getInstance(Consts.SP_INSTANT_NAME).getString(Consts.SP_ITEM_PHONE_NUMBER);
        if (rememberPwd) {
            idEdtJobNumber.setText(jobNumber);
            idEdtPhoneNumber.setText(phoneNumber);
            if (jobNumber.equals("")) {
                idEdtJobNumber.requestFocus();
            } else if (phoneNumber.equals("")) {
                idEdtPhoneNumber.requestFocus();
            } else if (!jobNumber.equals("") && !phoneNumber.equals("")) {
                idVerifyNumber.requestFocus();
            }
        }
    }

    @Override
    protected int setLayoutId() {
        return R.layout.phone_verify_passwd_activity_layout;
    }


    @OnClick(R2.id.id_btn_next)
    public void onViewClicked() {
        next();
    }

    private void next() {
        String verifyNumber = idVerifyNumber.getText().toString().trim();
        if (verifyNumber.isEmpty()) {
            idVerifyNumber.setError("请先获取验证码!");
            return;
        } else {
            idVerifyNumber.setError(null);
        }
        HashMap<String, String> map = new HashMap<>();
        map.put("emp", SPUtils.getInstance(Consts.SP_INSTANT_NAME).getString(Consts.SP_ITEM_USER_JOB_NUMBER));
        map.put("code", verifyNumber);
        JSONObject jsonObject = new JSONObject(map);
        RequestBody requestBody =
                RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString());
        DialogLoaderManager.showLoading(this);
        ViseHttp.RETROFIT().create(WebApiServices.class).PostValidateCode(requestBody)
                .compose(ApiTransformer.<PostValidateCodeResponse>norTransformer()).subscribe(new ApiCallbackSubscriber<>(new dealTokenExpire<PostValidateCodeResponse>(this) {
            @Override
            public void onSuccess(PostValidateCodeResponse data) {
                super.onSuccess(data);
                if (data.getContent() != null && data.getContent().size() > 0) {
                    Intent intent = new Intent(PhoneVerifyFindPasswdActivity.this, FindPasswordActivity.class);
                    intent.putExtra("token", data.getContent().get(0).getToken());
                    startActivity(intent);
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


    @OnClick(R2.id.id_btn_get_verifynumber)
    public void onViewClicked2() {
        if (checkForm()) {
            getVerifyNumber();
        }
    }

    private void getVerifyNumber() {
        HashMap<String, String> map = new HashMap<>();
        map.put("emp", SPUtils.getInstance(Consts.SP_INSTANT_NAME).getString(Consts.SP_ITEM_USER_JOB_NUMBER));
        map.put("equip", DeviceUtils.getModel());
        map.put("ip", NetworkUtils.getIPAddress(true));
        JSONObject jsonObject = new JSONObject(map);
        RequestBody requestBody =
                RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString());
        DialogLoaderManager.showLoading(this);
        ViseHttp.RETROFIT().create(WebApiServices.class).PostSendVerificationCode(requestBody)
                .compose(ApiTransformer.<PostSendVerificationCodeResponse>norTransformer()).subscribe(new ApiCallbackSubscriber<>(new dealTokenExpire<PostSendVerificationCodeResponse>(this) {
            @Override
            public void onSuccess(PostSendVerificationCodeResponse data) {
                super.onSuccess(data);
                if (data.getContent() != null && data.getContent().size() > 0) {
                    ToastUtils.showShort(data.getContent().get(0));
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

    private boolean checkForm() {
        boolean flag = true;
        String phoneNumber = idEdtPhoneNumber.getText().toString().trim();
        if (phoneNumber.isEmpty() || !RegexUtils.isMobileSimple(phoneNumber)) {
            idEdtPhoneNumber.setError("请填写正确的手机号码!");
            flag = false;
        } else {
            idEdtPhoneNumber.setError(null);
        }
        return flag;
    }
}
