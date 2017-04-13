package com.practice.li.rollingdesign.ui.user.like;

import com.practice.li.rollingdesign.entity.ShotLikesEntity;
import com.practice.li.rollingdesign.entity.ShotsEntity;
import com.practice.li.rollingdesign.mvpframe.base.BaseModel;
import com.practice.li.rollingdesign.mvpframe.base.BasePresenter;
import com.practice.li.rollingdesign.mvpframe.base.BaseView;

import java.util.List;

import rx.Observable;

/**
 * Created by Lazxy on 2017/3/25.
 */

public interface UserLikeContract {
    interface Model extends BaseModel {
        Observable<List<ShotLikesEntity>> getLikesList(int id, int page);
    }

    interface View extends BaseView{
        void updateList(List<ShotsEntity> shots);
    }

    abstract class Presenter extends BasePresenter<Model,View> {
        public abstract void getLikesList(int id ,int page);
    }
}
