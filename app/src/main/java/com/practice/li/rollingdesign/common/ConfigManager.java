package com.practice.li.rollingdesign.common;

import com.practice.li.rollingdesign.api.ApiConstants;
import com.practice.li.rollingdesign.utils.SPUtils;

import static com.practice.li.rollingdesign.RollingDesign.applicationContext;

/**
 * Created by Lazxy on 2017/3/13.
 * 配置设置类
 */

public class ConfigManager {

    public static class Login {

        public static String getRecentToken() {
            return (String) getConfig(CommonConstants.User.ACCESS_TOKEN, "");
        }

        public static void saveToken(String token) {
            setConfig(CommonConstants.User.ACCESS_TOKEN, token);
        }


    }

    public static class User {

        public static boolean isFirstRun() {
            return (boolean) getConfig(CommonConstants.User.IS_FIRST_RUN, true);
        }

        public static void setIsFirstRun(boolean isFirstRun) {
            setConfig(CommonConstants.User.IS_FIRST_RUN, isFirstRun);
        }

        public static String getRecentUser() {
            return (String) getConfig(CommonConstants.User.CURRENT_USER, "");
        }

        public static void saveUser(String userInJson) {
            setConfig(CommonConstants.User.CURRENT_USER, userInJson);
        }

    }

    public static class Shot {

        public static int getViewMode() {
            return (int) getConfig(CommonConstants.Shots.VIEW_MODE,
                    CommonConstants.Shots.VIEW_MODE_SMALL_WITH_INFO);
        }

        public static void saveViewMode(int viewMode) {
            setConfig(CommonConstants.Shots.VIEW_MODE, viewMode);
        }

        public static boolean isAutoAnimated() {
            return (boolean) getConfig(CommonConstants.Config.RUN_GIF_AUTO, false);
        }

        public static void setIsAutoAnimated(boolean isAutoAnimated) {
            setConfig(CommonConstants.Config.RUN_GIF_AUTO, isAutoAnimated);
        }

        public static int getShotSort() {
            return (int) getConfig(ApiConstants.ParamKey.SORT, 0);
        }

        public static void setShotSort(int sort) {
            setConfig(ApiConstants.ParamKey.SORT, sort);
        }

        public static int getShotType() {
            return (int) getConfig(ApiConstants.ParamKey.TYPE, 0);
        }

        public static void setShotType(int type) {
            setConfig(ApiConstants.ParamKey.TYPE, type);
        }

        public static int getShotTimeFrame() {
            return (int) getConfig(ApiConstants.ParamKey.TIME_FRAME, 0);
        }

        public static void setShotTimeFrame(int timeFrame) {
            setConfig(ApiConstants.ParamKey.TIME_FRAME, timeFrame);
        }
    }

    public static class Theme {
        public static String getTheme() {
            return (String) getConfig(CommonConstants.Theme.THEME_KEY, CommonConstants.Theme.THEME_GREY);
        }

        public static void setTheme(String theme) {
            setConfig(CommonConstants.Theme.THEME_KEY, theme);
        }
    }

    private static Object getConfig(String key, Object defaultValue) {
        return SPUtils.get(applicationContext, key, defaultValue);
    }

    private static void setConfig(String key, Object value) {
        SPUtils.put(applicationContext, key, value);
    }

}
