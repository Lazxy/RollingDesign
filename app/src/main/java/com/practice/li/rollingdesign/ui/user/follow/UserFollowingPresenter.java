package com.practice.li.rollingdesign.ui.user.follow;

import com.practice.li.rollingdesign.entity.FollowingEntity;
import com.practice.li.rollingdesign.mvpframe.base.BaseSubscriber;

import java.util.List;

/**
 * Created by Lazxy on 2017/3/25.
 */

public class UserFollowingPresenter extends UserFollowingContract.Presenter {
    @Override
    void getFollowings(int page) {
        subscribe(mModel.getFollowings(page), new BaseSubscriber<List<FollowingEntity>>(mView) {
            @Override
            protected void onSuccess(List<FollowingEntity> followingEntities) {
                if(null!=followingEntities){
                    mView.updateList(followingEntities);
                }
            }
        });
    }

    @Override
    void unFollowUser(int id, final int position) {
        subscribe(mModel.unFollowUser(id), new BaseSubscriber<String>(mView) {
            @Override
            protected void onSuccess(String s) {
                mView.onUnFollowUserSuccess(position);
            }
        });
    }
}
