package com.practice.li.rollingdesign.ui.shots.list;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.practice.li.rollingdesign.R;
import com.practice.li.rollingdesign.common.CommonConstants;
import com.practice.li.rollingdesign.entity.AuthorEntity;
import com.practice.li.rollingdesign.entity.ShotsEntity;
import com.practice.li.rollingdesign.entity.UserEntity;
import com.practice.li.rollingdesign.ui.profile.user.UserProfileActivity;
import com.practice.li.rollingdesign.ui.profile.team.TeamProfileActivity;
import com.practice.li.rollingdesign.ui.shots.detail.ShotDetailActivity;
import com.practice.li.rollingdesign.utils.FrescoUtils;
import com.practice.li.rollingdesign.utils.StrConvertUtils;
import com.practice.li.rollingdesign.utils.TimeUtils;
import com.practice.li.rollingdesign.utils.UrlUtils;

import java.util.List;

/**
 * Created by Lazxy on 2017/3/9.
 */

public class ShotsAdapter extends BaseMultiItemQuickAdapter<ShotsEntity,BaseViewHolder> {

    private Activity mActivity;
    public ShotsAdapter(Activity activity, List<ShotsEntity> data) {
        super(data);
        mActivity=activity;
        /*将类型和其布局文件通过键值对的方式存在Adapter中，在ViewHolder为控件赋值时可以通过Entity的获取类型函数
        * 来进行不同的布局*/
        addItemType(CommonConstants.Shots.VIEW_MODE_SMALL, R.layout.item_shot_small);
        addItemType(CommonConstants.Shots.VIEW_MODE_SMALL_WITH_INFO,R.layout.item_shot_small_with_info);
        addItemType(CommonConstants.Shots.VIEW_MODE_LARGE,R.layout.item_shot_large);
        addItemType(CommonConstants.Shots.VIEW_MODE_LARGE_WITH_INFO,R.layout.item_shot_large_with_info);
    }

    @Override
    protected void convert(BaseViewHolder helper, ShotsEntity item) {
        int smallAvatarSize= (int)mActivity.getResources().getDimension(R.dimen.small_avatar_size);
        int normalAvatarSize= (int)mActivity.getResources().getDimension(R.dimen.normal_avatar_size);
        UserEntity userEntity=item.getUser();
        int type=helper.getItemViewType();
        if(type==CommonConstants.Shots.VIEW_MODE_SMALL_WITH_INFO||
                type==CommonConstants.Shots.VIEW_MODE_LARGE_WITH_INFO){
            helper.setText(R.id.tv_shot_view, StrConvertUtils.formatNumber(item.getViewsCount()));
            helper.setText(R.id.tv_shot_comment,StrConvertUtils.formatNumber(item.getCommentsCount()));
            helper.setText(R.id.tv_shot_like,StrConvertUtils.formatNumber(item.getLikesCount()));
            helper.setText(R.id.tv_author_name,userEntity.getName());
            if(type==CommonConstants.Shots.VIEW_MODE_SMALL_WITH_INFO) {
                FrescoUtils.setAvatar((SimpleDraweeView) helper.getView(R.id.author_avatar),
                        userEntity.getAvatarUrl(),smallAvatarSize);
            }else{
                FrescoUtils.setAvatar((SimpleDraweeView) helper.getView(R.id.author_avatar),
                        userEntity.getAvatarUrl(),normalAvatarSize);
                helper.setText(R.id.tv_shot_title,item.getTitle());
                helper.setText(R.id.tv_shot_date, TimeUtils.getTimeFromISO8601(item.getUpdatedAt()));
            }
            attachToAuthorInfo(helper.getView(R.id.layout_author_info),item);
        }
        if(item.isAnimated()){
            helper.setVisible(R.id.iv_shot_gif, true);
            FrescoUtils.setAnimationDepends((SimpleDraweeView)helper.getView(R.id.shot_preview),item.getImages());
        }else{
            FrescoUtils.setPreview((SimpleDraweeView)helper.getView(R.id.shot_preview),UrlUtils.getLowQualityImageUrl(item.getImages()));
            helper.setVisible(R.id.iv_shot_gif,false);
        }
        attachToShotDetail(helper.getView(R.id.layout_shot_container),item);
    }

    private void attachToAuthorInfo(View view,final ShotsEntity shotsEntity){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                AuthorEntity entity;
                if(shotsEntity.getUser().getType().equals(CommonConstants.User.USER_TYPE_PLAYER)) {
                    intent = new Intent(mActivity, UserProfileActivity.class);
                    entity=shotsEntity.getUser();
                }
                else {
                    intent = new Intent(mActivity, TeamProfileActivity.class);
                    entity=shotsEntity.getTeam();
                }
                intent.putExtra(CommonConstants.Shots.EXTRA_SHOT_AUTHOR,entity);
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
//                IntentUtils.startActivity(mActivity,v,intent);
                mActivity.startActivity(intent);
            }
        });
    }
}
