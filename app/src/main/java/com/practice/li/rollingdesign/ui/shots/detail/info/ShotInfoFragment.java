package com.practice.li.rollingdesign.ui.shots.detail.info;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.practice.li.rollingdesign.R;
import com.practice.li.rollingdesign.common.CommonConstants;
import com.practice.li.rollingdesign.entity.AuthorEntity;
import com.practice.li.rollingdesign.entity.ShotsEntity;
import com.practice.li.rollingdesign.event.EventSelectBucket;
import com.practice.li.rollingdesign.mvpframe.base.BaseFrameFragment;
import com.practice.li.rollingdesign.ui.profile.user.UserProfileActivity;
import com.practice.li.rollingdesign.ui.profile.team.TeamProfileActivity;
import com.practice.li.rollingdesign.ui.widget.TagFlowLayout;
import com.practice.li.rollingdesign.utils.FrescoUtils;
import com.practice.li.rollingdesign.utils.HtmlFormatUtils;
import com.practice.li.rollingdesign.utils.NetworkUtils;
import com.practice.li.rollingdesign.utils.TimeUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Lazxy on 2017/3/15.
 * 作品信息展示类
 */

public class ShotInfoFragment extends BaseFrameFragment<ShotInfoPresenter,ShotInfoModel> implements ShotInfoContract.View {

    @BindView(R.id.tv_info_author_name)
    TextView authorName;
    @BindView(R.id.tv_info_time_gap)
    TextView timeGap;
    @BindView(R.id.tv_info_shot_title)
    TextView shotTitle;
    @BindView(R.id.tv_info_shot_description)
    TextView shotDescription;
    @BindView(R.id.tv_info_shot_view)
    TextView shotViews;
    @BindView(R.id.tv_info_shot_like)
    TextView shotLikes;
    @BindView(R.id.tv_info_shot_comment)
    TextView shotComment;
    @BindView(R.id.tv_info_shot_bucket)
    TextView shotBuckets;
    @BindView(R.id.info_tag_layout)
    TagFlowLayout tagLayout;
    @BindView(R.id.info_author_avatar)
    SimpleDraweeView authorAvatar;

    private ShotsEntity mShot;
    private boolean mIsLiked;
    private boolean mShouldLogin;
    private int mCounter=0;
    private Drawable likeDrawable,unlikeDrawable,hasBucket,noBucket;

    @Override
    public void onCreate(Bundle onSaveInstanceState){
        super.onCreate(onSaveInstanceState);
        setContentView(R.layout.fragment_shots_info);
        ButterKnife.bind(this,getContentView());
        EventBus.getDefault().register(this);
    }

    @Override
    public void initData(){
        mShot=(ShotsEntity) getArguments().getSerializable(CommonConstants.Shots.EXTRA_SHOT_DETAIL);
        mShouldLogin=getArguments().getBoolean(CommonConstants.User.EXTRA_SHOULD_LOGIN);
        if(!mShouldLogin) {
            mPresenter.checkShotsLike(mShot.getId());
        }
        //准备不同状态的图片
        likeDrawable=getActivity().getResources().getDrawable(R.drawable.iv_like_blue_24dp);
        likeDrawable.setBounds(0,0,likeDrawable.getMinimumWidth(),likeDrawable.getMinimumHeight());
        unlikeDrawable=getActivity().getResources().getDrawable(R.drawable.iv_like_grey_24dp);
        unlikeDrawable.setBounds(0,0,unlikeDrawable.getMinimumWidth(),unlikeDrawable.getMinimumHeight());
        hasBucket=getActivity().getResources().getDrawable(R.drawable.iv_bucket_blue_24dp);
        hasBucket.setBounds(0,0,hasBucket.getMinimumWidth(),hasBucket.getMinimumHeight());
        noBucket=getActivity().getResources().getDrawable(R.drawable.iv_bucket_grey_24dp);
        noBucket.setBounds(0,0,noBucket.getMinimumWidth(),noBucket.getMinimumHeight());

    }

    @Override
    public void initView(){
        int normalAvatarSize=(int)getActivity().getResources().getDimension(R.dimen.normal_avatar_size);
        FrescoUtils.setAvatar(authorAvatar,mShot.getUser().getAvatarUrl(),normalAvatarSize);
        authorName.setText(mShot.getUser().getName());
        timeGap.setText("投递于 "+TimeUtils.getTimeFromISO8601(mShot.getUpdatedAt()));
        shotTitle.setText(mShot.getTitle());
        shotViews.setText(mShot.getViewsCount()+"");
        shotLikes.setText(mShot.getLikesCount()+"");
        shotComment.setText(mShot.getCommentsCount()+"");
        shotBuckets.setText(mShot.getBucketsCount()+"");
        tagLayout.setTag(mShot.getTags());
        String description=mShot.getDescription();
        if(!TextUtils.isEmpty(description)) {
            HtmlFormatUtils.Html2StringNoP(shotDescription,description);
        }else{
            shotDescription.setVisibility(View.GONE);
        }
    }

    @Override
    public void initListener(){
        shotLikes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //切换喜欢的状态
                if(!mShouldLogin) {
                    if(NetworkUtils.getIsNetWorking()) {
                        mPresenter.changeShotsStatus(mShot.getId(), !mIsLiked);
                        if (!mIsLiked) {
                            shotLikes.setCompoundDrawables(null, likeDrawable, null, null);
                            shotLikes.setText(mShot.getLikesCount() + 1 + "");
                            mShot.setLikesCount(mShot.getLikesCount() + 1);
                        } else {
                            shotLikes.setCompoundDrawables(null, unlikeDrawable, null, null);
                            shotLikes.setText(mShot.getLikesCount() - 1 + "");
                            mShot.setLikesCount(mShot.getLikesCount() - 1);
                            /*增加动画效果*/
                        }
                    }else{
                        Toast.makeText(getActivity(),"网络好像不太好哦",Toast.LENGTH_SHORT).show();
                    }
                }
                else
                    Snackbar.make(v,"请先登陆",Snackbar.LENGTH_SHORT).show();
            }
        });

        shotBuckets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //显示一个Bucket列表然后根据选择发送加入收藏请求
                if(!mShouldLogin){
                    BucketSelectFragment selectFragment=new BucketSelectFragment();
                    selectFragment.show(((AppCompatActivity)getActivity()).getSupportFragmentManager(),
                            BucketSelectFragment.class.getSimpleName());
                }else{
                    Snackbar.make(v,"请先登陆",Snackbar.LENGTH_SHORT).show();
                }
            }
        });

        //跳转入作者信息，预先进行作者类型判断
        authorName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                AuthorEntity entity;
                if(mShot.getUser().getType().equals(CommonConstants.User.USER_TYPE_PLAYER)) {
                    intent = new Intent(getActivity(), UserProfileActivity.class);
                    entity=mShot.getUser();
                }
                else {
                    intent = new Intent(getActivity(), TeamProfileActivity.class);
                    entity=mShot.getTeam();
                }
                intent.putExtra(CommonConstants.Shots.EXTRA_SHOT_AUTHOR,entity);
                getActivity().startActivity(intent);
            }
        });

        authorAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                AuthorEntity entity;
                if(mShot.getUser().getType().equals(CommonConstants.User.USER_TYPE_PLAYER)) {
                    intent = new Intent(getActivity(), UserProfileActivity.class);
                    entity=mShot.getUser();
                }
                else {
                    intent = new Intent(getActivity(), TeamProfileActivity.class);
                    entity=mShot.getTeam();
                }
                    intent.putExtra(CommonConstants.Shots.EXTRA_SHOT_AUTHOR,entity);
                getActivity().startActivity(intent);
            }
        });
    }

    /**
     * 添加被选中的作品进入收藏
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getSelectBucketId(EventSelectBucket event){
        mPresenter.addShotsToBuckets(event.bucketsId,mShot.getId());
        shotBuckets.setCompoundDrawables(null,hasBucket,null,null);
        shotBuckets.setText(mShot.getBucketsCount()+1);
    }

    public static ShotInfoFragment newInstance(ShotsEntity entity,boolean shouldLogin){
        Bundle args=new Bundle();
        args.putSerializable(CommonConstants.Shots.EXTRA_SHOT_DETAIL,entity);
        args.putBoolean(CommonConstants.User.EXTRA_SHOULD_LOGIN,shouldLogin);
        ShotInfoFragment fragment=new ShotInfoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void checkShotsLikeOnSuccess(boolean isLiked , int likesCount) {
        mIsLiked=isLiked;
        if(isLiked) {
            mShot.setLikesCount(likesCount);
            shotLikes.setText(likesCount+"");
            shotLikes.setCompoundDrawables(null, likeDrawable, null, null);
        }
        else {
            shotLikes.setCompoundDrawables(null, unlikeDrawable, null, null);
        }
    }

    @Override
    public void changeShotsLikeStatusOnSuccess(boolean isLiked) {
        mIsLiked=isLiked;
    }

    @Override
    public void addShotsToBucketsOnSuccess(int size) {
        if(++mCounter==size){
            Toast.makeText(getActivity(),"收藏添加成功",Toast.LENGTH_SHORT).show();
            shotBuckets.setCompoundDrawables(null,hasBucket,null,null);
            shotBuckets.setText(mShot.getBucketsCount()+1);
        }
    }

    @Override
    public void onRequestStart() {

    }

    @Override
    public void onRequestError(String msg) {

    }

    @Override
    public void onRequestEnd() {

    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
