package com.practice.li.rollingdesign.ui.profile.user.follower;

import com.practice.li.rollingdesign.entity.FollowerEntity;
import com.practice.li.rollingdesign.mvpframe.base.BaseSubscriber;

import java.util.List;

/**
 * Created by Lazxy on 2017/3/24.
 */

public class ProfileFollowerPresenter extends ProfileFollowerConstract.Presenter {

    @Override
    void getFollowers(int id, int page) {
        subscribe(mModel.getFollowers(id, page), new BaseSubscriber<List<FollowerEntity>>(mView) {
            @Override
            protected void onSuccess(List<FollowerEntity> followerEntities) {
                if(followerEntities!=null){
                    mView.updateList(followerEntities);
                }
            }
        });
    }
}
