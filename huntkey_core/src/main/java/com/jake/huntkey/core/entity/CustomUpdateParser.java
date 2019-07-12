package com.jake.huntkey.core.entity;

import com.blankj.utilcode.util.AppUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jake.huntkey.core.app.ConfigKeys;
import com.jake.huntkey.core.app.HkEngine;
import com.jake.huntkey.core.netbean.CheckVersionResponse;
import com.xuexiang.xupdate.entity.UpdateEntity;
import com.xuexiang.xupdate.proxy.IUpdateParser;

public class CustomUpdateParser implements IUpdateParser {
    @Override
    public UpdateEntity parseJson(String json) throws Exception {
        Gson gson = new Gson();
        CheckVersionResponse result = gson.fromJson(json, new TypeToken<CheckVersionResponse>() {
        }.getType());
        boolean flag = false;
        if (AppUtils.getAppVersionName().compareTo(result.getContent().get(0).getVersion().trim()) < 0) {
            flag = true;
        }
        if (result != null) {
            String url = (String) HkEngine.getConfiguration(ConfigKeys.API_HOST);
            return new UpdateEntity()
                    .setHasUpdate(flag)
                    .setVersionCode(AppUtils.getAppVersionCode())
                    .setVersionName(result.getContent().get(0).getVersion())
                    .setUpdateContent(result.getContent().get(0).getContent())
                    .setDownloadUrl(url+result.getContent().get(0).getUrl());
        }
        return null;
    }
}