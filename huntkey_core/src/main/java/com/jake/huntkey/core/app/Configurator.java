package com.jake.huntkey.core.app;

import android.app.Activity;
import android.app.Application;
import android.os.Handler;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.jake.huntkey.core.net.OKHttpUpdateHttpService;
import com.joanzapata.iconify.IconFontDescriptor;
import com.joanzapata.iconify.Iconify;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.vise.log.ViseLog;
import com.vise.log.inner.LogcatTree;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.interceptor.HttpLogInterceptor;
import com.xuexiang.xui.XUI;
import com.xuexiang.xupdate.XUpdate;
import com.xuexiang.xupdate.entity.UpdateError;
import com.xuexiang.xupdate.listener.OnUpdateFailureListener;
import com.xuexiang.xupdate.logs.LogcatLogger;
import com.xuexiang.xupdate.proxy.IUpdateHttpService;
import com.xuexiang.xupdate.utils.UpdateUtils;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;


public final class Configurator {

    private static final HashMap<Object, Object> LATTE_CONFIGS = new HashMap<>();
    private static final Handler HANDLER = new Handler();
    private static final ArrayList<IconFontDescriptor> ICONS = new ArrayList<>();
    private static final ArrayList<Interceptor> INTERCEPTORS = new ArrayList<>();

    private Configurator() {
        LATTE_CONFIGS.put(ConfigKeys.CONFIG_READY, false);
        LATTE_CONFIGS.put(ConfigKeys.HANDLER, HANDLER);
    }

    static Configurator getInstance() {
        return Holder.INSTANCE;
    }

    final HashMap<Object, Object> getHkConfigs() {
        return LATTE_CONFIGS;
    }

    private static class Holder {
        private static final Configurator INSTANCE = new Configurator();
    }

    public final void configure() {
        //添加日志记录
        Logger.addLogAdapter(new AndroidLogAdapter());
        initIcons();
        LATTE_CONFIGS.put(ConfigKeys.CONFIG_READY, true);
        //初始化Android通用工具集合
        Utils.init(HkEngine.getApplicationContext());
        XUI.init((Application) HkEngine.getApplicationContext());
        //网络请求库初始化
        ViseLog.getLogConfig()
                .configAllowLog(true)//是否输出日志
                .configShowBorders(false);//是否排版显示
        ViseLog.plant(new LogcatTree());//添加打印日志信息到Logcat的树
        ViseHttp.init((Application) HkEngine.getApplicationContext());
        ViseHttp.CONFIG().baseUrl((String) HkEngine.getConfiguration(ConfigKeys.API_HOST))//设置全局URL  url只能是域名 或者域名+端口号
                .connectTimeout(10, TimeUnit.SECONDS)//设置连接超时时间10秒
                //配置日志拦截器
                .interceptor(new HttpLogInterceptor()
                        .setLevel(HttpLogInterceptor.Level.BODY));


        XUpdate.get()
                .setILogger(new LogcatLogger())
                .isGet(true)
                .param("client", "EMS")         //设置默认公共请求参数
                .setOnUpdateFailureListener(new OnUpdateFailureListener() {  //设置版本更新出错的监听
                    @Override
                    public void onFailure(UpdateError error) {
                    }
                })
                .setIUpdateHttpService(new OKHttpUpdateHttpService())
                .init((Application) HkEngine.getApplicationContext());


    }

    public final Configurator withApiHost(String host) {
        LATTE_CONFIGS.put(ConfigKeys.API_HOST, host);
        return this;
    }

    public final Configurator withLoaderDelayed(long delayed) {
        LATTE_CONFIGS.put(ConfigKeys.LOADER_DELAYED, delayed);
        return this;
    }

    private void initIcons() {
        if (ICONS.size() > 0) {
            final Iconify.IconifyInitializer initializer = Iconify.with(ICONS.get(0));
            for (int i = 1; i < ICONS.size(); i++) {
                initializer.with(ICONS.get(i));
            }
        }
    }

    public final Configurator withIcon(IconFontDescriptor descriptor) {
        ICONS.add(descriptor);
        return this;
    }

    public final Configurator withInterceptor(Interceptor interceptor) {
        INTERCEPTORS.add(interceptor);
        LATTE_CONFIGS.put(ConfigKeys.INTERCEPTOR, INTERCEPTORS);
        return this;
    }

    public final Configurator withInterceptors(ArrayList<Interceptor> interceptors) {
        INTERCEPTORS.addAll(interceptors);
        LATTE_CONFIGS.put(ConfigKeys.INTERCEPTOR, INTERCEPTORS);
        return this;
    }

    public final Configurator withWeChatAppId(String appId) {
        LATTE_CONFIGS.put(ConfigKeys.WE_CHAT_APP_ID, appId);
        return this;
    }

    public final Configurator withWeChatAppSecret(String appSecret) {
        LATTE_CONFIGS.put(ConfigKeys.WE_CHAT_APP_SECRET, appSecret);
        return this;
    }

    public final Configurator withActivity(Activity activity) {
        LATTE_CONFIGS.put(ConfigKeys.ACTIVITY, activity);
        return this;
    }


    private void checkConfiguration() {
        final boolean isReady = (boolean) LATTE_CONFIGS.get(ConfigKeys.CONFIG_READY);
        if (!isReady) {
            throw new RuntimeException("Configuration is not ready,call configure");
        }
    }

    @SuppressWarnings("unchecked")
    final <T> T getConfiguration(Object key) {
        checkConfiguration();
        final Object value = LATTE_CONFIGS.get(key);
        if (value == null) {
            throw new NullPointerException(key.toString() + " IS NULL");
        }
        return (T) LATTE_CONFIGS.get(key);
    }
}
