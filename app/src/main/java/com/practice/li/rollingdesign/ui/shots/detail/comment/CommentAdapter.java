package com.practice.li.rollingdesign.ui.shots.detail.comment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.practice.li.rollingdesign.R;
import com.practice.li.rollingdesign.common.CommonConstants;
import com.practice.li.rollingdesign.entity.CommentEntity;
import com.practice.li.rollingdesign.entity.UserEntity;
import com.practice.li.rollingdesign.ui.profile.user.UserProfileActivity;
import com.practice.li.rollingdesign.utils.FrescoUtils;
import com.practice.li.rollingdesign.utils.HtmlFormatUtils;
import com.practice.li.rollingdesign.utils.NetworkUtils;
import com.practice.li.rollingdesign.utils.TimeUtils;
import com.practice.li.rollingdesign.utils.UIUtils;
import com.practice.li.rollingdesign.utils.UserInfoUtils;

/**
 * Created by Lazxy on 2017/3/18.
 */

public class CommentAdapter extends BaseQuickAdapter<CommentEntity,BaseViewHolder> {

    private Context mContext;
    private CommentContract.Presenter mPresenter;
    private Drawable likeDrawable,unlikeDrawable;

    public CommentAdapter(CommentContract.Presenter presenter) {
        super(R.layout.item_comment);
        mContext=presenter.getContext();
        mPresenter=presenter;
        likeDrawable=mContext.getResources().getDrawable(R.drawable.iv_like_blue_16dp);
        likeDrawable.setBounds(0,0,likeDrawable.getMinimumWidth(),likeDrawable.getMinimumHeight());
        unlikeDrawable=mContext.getResources().getDrawable(R.drawable.iv_like_grey_16dp);
        unlikeDrawable.setBounds(0,0,unlikeDrawable.getMinimumWidth(),unlikeDrawable.getMinimumHeight());
    }

    @Override
    protected void convert(BaseViewHolder helper, CommentEntity item) {
        helper.setText(R.id.tv_comment_name,item.getUser().getName());
        helper.setText(R.id.tv_comment_time_gap, TimeUtils.getTimeFromISO8601(item.getUpdatedAt()));
        helper.setText(R.id.tv_comment_like,item.getLikesCount()+"");
        TextView content=helper.getView(R.id.tv_comment_content);
        HtmlFormatUtils.Html2StringNoP(content,item.getBody());
        TextView like=helper.getView(R.id.tv_comment_like);
        if(like.getCurrentTextColor()==mContext.getResources().getColor(R.color.text_blue))
        {
            //这里helper中有七个convertView供复用，而当某一个的属性被改变时，再下次复用时还会保留被改变的属性，所以
            //这里需要重置一下其属性。
            like.setTextColor(mContext.getResources().getColor(R.color.text_grey));
            like.setCompoundDrawables(unlikeDrawable, null, null, null);
        }
        if(item.getIsLiked()){
            like.setCompoundDrawables(likeDrawable
                    ,null,null,null);
            like.setTextColor(mContext.getResources().getColor(R.color.text_blue));
        }
        SimpleDraweeView avatar=helper.getView(R.id.comment_avatar);
        FrescoUtils.setAvatar(avatar,item.getUser().getAvatarUrl(),
                (int)mContext.getResources().getDimension(R.dimen.normal_avatar_size));
        TextView deleteComment=helper.getView(R.id.tv_comment_delete);
        if(UserInfoUtils.getCurrentUser()!=null&&item.getUser().getId() == UserInfoUtils.getCurrentUser().getId() ){
            deleteComment.setVisibility(View.VISIBLE);
        }else{
            deleteComment.setVisibility(View.GONE);
        }
        attachToAuthorInfo(avatar,item.getUser());
        attachChangeStatus(like,item);
        attachDeleteComment(deleteComment,item);

    }


    private void attachToAuthorInfo(View view,final UserEntity userEntity){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext,UserProfileActivity.class);
                intent.putExtra(CommonConstants.Shots.EXTRA_SHOT_AUTHOR,userEntity);
                mContext.startActivity(intent);
            }
        });
    }
    private void attachChangeStatus(View view,final CommentEntity comment){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mPresenter.getShouldLogin()) {
                    if(NetworkUtils.getIsNetWorking()) {
                        boolean isLiked = comment.getIsLiked();
                        comment.setIsLiked(!isLiked);
                        if (isLiked) {
                            mPresenter.unlikeComment(comment);
                            ((TextView) v).setCompoundDrawables(unlikeDrawable, null, null, null);
                            ((TextView) v).setTextColor(mContext.getResources().getColor(R.color.text_grey));
                            ((TextView) v).setText(Integer.decode(((TextView) v).getText().toString()) - 1 + "");
                        } else {
                            mPresenter.likeComment(comment);
                            ((TextView) v).setCompoundDrawables(likeDrawable, null, null, null);
                            ((TextView) v).setTextColor(mContext.getResources().getColor(R.color.text_blue));
                            ((TextView) v).setText(Integer.decode(((TextView) v).getText().toString()) + 1 + "");
                        }
                    }else{
                        Toast.makeText(mContext,"网络好像不太好哦",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(mContext,"请先登录",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void attachDeleteComment(View view,final CommentEntity commentEntity){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIUtils.showSimpleAlertDialog(mContext, null, "确认删除此条评论吗？", "删除", "取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mPresenter.deleteComment(commentEntity.getId());
                    }
                },null);
            }
        });
    }

}
