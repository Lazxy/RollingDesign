package com.practice.li.rollingdesign.ui.user.bucket;

import com.practice.li.rollingdesign.api.ApiClient;
import com.practice.li.rollingdesign.api.ApiConstants;
import com.practice.li.rollingdesign.api.BucketApi;
import com.practice.li.rollingdesign.entity.BucketsEntity;
import com.practice.li.rollingdesign.entity.ShotsEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;

/**
 * Created by Lazxy on 2017/3/29.
 */

public class UserBucketModel implements UserBucketContract.Model {
    @Override
    public Observable<List<BucketsEntity>> getBucketsList(int page) {
        Map<String,String> params=new HashMap();
        params.put(ApiConstants.ParamKey.PAGE,page+"");
        params.put(ApiConstants.ParamKey.PER_PAGE,ApiConstants.ParamValue.PAGE_SIZE+"");
        return ApiClient.getForInit(BucketApi.class).getUserBucketsList(params);
    }

    @Override
    public Observable<List<ShotsEntity>> getShotsFromBucket(int id) {
        Map<String,String> params=new HashMap();
        params.put(ApiConstants.ParamKey.PAGE,1+"");
        params.put(ApiConstants.ParamKey.PER_PAGE,1+"");
        return ApiClient.getForInit(BucketApi.class).getShotsFromBucket(id+"",params);
    }
}
