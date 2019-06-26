package com.jake.huntkey.core.delegates.EChartsDelegate;

import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.just.agentweb.core.AgentWeb;

import me.yokeyword.fragmentation.SupportFragment;

public class WebViewCreater {

    /**
     * 创建AgentWeb
     *
     * @param fragment
     * @param viewGroup
     * @param url
     * @return
     */
    public static AgentWeb createAgentWeb(SupportFragment fragment, ViewGroup viewGroup, String url) {
        return AgentWeb.with(fragment)
                .setAgentWebParent(viewGroup, -1, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
                .useDefaultIndicator(-1, 3)
                .createAgentWeb()
                .ready()
                .go(url);
    }
}
