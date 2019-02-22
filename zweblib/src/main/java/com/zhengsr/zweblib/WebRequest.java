package com.zhengsr.zweblib;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;

import com.zhengsr.zweblib.entrance.WebRequestManager;
import com.zhengsr.zweblib.widght.ZWebChromeClient;
import com.zhengsr.zweblib.widght.ZWebViewClient;

/**
 * Created by zhengshaorui
 * Time on 2019/2/21
 */

public class WebRequest {
    private static final String TAG = "ZWebRequest";
    private WebBuilder mBuilder;
    public static WebRequest create(){
        return new WebRequest();
    }
    private WebRequest(){}


    public WebBuilder getBuilder(Context context){
        mBuilder = new WebBuilder();
        mBuilder.context = context;
        return mBuilder;
    }

    public static class WebBuilder {
        Context context;
        String url;
        ViewGroup parentView;
        ZWebViewClient webViewClient;
        ZWebChromeClient webChromeClient;
        int barHeight = -1,barColor = -1;
        View errorView;
        String errorUrl;

        public WebBuilder url(String url){
            this.url = url;
            return this;
        }

        public WebBuilder parentView(ViewGroup parentView){
            this.parentView = parentView;
            return this;
        }

        public WebBuilder webViewClient(ZWebViewClient webViewClient){
            this.webViewClient = webViewClient;
            return this;
        }
        public WebBuilder webChromeClient(ZWebChromeClient webChromeClient){
            this.webChromeClient = webChromeClient;
            return this;
        }
        public WebBuilder topIndicator(int barHeight,int barColor){
            this.barColor = barColor;
            this.barHeight = barHeight;
            return this;
        }

        public WebBuilder errorView(View errorView){
            this.errorView = errorView;
            return this;
        }
        public WebBuilder errorUrl(String errorUrl){
            this.errorUrl = errorUrl;
            return this;
        }

        public WebBuilder go(){
            checkNull(this);
            if (!URLUtil.isNetworkUrl(url) && !URLUtil.isAssetUrl(url)){
                url = "file:///android_asset/"+url;
            }
            if (!URLUtil.isNetworkUrl(errorUrl) && !URLUtil.isAssetUrl(errorUrl)){
                errorUrl = "file:///android_asset/"+errorUrl;
            }
            WebRequestManager.getInstance().checkData(this);
            return this;
        }



        public Context getContext() {
            return context;
        }

        public String getUrl() {
            return url;
        }

        public ViewGroup getParentView() {
            return parentView;
        }

        public ZWebViewClient getWebViewClient() {
            return webViewClient;
        }

        public ZWebChromeClient getWebChromeClient() {
            return webChromeClient;
        }

        public int getBarHeight() {
            return barHeight;
        }

        public int getBarColor() {
            return barColor;
        }

        public View getErrorView() {
            return errorView;
        }

        public String getErrorUrl() {
            return errorUrl;
        }
    }

    private static void checkNull(WebBuilder builder) {
        if (TextUtils.isEmpty(builder.url)){
            throw new NullPointerException("url cannot null null");
        }
        if (builder.parentView == null){
            throw new NullPointerException("parentView cannot be null");
        }
    }


}
