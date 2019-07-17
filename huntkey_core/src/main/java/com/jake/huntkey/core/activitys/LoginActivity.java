package com.jake.huntkey.core.activitys;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.Toolbar;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.DeviceUtils;
import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.jake.huntkey.core.R;
import com.jake.huntkey.core.R2;
import com.jake.huntkey.core.app.ConfigKeys;
import com.jake.huntkey.core.app.Consts;
import com.jake.huntkey.core.app.HkEngine;
import com.jake.huntkey.core.entity.CustomUpdateParser;
import com.jake.huntkey.core.net.WebApiServices;
import com.jake.huntkey.core.netbean.GetUserInfoResponse;
import com.jake.huntkey.core.netbean.LoginResponse;
import com.jake.huntkey.core.ui.icon.HKIcons;
import com.jake.huntkey.core.ui.icon.Loading.DialogLoaderManager;
import com.joanzapata.iconify.IconDrawable;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.vise.xsnow.http.core.ApiTransformer;
import com.vise.xsnow.http.subscriber.ApiCallbackSubscriber;
import com.xuexiang.xupdate.XUpdate;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;


public class LoginActivity extends BaseActivity {


    @BindView(R2.id.id_btn_login)
    protected Button login_btn;
    @BindView(R2.id.toolbar)
    protected Toolbar toolbar;
    @BindView(R2.id.id_img_user)
    ImageView idImgUser;
    @BindView(R2.id.id_img_passwd)
    ImageView idImgPasswd;
    @BindView(R2.id.id_tv_forget_passwd)
    TextView idTvForgetPasswd;
    @BindView(R2.id.id_edt_username)
    AppCompatEditText idEdtUsername;
    @BindView(R2.id.id_edt_passwd)
    AppCompatEditText idEdtPasswd;
    @BindView(R2.id.id_checkbox_rememberpwd)
    AppCompatCheckBox idCheckboxRememberpwd;


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
        String username = SPUtils.getInstance(Consts.SP_INSTANT_NAME).getString(Consts.SP_ITEM_USER_JOB_NUMBER);
        boolean rememberPwd = SPUtils.getInstance(Consts.SP_INSTANT_NAME).getBoolean(Consts.SP_ITEM_CHECKBOX_REMEMBER_PWD);
        if (rememberPwd) {
            idEdtUsername.setText(username);
            idCheckboxRememberpwd.setChecked(true);
        }
        idCheckboxRememberpwd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    SPUtils.getInstance(Consts.SP_INSTANT_NAME).put(Consts.SP_ITEM_CHECKBOX_REMEMBER_PWD, false);
                } else {
                    SPUtils.getInstance(Consts.SP_INSTANT_NAME).put(Consts.SP_ITEM_CHECKBOX_REMEMBER_PWD, true);
                }
            }
        });
        idEdtUsername.setSelection(idEdtUsername.getText().toString().length());
        Drawable drawableUser = new IconDrawable(this, HKIcons.icon_user);
        Drawable drawablePasswd = new IconDrawable(this, HKIcons.icon_password);
        idImgUser.setImageDrawable(drawableUser);
        idImgPasswd.setImageDrawable(drawablePasswd);
        toolbar.setTitle(getResources().getString(R.string.app_name));
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        checkVersion();

    }

    private void checkVersion() {
        String url = (String) HkEngine.getConfiguration(ConfigKeys.API_HOST);
        XUpdate.newBuild(this)
                .updateUrl(url + "api/Home/CheckVersion")
                .updateParser(new CustomUpdateParser()) //设置自定义的版本更新解析器
                .update();

    }

    private void login() {
        if (checkForm()) {
            HashMap<String, String> map = new HashMap<>();
            map.put("emp", idEdtUsername.getText().toString());
            map.put("pwd", idEdtPasswd.getText().toString());
            map.put("ip", NetworkUtils.getIPAddress(true));
            map.put("mac", DeviceUtils.getAndroidID());
            map.put("equip", DeviceUtils.getModel());
            JSONObject jsonObject = new JSONObject(map);
            RequestBody requestBody =
                    RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString());
            DialogLoaderManager.showLoading(this);
            ViseHttp.RETROFIT().create(WebApiServices.class).Login(requestBody)
                    .compose(ApiTransformer.<LoginResponse>norTransformer())
                    .flatMap(new Function<LoginResponse, ObservableSource<?>>() {
                        @Override
                        public ObservableSource<GetUserInfoResponse> apply(LoginResponse data) throws Exception {
                            if (data != null && data.getStatus().equals("OK") && data.getContent() != null && data.getContent().size() > 0) {
                                if (data.getContent().get(0).getResult().equals("1")) {
                                    handleLoginSuccess(data);
                                    ToastUtils.showShort("登录成功");
                                } else if (data.getContent().get(0).getResult().equals("2")) {
                                    //密码错误
                                    idEdtPasswd.setError(data.getContent().get(0).getMessage());
                                } else if (data.getContent().get(0).getResult().equals("3")) {
                                    //登录成功 提示密码即将过期
                                    handleLoginSuccess(data);
                                    ToastUtils.showShort("密码即将过期,请尽快修改密码");
                                } else if (data.getContent().get(0).getResult().equals("4")) {
                                    //密码已经过期强制跳到修改密码界面
                                    ToastUtils.showShort("密码已经过期,请修改密码");
                                    ActivityUtils.startActivity(ChangePasswdActivity.class);
                                } else if (data.getContent().get(0).getResult().equals("5")) {
                                    //账号已经锁定
                                    ToastUtils.showShort("密码错误超过5次,账号已经锁定!");
                                } else if (data.getContent().get(0).getResult().equals("0")) {
                                    //初始密码，轻质跳到修改密码界面
                                    ToastUtils.showShort("请修改初始密码!");
                                    ActivityUtils.startActivity(ChangePasswdActivity.class);
                                } else if (data.getContent().get(0).getResult().equals("-1")) {
                                    //账号无效
                                    idEdtUsername.setError(data.getContent().get(0).getMessage());
                                }
                            } else {
                                ToastUtils.showShort("登录失败");
                            }
                            DialogLoaderManager.stopLoading();
                            return ViseHttp.RETROFIT().create(WebApiServices.class).GetUserInfo(idEdtUsername.getText().toString()).subscribeOn(Schedulers.io());
                        }
                    })
                    .subscribe(new ApiCallbackSubscriber<>(new ACallback<Object>() {
                        @Override
                        public void onSuccess(Object data) {
                            if (data != null && data instanceof GetUserInfoResponse) {
                                GetUserInfoResponse userInfo = (GetUserInfoResponse) data;
                                if (userInfo.getContent() != null && userInfo.getStatus().equals("OK")) {
                                    SPUtils.getInstance(Consts.SP_INSTANT_NAME).put(Consts.SP_ITEM_PHONE_NUMBER, userInfo.getContent().get(0).getPhone());//获取手机号码
                                    SPUtils.getInstance(Consts.SP_INSTANT_NAME).put(Consts.SP_ITEM_DEPT_NAME, userInfo.getContent().get(0).getDeptName());//获取员工部门
                                }
                            }
                            DialogLoaderManager.stopLoading();
                        }

                        @Override
                        public void onFail(int errCode, String errMsg) {
                            DialogLoaderManager.stopLoading();
                            ToastUtils.showShort(errMsg);
                        }
                    }));


//            ViseHttp.POST("api/Home/Login")
//                    .setJson(jsonObject.toString())
//                    .request(new ACallback<LoginResponse>() {
//                        @Override
//                        public void onSuccess(LoginResponse data) {
//                            if (data != null && data.getStatus().equals("OK") && data.getContent() != null && data.getContent().size() > 0) {
//                                if (data.getContent().get(0).getResult().equals("1")) {
//                                    finish();
//                                    //给网络请求设置全局的请求头部  添加token
//                                    HashMap<String, String> map = new HashMap<>();
//                                    map.put("Authorization", "Bearer " + data.getContent().get(0).getToken());
//                                    map.put("Content-Type", "application/json;charset=utf-8");
//                                    handleLoginSuccess(data);
//                                    ViseHttp.CONFIG().globalHeaders(map);//设置全局请求头
//                                    ActivityUtils.startActivity(MainActivity.class);
//                                    //发送工厂对象实体
//                                    EventBus.getDefault().postSticky(data.getContent().get(0).getFactorys());
//                                    ToastUtils.showShort("登录成功");
//                                } else {
//                                    ToastUtils.showShort(data.getContent().get(0).getMessage());
//                                }
//                            } else {
//                                ToastUtils.showShort("登录失败");
//                            }
//                            DialogLoaderManager.stopLoading();
//                        }
//
//                        @Override
//                        public void onFail(int errCode, String errMsg) {
//                            ToastUtils.showShort(errMsg);
//                            DialogLoaderManager.stopLoading();
//                        }
//                    });
        }
    }

    private void handleLoginSuccess(LoginResponse data) {
        finish();
        //给网络请求设置全局的请求头部  添加token
        HashMap<String, String> map = new HashMap<>();
        map.put("Authorization", "Bearer " + data.getContent().get(0).getToken());
        map.put("Content-Type", "application/json;charset=utf-8");
        SPUtils spUtils = SPUtils.getInstance(Consts.SP_INSTANT_NAME);
        spUtils.put(Consts.SP_ITEM_TOKEN_NAME, data.getContent().get(0).getToken());
        spUtils.put(Consts.SP_ITEM_DEPTCODE_NAME, data.getContent().get(0).getDeptAuthority());
        spUtils.put(Consts.SP_ITEM_USER_NAME, data.getContent().get(0).getName());//用户名
        spUtils.put(Consts.SP_ITEM_USER_JOB_NUMBER, data.getContent().get(0).getUser());//保存账号
        if (idCheckboxRememberpwd.isChecked()) {
            spUtils.put(Consts.SP_ITEM_CHECKBOX_REMEMBER_PWD, true);//
        } else {
            spUtils.put(Consts.SP_ITEM_CHECKBOX_REMEMBER_PWD, false);//
        }
        ViseHttp.CONFIG().globalHeaders(map);//设置全局请求头
        ActivityUtils.startActivity(MainActivity.class);
        //通过EventBus发送工厂对象实体
        EventBus.getDefault().postSticky(data.getContent().get(0).getFactorys());

    }


    private boolean checkForm() {
        boolean isPass = true;
        if (idEdtUsername.getText().toString().isEmpty()) {
            idEdtUsername.setError("用户名不能为空");
            isPass = false;
        } else {
            idEdtUsername.setError(null);
        }
        if (idEdtPasswd.getText().toString().isEmpty()) {
            idEdtPasswd.setError("密码不能为空");
            isPass = false;
        } else {
            idEdtPasswd.setError(null);
        }
        return isPass;
    }


    @OnClick(R2.id.id_tv_forget_passwd)
    public void onViewClicked() {
        ActivityUtils.startActivity(PhoneVerifyFindPasswdActivity.class);
    }


}


