package com.practice.li.rollingdesign.ui.user.login;

import com.practice.li.rollingdesign.api.ApiClient;
import com.practice.li.rollingdesign.api.ApiConstants;
import com.practice.li.rollingdesign.api.UserApi;
import com.practice.li.rollingdesign.entity.TokenEntity;
import com.practice.li.rollingdesign.entity.UserEntity;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;

/**
 * Created by Lazxy on 2017/3/8.
 */

public class LoginModel implements LoginContact.Model {

    @Override
    public Observable<TokenEntity> requestToken(String code) {
        Map<String,String> params=new HashMap<> ();
        params.put(ApiConstants.ParamKey.CLIENT_ID,ApiConstants.ParamValue.CLIENT_ID);
        params.put(ApiConstants.ParamKey.CLIENT_SECRET,ApiConstants.ParamValue.CLIENT_SECRET);
        params.put(ApiConstants.ParamKey.CODE,code);
        params.put(ApiConstants.ParamKey.REDIRECT_URI,ApiConstants.ParamValue.REDIRECT_URI);
        return ApiClient.getForOAuth().getToken(params);
    }

    @Override
    public Observable<UserEntity> getUserInfo() {
        return ApiClient.getForInit(UserApi.class).getUserInfo();
    }
}
