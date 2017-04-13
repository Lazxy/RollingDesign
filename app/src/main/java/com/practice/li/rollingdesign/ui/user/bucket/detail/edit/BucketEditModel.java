package com.practice.li.rollingdesign.ui.user.bucket.detail.edit;

import com.practice.li.rollingdesign.api.ApiClient;
import com.practice.li.rollingdesign.api.ApiConstants;
import com.practice.li.rollingdesign.api.BucketApi;
import com.practice.li.rollingdesign.entity.BucketsEntity;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;

/**
 * Created by Lazxy on 2017/3/30.
 */

public class BucketEditModel implements BucketEditContract.Model {
    @Override
    public Observable<BucketsEntity> updateBucket(String name, String description, int id) {
        Map<String,String> params=new HashMap<>();
        params.put(ApiConstants.ParamKey.BUCKET_NAME , name);
        params.put(ApiConstants.ParamKey.BUCKET_DESCRIPTION , description);
        return ApiClient.getForInit(BucketApi.class).updateBucket(id+"",params);
    }

    @Override
    public Observable<String> deleteBucket(int id) {
        return ApiClient.getForInit(BucketApi.class).deleteBucket(id+"");
    }
}
