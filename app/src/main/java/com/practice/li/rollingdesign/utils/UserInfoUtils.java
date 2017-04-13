package com.practice.li.rollingdesign.utils;

import android.text.TextUtils;
import android.webkit.CookieManager;

import com.google.gson.Gson;
import com.practice.li.rollingdesign.common.ConfigManager;
import com.practice.li.rollingdesign.entity.UserEntity;

/**
 * Created by Lazxy on 2017/3/8.
 * 用户信息操作工具类
 */

public class UserInfoUtils {

    public static UserEntity sCurrentUser;

    /**
     * 获取当前存储的用户信息
     *
     * @return 用户信息
     */
    public static UserEntity getCurrentUser() {
        if (sCurrentUser != null)
            return sCurrentUser;
        String info = ConfigManager.User.getRecentUser();
        if (!TextUtils.isEmpty(info)) {
            UserEntity entity = new Gson().fromJson(info, UserEntity.class);
            sCurrentUser = entity;
            return entity;
        }
        return null;
    }

    /**
     * 清除当前用户信息与登陆页Cookie
     */
    public static void clearUserInfo() {
        sCurrentUser = null;
        ConfigManager.User.saveUser("");
        ConfigManager.Login.saveToken("");
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();
    }

    /**
     * 设置当前用户信息
     *
     * @param entity 待设置用户信息
     */
    public static void setUserInfo(UserEntity entity) {
        sCurrentUser = entity;
        ConfigManager.User.saveUser(new Gson().toJson(entity));
    }
}
