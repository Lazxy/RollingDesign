package com.practice.li.rollingdesign.api;

import com.practice.li.rollingdesign.entity.BucketsEntity;
import com.practice.li.rollingdesign.entity.ShotsEntity;

import java.util.List;
import java.util.Map;

import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;


/**
 * Created by Lazxy on 2017/3/29.
 */

public interface BucketApi {
    @GET(ApiConstants.Path.BUCKETS_DETAIL)
    Observable<List<ShotsEntity>> getBucketsDetail(@Path("id") String id, @QueryMap Map<String, String> params);

    @PUT(ApiConstants.Path.ADD_SHOTS_TO_BUCKETS)
    Observable<String> addShotsToBuckets(@Path("id") String id, @QueryMap Map<String, String> params);

    @DELETE (ApiConstants.Path.SHOTS_FROM_BUCKET)
    Observable<String> removeShotFromBucket(@Path("id") String bucketId , @Query("shot_id") String shotId);

    @GET (ApiConstants.Path.SHOTS_FROM_BUCKET)
    Observable<List<ShotsEntity>> getShotsFromBucket(@Path("id") String id ,@QueryMap Map<String,String> params);

    @GET(ApiConstants.Path.USER_BUCKETS)
    Observable<List<BucketsEntity>> getBucketsList(@Path("id") String id, @QueryMap Map<String,String> params);

    @GET(ApiConstants.Path.CURRENT_USER_BUCKETS)
    Observable<List<BucketsEntity>> getUserBucketsList(@QueryMap Map<String,String> params);

    @POST(ApiConstants.Path.CREATE_BUCKET)
    Observable<BucketsEntity> createBucket(@QueryMap Map<String,String> params);

    @PUT(ApiConstants.Path.MANAGE_BUCKET)
    Observable<BucketsEntity> updateBucket(@Path("id") String id,@QueryMap Map<String,String> params);

    @DELETE(ApiConstants.Path.MANAGE_BUCKET)
    Observable<String> deleteBucket(@Path("id") String id);

}
