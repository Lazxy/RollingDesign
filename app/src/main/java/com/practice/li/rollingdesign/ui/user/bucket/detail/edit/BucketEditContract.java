package com.practice.li.rollingdesign.ui.user.bucket.detail.edit;

import com.practice.li.rollingdesign.entity.BucketsEntity;
import com.practice.li.rollingdesign.mvpframe.base.BaseModel;
import com.practice.li.rollingdesign.mvpframe.base.BasePresenter;
import com.practice.li.rollingdesign.mvpframe.base.BaseView;

import rx.Observable;

/**
 * Created by Lazxy on 2017/3/30.
 */

public interface BucketEditContract {
    interface Model extends BaseModel{
        Observable<BucketsEntity> updateBucket(String name,String description,int id);
        Observable<String> deleteBucket(int id);
    }

    interface View extends BaseView{
        void onUpdateBucketSuccess(BucketsEntity bucket);
        void onDeleteBucketSuccess();
    }

    abstract class Presenter extends BasePresenter<Model,View> {
        abstract void updateBucket(String name,String description,int id);
        abstract void deleteBucket(int id);
    }
}
