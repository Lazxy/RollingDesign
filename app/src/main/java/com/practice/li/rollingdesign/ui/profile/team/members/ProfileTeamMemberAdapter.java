package com.practice.li.rollingdesign.ui.profile.team.members;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.practice.li.rollingdesign.R;
import com.practice.li.rollingdesign.common.CommonConstants;
import com.practice.li.rollingdesign.entity.UserEntity;
import com.practice.li.rollingdesign.ui.profile.user.UserProfileActivity;
import com.practice.li.rollingdesign.utils.FrescoUtils;
import com.practice.li.rollingdesign.utils.HtmlFormatUtils;


/**
 * Created by Lazxy on 2017/3/28.
 */

public class ProfileTeamMemberAdapter extends BaseQuickAdapter<UserEntity,BaseViewHolder> {

    private Activity mActivity;
    private int mAvatarSize;

    public ProfileTeamMemberAdapter(Activity activity){
        super(R.layout.item_profile_team_member);
        mActivity=activity;
        mAvatarSize=(int)mActivity.getResources().getDimension(R.dimen.normal_avatar_size);
    }

    @Override
    protected void convert(BaseViewHolder helper, UserEntity item) {
        FrescoUtils.setAvatar((SimpleDraweeView) helper.getView(R.id.profile_team_member_avatar),
                item.getAvatarUrl(),mAvatarSize);
        helper.setText(R.id.tv_profile_team_member_name,item.getName());
        helper.setText(R.id.tv_profile_team_member_shots_count,item.getShotsCount()+" 作品");
        helper.setText(R.id.tv_profile_team_member_follower_count,item.getFollowersCount()+" 粉丝");
        helper.setText(R.id.tv_profile_team_member_sign,HtmlFormatUtils.Html2String(item.getBio()));
        attachToAuthorInfo(helper.getView(R.id.profile_team_member_layout),item);
    }

    private void attachToAuthorInfo(View view, final UserEntity userEntity){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mActivity,UserProfileActivity.class);
                intent.putExtra(CommonConstants.Shots.EXTRA_SHOT_AUTHOR,userEntity);
                mActivity.startActivity(intent);
            }
        });
    }
}
