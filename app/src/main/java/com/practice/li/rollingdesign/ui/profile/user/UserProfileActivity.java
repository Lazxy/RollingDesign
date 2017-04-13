package com.practice.li.rollingdesign.ui.profile.user;


import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;
import com.practice.li.rollingdesign.R;
import com.practice.li.rollingdesign.common.BaseFragment;
import com.practice.li.rollingdesign.common.BaseTabPagerAdapter;
import com.practice.li.rollingdesign.common.CommonConstants;
import com.practice.li.rollingdesign.entity.UserEntity;
import com.practice.li.rollingdesign.mvpframe.base.BaseFrameActivity;
import com.practice.li.rollingdesign.ui.profile.common.SimpleControllerListener;
import com.practice.li.rollingdesign.ui.profile.common.follow.ProfileFollowContract;
import com.practice.li.rollingdesign.ui.profile.common.follow.ProfileFollowModel;
import com.practice.li.rollingdesign.ui.profile.common.follow.ProfileFollowPresenter;
import com.practice.li.rollingdesign.ui.profile.user.follower.ProfileFollowerFragment;
import com.practice.li.rollingdesign.ui.profile.common.info.ProfileInfoFragment;
import com.practice.li.rollingdesign.ui.profile.user.like.ProfileLikeFragment;
import com.practice.li.rollingdesign.ui.profile.common.works.ProfileWorksFragment;
import com.practice.li.rollingdesign.utils.FrescoUtils;
import com.practice.li.rollingdesign.utils.HtmlFormatUtils;
import com.practice.li.rollingdesign.utils.NetworkUtils;
import com.practice.li.rollingdesign.utils.UIUtils;
import com.practice.li.rollingdesign.utils.UserInfoUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Lazxy on 2017/3/9.
 * 单人用户简介类
 */

public class UserProfileActivity extends BaseFrameActivity<ProfileFollowPresenter, ProfileFollowModel> implements ProfileFollowContract.View {

    @BindView(R.id.toolbar_profile)
    Toolbar toolbar;
    @BindView(R.id.profile_tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.profile_info_pager)
    ViewPager infoPager;
    @BindView(R.id.btn_profile_common_follow)
    Button followBtn;
    @BindView(R.id.tv_profile_common_name)
    TextView userName;
    @BindView(R.id.tv_profile_common_sign)
    TextView userSign;
    @BindView(R.id.tv_profile_common_location)
    TextView userLocation;
    @BindView(R.id.profile_common_bg)
    SimpleDraweeView userBackground;
    @BindView(R.id.profile_common_avatar)
    SimpleDraweeView userAvatar;

    private UserEntity mUser;
    private final String ALREADY_FOLLOW = "已关注";
    private final String FOLLOW = "关注";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_profile);
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
        mUser = (UserEntity) getIntent().getSerializableExtra(CommonConstants.Shots.EXTRA_SHOT_AUTHOR);
        List<BaseFragment> fragments = new ArrayList<>();
        //创建四个碎片并传入相应参数
        fragments.add(ProfileInfoFragment.getInstance(mUser));
        fragments.add(ProfileWorksFragment.getInstance(mUser));
        fragments.add(ProfileFollowerFragment.getInstance(mUser.getId()));
        fragments.add(ProfileLikeFragment.getInstance(mUser.getId()));
        BaseTabPagerAdapter<BaseFragment> adapter = new BaseTabPagerAdapter<>(getFragmentManager(),
                fragments, CommonConstants.User.PROFILE_USER_TAB_TITLE);
        infoPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(infoPager);
    }

    @Override
    public void initView() {
        UIUtils.translucentStatusBar(this);
        FrescoUtils.setAvatar(userAvatar, mUser.getAvatarUrl(),
                (int) getResources().getDimension(R.dimen.large_avatar_size));
        FrescoUtils.setBlurBackground(userBackground, Uri.parse(mUser.getAvatarUrl()), listener);
        userName.setText(mUser.getName());
        String location = TextUtils.isEmpty(mUser.getLocation()) ? "未知" : mUser.getLocation();
        userLocation.setText(location);
        HtmlFormatUtils.Html2String(userSign, mUser.getBio());
        if (UserInfoUtils.getCurrentUser() == null) {
            followBtn.setEnabled(false);
            followBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_radius_button_grey));
        } else if (mUser.getId() == UserInfoUtils.getCurrentUser().getId()) {
            followBtn.setText("本人");
            followBtn.setEnabled(false);
            followBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_radius_button_grey));
        } else {
            followBtn.setEnabled(false);
            followBtn.setText("获取中");
            mPresenter.checkFollowStatus(mUser.getId());
        }
        //这里出现了异步加载图片导致的Toolbar遮挡问题，依图片绘制的快慢决定
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
        followBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NetworkUtils.getIsNetWorking()) {
                    boolean isFollow = !followBtn.getText().equals(ALREADY_FOLLOW);
                    mPresenter.changeFollowStatus(mUser.getId(), isFollow);
                    toggleFollowStatus();
                } else {
                    Toast.makeText(UserProfileActivity.this, "网络好像不太好哦", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void toggleFollowStatus() {
        if (followBtn.getText().equals(ALREADY_FOLLOW)) {
            followBtn.setText(FOLLOW);
            followBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_radius_button_accent));
        } else {
            followBtn.setText(ALREADY_FOLLOW);
            followBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_radius_button_grey));
        }
    }

    @Override
    public void onCheckFollowStatusSuccess(boolean isFollowed) {
        if (isFollowed)
            toggleFollowStatus();
        else{
            followBtn.setText(FOLLOW);
            followBtn.setEnabled(true);
        }
    }

    @Override
    public void onChangeFollowStatusSuccess(String result) {
        Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
    }

    private SimpleControllerListener listener = new SimpleControllerListener() {
        @Override
        public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
            toolbar.layout(toolbar.getLeft(), toolbar.getTop(), toolbar.getRight(), toolbar.getBottom());
        }
    };

    @Override
    public void onRequestStart() {

    }

    @Override
    public void onRequestError(String msg) {
        if (msg.equals(CommonConstants.Error.ERROR_MESSAGE_CHECK_FOLLOW)) {
            followBtn.setText("未知");
            Toast.makeText(this, "检查关注状态失败,请确认您的网络状况是否正常", Toast.LENGTH_SHORT).show();
        } else if (msg.equals(CommonConstants.Error.ERROR_MESSAGE_CHANGE_FOLLOW_STATUS)) {
            Toast.makeText(this, "更改关注状态失败,请确认您的网络状况是否正常", Toast.LENGTH_SHORT).show();
            toggleFollowStatus();
        }
    }

    @Override
    public void onRequestEnd() {

    }


}
