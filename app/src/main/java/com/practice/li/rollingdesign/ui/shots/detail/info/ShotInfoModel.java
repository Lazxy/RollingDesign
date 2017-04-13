package com.practice.li.rollingdesign.ui.shots.detail.info;

import com.practice.li.rollingdesign.api.ApiClient;
import com.practice.li.rollingdesign.api.ApiConstants;
import com.practice.li.rollingdesign.api.BucketApi;
import com.practice.li.rollingdesign.api.ShotApi;
import com.practice.li.rollingdesign.entity.CheckLikeEntity;
import com.practice.li.rollingdesign.entity.ShotsEntity;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;

/**
 * Created by Lazxy on 2017/3/16.
 */

public class ShotInfoModel implements ShotInfoContract.Model {
    @Override
    public Observable<CheckLikeEntity> checkShotsLike(int id) {
        return ApiClient.getForInit(ShotApi.class).checkShotsLike(id+"");
    }

    @Override
    public Observable<ShotsEntity> getShotDetail(int id) {
        return ApiClient.getForInit(ShotApi.class).getShotsDetail(id+"");
    }

    @Override
    public Observable<CheckLikeEntity> changeShotsStatus(int id, boolean isLike) {
        if(isLike)
            return ApiClient.getForInit(ShotApi.class).likeShots(id+"");
        else
            return ApiClient.getForInit(ShotApi.class).unlikeShots(id+"");

    }

    @Override
    public Observable<String> addShotsToBuckets(int bucketsId, int shotsId) {
        Map<String,String> params=new HashMap<>();
        params.put(ApiConstants.ParamKey.SHOTS_ID,shotsId+"");
        return ApiClient.getForInit(BucketApi.class).addShotsToBuckets(bucketsId+"",params);
    }
}
