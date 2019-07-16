package com.jake.huntkey.core.activitys;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.Toolbar;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.SPUtils;
import com.jake.huntkey.core.R;
import com.jake.huntkey.core.R2;
import com.jake.huntkey.core.app.Consts;


import butterknife.BindView;
import butterknife.OnClick;

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
        ActivityUtils.startActivity(FindPasswordActivity.class);
    }


    @OnClick(R2.id.id_btn_get_verifynumber)
    public void onViewClicked2() {
        if (checkForm()) {
        }
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
