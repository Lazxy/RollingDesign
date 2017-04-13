package com.practice.li.rollingdesign.ui.user.bucket.detail;

import com.practice.li.rollingdesign.api.ApiClient;
import com.practice.li.rollingdesign.api.ApiConstants;
import com.practice.li.rollingdesign.api.BucketApi;
import com.practice.li.rollingdesign.entity.ShotsEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;

/**
 * Created by Lazxy on 2017/3/29.
 */

public class BucketDetailModel implements BucketDetailContract.Model {
    @Override
    public Observable<String> removeShotFromBucket(int shotId, int id) {
        return ApiClient.getForInit(BucketApi.class).removeShotFromBucket(id+"",shotId+"");
    }

    @Override
    public Observable<List<ShotsEntity>> getShotsFromBucket(int id, int page) {
        Map<String,String> params=new HashMap();
        params.put(ApiConstants.ParamKey.PAGE,page+"");
        params.put(ApiConstants.ParamKey.PER_PAGE,ApiConstants.ParamValue.PAGE_SIZE+"");
        return ApiClient.getForInit(BucketApi.class).getShotsFromBucket(id+"",params);
    }
}
