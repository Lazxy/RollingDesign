package com.practice.li.rollingdesign.ui.main;


import com.practice.li.rollingdesign.api.ApiClient;
import com.practice.li.rollingdesign.api.UserApi;
import com.practice.li.rollingdesign.entity.UserEntity;

import rx.Observable;

/**
 * Created by Lazxy on 2017/3/11.
 */

public class MainModel implements MainContract.Model {
    @Override
    public Observable<UserEntity> getUserInfo() {
        return ApiClient.getForInit(UserApi.class).getUserInfo();
    }
}
