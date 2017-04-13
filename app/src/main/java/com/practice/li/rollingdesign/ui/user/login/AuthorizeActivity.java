package com.practice.li.rollingdesign.ui.user.login;

import android.content.Intent;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.practice.li.rollingdesign.api.ApiConstants;
import com.practice.li.rollingdesign.common.BaseWebActivity;
import com.practice.li.rollingdesign.common.CommonConstants;

/**
 * Created by Lazxy on 2017/3/8.
 * 登陆界面类
 */

public class AuthorizeActivity extends BaseWebActivity{

    @Override
    protected WebViewClient getWebViewClient() {
        return new MyWebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.contains("code")) {
                    //将认证后返回Url中的认证信息截取出来并返回
                    String code = url.replace(ApiConstants.Url.REDIRECT_URL + "?code=", "")
                            .replace("&state="+ApiConstants.ParamValue.STATE, "");
                    Intent intent = new Intent();
                    intent.putExtra(CommonConstants.Login.EXTRA_AUTHORIZE_CODE, code);
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    view.loadUrl(url);
                }
                return true;
            }
        };
    }
}
