package com.practice.li.rollingdesign.ui.profile.user.follower;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.practice.li.rollingdesign.R;
import com.practice.li.rollingdesign.common.CommonConstants;
import com.practice.li.rollingdesign.entity.FollowerEntity;
import com.practice.li.rollingdesign.entity.UserEntity;
import com.practice.li.rollingdesign.ui.profile.user.UserProfileActivity;
import com.practice.li.rollingdesign.utils.FrescoUtils;
import com.practice.li.rollingdesign.utils.HtmlFormatUtils;

/**
 * Created by Lazxy on 2017/3/24.
 */

public class FollowerAdapter extends BaseQuickAdapter<FollowerEntity,BaseViewHolder> {

    Activity mActivity;

    public FollowerAdapter(Activity activity){
        super(R.layout.item_profile_follower);
        mActivity=activity;
    }


    @Override
    protected void convert(BaseViewHolder helper, FollowerEntity item) {
        String location=item.getFollower().getLocation();
        location= TextUtils.isEmpty(location)?"未知":location;
        SimpleDraweeView avatar=helper.getView(R.id.profile_follower_avatar);
        FrescoUtils.setAvatar(avatar,item.getFollower().getAvatarUrl(),
                (int)mActivity.getResources().getDimension(R.dimen.normal_avatar_size));
        helper.setText(R.id.tv_profile_follower_name,item.getFollower().getName());
        helper.setText(R.id.tv_profile_follower_location,location);
        helper.setText(R.id.tv_profile_follower_shots_count,item.getFollower().getShotsCount()+" :作品");
        if(!TextUtils.isEmpty(item.getFollower().getBio()))
            helper.setText(R.id.tv_profile_follower_sign,HtmlFormatUtils.Html2String(item.getFollower().getBio()));
        else
            helper.setText(R.id.tv_profile_follower_sign,"这个人很懒，什么都没有写");
        attachToAuthorInfo(helper.getView(R.id.profile_follower_layout),item.getFollower());
    }

    private void attachToAuthorInfo(View view,final UserEntity userEntity){
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
