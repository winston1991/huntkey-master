package com.jake.huntkey.core.activitys;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.jake.huntkey.core.R;
import com.jake.huntkey.core.R2;
import com.jake.huntkey.core.netbean.LoginResponse;
import com.jake.huntkey.core.ui.icon.Loading.DialogLoaderManager;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.vise.xsnow.http.mode.CacheMode;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.Toolbar;

import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;


public class LoginActivity extends BaseActivity {


    @BindView(R2.id.id_edt_username)
    protected AppCompatEditText userName;

    @BindView(R2.id.id_edt_passwd)
    protected AppCompatEditText passwd;

    @BindView(R2.id.id_btn_login)
    protected Button login_btn;

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
        userName.setSelection(userName.getText().toString().length());
        toolbar.setTitle("MES系统");
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

    }

    private void login() {
        HashMap<String, String> map = new HashMap<>();
        map.put("i_emp", userName.getText().toString());
        map.put("i_pwd", "");
        JSONObject jsonObject = new JSONObject(map);

        DialogLoaderManager.showLoading(this);
        ViseHttp.POST("api/Home/Login")
                .setJson(jsonObject.toString())
                .request(new ACallback<LoginResponse>() {
                    @Override
                    public void onSuccess(LoginResponse data) {
                        ToastUtils.showShort("登录成功");
                        DialogLoaderManager.stopLoading();
                        finish();
                        ActivityUtils.startActivity(MainActivity.class);
                    }
                    @Override
                    public void onFail(int errCode, String errMsg) {
                        ToastUtils.showShort(errMsg);
                        DialogLoaderManager.stopLoading();
                    }
                });
    }

}


