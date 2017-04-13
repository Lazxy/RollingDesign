package com.practice.li.rollingdesign.ui.user.team;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.practice.li.rollingdesign.R;
import com.practice.li.rollingdesign.common.CommonConstants;
import com.practice.li.rollingdesign.entity.TeamEntity;
import com.practice.li.rollingdesign.ui.profile.team.TeamProfileActivity;
import com.practice.li.rollingdesign.utils.FrescoUtils;
import com.practice.li.rollingdesign.utils.HtmlFormatUtils;

/**
 * Created by Lazxy on 2017/3/27.
 */

public class UserTeamsAdapter extends BaseQuickAdapter<TeamEntity,BaseViewHolder> {

    private Activity mActivity;

    public UserTeamsAdapter(Activity activity){
        super(R.layout.item_user_team);
        mActivity=activity;
    }

    @Override
    protected void convert(BaseViewHolder helper, TeamEntity item) {
        FrescoUtils.setAvatar((SimpleDraweeView) helper.getView(R.id.user_team_avatar),item.getAvatarUrl(),
                (int)mActivity.getResources().getDimension(R.dimen.normal_avatar_size));
        helper.setText(R.id.tv_user_team_name,item.getTeamName());
        helper.setText(R.id.tv_user_team_members_count,item.getMembersCount()+" 成员");
        helper.setText(R.id.tv_user_team_shots_count,item.getShotsCount()+" 作品");
        HtmlFormatUtils.Html2String((TextView)helper.getView(R.id.tv_user_team_sign),item.getBio());
        attachToTeamProfile(helper.getView(R.id.layout_profile_detail_team),item);
    }

    private void attachToTeamProfile(View view,final TeamEntity item){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mActivity, TeamProfileActivity.class);
                intent.putExtra(CommonConstants.User.EXTRA_PROFILE_AUTHOR,item);
                mActivity.startActivity(intent);
            }
        });
    }
}
