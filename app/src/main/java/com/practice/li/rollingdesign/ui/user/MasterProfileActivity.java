package com.practice.li.rollingdesign.ui.user;

import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.util.Linkify;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.practice.li.rollingdesign.R;
import com.practice.li.rollingdesign.common.BaseActivity;
import com.practice.li.rollingdesign.common.CommonConstants;
import com.practice.li.rollingdesign.entity.UserEntity;
import com.practice.li.rollingdesign.utils.FrescoUtils;
import com.practice.li.rollingdesign.utils.HtmlFormatUtils;
import com.practice.li.rollingdesign.utils.UIUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Lazxy on 2017/3/30.
 * 当前用户简介类
 */

public class MasterProfileActivity extends BaseActivity {

    @BindView(R.id.tv_master_profile_name)
    TextView userName;
    @BindView(R.id.tv_master_profile_sign)
    TextView userSign;
    @BindView(R.id.tv_master_profile_location)
    TextView userLocation;
    @BindView(R.id.master_profile_bg)
    SimpleDraweeView userBackground;
    @BindView(R.id.master_profile_avatar)
    SimpleDraweeView userAvatar;
    @BindView(R.id.tv_master_profile_detail_shots)
    TextView shotsCount;
    @BindView(R.id.tv_master_profile_detail_like)
    TextView likesCount;
    @BindView(R.id.tv_master_profile_detail_buckets)
    TextView bucketsCount;
    @BindView(R.id.tv_master_profile_detail_follower)
    TextView followersCount;
    @BindView(R.id.tv_master_profile_detail_following)
    TextView followingsCount;
    @BindView(R.id.tv_master_profile_detail_project)
    TextView projectsCount;
    @BindView(R.id.tv_master_profile_detail_twitter)
    TextView twitterAddress;
    @BindView(R.id.tv_master_profile_detail_web)
    TextView webAddress;

    private UserEntity mMaster;

    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_master_profile);
        ButterKnife.bind(this);
    }

    @Override
    public void initData(){
        mMaster=(UserEntity)getIntent().getSerializableExtra(CommonConstants.User.EXTRA_PROFILE_AUTHOR);
    }

    @Override
    public void initView(){
        UIUtils.translucentStatusBar(this);
        //注意这里和所有简介类一样，存在若头像是GIF格式(Dribbble默认头像格式)时则无法显示圆形图像的BUG
        FrescoUtils.setAvatar(userAvatar,mMaster.getAvatarUrl(),
                (int)getResources().getDimension(R.dimen.large_avatar_size));
        FrescoUtils.setBlurBackground(userBackground, Uri.parse(mMaster.getAvatarUrl()),null);
        userName.setText(mMaster.getName());
        String location= TextUtils.isEmpty(mMaster.getLocation())?"未知":mMaster.getLocation();
        userLocation.setText(location);
        HtmlFormatUtils.Html2String(userSign,mMaster.getBio());
        shotsCount.setText(mMaster.getShotsCount()+" 作品");
        bucketsCount.setText(mMaster.getBucketsCount()+" 收藏");
        likesCount.setText(mMaster.getLikesCount()+" 喜欢");
        followersCount.setText(mMaster.getFollowersCount()+" 粉丝");
        followingsCount.setText(mMaster.getFollowingsCount()+" 关注");
        projectsCount.setText(mMaster.getProjectsCount()+" 项目");
        String twitterUrl=mMaster.getLinks().getTwitter();
        twitterUrl= TextUtils.isEmpty(twitterUrl)?"未知":twitterUrl;
        twitterAddress.setText(twitterUrl);
        twitterAddress.setAutoLinkMask(Linkify.WEB_URLS);
        String webUrl=mMaster.getLinks().getWeb();
        webUrl= TextUtils.isEmpty(webUrl)?"未知":webUrl;
        webAddress.setText(webUrl);
        webAddress.setAutoLinkMask(Linkify.WEB_URLS);
    }

}
