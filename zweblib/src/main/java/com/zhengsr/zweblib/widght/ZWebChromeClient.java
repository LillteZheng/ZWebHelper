package com.zhengsr.zweblib.widght;

import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.zhengsr.zweblib.callback.ZwebLoadListener;

/**
 * Created by zhengshaorui
 * Time on 2019/2/22
 */

public class ZWebChromeClient extends WebChromeClient {
    private ZwebLoadListener mListener;
    public void setListener(ZwebLoadListener listener){
        mListener = listener;
    }
    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        if (mListener != null){
            mListener.onPageProgress(newProgress);
        }
    }
}
