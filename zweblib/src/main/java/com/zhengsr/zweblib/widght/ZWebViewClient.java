package com.zhengsr.zweblib.widght;

import android.graphics.Bitmap;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.zhengsr.zweblib.callback.ZwebLoadListener;

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
}
