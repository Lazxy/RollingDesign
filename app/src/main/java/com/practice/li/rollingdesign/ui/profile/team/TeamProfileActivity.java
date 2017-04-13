package com.practice.li.rollingdesign.ui.profile.team;

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
import com.practice.li.rollingdesign.entity.TeamEntity;
import com.practice.li.rollingdesign.mvpframe.base.BaseFrameActivity;
import com.practice.li.rollingdesign.ui.profile.common.SimpleControllerListener;
import com.practice.li.rollingdesign.ui.profile.common.follow.ProfileFollowContract;
import com.practice.li.rollingdesign.ui.profile.common.follow.ProfileFollowModel;
import com.practice.li.rollingdesign.ui.profile.common.follow.ProfileFollowPresenter;
import com.practice.li.rollingdesign.ui.profile.common.info.ProfileInfoFragment;
import com.practice.li.rollingdesign.ui.profile.common.works.ProfileWorksFragment;
import com.practice.li.rollingdesign.ui.profile.team.members.ProfileTeamMemberFragment;
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
 * Created by Lazxy on 2017/3/27.
 * 团队简介类
 */

public class TeamProfileActivity extends BaseFrameActivity<ProfileFollowPresenter, ProfileFollowModel> implements ProfileFollowContract.View {

    @BindView(R.id.toolbar_profile)
    Toolbar toolbar;
    @BindView(R.id.profile_tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.profile_info_pager)
    ViewPager infoPager;
    @BindView(R.id.btn_profile_common_follow)
    Button followBtn;
    @BindView(R.id.tv_profile_common_name)
    TextView teamName;
    @BindView(R.id.tv_profile_common_sign)
    TextView teamSign;
    @BindView(R.id.tv_profile_common_location)
    TextView teamLocation;
    @BindView(R.id.profile_common_bg)
    SimpleDraweeView teamBackground;
    @BindView(R.id.profile_common_avatar)
    SimpleDraweeView teamAvatar;

    private TeamEntity mTeam;
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
        mTeam = (TeamEntity) getIntent().getSerializableExtra(CommonConstants.Shots.EXTRA_SHOT_AUTHOR);
        List<BaseFragment> fragments = new ArrayList<>();
        fragments.add(ProfileInfoFragment.getInstance(mTeam));
        fragments.add(ProfileTeamMemberFragment.getInstance(mTeam.getId()));
        fragments.add(ProfileWorksFragment.getInstance(mTeam));
        BaseTabPagerAdapter<BaseFragment> adapter = new BaseTabPagerAdapter<>(getFragmentManager(),
                fragments, CommonConstants.User.PROFILE_TEAM_TAB_TITLE);
        infoPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(infoPager);
    }

    @Override
    public void initView() {
        //透明化状态栏，对于4.4系统不起效。
        UIUtils.translucentStatusBar(this);
        FrescoUtils.setAvatar(teamAvatar, mTeam.getAvatarUrl(),
                (int) getResources().getDimension(R.dimen.large_avatar_size));
        //产生模糊化头像背景
        FrescoUtils.setBlurBackground(teamBackground, Uri.parse(mTeam.getAvatarUrl()), mListener);
        teamName.setText(mTeam.getTeamName());
        String location = TextUtils.isEmpty(mTeam.getLocation()) ? "未知" : mTeam.getLocation();
        teamLocation.setText(location);
        HtmlFormatUtils.Html2String(teamSign, mTeam.getBio());
        //判断当前用户的信息与该简介内的信息是否有交集
        if (UserInfoUtils.getCurrentUser() == null) {
            followBtn.setEnabled(false);
            followBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_radius_button_grey));
        } else if (mTeam.getId() == UserInfoUtils.getCurrentUser().getId()) {
            followBtn.setText("Self");
            followBtn.setEnabled(false);
            followBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_radius_button_grey));
        } else {
            followBtn.setEnabled(false);
            mPresenter.checkFollowStatus(mTeam.getId());
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
                    mPresenter.changeFollowStatus(mTeam.getId(), isFollow);
                    toggleFollowStatus();
                } else {
                    Toast.makeText(TeamProfileActivity.this, "网络好像不太好哦", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * 切换关注与非关注状态
     */
    private void toggleFollowStatus() {
        followBtn.setEnabled(true);
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
        else {
            followBtn.setText(FOLLOW);
            followBtn.setEnabled(true);
        }
    }

    @Override
    public void onChangeFollowStatusSuccess(String result) {
        Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
    }

    private SimpleControllerListener mListener = new SimpleControllerListener() {
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
