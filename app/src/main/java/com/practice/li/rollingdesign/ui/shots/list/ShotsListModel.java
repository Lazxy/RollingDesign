package com.practice.li.rollingdesign.ui.shots.list;

import com.practice.li.rollingdesign.api.ApiClient;
import com.practice.li.rollingdesign.api.ApiConstants;
import com.practice.li.rollingdesign.api.ShotApi;
import com.practice.li.rollingdesign.common.ConfigManager;
import com.practice.li.rollingdesign.entity.ShotsEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;

/**
 * Created by Lazxy on 2017/3/10.
 */

public class ShotsListModel implements ShotsListContract.Model {
    @Override
    public Observable<List<ShotsEntity>> getShotsList(String sort, String type, String timeFrame,int page) {
        Map<String,String> params=new HashMap<>();
        params.put(ApiConstants.ParamKey.TYPE,type);
        params.put(ApiConstants.ParamKey.SORT,sort);
        params.put(ApiConstants.ParamKey.TIME_FRAME,timeFrame);
        params.put(ApiConstants.ParamKey.PAGE,page+"");
        params.put(ApiConstants.ParamKey.PER_PAGE,ApiConstants.ParamValue.PAGE_SIZE+"");
        return ApiClient.getForInit(ShotApi.class).getShotsList(params);
    }

    @Override
    public Map<String, Integer> getAllDefaultConfig() {
        Map<String,Integer> params=new HashMap<>();
        params.put(ApiConstants.ParamKey.SORT, ConfigManager.Shot.getShotSort());
        params.put(ApiConstants.ParamKey.TYPE, ConfigManager.Shot.getShotType());
        params.put(ApiConstants.ParamKey.TIME_FRAME, ConfigManager.Shot.getShotTimeFrame());
        return params;
    }
}
