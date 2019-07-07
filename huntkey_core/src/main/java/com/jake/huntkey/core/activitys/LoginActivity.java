package com.jake.huntkey.core.activitys;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.jake.huntkey.core.R;
import com.jake.huntkey.core.R2;
import com.jake.huntkey.core.netbean.LoginResponse;
import com.jake.huntkey.core.ui.icon.HKIcons;
import com.jake.huntkey.core.ui.icon.Loading.DialogLoaderManager;
import com.joanzapata.iconify.IconDrawable;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import me.yokeyword.eventbusactivityscope.EventBusActivityScope;


public class LoginActivity extends BaseActivity {


    @BindView(R2.id.id_edt_username)
    protected AppCompatEditText userName;

    @BindView(R2.id.id_edt_passwd)
    protected AppCompatEditText passwd;

    @BindView(R2.id.id_btn_login)
    protected Button login_btn;

    @BindView(R2.id.toolbar)
    protected Toolbar toolbar;
    @BindView(R2.id.id_img_user)
    ImageView idImgUser;
    @BindView(R2.id.id_img_passwd)
    ImageView idImgPasswd;


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

        Drawable drawableUser = new IconDrawable(this, HKIcons.icon_user);
        Drawable drawablePasswd = new IconDrawable(this, HKIcons.icon_password);
        idImgUser.setImageDrawable(drawableUser);
        idImgPasswd.setImageDrawable(drawablePasswd);
        toolbar.setTitle("MES系统");
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testlogin();
            }
        });

    }

    private void login() {
        HashMap<String, String> map = new HashMap<>();
        map.put("i_emp", userName.getText().toString());
        map.put("i_pwd", passwd.getText().toString());
        JSONObject jsonObject = new JSONObject(map);

        DialogLoaderManager.showLoading(this);
        ViseHttp.POST("api/Home/Login")
                .setJson(jsonObject.toString())
                .request(new ACallback<LoginResponse>() {
                    @Override
                    public void onSuccess(LoginResponse data) {
                        if (data != null && data.getContent().size() > 0) {
                            if (data.getContent().get(0).getResult().equals("1")) {
                                finish();
                                //给网络请求设置全局的请求头部  添加token
                                HashMap<String, String> map = new HashMap<>();
                                map.put("Authorization", "Bearer " + data.getContent().get(0).getToken());
                                map.put("Content-Type", "application/json;charset=utf-8");
                                SPUtils spUtils = SPUtils.getInstance("loginToken");
                                spUtils.put("Authorization", data.getContent().get(0).getToken());

                                ViseHttp.CONFIG().globalHeaders(map);//设置全局请求头
                                Bundle bundle1 = new Bundle();
                                // 把Persion数据放入到bundle中
                                //bundle1.put("factorys",data.getContent().get(0).getFactorys())
                                ActivityUtils.startActivity(bundle1, MainActivity.class);
                                ToastUtils.showShort("登录成功");
                            } else {
                                ToastUtils.showShort(data.getContent().get(0).getMessage());
                            }
                        } else {
                            ToastUtils.showShort("登录失败");
                        }
                        DialogLoaderManager.stopLoading();
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {
                        ToastUtils.showShort(errMsg);
                        DialogLoaderManager.stopLoading();
                    }
                });
    }


    private void testlogin() {
        DialogLoaderManager.showLoading(this);
        Observable.timer(5, TimeUnit.SECONDS).subscribe(new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {

                ActivityUtils.startActivity(MainActivity.class);
                ToastUtils.showShort("登录成功");
                finish();
                DialogLoaderManager.stopLoading();
            }
        });
    }


    public static class MessageEvent {
        public String name;

        public MessageEvent(String name) {
            this.name = name;
        }

    }
}


