package com.zhengsr.zweblib;

import android.content.Context;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.zhengsr.zweblib.entrance.WebRequestManager;

/**
 * Created by zhengshaorui
 * Time on 2019/2/21
 */

public class ZWebHelper {
    public static WebRequest.WebBuilder with(Context context){
        return WebRequest.create().getBuilder(context);
    }

    public static void onPause(){

    }

    public static void onDestroy(){

    }
    public static void onResume(){

    }

    public static boolean canGoBack(int keyCode){
        return WebRequestManager.getInstance().canGoBack(keyCode);
    }

    public static WebView getWebView(){
        return WebRequestManager.getInstance().getWebView();
    }

    public static WebSettings getWebSettings(){
        return WebRequestManager.getInstance().getWebSettings();
    }

    public static void test(String action){
        WebRequestManager.getInstance().sendActionJs(action);
    }

    public static void evaluateJavascript(String action,ValueCallback<String> callback){
        WebRequestManager.getInstance().evaluateJavascript(action,callback);
    }



}
