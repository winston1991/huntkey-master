package com.jake.huntkey.core.activitys;

import android.view.View;
import android.widget.Button;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.Toolbar;

import com.jake.huntkey.core.R;
import com.jake.huntkey.core.R2;

import butterknife.BindView;

public class FindPasswordActivity extends BaseActivity {
    @BindView(R2.id.toolbar)
    Toolbar toolbar;
    @BindView(R2.id.id_edt_new_passwd)
    AppCompatEditText idEdtNewPasswd;
    @BindView(R2.id.id_edt_renew_passwd)
    AppCompatEditText idEdtRenewPasswd;
    @BindView(R2.id.id_btn_commit)
    Button idBtnCommit;


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

    }

    @Override
    protected int setLayoutId() {
        return R.layout.forget_passwd_activity_layout;
    }



}