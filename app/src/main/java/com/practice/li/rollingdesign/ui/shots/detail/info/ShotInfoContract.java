package com.practice.li.rollingdesign.ui.shots.detail.info;

import com.practice.li.rollingdesign.entity.CheckLikeEntity;
import com.practice.li.rollingdesign.entity.ShotsEntity;
import com.practice.li.rollingdesign.mvpframe.base.BaseModel;
import com.practice.li.rollingdesign.mvpframe.base.BasePresenter;
import com.practice.li.rollingdesign.mvpframe.base.BaseView;

import java.util.List;

import rx.Observable;

/**
 * Created by Lazxy on 2017/3/15.
 */

public interface ShotInfoContract {

    interface Model extends BaseModel {

        Observable<CheckLikeEntity> checkShotsLike(int id);

        Observable<ShotsEntity> getShotDetail(int id);

        Observable<CheckLikeEntity> changeShotsStatus(int id, boolean isLike);

        Observable<String> addShotsToBuckets(int bucketsId, int shotsId);
    }

    interface View extends BaseView {

        void checkShotsLikeOnSuccess(boolean isLiked , int likeCount);

        void changeShotsLikeStatusOnSuccess(boolean isLiked);

        void addShotsToBucketsOnSuccess(int size);
    }

    abstract class Presenter extends BasePresenter<Model, View> {

        abstract void checkShotsLike(int id);

        abstract void changeShotsStatus(int id, boolean isLike);

        abstract void addShotsToBuckets(List<Integer> bucketsId, int shotsId);

        abstract void getShotDetail(int id );

    }
}
