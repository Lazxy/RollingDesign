package com.practice.li.rollingdesign.ui.shots.list;

import com.practice.li.rollingdesign.entity.ShotsEntity;
import com.practice.li.rollingdesign.event.EventChangeTheme;
import com.practice.li.rollingdesign.event.EventChangeViewMode;
import com.practice.li.rollingdesign.mvpframe.base.BaseModel;
import com.practice.li.rollingdesign.mvpframe.base.BasePresenter;
import com.practice.li.rollingdesign.mvpframe.base.BaseView;

import java.util.List;
import java.util.Map;

import rx.Observable;

/**
 * Created by Lazxy on 2017/3/10.
 */

public interface ShotsListContract {
    interface Model extends BaseModel {
        Observable<List<ShotsEntity>> getShotsList(String sort, String type, String timeFrame,int page);
        Map<String,Integer> getAllDefaultConfig();
    }
    interface View extends BaseView{
        void changeViewMode(EventChangeViewMode viewMode);
        void changeTheme(EventChangeTheme theme);
        void updateList(List<ShotsEntity> shotsEntities);
    }
    abstract class Presenter extends BasePresenter<Model,View> {
        abstract void getShotsList(String sort,String type,String timeFrame,int page);
        abstract Map<String,Integer> getAllDefaultConfig();

    }
}
