package com.practice.li.rollingdesign.ui.main;

import com.practice.li.rollingdesign.entity.UserEntity;
import com.practice.li.rollingdesign.mvpframe.base.BaseModel;
import com.practice.li.rollingdesign.mvpframe.base.BasePresenter;
import com.practice.li.rollingdesign.mvpframe.base.BaseView;

import rx.Observable;


/**
 * Created by Lazxy on 2017/3/7.
 */

public interface MainContract {
    interface Model extends BaseModel {
        Observable<UserEntity> getUserInfo();
    }

    interface View extends BaseView {
        void setUser(UserEntity user);
    }

    abstract class Presenter extends BasePresenter<Model,View> {
        abstract void getUserInfo();
    }
}
