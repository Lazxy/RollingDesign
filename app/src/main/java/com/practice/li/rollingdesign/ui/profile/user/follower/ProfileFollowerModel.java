package com.practice.li.rollingdesign.ui.profile.user.follower;

import com.practice.li.rollingdesign.api.ApiClient;
import com.practice.li.rollingdesign.api.ApiConstants;
import com.practice.li.rollingdesign.api.UserApi;
import com.practice.li.rollingdesign.entity.FollowerEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;

/**
 * Created by Lazxy on 2017/3/24.
 */

public class ProfileFollowerModel implements ProfileFollowerConstract.Model {

    @Override
    public Observable<List<FollowerEntity>> getFollowers(int id, int page) {
        Map<String,String> params=new HashMap<>();
        params.put(ApiConstants.ParamKey.PAGE,page+"");
        params.put(ApiConstants.ParamKey.PER_PAGE,ApiConstants.ParamValue.PAGE_SIZE+"");
        return ApiClient.getForInit(UserApi.class).getFollowers(id+"",params);
    }
}
