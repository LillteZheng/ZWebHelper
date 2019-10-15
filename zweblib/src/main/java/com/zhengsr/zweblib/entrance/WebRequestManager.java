package com.zhengsr.zweblib.entrance;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.zhengsr.zweblib.WebRequest;
import com.zhengsr.zweblib.bean.LoadBaseBean;
import com.zhengsr.zweblib.bean.LoadDataBean;
import com.zhengsr.zweblib.callback.ZwebLoadListener;
import com.zhengsr.zweblib.view.ProgressView;
import com.zhengsr.zweblib.view.ZwebView;
import com.zhengsr.zweblib.widght.ZWebChromeClient;
import com.zhengsr.zweblib.widght.ZWebViewClient;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by zhengshaorui
 * Time on 2019/2/21
 */

public class WebRequestManager implements ZwebLoadListener {
    private static final String TAG = "WebRequestManager";
    private ZwebView mWebView;
    private WebRequest.WebBuilder mBuilder;
    private ProgressView mBar;
    private  Context mContext;
    private WebSettings mWebSettings;
    private boolean isOnResume,isOnPause,isOndestory;
    private boolean isErrorLoad;
    private ViewGroup mParentView;
    private HashMap<String,WeakReference<Object>> mWeakMap = new LinkedHashMap<>(
            new HashMap<String, WeakReference<Object>>());

    private static class Holder {
       static  WebRequestManager INSTANCE = new WebRequestManager();
    }

    public static WebRequestManager getInstance() {
       return Holder.INSTANCE;
    }

    private WebRequestManager() {
    }


    public Context getContext(){
        return mContext;
    }

    

    public void checkData(WebRequest.WebBuilder builder){
        mBuilder = builder;
        mWeakMap.put(RouteKey.PARENTVIEW.name(),new WeakReference<Object>(builder.getParentView()));
        mWeakMap.put(RouteKey.CONTEXT.name(),new WeakReference<Object>(builder.getContext()));
        if (builder.getErrorView() != null) {
            mWeakMap.put(RouteKey.ERRORView.name(), new WeakReference<Object>(builder.getErrorView()));
        }

        if (mParentView != null){
            mParentView = null;
        }
        if (mWebView != null){
            mWebView.removeAllViews();
            mWebView = null;
        }

        mContext = (Context) mWeakMap.get(RouteKey.CONTEXT.name()).get();
        mParentView = (ViewGroup) mWeakMap.get(RouteKey.PARENTVIEW.name()).get();


        configData(mBuilder);

    }


    private void configData(WebRequest.WebBuilder builder){

        isOndestory = false;
        isOnPause = false;
        isOnResume = false;

        if (mWebView == null) {
            mWeakMap.put(RouteKey.WEBVIEW.name(),new WeakReference<Object>(
                    new ZwebView(builder.getContext())));
        }
        mWebView = (ZwebView) mWeakMap.get(RouteKey.WEBVIEW.name()).get();
        //开启硬件加速
        mWebView.setLayerType(View.LAYER_TYPE_HARDWARE,null);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }
        configWebSettings();
        mBar = new ProgressView(mContext).setSize(mContext.getResources().getDisplayMetrics().widthPixels,
                builder.getBarHeight() );
        mBar.setColor(builder.getBarColor());
        mBar.setProgress(10);
        mWebView.addView(mBar);
        mParentView.addView(mWebView);

        webClient();
        if (!TextUtils.isEmpty(builder.getUrl())) {
            mWebView.loadUrl(builder.getUrl());
        }else if (builder.getLoadBean() != null){
            LoadDataBean bean = builder.getLoadBean();
            mWebView.loadData(bean.data,bean.mineType,bean.encoding);
        }else if (builder.getLoadBaseBean() != null){
            LoadBaseBean bean = builder.getLoadBaseBean();
            mWebView.loadDataWithBaseURL(bean.baseUrl,bean.data,bean.mineType,bean.encoding,bean.historyUrl);
        }
    }
    @SuppressLint("SetJavaScriptEnabled")
    private void configWebSettings(){
        mWebSettings = mWebView.getSettings();
        mWebSettings.setDefaultTextEncodingName("utf-8");

        //支持 js
        mWebSettings.setJavaScriptEnabled(true);
        //支持 js 打开新窗口
        mWebSettings.setJavaScriptCanOpenWindowsAutomatically(true);

        //屏幕自适应
       // mWebSettings.setUseWideViewPort(true);
        mWebSettings.setLoadWithOverviewMode(true);
        mWebSettings.setDomStorageEnabled(true);
        mWebSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
      //  mWebSettings.setTextZoom(100);

        //启动数据库
        mWebSettings.setDatabaseEnabled(true);
        //数据库定位
        String dir = mContext.getApplicationContext().getDir("database",Context.MODE_PRIVATE).getPath();
        mWebSettings.setGeolocationDatabasePath(dir);


        //缩放操作
        mWebSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        mWebSettings.setBuiltInZoomControls(false); //设置内置的缩放控件。若为false，则该WebView不可缩放
        mWebSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件

        //文件权限
        mWebSettings.setAllowFileAccess(true);
        mWebSettings.setAllowFileAccessFromFileURLs(true);
        mWebSettings.setAllowUniversalAccessFromFileURLs(true);
        mWebSettings.setAllowContentAccess(true);

        //GPRS ,LBS
        mWebSettings.setGeolocationEnabled(true);
        mWebSettings.setGeolocationDatabasePath("");
        //防止图片加载不出 http 开头的图片
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //两者都可以
            mWebSettings.setMixedContentMode(mWebSettings.getMixedContentMode());
        }

        if (!mBuilder.isAutoLoadImage()){
            //为了加快速度，可以刚开始不加载图片，等结束后再去加载图片
            mWebSettings.setLoadsImagesAutomatically(false);
        }

        if (mBuilder.isUseCache()) {
            //缓存和图片加载，请自行配置
            mWebSettings.setAppCacheEnabled(true);
            mWebSettings.setDatabaseEnabled(true);
            mWebSettings.setDatabaseEnabled(true);
            if (isNetworkConnected()) {
                mWebSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
            } else {
                mWebSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
            }
        }
    }


    private void webClient(){
        ZWebViewClient client = mBuilder.getWebViewClient();
        if (client == null){
           client = new ZWebViewClient();
        }
        ZWebChromeClient chromeClient = mBuilder.getWebChromeClient();
        if (chromeClient == null){
            chromeClient = new ZWebChromeClient();
        }

        mWebView.setWebViewClient(client);
        mWebView.setWebChromeClient(chromeClient);
        client.setListener(this);
        chromeClient.setListener(this);
        

    }

    public WebView getWebView(){
        if (mWebView == null){
            throw new NullPointerException("webview is null ");
        }
        return mWebView;
    }

    public WebSettings getWebSettings(){
        if (mWebSettings == null){
            throw new NullPointerException("webview is null ");
        }
        return mWebSettings;
    }



    public void onResume(){
        isOnResume = true;
        if (mWebView != null) {
            mWebView.onResume();
            //for js
            mWebView.resumeTimers();

        }
    }
    public void onPause(){
        isOnPause = true;
        if (mWebView != null) {
            mWebView.onPause();
            //for js
            mWebView.pauseTimers();

        }
    }
    public void onDestroy(){
        isOndestory = true;
        if (mWebView != null) {
            mWebView.setWebViewClient(null);
            mWebView.setWebChromeClient(null);
            mWebView.clearHistory();
            mWebView.removeAllViews();
            mWebView.destroy();
            mWebView = null;
        }
        mParentView.removeAllViewsInLayout();
        mParentView.removeView(mWebView);
        mParentView = null;
        mContext = null;
        mWebView = null;
        mBar = null;
        mBuilder = null;
        mWebSettings = null;
    }

    @Override
    public void onPageStart() {
        if (mBar != null) {
            mBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onPageProgress(int progress) {
        if (mBar != null) {
            mBar.setProgress(progress);
        }
    }

    @Override
    public void onPageFinish() {
        if (mBar != null) {
            mBar.setVisibility(View.GONE);
        }
        if (!getWebSettings().getLoadsImagesAutomatically()){
            getWebSettings().setLoadsImagesAutomatically(true);
        }
    }



    @Override
    public void onReceivedError(String errorUrl, String errorMsg) {
        isErrorLoad = true;
        Log.d(TAG, "zsr --> onReceivedError: "+errorMsg);
        if (mBuilder != null && !mBuilder.isUseCache()) {
            if (!TextUtils.isEmpty(mBuilder.getErrorUrl())) {
                mWebView.loadUrl(mBuilder.getErrorUrl());
            }
            if (mBuilder.getErrorView() != null) {
                mParentView.removeAllViews();
                if (mWebView != null) {
                    //去除以前的加载页
                    mWebView.loadDataWithBaseURL(null,"","text/html","utf-8",null);
                }
                mParentView.addView(mBuilder.getErrorView());
            }
        }
    }

    public boolean canGoBack(int keyCode){
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mWebView != null) {
                if (mWebView.canGoBack()) {
                    mWebView.goBack();
                    return true;
                }
            }
        }
        return false;
    }


    public  void sendActionJs(String action){
        if (!action.contains("javascript")){
            action = "javascript:"+action;
        }
        if (mWebView != null) {
           mWebView.loadUrl(action);
        }
    }

    public void evaluateJavascript(String action,ValueCallback<String> callback) {
        mWebView.evaluateJavascript(action,callback);
    }


    private   boolean isNetworkConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        return connectivityManager.getActiveNetworkInfo() != null;
    }
}
