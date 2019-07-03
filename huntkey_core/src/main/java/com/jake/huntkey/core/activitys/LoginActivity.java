package com.jake.huntkey.core.activitys;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.blankj.utilcode.util.ToastUtils;

import com.jake.huntkey.core.R;
import com.jake.huntkey.core.R2;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;

public class LoginActivity extends BaseActivity {


    @BindView(R2.id.id_edt_username)
    protected AppCompatEditText userName;

    @BindView(R2.id.id_edt_passwd)
    protected AppCompatEditText passwd;

    @BindView(R2.id.id_btn_login)
    protected Button login;

    @BindView(R2.id.toolbar)
    protected Toolbar toolbar;


    @Override
    protected int setLayoutId() {
        return R.layout.login_activity_layout;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initView() {
        toolbar.setTitle("MES系统");
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }


}
