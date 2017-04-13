package com.practice.li.rollingdesign.ui.shots.detail;

import android.Manifest;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


import com.facebook.drawee.drawable.ProgressBarDrawable;
import com.facebook.drawee.view.SimpleDraweeView;
import com.practice.li.rollingdesign.R;
import com.practice.li.rollingdesign.common.BaseActivity;
import com.practice.li.rollingdesign.common.BaseTabPagerAdapter;
import com.practice.li.rollingdesign.common.CommonConstants;
import com.practice.li.rollingdesign.entity.ShotsEntity;
import com.practice.li.rollingdesign.mvpframe.base.BaseFrameFragment;
import com.practice.li.rollingdesign.ui.shots.detail.comment.CommentFragment;
import com.practice.li.rollingdesign.ui.shots.detail.info.ShotInfoFragment;
import com.practice.li.rollingdesign.utils.FrescoUtils;
import com.practice.li.rollingdesign.utils.IntentUtils;
import com.practice.li.rollingdesign.utils.PermissionUtils;
import com.practice.li.rollingdesign.utils.UIUtils;
import com.practice.li.rollingdesign.utils.UrlUtils;
import com.practice.li.rollingdesign.utils.UserInfoUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Lazxy on 2017/3/9.
 * Shot信息展示类
 */

public class ShotDetailActivity extends BaseActivity implements PermissionUtils.PermissionListener {

    @BindView(R.id.display_shot_detail)
    SimpleDraweeView shotContainer;
    @BindView(R.id.toolbar_shot_detail)
    Toolbar toolbar;
    @BindView(R.id.tab_shot_detail)
    TabLayout tabLayout;
    @BindView(R.id.pager_shots_detail)
    ViewPager detailPager;

    private ShotsEntity mShot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UIUtils.translucentStatusBar(this);
        setContentView(R.layout.activity_shot_detail);
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
        mShot = (ShotsEntity) getIntent().getSerializableExtra(CommonConstants.Shots.EXTRA_SHOT_DETAIL);
        boolean shouldLogin = (UserInfoUtils.getCurrentUser() == null);
        List<BaseFrameFragment> fragments = new ArrayList<>();
        /*创建两个碎片并传入相应参数*/
        fragments.add(ShotInfoFragment.newInstance(mShot, shouldLogin));
        fragments.add(CommentFragment.newInstance(mShot, shouldLogin));
        BaseTabPagerAdapter<BaseFrameFragment> adapter = new BaseTabPagerAdapter<>(getFragmentManager(),
                fragments, CommonConstants.Shots.SHOT_TAB_TITLE);
        detailPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(detailPager);
    }

    @Override
    public void initView() {
        //设置图片加载进度条
        shotContainer.getHierarchy().setProgressBarImage(new ProgressBarDrawable());
        FrescoUtils.setPreview(shotContainer, UrlUtils.getHighQualityImageUrl(mShot.getImages()), true);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void initListener() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.menu_share) {
                    /*先判断一波是否有读取存储的权限*/
                    boolean hasPermission = PermissionUtils.requestPermission(ShotDetailActivity.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, ShotDetailActivity.this);
                    if (hasPermission) {
                        showShotShareSheet();
                    }
                } else if (id == R.id.menu_open_browser) {
                    IntentUtils.startActivityToBrowser(ShotDetailActivity.this, mShot.getHtmlUrl());
                }
                return true;
            }
        });
        shotContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(CommonConstants.Shots.EXTRA_SHOT_IMAGE_URL, UrlUtils.getHighQualityImageUrl(mShot.getImages()));
                bundle.putBoolean(CommonConstants.Shots.EXTRA_SHOT_GIF, mShot.isAnimated());
                openActivity(ShotDisplayActivity.class, bundle);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.shot_detail_toolbar, menu);
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionUtils.onPermissionRequestResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onGranted() {
        showShotShareSheet();
    }

    @Override
    public void onDenied(String permission) {
        PermissionUtils.showMissingPermissionDialog(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .show();
    }

    /**
     * 显示收藏页面。
     */
    private void showShotShareSheet() {
        ShotShareSheet shareSheet = ShotShareSheet.newInstance(mShot);
        getSupportFragmentManager().beginTransaction().add(shareSheet, ShotShareSheet.class.getSimpleName())
                .commitAllowingStateLoss();
    }

}
