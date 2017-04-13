package com.practice.li.rollingdesign.ui.shots.list;

import android.util.Log;

import com.practice.li.rollingdesign.entity.ShotsEntity;
import com.practice.li.rollingdesign.mvpframe.base.BaseSubscriber;

import java.util.List;
import java.util.Map;

/**
 * Created by Lazxy on 2017/3/10.
 */

public class ShotsListPresenter extends ShotsListContract.Presenter {
    @Override
    public void getShotsList(String sort, String type, String timeFrame,int page) {
        Log.e("GET_LIST","Start get List!");
        subscribe(mModel.getShotsList(sort, type, timeFrame,page), new BaseSubscriber<List<ShotsEntity>>(mView) {
            @Override
            protected void onSuccess(List<ShotsEntity> shotsEntities) {
                    mView.updateList(shotsEntities);
            }
        });
    }

    @Override
    public Map<String, Integer> getAllDefaultConfig() {
        return mModel.getAllDefaultConfig();
    }
}
