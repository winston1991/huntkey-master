package com.jake.huntkey.core.activitys;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.Toolbar;

import com.blankj.utilcode.util.ActivityUtils;
import com.jake.huntkey.core.R;
import com.jake.huntkey.core.R2;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
    }

    @Override
    protected int setLayoutId() {
        return R.layout.phone_verify_passwd_activity_layout;
    }


    @OnClick(R2.id.id_btn_next)
    public void onViewClicked() {
        ActivityUtils.startActivity(FindPasswordActivity.class);
    }
}
