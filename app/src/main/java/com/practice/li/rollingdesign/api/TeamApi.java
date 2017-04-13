package com.practice.li.rollingdesign.api;

import com.practice.li.rollingdesign.entity.ShotsEntity;
import com.practice.li.rollingdesign.entity.UserEntity;

import java.util.List;
import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by Lazxy on 2017/3/5.
 */

public interface TeamApi {
    @GET(ApiConstants.Path.TEAM_SHOTS)
    Observable<List<ShotsEntity>> getShots(@Path("id") String id, @QueryMap Map<String, String> params);

    @GET(ApiConstants.Path.TEAM_MEMBERS)
    Observable<List<UserEntity>> getMembers(@Path("id") String id, @QueryMap Map<String, String> params);
}
