/*
 * Copyright (C) 2019 xuexiangjys(xuexiangjys@163.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.jake.huntkey.core.delegates.EChartsDelegate;

import com.jake.huntkey.core.delegates.basedelegate.BaseBackDelegate;
import com.jake.huntkey.core.delegates.basedelegate.CheckPermissionDelegate;
import com.just.agentweb.core.AgentWeb;


public abstract class BaseWebViewDelegate extends CheckPermissionDelegate {

    protected AgentWeb mAgentWeb;

    //===================生命周期管理===========================//
    @Override
    public void onResume() {
        mAgentWeb.getWebLifeCycle().onResume();//恢复
        super.onResume();
    }

    @Override
    public void onPause() {
        mAgentWeb.getWebLifeCycle().onPause(); //暂停应用内所有WebView ， 调用mWebView.resumeTimers();/mAgentWeb.getWebLifeCycle().onResume(); 恢复。
        super.onPause();
    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        return mAgentWeb.handleKeyEvent(keyCode, event);
//    }


    @Override
    public void onDestroyView() {
        mAgentWeb.destroy();
        super.onDestroyView();
    }
}
