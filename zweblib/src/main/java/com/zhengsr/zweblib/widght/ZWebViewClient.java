package com.zhengsr.zweblib.widght;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.util.Log;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceRequest;
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
    public static final String SCHEME_SMS = "sms:";
    private ZwebLoadListener mListener;
    public void setListener(ZwebLoadListener listener){
        mListener = listener;
    }


    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        //处理邮件，电话等重用url
        if (handleCommonLink(url)){
            return true;
        }
        view.loadUrl(url);
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




    private boolean handleCommonLink(String url) {
        if (url.startsWith(WebView.SCHEME_TEL)
                || url.startsWith(SCHEME_SMS)
                || url.startsWith(WebView.SCHEME_MAILTO)
                || url.startsWith(WebView.SCHEME_GEO)) {
            try {
                Context mActivity = null;
                if ((mActivity = WebRequestManager.getInstance().getContext()) == null) {
                    return false;
                }
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                mActivity.startActivity(intent);
            } catch (ActivityNotFoundException ignored) {
                ignored.printStackTrace();

            }
            return true;
        }
        return false;
    }



    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        super.onReceivedError(view, errorCode, description, failingUrl);

        if ((failingUrl != null
                && !failingUrl.equals(view.getUrl())
                && !failingUrl.equals(view.getOriginalUrl())) //错误连接与加载连接不一致，排除
                || (failingUrl == null && errorCode != -12) // 错误连接为null
                || errorCode == -1) { //当 errorCode = -1 且错误信息为 net::ERR_CACHE_MISS
            return;
        }

        if (mListener != null){
            mListener.onReceivedError(failingUrl,errorCode+" + "+description);
        }
    }


    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        //super.onReceivedSslError(view, handler, error);
        //证书不对，允许继续显示
        handler.proceed();
    }


}
