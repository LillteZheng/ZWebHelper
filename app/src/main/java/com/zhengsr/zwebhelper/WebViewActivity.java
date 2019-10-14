package com.zhengsr.zwebhelper;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.FrameLayout;

import com.zhengsr.zweblib.ZWebHelper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

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

        String testUrl = "<!DOCTYPE html> \n" +
                "<html> \n" +
                "<head> \n" +
                "<meta charset=\"utf-8\" /> \n" +
                "<title>图片插入html 在线演示 www.divcss5.com</title> \n" +
                "</head> \n" +
                " \n" +
                "<body> \n" +
                "<p>原始大图片</p> \n" +
                "<p> \n" +
                "<img src=\"https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2116167324,1582233854&fm=11&gp=0.jpg\" /> \n" +
                "</p> \n" +
                "<p>改小图片</p> \n" +
                "<p> \n" +
                "<img src=\"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1547551786322&di=f60835f88cbb21a117349cabd06ca6c6&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F0122e4578f37050000018c1bff84ce.jpg%401280w_1l_2o_100sh.jpg\"/> \n" +
                "</p> \n" +
                "<p>答：1、三者的定义不同： 艺人，自古以来，泛指有才艺、有才艺者，也用于身份自称，作为职业，它与文人有一个规范的叫法，即“文化艺术工作者”（文艺工作者）。 演员，指专职演出，或在表演艺术中扮演某个角色的人物。演员也是指参加戏曲、戏剧、电视</p>\n" +
                "<p>改大图片</p> \n" +
                "<p> \n" +
                "<img src=\"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1547551786321&di=949ecb5be73a0a7a37b6da9878177a6b&imgtype=0&src=http%3A%2F%2Fc.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2F1ad5ad6eddc451da1b8e0aabbdfd5266d0163271.jpg\"/> \n" +
                "</p> \n" +
                "<p>答：1、三者的定义不同： 艺人，自古以来，泛指有才艺、有才艺者，也用于身份自称，作为职业，它与文人有一个规范的叫法，即“文化艺术工作者”（文艺工作者）。 演员，指专职演出，或在表演艺术中扮演某个角色的人物。演员也是指参加戏曲、戏剧、电视</p><p>答：1、三者的定义不同： 艺人，自古以来，泛指有才艺、有才艺者，也用于身份自称，作为职业，它与文人有一个规范的叫法，即“文化艺术工作者”（文艺工作者）。 演员，指专职演出，或在表演艺术中扮演某个角色的人物。演员也是指参加戏曲、戏剧、电视</p><p>答：1、三者的定义不同： 艺人，自古以来，泛指有才艺、有才艺者，也用于身份自称，作为职业，它与文人有一个规范的叫法，即“文化艺术工作者”（文艺工作者）。 演员，指专职演出，或在表演艺术中扮演某个角色的人物。演员也是指参加戏曲、戏剧、电视</p><p>答：1、三者的定义不同： 艺人，自古以来，泛指有才艺、有才艺者，也用于身份自称，作为职业，它与文人有一个规范的叫法，即“文化艺术工作者”（文艺工作者）。 演员，指专职演出，或在表演艺术中扮演某个角色的人物。演员也是指参加戏曲、戏剧、电视</p><p>答：1、三者的定义不同： 艺人，自古以来，泛指有才艺、有才艺者，也用于身份自称，作为职业，它与文人有一个规范的叫法，即“文化艺术工作者”（文艺工作者）。 演员，指专职演出，或在表演艺术中扮演某个角色的人物。演员也是指参加戏曲、戏剧、电视</p>\n" +
                "</body> \n" +
                "</html>\n" ;


        String dataUrl = "    <html lang=\"zh-CN\">\n" +
                "    <head>\n" +
                "        <meta charset=\"utf-8\">\n" +
                "        <!-- 防百度转码 -->\n" +
                "        <meta name=\"applicable-device\" content=\"pc,mobile\">\n" +
                "        <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                "        <meta name=\"renderer\" content=\"webkit\">\n" +
                "        <meta http-equiv=\"Cache-Control\" content=\"no-siteapp\"/>\n" +
                "        <!-- CSRF Token -->\n" +
                "        <meta name=\"csrf-token\" content=\"fdYF4jO9iI0fpj3hB9m5jmIDpFr0qhPZNEQ03pkt\">\n" +
                "        <meta name=\"keywords\" content=\"亿方云企业网盘登录，亿方云登录页面，登录亿方云，企业级网盘，企业网盘，企业云盘，网盘，云盘，共享网盘，共享云盘，亿方云\">\n" +
                "        <meta name=\"description\" content=\"亿方云为小型团队、中小型、中大型乃至大型跨国企业提供企业网盘、企业云盘、企业文件管理、移动办公、云端协同办公服务，通过登录，用户可享受安全便捷的企业网盘、企业云盘、文件访问、共享和协作的高效办公服务。\">\n" +
                "        <title> 登录亿方云_企业共享网盘 - 亿方云</title>\n" +
                "            <link rel=\"shortcut icon\" href=\"https://account.fangcloud.com/favicon.ico\" type=\"image/x-icon\" />\n" +
                "            <!-- dns prefetch -->\n" +
                "        <link rel=\"preconnect\" href=\"https://account.fangcloud.com/\">\n" +
                "        <link rel=\"dns-prefetch\" href=\"https://account.fangcloud.com/\">\n" +
                "        <script>\n" +
                "            if ((function (ua) {\n" +
                "                var browser = {},\n" +
                "                    ie = ua.match(/MSIE\\s([\\d.]+)/) || ua.match(/Trident\\/[\\d](?=[^\\?]+).*rv:([0-9.].)/);\n" +
                "                if (ie) browser.ie = true, browser.version = Number(ie[1]);\n" +
                "                return browser.ie && (browser.version < 10);\n" +
                "            })(navigator.userAgent)) { document.location.href = \"https://v2.fangcloud.com/apps/files/incompatible\"; }\n" +
                "        </script>\n" +
                "            <!-- Styles -->\n" +
                "            <link href=\"https://account.fangcloud.com/auth/dist/css/web_e541739.css\" rel=\"stylesheet\">\n" +
                "        \n" +
                "    </head>\n" +
                "    <body class=\"i18n-zh-CN\">\n" +
                "        \n" +
                "        <div class=\"wrapper open-wrapper\">\n" +
                "                    <div class=\"header open-header\">\n" +
                "        <div class=\"container\">\n" +
                "            <a href=\"https://v2.fangcloud.com/\" class=\"logo\">\n" +
                "                        <span class=\"default-logo\"></span>\n" +
                "                                </a>\n" +
                "                    <div class=\"account-box\">\n" +
                "                <div class=\"language-switch dropdown\">\n" +
                "                    <span>\n" +
                "                     <i class=\"iconfont icon-language\"></i>\n" +
                "                        <span>简体中文</span>\n" +
                "                        <i class=\"iconfont icon-arrow\"></i>\n" +
                "                    </span>\n" +
                "                    <ul class=\"language-switch-dropdown\">\n" +
                "                        <li data-by=\"zh-CN\">简体中文</li>\n" +
                "                        <li data-by=\"zh-TW\">繁體中文</li>\n" +
                "                        <li data-by=\"en\">English</li>\n" +
                "                    </ul>\n" +
                "                </div>\n" +
                "                                    <a class=\"btn btn-default btn-register\" href=\"https://account.fangcloud.com/register\">注册</a>\n" +
                "                            <a class=\"btn btn-default btn-login\" href=\"https://v2.fangcloud.com/sso/login\">登录</a>\n" +
                "                    </div>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "        <div class=\"login-box\">\n" +
                "        <div class=\"switch-box\">\n" +
                "        <a class=\"tab active\" href=\"#form\" data-view=\"form\">密码登录</a>\n" +
                "        <a class=\"tab action-icon\" href=\"#qrcode-login\" data-view=\"qrcode-login\">扫码登录</a>\n" +
                "    </div>\n" +
                "    <p class=\"oauth-des\">登录以授权<a href=\"http://www.hitevision.com/\" target=\"_blank\">艾上云</a>访问你的亿方云��号</p>\n" +
                "    <div class=\"qrcode-login\">\n" +
                "        <div class=\"qrcode-container\">\n" +
                "            <div class=\"qr-code\">\n" +
                "                <img data-role=\"qrcode\" src=\"\" alt=\"\">\n" +
                "            </div>\n" +
                "            <p class=\"qr-header-hint\">\n" +
                "                <span class=\"scan-tip\">\n" +
                "                                    打开 <a target=\"_blank\" href=\"https://www.fangcloud.com/home/download\">亿方云V2 APP</a>(V3.5.0及以上版本)<br>\n" +
                "                                    扫描二维码登录\n" +
                "                    <i class=\"iconfont icon-retry action-icon\"></i>\n" +
                "                </span>\n" +
                "                <span class=\"qr-expire-tip\">\n" +
                "                    二维码已失效，点击刷新\n" +
                "            </span>\n" +
                "            </p>\n" +
                "        </div>\n" +
                "        <div class=\"scan-success-container\">\n" +
                "            <div class=\"scan-success\"></div>\n" +
                "            <p class=\"success-title\">扫描成功</p>\n" +
                "            <p class=\"success-message\">请在手机上点击“确认登录”</p>\n" +
                "            <a href=\"#\" class=\"btn btn-default return-scan\">返回二维码</a>\n" +
                "        </div>\n" +
                "        <div class=\"login-expire-container\">\n" +
                "            <div class=\"login-expire\"></div>\n" +
                "            <p class=\"expire-title\">登录已失效</p>\n" +
                "            <a href=\"#\" class=\"btn btn-default refresh-scan\">刷新二维码</a>\n" +
                "        </div>\n" +
                "    </div>    <form action=\"\" class=\"form web-form\" onSubmit=\"return false;\">\n" +
                "        <input type=\"hidden\" name=\"_token\" value=\"fdYF4jO9iI0fpj3hB9m5jmIDpFr0qhPZNEQ03pkt\">\n" +
                "            <input type=\"hidden\" name=\"_fstate\" value=\"KJAHBJGHICFL\">\n" +
                "            <div class=\"form-group login\">\n" +
                "        <div class=\"input-group\">\n" +
                "            <input type=\"text\" class=\"username text form-control\" id=\"login\" name=\"login\" placeholder=\"请输入手机号/邮箱\">\n" +
                "    </div>\n" +
                "    </div>\n" +
                "    <div class=\"form-group passowrd-group\">\n" +
                "        <div class=\"input-group\">\n" +
                "            <input type=\"password\" name=\"password\" class=\"password text form-control\" placeholder=\"请输入密码\" id=\"password\"  autocomplete=\"off\">\n" +
                "        </div>\n" +
                "    </div>        <div class=\"form-group remember-login\">\n" +
                "            <label for=\"remember_login\" class=\"remember-login\">\n" +
                "            <i class=\"checkbox-inner\"></i>\n" +
                "            <input type=\"checkbox\" name=\"remember_login\" id=\"remember_login\"> \n" +
                "            记住我\n" +
                "        </label>\n" +
                "            <a class=\"form-right forgot-pwd action-icon\" href=\"https://account.fangcloud.com/password/forgot?_fstate=KJAHBJGHICFL\">忘记密码</a>\n" +
                "    </div>        <div class=\"form-group action-group\">\n" +
                "                <button class=\"btn btn-primary\" type=\"submit\">登录</button>\n" +
                "            </div>\n" +
                "                                <div class=\"form-footer\">\n" +
                "        <div class=\"form-group inline open-none\">\n" +
                "         <div class=\"label\">其它方式登录：</div>\n" +
                "            <div class=\"form-control\">\n" +
                "                <a class=\"action-icon\" href=\"https://account.fangcloud.com/international_login?_fstate=KJAHBJGHICFL\">国际手机号</a>\n" +
                "                        </div>\n" +
                "        </div>\n" +
                "            </div>\n" +
                "                </form>\n" +
                "        \n" +
                "            <input type=\"hidden\" id=\"oauth_login\" value=\"1\">\n" +
                "        \n" +
                "                <div class=\"oauth-tip\">为保障帐号安全，请认准本页URL地址是以www.fangcloud.com/开头，若不符请勿登录。如有疑问请联系亿方云客服。</div>\n" +
                "        </div>\n" +
                "    <!-- <div class=\"oauth-tip\">为保障帐号安全，请认准本页URL地址是以www.fangcloud.com/开头，若不符请勿登录。如有疑问请联系亿方云客服。</div>\n" +
                "     -->\n" +
                "        </div>\n" +
                "        <input type=\"hidden\" id=\"asset_url\" value=\"auth/dist/\">\n" +
                "        <input type=\"hidden\" id=\"product_name\" value=\"亿方云\">\n" +
                "        <input type=\"hidden\" id=\"Language\" value=\"zh-CN\">\n" +
                "        <input type=\"hidden\" id=\"api_url\" value=\"https://v2.fangcloud.com/\">\n" +
                "        <script type=\"text/javascript\" src=\"https://account.fangcloud.com/auth/dist/js/vendor_8eef9a5.js\"></script>\n" +
                "            <script type=\"text/javascript\" src=\"https://account.fangcloud.com/auth/dist/js/login_c1c0351.js\"></script>\n" +
                "        \n" +
                "        <script>\n" +
                "    var _hmt = _hmt || [];\n" +
                "    (function() {\n" +
                "    var hm = document.createElement(\"script\");\n" +
                "    hm.src = \"https://hm.baidu.com/hm.js?762d2bc251bef4b42a758268dc7edda3\";\n" +
                "    var s = document.getElementsByTagName(\"script\")[0]; \n" +
                "    s.parentNode.insertBefore(hm, s);\n" +
                "    })();\n" +
                "    </script>\n" +
                "    <script type=\"text/javascript\">\n" +
                "     !function(e,t,n,g,i){e[i]=e[i]||function(){(e[i].q=e[i].q||[]).push(arguments)},n=t.createElement(\"script\"),tag=t.getElementsByTagName(\"script\")[0],n.async=1,n.src=(\"https:\"==document.location.protocol?\"https://\":\"http://\")+g,tag.parentNode.insertBefore(n,tag)}(window,document,\"script\",\"assets.growingio.com/2.1/gio.js\",\"gio\");\n" +
                "        gio(\"init\", \"8a762667df5cb9d5\", {});\n" +
                "        gio(\"send\");\n" +
                "    </script>\n" +
                " </body>\n" +
                "    </html>";

        /*WebView webView  =new WebView(this);
        frameLayout.addView(webView);
        webView.loadDataWithBaseURL(null,dataUrl,"text/html","utf-8",null);*/
        Document doc = Jsoup.parse(dataUrl);

        ZWebHelper.with(this)
                //.url(url)
                //.loadData(data)
                .loadDataWithBaseURL(null,doc.toString())
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
