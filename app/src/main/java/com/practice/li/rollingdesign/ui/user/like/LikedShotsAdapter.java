package com.practice.li.rollingdesign.ui.user.like;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.practice.li.rollingdesign.R;
import com.practice.li.rollingdesign.common.CommonConstants;
import com.practice.li.rollingdesign.entity.ShotsEntity;
import com.practice.li.rollingdesign.entity.UserEntity;
import com.practice.li.rollingdesign.ui.profile.user.UserProfileActivity;
import com.practice.li.rollingdesign.ui.profile.team.TeamProfileActivity;
import com.practice.li.rollingdesign.ui.shots.detail.ShotDetailActivity;
import com.practice.li.rollingdesign.utils.FrescoUtils;
import com.practice.li.rollingdesign.utils.StrConvertUtils;
import com.practice.li.rollingdesign.utils.UrlUtils;

/**
 * Created by Lazxy on 2017/3/25.
 */

public class LikedShotsAdapter extends BaseQuickAdapter<ShotsEntity,BaseViewHolder> {

    private Activity mActivity;

    public LikedShotsAdapter(Activity activity){
        super(R.layout.item_shot_small_with_info);
        mActivity=activity;
    }

    @Override
    protected void convert(BaseViewHolder helper, ShotsEntity item) {
        SimpleDraweeView preview=helper.getView(R.id.shot_preview);
        SimpleDraweeView avatar=helper.getView(R.id.author_avatar);
        if(item.isAnimated()) {
            helper.setVisible(R.id.iv_shot_gif,true);
            FrescoUtils.setAnimationDepends(preview,item.getImages());
        }else
            FrescoUtils.setPreview(preview, UrlUtils.getLowQualityImageUrl(item.getImages()));
        FrescoUtils.setAvatar(avatar,item.getUser().getAvatarUrl(),
                (int)mActivity.getResources().getDimension(R.dimen.small_avatar_size));
        helper.setText(R.id.tv_author_name,item.getUser().getName());
        helper.setText(R.id.tv_shot_view, StrConvertUtils.formatNumber(item.getViewsCount()));
        helper.setText(R.id.tv_shot_like,StrConvertUtils.formatNumber(item.getLikesCount()));
        helper.setText(R.id.tv_shot_comment,StrConvertUtils.formatNumber(item.getCommentsCount()));
        attachToAuthorInfo(helper.getView(R.id.layout_author_info),item.getUser());
        attachToShotDetail(helper.getView(R.id.layout_shot_container),item);
    }
    private void attachToAuthorInfo(View view, final UserEntity userEntity){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                if(userEntity.getType().equals(CommonConstants.User.USER_TYPE_PLAYER))
                    intent=new Intent(mActivity,UserProfileActivity.class);
                else
                    intent=new Intent(mActivity, TeamProfileActivity.class);
                intent.putExtra(CommonConstants.Shots.EXTRA_SHOT_AUTHOR,userEntity);
                mActivity.startActivity(intent);
            }
        });
    }

    private void attachToShotDetail(View view,final ShotsEntity shotsEntity){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mActivity,ShotDetailActivity.class);
                intent.putExtra(CommonConstants.Shots.EXTRA_SHOT_DETAIL,shotsEntity);
                mActivity.startActivity(intent);
            }
        });
    }
}
