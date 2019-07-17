package com.jake.huntkey;

import android.app.Application;

import com.jake.huntkey.core.app.HkEngine;
import com.jake.huntkey.core.ui.icon.FontHkModule;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        HkEngine.init(this)
                .withIcon(new FontAwesomeModule())
                .withIcon(new FontHkModule())
                .withApiHost(BuildConfig.HostUrl).configure();
    }
}
