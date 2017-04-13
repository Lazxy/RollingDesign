package com.practice.li.rollingdesign.ui.user.like;

import com.practice.li.rollingdesign.entity.ShotLikesEntity;
import com.practice.li.rollingdesign.entity.ShotsEntity;
import com.practice.li.rollingdesign.mvpframe.base.BaseSubscriber;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lazxy on 2017/3/25.
 */

public class UserLikePresenter extends UserLikeContract.Presenter {
    @Override
    public void getLikesList(int id, int page) {
        subscribe(mModel.getLikesList(id, page), new BaseSubscriber<List<ShotLikesEntity>>(mView) {
            @Override
            protected void onSuccess(List<ShotLikesEntity> shotLikesEntities) {
                if(shotLikesEntities!=null){
                    List<ShotsEntity> shots=new ArrayList<>();
                    for(ShotLikesEntity item : shotLikesEntities)
                        shots.add(item.getShot());
                    mView.updateList(shots);
                }
            }
        });
    }
}
