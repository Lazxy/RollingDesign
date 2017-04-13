package com.practice.li.rollingdesign.ui.user.follow;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.practice.li.rollingdesign.R;
import com.practice.li.rollingdesign.common.CommonConstants;
import com.practice.li.rollingdesign.entity.FollowingEntity;
import com.practice.li.rollingdesign.entity.UserEntity;
import com.practice.li.rollingdesign.event.EventUnFollowUser;
import com.practice.li.rollingdesign.ui.profile.user.UserProfileActivity;
import com.practice.li.rollingdesign.utils.FrescoUtils;
import com.practice.li.rollingdesign.utils.HtmlFormatUtils;
import com.practice.li.rollingdesign.utils.TimeUtils;
import com.practice.li.rollingdesign.utils.UIUtils;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Lazxy on 2017/3/27.
 */

public class UserFollowingAdapter extends BaseQuickAdapter<FollowingEntity,BaseViewHolder> {

    Activity mActivity;

    public UserFollowingAdapter(Activity activity) {
        super(R.layout.item_user_following);
        mActivity=activity;
    }

    @Override
    protected void convert(BaseViewHolder helper, FollowingEntity item) {
        SimpleDraweeView avatar=helper.getView(R.id.user_following_avatar);
        FrescoUtils.setAvatar(avatar,item.getFollowing().getAvatarUrl(),
                (int)mActivity.getResources().getDimension(R.dimen.normal_avatar_size));
        helper.setText(R.id.tv_user_following_name,item.getFollowing().getName());
        helper.setText(R.id.tv_user_following_update, TimeUtils.getTimeFromISO8601(item.getFollowing().getUpdatedAt()));
        helper.setText(R.id.tv_user_following_shots_count,"作品: "+item.getFollowing().getShotsCount());
        if(!TextUtils.isEmpty(item.getFollowing().getBio()))
            helper.setText(R.id.tv_user_following_sign,HtmlFormatUtils.Html2String(item.getFollowing().getBio()));
        else
            helper.setText(R.id.tv_user_following_sign,"这个人很懒，什么都没有写");
        attachToAuthorInfo(helper.getView(R.id.user_following_layout),item.getFollowing());
        int position=helper.getLayoutPosition() - getHeaderLayoutCount();
        int id=item.getFollowing().getId();
        setUnFollowClick(helper.getView(R.id.iv_user_unfollow),position,id);

    }

    private void attachToAuthorInfo(View view, final UserEntity userEntity){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*无法区分Player与Team*/
                Intent intent=new Intent(mActivity,UserProfileActivity.class);
                intent.putExtra(CommonConstants.Shots.EXTRA_SHOT_AUTHOR,userEntity);
                mActivity.startActivity(intent);
            }
        });
    }

    private void setUnFollowClick(View view,final int position,final int id){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIUtils.showSimpleAlertDialog(mActivity, null, "要取消关注吗？", "取消关注", "返回", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EventBus.getDefault().post(new EventUnFollowUser(position,id));
                    }
                },null);
            }
        });
    }
}
