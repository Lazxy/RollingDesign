package com.practice.li.rollingdesign.ui.user.team;

import com.practice.li.rollingdesign.entity.TeamEntity;
import com.practice.li.rollingdesign.mvpframe.base.BaseSubscriber;

import java.util.List;

/**
 * Created by Lazxy on 2017/3/27.
 */

public class UserTeamsPresenter extends UserTeamsContract.Presenter {
    @Override
    void getTeamsList() {
        subscribe(mModel.getTeamsList(), new BaseSubscriber<List<TeamEntity>>(mView) {
            @Override
            protected void onSuccess(List<TeamEntity> teamEntities) {
                if(teamEntities!=null)
                    mView.updateData(teamEntities);
            }
        });
    }
}
