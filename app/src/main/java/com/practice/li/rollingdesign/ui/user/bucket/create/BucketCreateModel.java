package com.practice.li.rollingdesign.ui.user.bucket.create;

import com.practice.li.rollingdesign.api.ApiClient;
import com.practice.li.rollingdesign.api.ApiConstants;
import com.practice.li.rollingdesign.api.BucketApi;
import com.practice.li.rollingdesign.entity.BucketsEntity;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;

/**
 * Created by Lazxy on 2017/4/2.
 */

public class BucketCreateModel implements BucketCreateContract.Model {
    @Override
    public Observable<BucketsEntity> createBucket(String name, String description) {
        Map<String,String> params=new HashMap<>();
        params.put(ApiConstants.ParamKey.BUCKET_NAME,name);
        params.put(ApiConstants.ParamKey.BUCKET_DESCRIPTION,description);
        return ApiClient.getForInit(BucketApi.class).createBucket(params);
    }
}
