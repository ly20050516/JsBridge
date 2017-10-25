package com.github.lzyzsd.jsbridge;

import android.graphics.Bitmap;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * Created by bruce on 10/28/15.
 */
public class BridgeWebViewClient extends WebViewClient {
    private BridgeWebView webView;

    public BridgeWebViewClient(BridgeWebView webView) {
        this.webView = webView;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        Log.d("LiuTag", "shouldOverrideUrlLoading: url = " + url);
        try {
            url = URLDecoder.decode(url, "UTF-8");
            Log.d("LiuTag", "shouldOverrideUrlLoading: decode url = " + url);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        if (url.startsWith(BridgeUtil.YY_RETURN_DATA)) { // 如果是返回数据
            Log.d("LiuTag", "shouldOverrideUrlLoading: YY_RETURN_DATA = " + url);
            webView.handlerReturnData(url);
            return true;
        } else if (url.startsWith(BridgeUtil.YY_OVERRIDE_SCHEMA)) { // js 调用 java?
            Log.d("LiuTag", "shouldOverrideUrlLoading: YY_OVERRIDE_SCHEMA = " + url);
            webView.flushMessageQueue();
            return true;
        } else {
            return super.shouldOverrideUrlLoading(view, url);
        }
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);

        // TODO: 2017/10/25 每次页面打开完成都要加载一次吗
        if (BridgeWebView.toLoadJs != null) {
            long time1 = System.currentTimeMillis();
            Log.d("LiuTag", "onPageFinished: load WebViewJavascriptBridge.js begin");
            BridgeUtil.webViewLoadLocalJs(view, BridgeWebView.toLoadJs);
            Log.d("LiuTag", "onPageFinished: load WebViewJavascriptBridge.js end;" + (System.currentTimeMillis() - time1));

        }

        /**
         * 页面加载完成时，清空消息列表中的消息
         */
        if (webView.getStartupMessage() != null) {
            Log.d("LiuTag", "onPageFinished: clear start up message");
            for (Message m : webView.getStartupMessage()) {
                webView.dispatchMessage(m);
            }
            webView.setStartupMessage(null);
        }
    }

    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        super.onReceivedError(view, errorCode, description, failingUrl);
    }
}