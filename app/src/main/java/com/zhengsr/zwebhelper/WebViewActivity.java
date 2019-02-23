package com.zhengsr.zwebhelper;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
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
        View view = LayoutInflater.from(this).inflate(R.layout.errorview,null);
       /* ZWebHelper.with(this)
                .url(url)
              //  .errorView(view)
                .parentView(frameLayout)
                .go();*/
       TestManager.getInstance().setView(view);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return ZWebHelper.canGoBack(keyCode) || super.onKeyDown(keyCode, event);
    }
}
