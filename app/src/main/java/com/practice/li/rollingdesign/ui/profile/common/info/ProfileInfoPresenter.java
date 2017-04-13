package com.practice.li.rollingdesign.ui.profile.common.info;

import com.practice.li.rollingdesign.entity.TeamEntity;
import com.practice.li.rollingdesign.mvpframe.base.BaseSubscriber;

import java.util.List;

/**
 * Created by Lazxy on 2017/3/27.
 */

public class ProfileInfoPresenter extends ProfileInfoContract.Presenter {
    @Override
    void getTeamInfo(int id) {
        subscribe(mModel.getTeamInfo(id), new BaseSubscriber<List<TeamEntity>>(mView) {
            @Override
            protected void onSuccess(List<TeamEntity> teamEntities) {
                if(teamEntities!=null)
                    mView.updateTeamInfo(teamEntities);
            }
        });
    }
}
