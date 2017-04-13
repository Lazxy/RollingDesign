package com.practice.li.rollingdesign.ui.user.bucket.create;

import com.practice.li.rollingdesign.entity.BucketsEntity;
import com.practice.li.rollingdesign.mvpframe.base.BaseSubscriber;

/**
 * Created by Lazxy on 2017/4/2.
 */

public class BucketCreatePresenter extends BucketCreateContract.Presenter {
    @Override
    void createBucket(String name, String description) {
        subscribe(mModel.createBucket(name, description), new BaseSubscriber<BucketsEntity>(mView) {
            @Override
            protected void onSuccess(BucketsEntity bucketsEntity) {
                if(bucketsEntity!=null)
                    mView.onCreateBucketSuccess(bucketsEntity);
            }
        });
    }
}
