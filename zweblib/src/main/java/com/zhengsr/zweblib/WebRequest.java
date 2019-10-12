package com.zhengsr.zweblib;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;

import com.zhengsr.zweblib.bean.LoadBaseBean;
import com.zhengsr.zweblib.bean.LoadDataBean;
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
        LoadDataBean loadBean;
        LoadBaseBean loadBaseBean;
        private boolean isAutoLoadImage = true;

        public WebBuilder url(String url){
            this.url = url;
            if (!URLUtil.isNetworkUrl(url) && !URLUtil.isAssetUrl(url)){
                this.url = "file:///android_asset/"+url;
            }

            return this;
        }
        public WebBuilder loadData(String data,String mimeType,String encoding){
            loadBean = new LoadDataBean();
            loadBean.data = data;
            loadBean.mineType = mimeType;
            loadBean.encoding = encoding;
            return this;
        }
        public WebBuilder loadData(String data){
            loadBean = new LoadDataBean();
            loadBean.data = data;
            return this;
        }
        public WebBuilder loadDataWithBaseURL(String baseUrl,String data,
                                              String mineType,String encoding,String historyUrl){
            loadBaseBean = new LoadBaseBean();
            loadBaseBean.baseUrl = baseUrl;
            loadBaseBean.data = data;
            loadBaseBean.mineType = mineType;
            loadBaseBean.encoding = encoding;
            loadBaseBean.historyUrl = historyUrl;
            return this;
        }

        public WebBuilder loadDataWithBaseURL(String baseUrl,String data){
            loadBaseBean = new LoadBaseBean();
            loadBaseBean.baseUrl = baseUrl;
            loadBaseBean.data = data;
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
            if (!URLUtil.isNetworkUrl(errorUrl) && !URLUtil.isAssetUrl(errorUrl)){
                this.errorUrl = "file:///android_asset/"+errorUrl;
            }
            return this;
        }
        public WebBuilder autoLoadImage(boolean isAutoLoadImage){
            this.isAutoLoadImage = isAutoLoadImage;
            return this;
        }

        public WebBuilder go(){
            checkNull(this);

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

        public boolean isAutoLoadImage() {
            return isAutoLoadImage;
        }

        public LoadDataBean getLoadBean() {
            return loadBean;
        }

        public LoadBaseBean getLoadBaseBean() {
            return loadBaseBean;
        }
    }

    private static void checkNull(WebBuilder builder) {

        if (builder.parentView == null){
            throw new NullPointerException("parentView cannot be null");
        }
    }


}
