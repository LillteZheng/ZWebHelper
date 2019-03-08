package com.zhengsr.zwebhelper;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private  boolean noImage = true;

    @SuppressLint("JavascriptInterface")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Locale locale = Locale.getDefault();
        Log.d(TAG, "zsr --> onCreate: "+locale.getLanguage()+" / "+locale.getDisplayLanguage()+" / "
        +locale.getDisplayName()+" / "+locale.getCountry());

    }




    public void test(View view) {
        startActivity(new Intent(this,WebViewActivity.class));

    }


}
