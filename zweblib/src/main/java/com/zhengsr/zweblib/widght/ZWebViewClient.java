package com.zhengsr.zweblib.widght;

import android.graphics.Bitmap;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.zhengsr.zweblib.callback.ZwebLoadListener;
import com.zhengsr.zweblib.entrance.WebRequestManager;

/**
 * Created by zhengshaorui
 * Time on 2019/2/22
 */

public class ZWebViewClient extends WebViewClient {
    private static final String TAG = "ZwebViewClient";
    private ZwebLoadListener mListener;
    public void setListener(ZwebLoadListener listener){
        mListener = listener;
    }


    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        WebRequestManager.getInstance().getWebView().loadUrl(url);
        return true;
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
       if (mListener != null){
           mListener.onPageStart();
       }
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        if (mListener != null){
            mListener.onPageFinish();
        }
    }



    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        super.onReceivedError(view, errorCode, description, failingUrl);
        if (mListener != null){
            mListener.onReceivedError(failingUrl,errorCode+" + "+description);
        }
    }
}
