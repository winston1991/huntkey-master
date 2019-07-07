

package com.jake.huntkey.core.delegates.EChartsDelegate;

import com.jake.huntkey.core.R2;
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

    public void ClearWebCache() {
        mAgentWeb.clearWebCache();
    }
}
