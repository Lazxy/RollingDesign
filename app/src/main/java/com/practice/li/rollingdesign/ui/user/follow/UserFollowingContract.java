package com.practice.li.rollingdesign.ui.user.follow;

import com.practice.li.rollingdesign.entity.FollowingEntity;
import com.practice.li.rollingdesign.mvpframe.base.BaseModel;
import com.practice.li.rollingdesign.mvpframe.base.BasePresenter;
import com.practice.li.rollingdesign.mvpframe.base.BaseView;

import java.util.List;

import rx.Observable;

/**
 * Created by Lazxy on 2017/3/25.
 */

public interface UserFollowingContract {
    interface Model extends BaseModel{
        Observable<List<FollowingEntity>> getFollowings(int page);
        Observable<String> unFollowUser(int id);
    }

    interface View extends BaseView{
        void updateList(List<FollowingEntity> followings);
        void onUnFollowUserSuccess(int position);
    }

    abstract class Presenter extends BasePresenter<Model,View> {
        abstract void getFollowings(int page);
        abstract void unFollowUser(int id,int position);
    }
}
