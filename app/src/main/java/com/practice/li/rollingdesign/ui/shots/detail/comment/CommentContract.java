package com.practice.li.rollingdesign.ui.shots.detail.comment;

import com.practice.li.rollingdesign.entity.CheckLikeEntity;
import com.practice.li.rollingdesign.entity.CommentEntity;
import com.practice.li.rollingdesign.mvpframe.base.BaseModel;
import com.practice.li.rollingdesign.mvpframe.base.BasePresenter;
import com.practice.li.rollingdesign.mvpframe.base.BaseView;

import java.util.List;

import rx.Observable;

/**
 * Created by Lazxy on 2017/3/13.
 */

public interface CommentContract {

    interface Model extends BaseModel{
        Observable<List<CommentEntity>> getCommentList(int shotId,int page);
        Observable<CheckLikeEntity> likeComment(int shotId,int commentId);
        Observable<String> unlikeComment(int shotId,int commentId);
        Observable<CheckLikeEntity> checkCommentLike(int shotId,int commentId);
        Observable<CommentEntity> createComment(int shotId,String content);
        Observable<String> deleteComment(int shotId,int commentId);
    }

    interface View extends BaseView {
        void setCommentData(List<CommentEntity> comments);
        void onChangeStatusSuccess(boolean isLike,CommentEntity comment);
        void onCreateCommentSuccess(CommentEntity entity);
        void onCreateCommentFail();
        void onDeleteCommentSuccess();
    }

    abstract class Presenter extends BasePresenter<Model,View> {
        abstract void getCommentList(int id,int page);
        abstract void likeComment(CommentEntity comment);
        abstract void unlikeComment(CommentEntity comment);
        abstract void checkCommentsLike(List<CommentEntity> comments);
        abstract void createComment(String content);
        abstract void deleteComment(int commentId);
        abstract void setShotId(int shotId);
        abstract void setShouldLogin(boolean shouldLogin);
        abstract boolean getShouldLogin();
    }
}
