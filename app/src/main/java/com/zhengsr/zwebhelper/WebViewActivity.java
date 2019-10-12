package com.zhengsr.zwebhelper;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.FrameLayout;

import com.zhengsr.zweblib.ZWebHelper;

public class WebViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        FrameLayout frameLayout = findViewById(R.id.content_web);
         final String url = "https://blog.csdn.net/u011418943";
      //  final String url = "https://mp.weixin.qq.com/s/nn-nwXnRI9JYSmknH1pzYg";
      //  String url = "test.html";
        String summary = "<html><body>You scored <b>192</b> 测试.</body></html>";
        View view = LayoutInflater.from(this).inflate(R.layout.errorview,null);

        String baseURL = "https://wanandroid.com/";
        String data = "相对地址:" +
                "<img src='blogimgs/50c115c2-cf6c-4802-aa7b-a4334de444cd.png'>" +"\n"+
                "本地地址:" +
                "<img src='file:///android_asset/test.jpg'>" +"\n"+
                "绝对地址:" +
                "<img src='https://wanandroid.com/blogimgs/67c28e8c-2716-4b78-95d3-22cbde65d924.jpeg'>";



       // frameLayout.addView(webView);
        //webView.loadDataWithBaseURL(baseURL,data,"text/html","utf-8",null);


        ZWebHelper.with(this)
                //.url(url)
                //.loadData(data)
                .loadDataWithBaseURL(baseURL,data)
                .errorView(view)
                .parentView(frameLayout)
                .autoLoadImage(true)
                .go();


    }


    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return ZWebHelper.canGoBack(keyCode) || super.onKeyDown(keyCode, event);
    }




}
