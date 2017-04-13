package com.practice.li.rollingdesign.ui.user.team;

import com.practice.li.rollingdesign.entity.TeamEntity;
import com.practice.li.rollingdesign.mvpframe.base.BaseModel;
import com.practice.li.rollingdesign.mvpframe.base.BasePresenter;
import com.practice.li.rollingdesign.mvpframe.base.BaseView;

import java.util.List;

import rx.Observable;

/**
 * Created by Lazxy on 2017/3/27.
 */

public interface UserTeamsContract {
    interface Model extends BaseModel {
        Observable<List<TeamEntity>> getTeamsList();
    }

    interface View extends BaseView{
        void updateData(List<TeamEntity> teams);
    }

    abstract class Presenter extends BasePresenter<Model,View>{
        abstract void getTeamsList();
    }
}
