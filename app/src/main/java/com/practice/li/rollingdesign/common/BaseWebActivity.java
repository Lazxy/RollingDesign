package com.practice.li.rollingdesign.common;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.practice.li.rollingdesign.R;

/**
 * Created by Lazxy on 2017/3/8.
 * WebView 基类
 */

public class BaseWebActivity extends BaseActivity {

    protected Toolbar mToolbar;
    protected ProgressBar mProgressWeb;
    protected WebView mWebView;
    protected String mWebTitle;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_base_web);
    }

    @Override
    public void initView() {
        mWebView = (WebView) findViewById(R.id.web_view);
        mProgressWeb = (ProgressBar) findViewById(R.id.progress_web);
        mToolbar = (Toolbar) findViewById(R.id.toolbar_web);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.iv_close_white_24dp);//设置Toolbar返回图标
        mWebTitle = getIntent().getExtras().getString(CommonConstants.Login.EXTRA_WEB_TITLE);
        if (!TextUtils.isEmpty(mWebTitle))
            mToolbar.setTitle(mWebTitle);
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true); //启用JS效果
        settings.setJavaScriptCanOpenWindowsAutomatically(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)//4.3以下不可用，会无法正常显示网页
            settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);

        mWebView.setWebChromeClient(getChromeClient());
        mWebView.setWebViewClient(getWebViewClient());
        mWebView.loadUrl(getIntent().getExtras().getString(CommonConstants.Login.EXTRA_WEB_LOAD_URL));
    }

    @Override
    public void initListener() {
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    protected WebChromeClient getChromeClient() {
        return new MyWebChromeClient();
    }

    protected WebViewClient getWebViewClient() {
        return new MyWebViewClient();
    }

    /**
     * 自定义ChromeClient，增加进度条效果和网页跳转标题改动
     */
    protected class MyWebChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress == 100) {
                mProgressWeb.setVisibility(View.GONE);
            } else {
                if (mProgressWeb.getVisibility() == View.GONE) {
                    mProgressWeb.setVisibility(View.VISIBLE);
                }
                mProgressWeb.setProgress(newProgress);
            }
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            if (TextUtils.isEmpty(mWebTitle)) {
                mToolbar.setTitle(title);
            }
        }


    }

    /**
     * 自定义WebViewClient 添加网页跳转监听和代理
     */
    protected class MyWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        public void onPageFinished(WebView view, String url) {
            mToolbar.setTitle(view.getTitle());
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            if (errorCode == ERROR_TIMEOUT) {
                showShortToast("页面加载超时，尝试重新加载，请检查是否存在网络问题");
                view.stopLoading();
                view.loadUrl(failingUrl);
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
