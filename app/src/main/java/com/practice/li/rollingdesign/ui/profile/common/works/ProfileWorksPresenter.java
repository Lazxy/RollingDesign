package com.practice.li.rollingdesign.ui.profile.common.works;

import com.practice.li.rollingdesign.entity.ShotsEntity;
import com.practice.li.rollingdesign.mvpframe.base.BaseSubscriber;

import java.util.List;

/**
 * Created by Lazxy on 2017/3/24.
 */

public class ProfileWorksPresenter extends ProfileWorksContract.Presenter {
    @Override
    void getShotList(int id, int page, boolean isUser) {
        subscribe(mModel.getShotList(id, page, isUser), new BaseSubscriber<List<ShotsEntity>>(mView) {
            @Override
            protected void onSuccess(List<ShotsEntity> shotsEntities) {
                if(shotsEntities!=null)
                    mView.updateList(shotsEntities);
            }
        });
    }
}
