package com.github.lzyzsd.jsbridge;

import android.webkit.WebView;

/**
 * Created by ly on 2017/10/25.
 */

public interface IJsBridge extends WebViewJavascriptBridge{
    void onPageStarted(WebView webView,String url);
    void onPageFinished(WebView webView,String url);
    void shouldOverrideUrlLoading(WebView webView, String url);
}
