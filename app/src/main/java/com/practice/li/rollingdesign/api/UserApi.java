package com.practice.li.rollingdesign.api;

import com.practice.li.rollingdesign.entity.FollowerEntity;
import com.practice.li.rollingdesign.entity.FollowingEntity;
import com.practice.li.rollingdesign.entity.ShotLikesEntity;
import com.practice.li.rollingdesign.entity.ShotsEntity;
import com.practice.li.rollingdesign.entity.TeamEntity;
import com.practice.li.rollingdesign.entity.UserEntity;

import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by Lazxy on 2017/3/5.
 */

public interface UserApi {

    @GET(ApiConstants.Path.USER_FOLLOWERS)
    Observable<List<FollowerEntity>> getFollowers(@Path("id") String id, @QueryMap Map<String, String> params);

    @GET(ApiConstants.Path.CURRENT_USER_FOLLOWING)
    Observable<List<FollowingEntity>> getCurrentFollowings(@QueryMap Map<String, String> params);

    @GET(ApiConstants.Path.USER_FOLLOWING)
    Observable<List<FollowerEntity>> getFollowings(@Path("id") String id, @QueryMap Map<String, String> params);

    @PUT(ApiConstants.Path.USER_FOLLOW)//return 204 no content
    Observable<String> followUser(@Path("id") String id);

    @DELETE(ApiConstants.Path.USER_FOLLOW)//return 204 no content
    Observable<String> unFollowUser(@Path("id") String id);

    @GET(ApiConstants.Path.USER)
    Observable<UserEntity> getUserInfo();

    @GET(ApiConstants.Path.USER_SHOTS)
    Observable<List<ShotsEntity>> getUserShots(@Path("id") String id, @QueryMap Map<String, String> params);

    @GET(ApiConstants.Path.CHECK_USER_FOLLOWING) //return 204 no content if success ,404 not found if fail
    Call<RequestBody> checkFollowing(@Path("id") String id);

    @GET(ApiConstants.Path.USER_LIKE_SHOTS)
    Observable<List<ShotLikesEntity>> getLikeShots(@Path("id") String id, @QueryMap Map<String,String> params);

    @GET(ApiConstants.Path.USER_TEAM)
    Observable<List<TeamEntity>> getTeamsList(@Path("id") String id);

    @GET(ApiConstants.Path.CURRENT_USER_TEAM)
    Observable<List<TeamEntity>> getCurrentTeamsList();
}
