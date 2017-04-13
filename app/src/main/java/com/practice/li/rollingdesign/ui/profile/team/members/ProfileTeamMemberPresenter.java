package com.practice.li.rollingdesign.ui.profile.team.members;

import com.practice.li.rollingdesign.entity.UserEntity;
import com.practice.li.rollingdesign.mvpframe.base.BaseSubscriber;

import java.util.List;

/**
 * Created by Lazxy on 2017/3/28.
 */

public class ProfileTeamMemberPresenter extends ProfileTeamMemberContract.Presenter {
    @Override
    void getMembersList(int id, int page) {
        subscribe(mModel.getMembersList(id, page), new BaseSubscriber<List<UserEntity>>(mView) {
            @Override
            protected void onSuccess(List<UserEntity> userEntities) {
                if(userEntities!=null){
                    mView.updateData(userEntities);
                }
            }
        });
    }
}
