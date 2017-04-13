package com.practice.li.rollingdesign.ui.user.follow;

import com.practice.li.rollingdesign.api.ApiClient;
import com.practice.li.rollingdesign.api.ApiConstants;
import com.practice.li.rollingdesign.api.UserApi;
import com.practice.li.rollingdesign.entity.FollowingEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;

/**
 * Created by Lazxy on 2017/3/25.
 */

public class UserFollowingModel implements UserFollowingContract.Model {
    @Override
    public Observable<List<FollowingEntity>> getFollowings(int page) {
        Map<String,String> params=new HashMap<>();
        params.put(ApiConstants.ParamKey.PAGE,page+"");
        params.put(ApiConstants.ParamKey.PER_PAGE,ApiConstants.ParamValue.PAGE_SIZE+"");
        return ApiClient.getForInit(UserApi.class).getCurrentFollowings(params);
    }

    @Override
    public Observable<String> unFollowUser(int id) {
        return ApiClient.getForInit(UserApi.class).unFollowUser(id+"");
    }
}
