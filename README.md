# ZWebHelper
一个封装的Webview，功能基本完善

关联：
```
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
```
implementation 'com.github.LillteZheng:ZWebHelper:v1.8.2'
```

## 基本使用
```
ZWebHelper.with(this)
        .url(url)
        //.loadData(data)
       // .loadDataWithBaseURL(null,dataUrl)
        .errorView(view)
        .useCache(true) //当使用cache 时，自己写的errorview不执行，因为会干扰 cache
        .parentView(frameLayout)
        .autoLoadImage(true)
        .go();
```
其中，自己配置了一些 WebSetting 的配置，如下：
```
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


//缓存和是否加载图片，请自行配置
```

## client 的配置
client 处理了一些常用的连接，以及一些错误回调
```
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
```

## Android 与 js 沟通
```
ZWebHelper.sendActionJs("sum(2,3)");
```

## js 返回值给Android
```
ZWebHelper.evaluateJavascript("sum(3,4)", new ValueCallback<String>() {
    @Override
    public void onReceiveValue(String value) {

    }
});
```

## 返回键
```
ZWebHelper.canGoBack(keyCode)
```

