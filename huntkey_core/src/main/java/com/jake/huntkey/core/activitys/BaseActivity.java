package com.jake.huntkey.core.activitys;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportActivity;

public abstract class BaseActivity extends SupportActivity implements ActivityCompat.OnRequestPermissionsResultCallback {


    private Unbinder mUnbinder = null;
    private final int REQUEST_READ_PHONE_STATE = 0;
    protected String IMEI;  //手机的IMEI码

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayoutId());
        mUnbinder = ButterKnife.bind(this);
        initView();
    }



    protected abstract void initView();

    protected abstract int setLayoutId();


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
        System.gc();
        System.runFinalization();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_READ_PHONE_STATE:
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    IMEI = getDeviceId(this);
                } else {
                    Toast.makeText(this, "权限已被用户拒绝", Toast.LENGTH_SHORT).show();
                }
                break;

            default:
                break;
        }
    }

    @SuppressLint("HardwareIds")
    public String getDeviceId(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (null != tm) {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_READ_PHONE_STATE);
            } else {
                if (tm.getDeviceId() != null) {
                    IMEI = tm.getDeviceId();
                } else {
                    IMEI = Settings.Secure.getString(context.getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
                }
            }
            return IMEI;
        }
        return "";
    }

}
