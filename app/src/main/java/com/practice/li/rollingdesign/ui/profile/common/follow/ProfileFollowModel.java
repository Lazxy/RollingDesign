package com.practice.li.rollingdesign.ui.profile.common.follow;

import com.practice.li.rollingdesign.api.ApiClient;
import com.practice.li.rollingdesign.api.UserApi;

import okhttp3.RequestBody;
import retrofit2.Call;
import rx.Observable;

/**
 * Created by Lazxy on 2017/3/28.
 */

public class ProfileFollowModel implements ProfileFollowContract.Model {
    @Override
    public Call<RequestBody> checkFollowStatus(int id) {
        return ApiClient.getForInit(UserApi.class).checkFollowing(id+"");
    }

    @Override
    public Observable<String> changeFollowStatus(int id , boolean isFollow) {
        if(isFollow)
            return ApiClient.getForInit(UserApi.class).followUser(id+"");
        else
            return ApiClient.getForInit(UserApi.class).unFollowUser(id+"");
    }
}
