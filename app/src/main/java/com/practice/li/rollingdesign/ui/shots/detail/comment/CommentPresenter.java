package com.practice.li.rollingdesign.ui.shots.detail.comment;

import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;

import com.practice.li.rollingdesign.entity.CheckLikeEntity;
import com.practice.li.rollingdesign.entity.CommentEntity;
import com.practice.li.rollingdesign.mvpframe.base.BaseSubscriber;

import java.util.List;

/**
 * Created by Lazxy on 2017/3/18.
 */

public class CommentPresenter extends CommentContract.Presenter {

    private int mShotId;
    private boolean mShouldLogin;

    private int requestCount=0;
    @Override
    void getCommentList(final int id, int page) {
        subscribe(mModel.getCommentList(id, page), new BaseSubscriber<List<CommentEntity>>(mView) {
            @Override
            protected void onSuccess(List<CommentEntity> commentEntities) {
                if (commentEntities != null) {
//                    checkCommentsLike(commentEntities);
                    mView.setCommentData(commentEntities);
                }
            }
            @Override
            public void onError(Throwable e){
                if(!e.getMessage().contains("404")){
                    mView.onRequestError(e.getMessage());
                }
            }
        });
    }

    @Override
    void likeComment( final CommentEntity comment) {
        subscribe(mModel.likeComment(mShotId, comment.getId()), new BaseSubscriber<CheckLikeEntity>(mView) {
            @Override
            protected void onSuccess(CheckLikeEntity checkLikeEntity) {
                if(!TextUtils.isEmpty(checkLikeEntity.getCreatedAt())){
                    mView.onChangeStatusSuccess(true,comment);
                }else{
                    mView.onChangeStatusSuccess(false,comment);
                }
            }
        });
    }

    @Override
    void unlikeComment( final CommentEntity comment) {
        subscribe(mModel.unlikeComment(mShotId, comment.getId()), new BaseSubscriber<String>(mView) {
            @Override
            protected void onSuccess(String response) {
                if(!TextUtils.isEmpty(response)){
                    mView.onChangeStatusSuccess(true,comment);
                }else{
                    mView.onChangeStatusSuccess(false,comment);
                }
            }
        });
    }


    /**
     * 依次检查每个评论是否被Like，因为会占用非常多的request次数，所以暂弃。
     * @param comments
     */
    @Override
    void checkCommentsLike(final List<CommentEntity> comments) {
        final SparseArray<CommentEntity> likeMap=new SparseArray<>();
        for(int i=0;i<comments.size();i++){
            CommentEntity comment=comments.get(i);
            if(comment.getLikesCount()>0){
                likeMap.append(likeMap.size(),comment);
            }
        }
        if(likeMap.size()==0){
            mView.setCommentData(comments);
        }
        for(int k=0;k<likeMap.size();k++) {
            final int index=k;
            if(likeMap.get(index)==null){
                Log.e("ERROR IN REQUEST","index is :"+index );
                continue;
            }
            subscribe(mModel.checkCommentLike(mShotId,likeMap.get(index).getId()), new BaseSubscriber<CheckLikeEntity>(mView) {
                @Override
                protected void onSuccess(CheckLikeEntity checkLikeEntity) {
                    if (checkLikeEntity != null && !TextUtils.isEmpty(checkLikeEntity.getCreatedAt())) {
                    /*想办法解决不同步回调问题*/
                        CommentEntity resultComment=likeMap.get(index);
                                resultComment.setIsLiked(true);
                    }
                    if(++requestCount==likeMap.size()){
                        mView.setCommentData(comments);
                        likeMap.clear();
                        requestCount=0;
                    }
                }
                @Override
                public void onError(Throwable e){
                    if(++requestCount==likeMap.size()){
                        mView.setCommentData(comments);
                        likeMap.clear();
                        requestCount=0;
                    }
                }
            });
        }
    }

    @Override
    void createComment(String content) {
        subscribe(mModel.createComment(mShotId, content), new BaseSubscriber<CommentEntity>(mView) {
            @Override
            protected void onSuccess(CommentEntity commentEntity) {
                if(commentEntity!=null){
                    mView.onCreateCommentSuccess(commentEntity);
                }
            }

            @Override
            public void onError(Throwable t){
                mView.onCreateCommentFail();
            }
        });
    }

    @Override
    void deleteComment(int commentId) {
        subscribe(mModel.deleteComment(mShotId, commentId), new BaseSubscriber<String>(mView) {
            @Override
            protected void onSuccess(String response) {
                if(response!=null){
                    mView.onDeleteCommentSuccess();
                }
            }
        });
    }

    @Override
    void setShotId(int shotId) {
        mShotId=shotId;
    }


    @Override
    void  setShouldLogin(boolean shouldLogin) {
        mShouldLogin=shouldLogin;
    }

    @Override
    boolean  getShouldLogin() {
        return mShouldLogin;
    }
}
