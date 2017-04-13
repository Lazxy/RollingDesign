package com.practice.li.rollingdesign.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Lazxy on 2017/3/8.
 * 持久化文件操作工具类
 */

public class SPUtils {
    private static final String CONFIG = "config";

    /**
     * 将内容存入指定文件
     *
     * @param context 当前上下文
     * @param key     存储键
     * @param value   存储值
     */
    public static void put(Context context, String key, Object value) {
        put(key, value, context.getSharedPreferences(CONFIG, Context.MODE_PRIVATE));
    }

    /**
     * 将内容存入指定文件
     *
     * @param key   存储键
     * @param value 存储值
     * @param sp    存储对象
     */
    public static void put(String key, Object value, SharedPreferences sp) {
        SharedPreferences.Editor editor = sp.edit();

        if (value instanceof String) {
            editor.putString(key, (String) value);
        } else if (value instanceof Integer) {
            editor.putInt(key, (Integer) value);
        } else if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
        } else if (value instanceof Float) {
            editor.putFloat(key, (Float) value);
        } else if (value instanceof Long) {
            editor.putLong(key, (Long) value);
        } else {
            editor.putString(key, value.toString());
        }

        editor.apply();
    }

    /**
     * 获取指定文件内的确定类型值值
     *
     * @param context      当前上下文
     * @param key          存储键
     * @param defaultValue 默认值
     * @return 取得的对象值
     */
    public static Object get(Context context, String key, Object defaultValue) {
        return get(key, defaultValue, context.getSharedPreferences(CONFIG, Context.MODE_PRIVATE));
    }

    /**
     * 获取指定文件内的确定类型值值
     *
     * @param key          存储键
     * @param defaultValue 默认值
     * @param sp           存储对象
     * @return 取得的对象值
     */
    public static Object get(String key, Object defaultValue, SharedPreferences sp) {
        if (defaultValue instanceof String) {
            return sp.getString(key, (String) defaultValue);
        } else if (defaultValue instanceof Integer) {
            return sp.getInt(key, (Integer) defaultValue);
        } else if (defaultValue instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) defaultValue);
        } else if (defaultValue instanceof Float) {
            return sp.getFloat(key, (Float) defaultValue);
        } else if (defaultValue instanceof Long) {
            return sp.getLong(key, (Long) defaultValue);
        }
        return null;
    }
}
