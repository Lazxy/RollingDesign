package com.practice.li.rollingdesign.ui.profile.common.works;


import com.practice.li.rollingdesign.api.ApiClient;
import com.practice.li.rollingdesign.api.ApiConstants;
import com.practice.li.rollingdesign.api.TeamApi;
import com.practice.li.rollingdesign.api.UserApi;
import com.practice.li.rollingdesign.entity.ShotsEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;

/**
 * Created by Lazxy on 2017/3/24.
 */

public class ProfileWorksModel implements ProfileWorksContract.Model {
    @Override
    public Observable<List<ShotsEntity>> getShotList(int id, int page ,boolean isUser) {
        Map<String,String> params=new HashMap<>();
        params.put(ApiConstants.ParamKey.PAGE,page+"");
        params.put(ApiConstants.ParamKey.PER_PAGE,ApiConstants.ParamValue.PAGE_SIZE+"");
        if(isUser)
            return ApiClient.getForInit(UserApi.class).getUserShots(id+"",params);
        else
            return ApiClient.getForInit(TeamApi.class).getShots(id+"", params);
    }
}
