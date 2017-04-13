package com.practice.li.rollingdesign.ui.shots.detail.comment;

import com.practice.li.rollingdesign.api.ApiClient;
import com.practice.li.rollingdesign.api.ApiConstants;
import com.practice.li.rollingdesign.api.ShotApi;
import com.practice.li.rollingdesign.entity.CheckLikeEntity;
import com.practice.li.rollingdesign.entity.CommentEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;

/**
 * Created by Lazxy on 2017/3/18.
 */

public class CommentModel implements CommentContract.Model {
    @Override
    public Observable<List<CommentEntity>> getCommentList(int id, int page) {
        Map<String, String> params = new HashMap();
        params.put(ApiConstants.ParamKey.PAGE, page + "");
        params.put(ApiConstants.ParamKey.PER_PAGE, ApiConstants.ParamValue.PAGE_SIZE + "");
        return ApiClient.getForInit(ShotApi.class).getComments(id + "", params);
    }

    @Override
    public Observable<CheckLikeEntity> likeComment(int shotId, int commentId) {
        return ApiClient.getForInit(ShotApi.class).likeComment(shotId + "", commentId + "");
    }

    @Override
    public Observable<String> unlikeComment(int shotId, int commentId) {
        return ApiClient.getForInit(ShotApi.class).unlikeComments(shotId + "", commentId + "");
    }

    @Override
    public Observable<CheckLikeEntity> checkCommentLike(int shotId, int commentId) {
        return ApiClient.getForInit(ShotApi.class).checkCommentsLike(shotId + "", commentId + "");
    }

    @Override
    public Observable<CommentEntity> createComment(int shotId, String content) {
        return ApiClient.getForInit(ShotApi.class).createComments(shotId + "", content);
    }

    @Override
    public Observable<String> deleteComment(int shotId, int commentId) {
        return ApiClient.getForInit(ShotApi.class).deleteComments(shotId + "", commentId + "");
    }

}
