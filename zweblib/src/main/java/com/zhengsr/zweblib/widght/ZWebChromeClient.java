package com.zhengsr.zweblib.widght;

import android.text.TextUtils;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.zhengsr.zweblib.callback.ZwebLoadListener;

/**
 * Created by zhengshaorui
 * Time on 2019/2/22
 */

public class ZWebChromeClient extends WebChromeClient {
    private static final String TAG = "ZWebChromeClient";
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

    @Override
    public void onReceivedTitle(WebView view, String title) {
        super.onReceivedTitle(view, title);
        if (!TextUtils.isEmpty(title) && title.toLowerCase().contains("error")
                || "404".equals(title)
                || "System Error".equals(title)){
            if (mListener != null){
                mListener.onReceivedError(view.getUrl(),title);
            }
        }


    }
}
