package com.zhengsr.zwebhelper;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.zhengsr.zweblib.ZWebHelper;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private  boolean noImage = true;

    @SuppressLint("JavascriptInterface")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final String url = "https://blog.csdn.net/u011418943";
        FrameLayout frameLayout = findViewById(R.id.content);
        ZWebHelper.with(this)
                //.url("test.html")
                .url(url)
                .parentView(frameLayout)
                .topIndicator(4, Color.parseColor("#3F51B5"))
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
        ZWebHelper.test("sum(2,3)");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return ZWebHelper.canGoBack(keyCode) || super.onKeyDown(keyCode, event);
    }
}
