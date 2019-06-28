package com.jake.huntkey.core.delegates.EChartsDelegate;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

import com.blankj.utilcode.util.ToastUtils;
import com.just.agentweb.core.AgentWeb;
import com.just.agentweb.core.client.WebChromeClientDelegate;

import me.yokeyword.fragmentation.SupportFragment;

public class WebViewCreater {


    OnPageLoadFinishedListener mOnPageLoadFinishedListener;

    WebViewCreater(OnPageLoadFinishedListener onPageLoadFinishedListener) {
        this.mOnPageLoadFinishedListener = onPageLoadFinishedListener;
    }

    /**
     * 创建AgentWeb
     *
     * @param fragment
     * @param viewGroup

     * @return
     */
    public AgentWeb createAgentWeb(SupportFragment fragment, ViewGroup viewGroup) {
        return AgentWeb.with(fragment)
                .setAgentWebParent(viewGroup, -1, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
                .useDefaultIndicator(-1, 3)
                .setWebViewClient(new WebViewClient() {

                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {


                        return super.shouldOverrideUrlLoading(view, request);
                    }

                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        return super.shouldOverrideUrlLoading(view, url);
                    }

                    @Override
                    public void onPageFinished(WebView view, String url) {
                        super.onPageFinished(view, url);
                        mOnPageLoadFinishedListener.onPageLoadFinished();
                    }


                    @Override
                    public void onPageCommitVisible(WebView view, String url) {
                        super.onPageCommitVisible(view, url);
                    }
                })
                .createAgentWeb()
                .ready()
                .go("file:///android_asset/chart/src/template.html");
    }


    public interface OnPageLoadFinishedListener {
        void onPageLoadFinished();
    }

}
