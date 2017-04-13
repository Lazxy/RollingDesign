package com.practice.li.rollingdesign.ui.user.bucket.detail.edit;

import com.practice.li.rollingdesign.entity.BucketsEntity;
import com.practice.li.rollingdesign.mvpframe.base.BaseSubscriber;

/**
 * Created by Lazxy on 2017/3/30.
 */

public class BucketEditPresenter extends BucketEditContract.Presenter {
    @Override
    void updateBucket(String name, String description, int id) {
        subscribe(mModel.updateBucket(name, description, id), new BaseSubscriber<BucketsEntity>(mView) {
            @Override
            protected void onSuccess(BucketsEntity bucketsEntity) {
                    mView.onUpdateBucketSuccess(bucketsEntity);
            }
        });
    }

    @Override
    void deleteBucket(int id) {
        subscribe(mModel.deleteBucket(id), new BaseSubscriber<String>(mView) {
            @Override
            protected void onSuccess(String s) {
                mView.onDeleteBucketSuccess();
            }
        });
    }
}
