package com.practice.li.rollingdesign.ui.user.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.practice.li.rollingdesign.R;
import com.practice.li.rollingdesign.common.CommonConstants;
import com.practice.li.rollingdesign.mvpframe.base.BaseFrameActivity;
import com.practice.li.rollingdesign.ui.main.MainActivity;
import com.practice.li.rollingdesign.utils.UrlUtils;


import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Lazxy on 2017/3/8.
 * 登陆代理类
 */

public class LoginActivity extends BaseFrameActivity<LoginPresenter, LoginModel> implements LoginContact.View,
        View.OnClickListener {

    @BindView(R.id.layout_login)
    ViewGroup loginLayout;
    @BindView(R.id.login_button)
    Button loginBtn;
    @BindView(R.id.close)
    ImageButton closeBtn;
    @BindView(R.id.login_prompt)
    TextView prompt;
    @BindView(R.id.progress_login)
    ProgressBar mProgress;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @Override
    public void initListener() {
        loginBtn.setOnClickListener(this);
        closeBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.login_button:
                Bundle bundle = new Bundle();
                bundle.putString(CommonConstants.Login.EXTRA_WEB_LOAD_URL, UrlUtils.getAuthorizeUrl());
                Intent intent = new Intent(this, AuthorizeActivity.class);
                intent.putExtras(bundle);
                startActivityForResult(intent, CommonConstants.Login.REQUEST_CODE_AUTHORIZE);
                break;
            case R.id.close:
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == CommonConstants.Login.REQUEST_CODE_AUTHORIZE) {
            String code = data.getStringExtra(CommonConstants.Login.EXTRA_AUTHORIZE_CODE);
            mPresenter.requestToken(code);
            prompt.setText("正在获取账号信息，请稍候...");
        }
    }

    @Override
    public void onRequestStart() {
        mProgress.setIndeterminate(true);
        mProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void onRequestError(String msg) {
        prompt.setText("网络连接错误，请点击重试");
        loginBtn.setText("重试");
    }

    @Override
    public void onRequestEnd() {
        mProgress.setVisibility(View.GONE);
        Intent intent = new Intent(this, MainActivity.class);
        setResult(RESULT_OK, intent);
        finish();
    }
}
