package com.practice.li.rollingdesign.api;

import com.practice.li.rollingdesign.entity.CheckLikeEntity;
import com.practice.li.rollingdesign.entity.CommentEntity;
import com.practice.li.rollingdesign.entity.ShotsEntity;

import java.util.List;
import java.util.Map;

import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by Lazxy on 2017/3/5.
 */

public interface ShotApi {
    @GET(ApiConstants.Path.SHOTS)
    Observable<List<ShotsEntity>> getShotsList(@QueryMap Map<String, String> params);

    @GET(ApiConstants.Path.SHOTS_DETAIL)
    Observable<ShotsEntity> getShotsDetail(@Path("id") String id);

    @GET(ApiConstants.Path.SHOTS_COMMENTS)
    Observable<List<CommentEntity>> getComments(@Path("id") String id, @QueryMap Map<String, String> params);

    @POST(ApiConstants.Path.SHOTS_CREATE_COMMENTS)
    Observable<CommentEntity> createComments(@Path("shot") String shots, @Query(ApiConstants.ParamKey.BODY) String content);

    @DELETE(ApiConstants.Path.SHOTS_CREATE_COMMENTS)
    Observable<String> deleteComments(@Path("shot") String shots, @Path("id") String id);

    @POST(ApiConstants.Path.SHOTS_COMMENTS_LIKE)
    Observable<CheckLikeEntity> likeComment(@Path("shot") String shot,@Path("id") String id);

    @DELETE(ApiConstants.Path.SHOTS_COMMENTS_LIKE)
    Observable<String> unlikeComments(@Path("shot") String shot,@Path("id") String id);

    @GET(ApiConstants.Path.SHOTS_COMMENTS_LIKE)
    Observable<CheckLikeEntity> checkCommentsLike(@Path("shot") String shots, @Path("id") String id);

    @GET(ApiConstants.Path.SHOTS_LIKE)
    Observable<CheckLikeEntity> checkShotsLike(@Path("id") String id);

    @POST(ApiConstants.Path.SHOTS_LIKE)
    Observable<CheckLikeEntity> likeShots(@Path("id") String id);

    @DELETE(ApiConstants.Path.SHOTS_LIKE)
    Observable<CheckLikeEntity> unlikeShots(@Path("id") String id);

}
