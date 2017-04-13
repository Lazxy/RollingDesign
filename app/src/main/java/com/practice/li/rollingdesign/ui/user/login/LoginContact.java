package com.practice.li.rollingdesign.ui.user.login;

import com.practice.li.rollingdesign.entity.TokenEntity;
import com.practice.li.rollingdesign.entity.UserEntity;
import com.practice.li.rollingdesign.mvpframe.base.BaseModel;
import com.practice.li.rollingdesign.mvpframe.base.BasePresenter;
import com.practice.li.rollingdesign.mvpframe.base.BaseView;

import rx.Observable;

/**
 * Created by Lazxy on 2017/3/8.
 */

public interface LoginContact {
    interface Model extends BaseModel {
        Observable<TokenEntity> requestToken(String code);
        Observable<UserEntity> getUserInfo();
    }
    interface View extends BaseView{

    }
    abstract class Presenter extends BasePresenter<Model,View>{
        abstract void requestToken(String code);
        abstract void getUserInfo();
    }
}
