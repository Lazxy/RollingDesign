package com.practice.li.rollingdesign.ui.user.bucket;

import com.practice.li.rollingdesign.entity.BucketsEntity;
import com.practice.li.rollingdesign.entity.ShotsEntity;
import com.practice.li.rollingdesign.mvpframe.base.BaseModel;
import com.practice.li.rollingdesign.mvpframe.base.BasePresenter;
import com.practice.li.rollingdesign.mvpframe.base.BaseView;

import java.util.List;

import rx.Observable;

/**
 * Created by Lazxy on 2017/3/29.
 */

public interface UserBucketContract {
    interface Model extends BaseModel{
        Observable<List<BucketsEntity>> getBucketsList(int page);
        Observable<List<ShotsEntity>> getShotsFromBucket(int id);
    }

    interface View extends BaseView{
        void updateData(List<BucketsEntity> buckets);
        void loadFirstShot(ShotsEntity shot, int position);
    }

    abstract class Presenter extends BasePresenter<Model,View>{
        abstract void getBucketsList(int page);
        abstract void getShotsFromBucket(int id, int position);
    }
}
