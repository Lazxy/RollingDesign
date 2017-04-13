package com.practice.li.rollingdesign.ui.user.bucket.create;

import com.practice.li.rollingdesign.entity.BucketsEntity;
import com.practice.li.rollingdesign.mvpframe.base.BaseModel;
import com.practice.li.rollingdesign.mvpframe.base.BasePresenter;
import com.practice.li.rollingdesign.mvpframe.base.BaseView;

import rx.Observable;

/**
 * Created by Lazxy on 2017/4/2.
 */

public interface BucketCreateContract {
    interface Model extends BaseModel {
        Observable<BucketsEntity> createBucket(String name, String description);
    }

    interface View extends BaseView{
        void onCreateBucketSuccess(BucketsEntity buckets);
    }

    abstract class Presenter extends BasePresenter<Model,View>{
        abstract void createBucket(String name, String description);
    }
}
