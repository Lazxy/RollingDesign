package com.practice.li.rollingdesign.ui.profile.team.members;

import com.practice.li.rollingdesign.entity.UserEntity;
import com.practice.li.rollingdesign.mvpframe.base.BaseModel;
import com.practice.li.rollingdesign.mvpframe.base.BasePresenter;
import com.practice.li.rollingdesign.mvpframe.base.BaseView;

import java.util.List;

import rx.Observable;

/**
 * Created by Lazxy on 2017/3/28.
 */

public interface ProfileTeamMemberContract  {
    interface Model extends BaseModel{
        Observable<List<UserEntity>> getMembersList(int id , int page);
    }

    interface View extends BaseView{
        void updateData(List<UserEntity> members);
    }

    abstract class Presenter extends BasePresenter<Model,View> {
        abstract void getMembersList(int id,int page);
    }
}
