package com.practice.li.rollingdesign.ui.main;

import android.text.TextUtils;

import com.practice.li.rollingdesign.common.ConfigManager;
import com.practice.li.rollingdesign.entity.UserEntity;
import com.practice.li.rollingdesign.mvpframe.base.BaseSubscriber;
import com.practice.li.rollingdesign.utils.UserInfoUtils;

/**
 * Created by Lazxy on 2017/3/11.
 */

public class MainPresenter extends MainContract.Presenter {
    @Override
    void getUserInfo() {
        subscribe(mModel.getUserInfo(), new BaseSubscriber<UserEntity>(mView) {
            @Override
            protected void onSuccess(UserEntity userEntity) {
                boolean isDefaultToken=TextUtils.isEmpty(ConfigManager.Login.getRecentToken());
                if(userEntity!=null&&!isDefaultToken){
                    UserInfoUtils.setUserInfo(userEntity);
                    mView.setUser(userEntity);
                }else if(isDefaultToken){
                    mView.setUser(null);
                }
            }
        });
    }
}
