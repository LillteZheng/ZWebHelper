# ZWebHelper
一个封装的Webview，暂时供自己用，功能完善再公布出来

##基本使用
```
ZWebHelper.with(this)
        .url(url)
        .parentView(frameLayout)
        .topIndicator(4, Color.parseColor("#3F51B5"))
        .webViewClient(zWebViewClient)
        .webChromeClient(zWebChromeClient)
        .go();
```
client 的配置
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

