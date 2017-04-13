package com.practice.li.rollingdesign.ui.user.bucket.detail;

import com.practice.li.rollingdesign.entity.ShotsEntity;
import com.practice.li.rollingdesign.mvpframe.base.BaseModel;
import com.practice.li.rollingdesign.mvpframe.base.BasePresenter;
import com.practice.li.rollingdesign.mvpframe.base.BaseView;

import java.util.HashSet;
import java.util.List;

import rx.Observable;

/**
 * Created by Lazxy on 2017/3/29.
 */

public interface BucketDetailContract {
    interface Model extends BaseModel {
        Observable<String> removeShotFromBucket(int shotId, int id);
        Observable<List<ShotsEntity>> getShotsFromBucket(int id, int page);
    }

    interface View extends BaseView {
        void onRemoveShotsSuccess();
        void updateData(List<ShotsEntity> shots);
    }

    abstract class Presenter extends BasePresenter<Model,View>{
        abstract void removeShotsFromBucket(HashSet<Integer> shots, int id);
        abstract void getShotsFromBucket(int id, int page);
    }
}
