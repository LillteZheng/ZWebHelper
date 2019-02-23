package com.zhengsr.zweblib.entrance;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.ist.lifecyclerlib.ZLifeCycle;
import com.ist.lifecyclerlib.listener.LifeListenerAdapter;
import com.zhengsr.zweblib.WebRequest;
import com.zhengsr.zweblib.callback.ZwebLoadListener;
import com.zhengsr.zweblib.view.ProgressView;
import com.zhengsr.zweblib.view.ZwebView;
import com.zhengsr.zweblib.widght.ZWebChromeClient;
import com.zhengsr.zweblib.widght.ZWebViewClient;

import java.lang.ref.WeakReference;

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
    private static class Holder {
       static final WebRequestManager INSTANCE = new WebRequestManager();
    }

    public static WebRequestManager getInstance() {
       return Holder.INSTANCE;
    }

    private WebRequestManager() {
    }

    public void checkData(WebRequest.WebBuilder builder){
        mBuilder = builder;
        mContext = mBuilder.getContext().getApplicationContext();
        getLifeCycle(mContext);

        configData(mBuilder);

    }


    private void configData(WebRequest.WebBuilder builder){

        isOndestory = false;
        isOnPause = false;
        isOnResume = false;
        if (mParentView != null){
            mParentView = null;
        }
        WeakReference<ViewGroup> weakReference = new WeakReference<ViewGroup>(builder.getParentView());
        mParentView = weakReference.get();

        if (mWebView == null) {
            mWebView = new ZwebView(builder.getContext().getApplicationContext());
            //开启硬件加速
            mWebView.setLayerType(View.LAYER_TYPE_HARDWARE,null);
            WebView.setWebContentsDebuggingEnabled(true);
            configWebSettings();
        }else{
            mWebView.removeAllViews();
        }
        mBar = new ProgressView(mContext).setSize(mContext.getResources().getDisplayMetrics().widthPixels,
                builder.getBarHeight() );
        mBar.setColor(builder.getBarColor());
        mBar.setProgress(10);
        mWebView.addView(mBar);
        mParentView.addView(mWebView);

        webClient();
        mWebView.loadUrl(builder.getUrl());
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
        mWebSettings.setUseWideViewPort(true);
        mWebSettings.setLoadsImagesAutomatically(true);
        mWebSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

        //缩放操作
        mWebSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        mWebSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
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
            //mWebView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        //为了加快速度，可以刚开始不加载图片，等结束后再去加载图片
        mWebSettings.setLoadsImagesAutomatically(false);


        //缓存和图片加载，请自行配置
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


    private void getLifeCycle(Context context){
        ZLifeCycle.with(context, new LifeListenerAdapter() {
            @Override
            public void onResume() {
                if (!isOnResume) {
                    WebRequestManager.this.onResume();
                }
                super.onResume();
            }


            @Override
            public void onPause() {
                if (!isOnPause) {
                    WebRequestManager.this.onPause();
                }
                super.onPause();
            }

            @Override
            public void onDestroy() {
                if (!isOndestory) {
                    WebRequestManager.this.onDestroy();
                }
                super.onDestroy();
            }

            @Override
            public void onFail(String errorMsg) {
                super.onFail(errorMsg);
            }
        });
    }

    public void onResume(){
        isOnResume = true;
        getWebView().onResume();
        //for js
        getWebView().resumeTimers();
    }
    public void onPause(){
        isOnPause = true;
        getWebView().onPause();
        //for js
        getWebView().pauseTimers();
    }
    public void onDestroy(){
        isOndestory = true;
        if (mWebView != null) {
            mWebView.setWebViewClient(null);
            mWebView.setWebChromeClient(null);
            mWebView.clearHistory();
            mWebView.removeAllViews();
            mWebView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            mParentView.removeView(mWebView);
            mWebView.destroy();
            mWebView = null;

        }
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
        if (mBuilder != null) {
            if (!TextUtils.isEmpty(mBuilder.getErrorUrl())) {
                mWebView.loadUrl(mBuilder.getErrorUrl());
            }
            if (mBuilder.getErrorView() != null) {
                mParentView.removeAllViews();
                getWebView().loadDataWithBaseURL(null,"","text/html","utf-8",null);
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
        getWebView().loadUrl(action);
    }

    public void evaluateJavascript(String action,ValueCallback<String> callback) {
        mWebView.evaluateJavascript(action,callback);
    }

}
