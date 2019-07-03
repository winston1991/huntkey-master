package com.jake.huntkey;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import com.blankj.utilcode.util.ToastUtils;
import com.jake.huntkey.core.R2;
import com.jake.huntkey.core.activitys.BaseActivity;

import butterknife.BindView;

public class LoginActivity extends BaseActivity {



    @BindView(R2.id.id_edt_username)
    protected AppCompatEditText userName;

    @BindView(R2.id.id_edt_passwd)
    protected AppCompatEditText passwd;

    @BindView(R2.id.id_btn_login)
    protected Button login;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity_layout);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.login_activity_layout);
    }

    @Override
    protected void initView() {

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ToastUtils.showShort("  ");
            }
        });
    }




}
