package com.jake.huntkey.core.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.Toolbar;

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
import com.jake.huntkey.core.netbean.GetUserInfoResponse;
import com.jake.huntkey.core.netbean.LoginResponse;
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
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 通过手机找回密码
 */
public class PhoneVerifyFindPasswdActivity extends BaseActivity {
    @BindView(R2.id.toolbar)
    Toolbar toolbar;

    @BindView(R2.id.id_verify_number)
    AppCompatEditText idVerifyNumber;
    @BindView(R2.id.id_btn_next)
    Button idBtnNext;
    @BindView(R2.id.id_btn_get_verifynumber)
    Button idBtnGetVerifynumber;
    @BindView(R2.id.id_tv_job_number)
    TextView idTvJobNumber;
    @BindView(R2.id.id_tv_phone_number)
    TextView idTvPhoneNumber;
    String jobNumber;
    private String phoneNum;

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
        jobNumber = getIntent().getStringExtra("jobNumber");
        phoneNum = SPUtils.getInstance(Consts.SP_INSTANT_NAME).getString(Consts.SP_ITEM_PHONE_NUMBER).trim();
        if (!jobNumber.equals(SPUtils.getInstance(Consts.SP_INSTANT_NAME).getString(Consts.SP_ITEM_USER_JOB_NUMBER).trim())) {
            idTvPhoneNumber.setText("");
            getUserInfo(jobNumber);
        } else {
            if (phoneNum.isEmpty()) {
                getUserInfo(jobNumber);
            } else {
                idTvPhoneNumber.setText(phoneNum.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2"));

            }
        }
        idTvJobNumber.setText(jobNumber);
        idVerifyNumber.requestFocus();

    }

    private void getUserInfo(String jobNumber) {
        ViseHttp.RETROFIT().create(WebApiServices.class).GetUserInfo(jobNumber)
                .compose(ApiTransformer.<GetUserInfoResponse>norTransformer())
                .subscribe(new ApiCallbackSubscriber<>(new ACallback<GetUserInfoResponse>() {


                    @Override
                    public void onSuccess(GetUserInfoResponse data) {
                        if (data != null && data.getContent() != null && data.getContent().size() > 0) {
                            phoneNum = data.getContent().get(0).getPhone().trim();
                            idTvPhoneNumber.setText(phoneNum.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2"));
                            idVerifyNumber.requestFocus();
                        }
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {

                    }
                }));
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
        map.put("emp", jobNumber);
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
                    intent.putExtra("token", data.getContent().get(0).getToken().trim());
                    intent.putExtra("emp", jobNumber);
                    startActivity(intent);
                    SPUtils.getInstance(Consts.SP_INSTANT_NAME).put(Consts.SP_ITEM_USER_JOB_NUMBER, jobNumber);//保存工号
                    SPUtils.getInstance(Consts.SP_INSTANT_NAME).put(Consts.SP_ITEM_PHONE_NUMBER, phoneNum);//获取手机号码
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
        map.put("emp", jobNumber);
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
        String jobNumber = idTvJobNumber.getText().toString().trim();
        if (jobNumber.isEmpty()) {
            ToastUtils.showShort("工号为空无法获取验证码");
            flag = false;
        }
        return flag;
    }


}
