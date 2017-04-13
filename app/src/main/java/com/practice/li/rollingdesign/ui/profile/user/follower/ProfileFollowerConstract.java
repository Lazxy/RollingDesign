package com.practice.li.rollingdesign.ui.profile.user.follower;

import com.practice.li.rollingdesign.entity.FollowerEntity;
import com.practice.li.rollingdesign.mvpframe.base.BaseModel;
import com.practice.li.rollingdesign.mvpframe.base.BasePresenter;
import com.practice.li.rollingdesign.mvpframe.base.BaseView;

import java.util.List;

import rx.Observable;

/**
 * Created by Lazxy on 2017/3/24.
 */

public interface ProfileFollowerConstract {

    interface Model extends BaseModel{

        Observable<List<FollowerEntity>> getFollowers(int id, int page);
    }

    interface View extends BaseView{

        void updateList(List<FollowerEntity> followers);
    }

    abstract class Presenter extends BasePresenter<Model,View>{

        abstract void getFollowers(int id, int page);
    }
}
