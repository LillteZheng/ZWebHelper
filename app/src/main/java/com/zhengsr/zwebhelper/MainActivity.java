package com.zhengsr.zwebhelper;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.zhengsr.zweblib.ZWebHelper;
import com.zhengsr.zweblib.widght.ZWebChromeClient;
import com.zhengsr.zweblib.widght.ZWebViewClient;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private  boolean noImage = true;

    @SuppressLint("JavascriptInterface")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final String url = "https://blog.csdn.net/u011418943";
        //final String url = "https://mp.weixin.qq.com/s/nn-nwXnRI9JYSmknH1pzYg";
        FrameLayout frameLayout = findViewById(R.id.content);
        View view = LayoutInflater.from(this).inflate(R.layout.errorview,null);
        ZWebHelper.with(this)
                .url(url)
                .errorView(view)
                .parentView(frameLayout)
                .go();





        WebView webView = ZWebHelper.getWebView();

        webView.addJavascriptInterface(new javaScripeTest(),"action");

        WebSettings settings = ZWebHelper.getWebSettings();
        //是否使用无图
        /*if (noImage){
            settings.setBlockNetworkImage(true);
        }else{
            settings.setBlockNetworkImage(false);
        }*/
        settings.setBlockNetworkImage(false);

        //是否自动缓存
        if (true){
            settings.setAppCacheEnabled(true);
            settings.setDomStorageEnabled(true);
            settings.setDatabaseEnabled(true);
            //有网络
            if (true) {
                settings.setCacheMode(WebSettings.LOAD_DEFAULT);
            }else{
                settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
            }
        }else{
            settings.setAppCacheEnabled(false);
            settings.setDomStorageEnabled(false);
            settings.setDatabaseEnabled(false);
            settings.setCacheMode(WebSettings.LOAD_NO_CACHE);

        }


    }
    ZWebViewClient zWebViewClient = new ZWebViewClient(){
        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
            return super.shouldInterceptRequest(view, request);
        }
    };

    ZWebChromeClient zWebChromeClient = new ZWebChromeClient(){
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
        }
    };

    class javaScripeTest {
        @JavascriptInterface
        public void toastMsg(String msg){
            Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
        }
        @JavascriptInterface
        public void toastMsg2(String msg){
            Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
        }
    }



    public void test(View view) {
        ZWebHelper.sendActionJs("sum(2,3)");
        ZWebHelper.evaluateJavascript("sum(3,4)", new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String value) {

            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return ZWebHelper.canGoBack(keyCode) || super.onKeyDown(keyCode, event);
    }
}
