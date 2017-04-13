package com.practice.li.rollingdesign.ui.setting;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.practice.li.rollingdesign.R;
import com.practice.li.rollingdesign.common.BaseActivity;
import com.practice.li.rollingdesign.common.ConfigManager;
import com.practice.li.rollingdesign.event.EventChangeLoginStatus;
import com.practice.li.rollingdesign.ui.user.login.LoginActivity;
import com.practice.li.rollingdesign.utils.FileCacheUtils;
import com.practice.li.rollingdesign.utils.FrescoUtils;
import com.practice.li.rollingdesign.utils.UIUtils;
import com.practice.li.rollingdesign.utils.UserInfoUtils;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Lazxy on 2017/3/12
 * 设置类
 */

public class SettingActivity extends BaseActivity {

    @BindView(R.id.switch_settings_auto_animated)
    SwitchCompat animateSwitch;
    @BindView(R.id.item_settings_clean_cache)
    RelativeLayout cleanCache;
    @BindView(R.id.toolbar_settings)
    Toolbar toolbar;
    @BindView(R.id.settings_cache_size)
    TextView cacheSizeText;
    @BindView(R.id.tv_settings_to_about)
    TextView aboutApp;
    @BindView(R.id.tv_settings_logout)
    TextView logoutText;

    private boolean isAutoAnimated;
    private String cacheSize;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
        isAutoAnimated = ConfigManager.Shot.isAutoAnimated();
    }

    @Override
    public void initView() {
        animateSwitch.setChecked(isAutoAnimated);
        setSupportActionBar(toolbar);
    }

    @Override
    public void initListener() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        cacheSizeText.setText("计算中");
        animateSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(final CompoundButton buttonView, final boolean isChecked) {
                if (!isChecked) {
                    ConfigManager.Shot.setIsAutoAnimated(isChecked);
                    FrescoUtils.changeLoadConfig(isChecked);
                } else {
                    UIUtils.showSimpleAlertDialog(SettingActivity.this, null,
                            "开启该选项会在预览时消耗更多您的流量，且可能会影响图片加载速度，确认开启吗？", "开启", "取消",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    ConfigManager.Shot.setIsAutoAnimated(isChecked);
                                    FrescoUtils.changeLoadConfig(isChecked);
                                }
                            }, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    buttonView.setChecked(false);
                                }
                            });
                }
            }
        });

        aboutApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingActivity.this, AboutAppActivity.class));
            }
        });

        cleanCache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIUtils.showSimpleAlertDialog(SettingActivity.this,
                        "清除确认", "确认清除缓存吗？", "确认", "取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                FileCacheUtils.clearAllCache(SettingActivity.this);
                                cacheSizeText.setText(FileCacheUtils.getTotalCacheSize(SettingActivity.this));
                            }
                        }, null);
            }
        });

        logoutText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(UserInfoUtils.getCurrentUser() != null) {
                    UIUtils.showSimpleAlertDialog(SettingActivity.this,
                            "退出确认", "确认退出当前账号吗？", "确认", "取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    UserInfoUtils.clearUserInfo();
                                    EventBus.getDefault().post(new EventChangeLoginStatus(false));
                                    openActivity(LoginActivity.class);
                                    finish();
                                }
                            }, null);
                }else{
                    openActivity(LoginActivity.class);
                }
            }
        });
    }

    @Override
    public void initLoad() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                cacheSize = FileCacheUtils.getTotalCacheSize(SettingActivity.this);
                cacheSizeText.setText(cacheSize);
            }
        }, 1000);
    }
}
