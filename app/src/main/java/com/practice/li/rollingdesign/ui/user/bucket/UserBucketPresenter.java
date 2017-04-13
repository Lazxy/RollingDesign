package com.practice.li.rollingdesign.ui.user.bucket;

import com.practice.li.rollingdesign.entity.BucketsEntity;
import com.practice.li.rollingdesign.entity.ShotsEntity;
import com.practice.li.rollingdesign.mvpframe.base.BaseSubscriber;

import java.util.List;

/**
 * Created by Lazxy on 2017/3/29.
 */

public class UserBucketPresenter extends UserBucketContract.Presenter {
    @Override
    void getBucketsList(int page) {
        subscribe(mModel.getBucketsList(page), new BaseSubscriber<List<BucketsEntity>>(mView) {
            @Override
            protected void onSuccess(List<BucketsEntity> bucketsEntities) {
                if(bucketsEntities!=null)
                    mView.updateData(bucketsEntities);
            }
        });
    }

    @Override
    void getShotsFromBucket(int id, final int position) {
        subscribe(mModel.getShotsFromBucket(id), new BaseSubscriber<List<ShotsEntity>>(mView) {
            @Override
            protected void onSuccess(List<ShotsEntity> shotsEntities) {
                if(shotsEntities!=null&&shotsEntities.size()==1)
                    mView.loadFirstShot(shotsEntities.get(0), position);
            }
        });
    }
}
