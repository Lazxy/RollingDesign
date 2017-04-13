package com.practice.li.rollingdesign.ui.user.login;

import com.practice.li.rollingdesign.api.ApiClient;
import com.practice.li.rollingdesign.common.ConfigManager;
import com.practice.li.rollingdesign.entity.TokenEntity;
import com.practice.li.rollingdesign.entity.UserEntity;
import com.practice.li.rollingdesign.mvpframe.base.BaseSubscriber;
import com.practice.li.rollingdesign.utils.UserInfoUtils;


/**
 * Created by Lazxy on 2017/3/8.
 */

public class LoginPresenter extends LoginContact.Presenter {

    @Override
    void requestToken(String code) {
        subscribe(mModel.requestToken(code), new BaseSubscriber<TokenEntity>(mView) {
            @Override
            protected void onSuccess(TokenEntity tokenEntity) {
                if(mView!=null&&tokenEntity!=null) {
                    ApiClient.resetApiClient();
                    ConfigManager.Login.saveToken(tokenEntity.getAccessToken());
                    getUserInfo();
                }
            }
        });
    }

    @Override
    void getUserInfo( ) {
        subscribe(mModel.getUserInfo(), new BaseSubscriber<UserEntity>(mView){
            @Override
            protected void onSuccess(UserEntity userEntity) {
                if(userEntity!=null&&mView!=null){
                    UserInfoUtils.setUserInfo(userEntity);
                    mView.onRequestEnd();
                }
            }
        });
    }

}
