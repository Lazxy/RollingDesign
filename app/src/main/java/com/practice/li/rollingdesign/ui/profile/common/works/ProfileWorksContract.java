package com.practice.li.rollingdesign.ui.profile.common.works;

import com.practice.li.rollingdesign.entity.ShotsEntity;
import com.practice.li.rollingdesign.mvpframe.base.BaseModel;
import com.practice.li.rollingdesign.mvpframe.base.BasePresenter;
import com.practice.li.rollingdesign.mvpframe.base.BaseView;

import java.util.List;

import rx.Observable;

/**
 * Created by Lazxy on 2017/3/24.
 */

public interface ProfileWorksContract {

    interface Model extends BaseModel {

        Observable<List<ShotsEntity>> getShotList(int id, int page, boolean isUser);

    }

    interface View extends BaseView{
        void updateList(List<ShotsEntity> shots);
    }

    abstract class Presenter extends BasePresenter<Model,View> {

        abstract void getShotList(int id, int page, boolean isUser);

    }
}
