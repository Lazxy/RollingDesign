package com.practice.li.rollingdesign.ui.profile.common.info;

import com.practice.li.rollingdesign.entity.TeamEntity;
import com.practice.li.rollingdesign.mvpframe.base.BaseModel;
import com.practice.li.rollingdesign.mvpframe.base.BasePresenter;
import com.practice.li.rollingdesign.mvpframe.base.BaseView;

import java.util.List;

import rx.Observable;

/**
 * Created by Lazxy on 2017/3/27.
 */

public interface ProfileInfoContract {
    interface Model extends BaseModel {
        Observable<List<TeamEntity>> getTeamInfo(int id);
    }

    interface View extends BaseView {
        void updateTeamInfo(List<TeamEntity> teams);
    }

    abstract class Presenter extends BasePresenter<Model, View> {
        abstract void getTeamInfo(int id);
    }
}
