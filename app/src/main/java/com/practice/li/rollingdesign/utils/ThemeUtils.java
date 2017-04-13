package com.practice.li.rollingdesign.utils;

import android.app.Activity;
import android.content.res.Resources;
import android.util.TypedValue;

import com.practice.li.rollingdesign.R;
import com.practice.li.rollingdesign.common.CommonConstants;
import com.practice.li.rollingdesign.common.ConfigManager;

/**
 * Created by Lazxy on 2017/4/8.
 * 主题切换工具类
 */

public class ThemeUtils {

    /**
     * 获得当前配置文件内的主题
     *
     * @return 当前主题
     */
    public static String getTheme() {
        return ConfigManager.Theme.getTheme();
    }

    /**
     * 将当前主题写入配置文件
     *
     * @param theme 当前主题
     */
    public static void setTheme(String theme) {
        ConfigManager.Theme.setTheme(theme);
    }

    public static int getThemeResource(String theme) {
        switch (theme) {
            case CommonConstants.Theme.THEME_PINK:
                return R.style.AppTheme_Pink;

            case CommonConstants.Theme.THEME_RED:
                return R.style.AppTheme_Red;

            case CommonConstants.Theme.THEME_BLUE:
                return R.style.AppTheme_Blue;

            case CommonConstants.Theme.THEME_GREEN:
                return R.style.AppTheme_Green;

            default:
                return R.style.AppTheme;
        }
    }

    /**
     * 获取当前主题下的主色
     *
     * @param activity 当前Activity
     * @return 主色颜色值
     */
    public static int getCurrentPrimaryColor(Activity activity) {
        Resources.Theme theme = activity.getTheme();
        TypedValue primaryColor = new TypedValue();
        theme.resolveAttribute(R.attr.colorPrimary, primaryColor, true);
        return activity.getResources().getColor(primaryColor.resourceId);
    }

    /**
     * 获取当前主题下的特征色
     *
     * @param activity 当前Activity
     * @return 特征色颜色值
     */
    public static int getCurrentAccentColor(Activity activity) {
        Resources.Theme theme = activity.getTheme();
        TypedValue accentColor = new TypedValue();
        theme.resolveAttribute(R.attr.colorAccent, accentColor, true);
        return activity.getResources().getColor(accentColor.resourceId);
    }

    /**
     * 获取当前主题下Spinner下拉框文字的样式
     *
     * @param theme 当前主题
     * @return 文字布局颜色
     */
    public static int getCurrentDropDownView(String theme) {
        switch (theme) {
            case CommonConstants.Theme.THEME_PINK:
                return R.layout.spinner_drop_down_pink;

            case CommonConstants.Theme.THEME_RED:
                return R.layout.spinner_drop_down_red;

            case CommonConstants.Theme.THEME_BLUE:
                return R.layout.spinner_drop_down_blue;

            case CommonConstants.Theme.THEME_GREEN:
                return R.layout.spinner_drop_down_green;

            default:
                return R.layout.spinner_drop_down;
        }
    }
}
