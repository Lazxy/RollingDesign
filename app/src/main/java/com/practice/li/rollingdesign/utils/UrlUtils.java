package com.practice.li.rollingdesign.utils;


import android.text.TextUtils;

import com.practice.li.rollingdesign.api.ApiConstants;
import com.practice.li.rollingdesign.entity.ImagesEntity;

import java.util.HashMap;
import java.util.Map;

/**
 * 链接操作工具
 */
public class UrlUtils {

    /**
     * 获取登录链接
     */
    public static String getAuthorizeUrl() {
        Map<String, String> params = new HashMap<>();
        params.put(ApiConstants.ParamKey.CLIENT_ID, ApiConstants.ParamValue.CLIENT_ID);
        params.put(ApiConstants.ParamKey.REDIRECT_URI, ApiConstants.ParamValue.REDIRECT_URI);
        params.put(ApiConstants.ParamKey.SCOPE, ApiConstants.ParamValue.SCOPE);
        params.put(ApiConstants.ParamKey.STATE, ApiConstants.ParamValue.STATE);

        return formatToUrl(ApiConstants.Url.OAUTH_URL + ApiConstants.Path.AUTHORIZE, params);
    }

    /**
     * 格式化字符串，使之成为Url参数格式
     * @param url 表示Url的字符串
     * @param params 参数键值对
     * @return 格式化后的Url地址
     */
    public static String formatToUrl(String url, Map<String, String> params) {
        StringBuilder sb = null;

        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (sb == null) {
                sb = new StringBuilder(url + "?");
            } else {
                sb.append("&");
            }

            sb.append(entry.getKey());
            sb.append("=");
            sb.append(entry.getValue());
        }

        return sb.toString();
    }

    /**
     * @param entity 图片地址实体类
     * @return 品质最高的图片地址
     */
    public static String getHighQualityImageUrl(ImagesEntity entity) {
        if (!TextUtils.isEmpty(entity.getHidpi())) return entity.getHidpi();

        if (!TextUtils.isEmpty(entity.getNormal())) return entity.getNormal();

        if (!TextUtils.isEmpty(entity.getTeaser())) return entity.getTeaser();

        return "";
    }

    /**
     * @param entity 图片地址实体类
     * @return 品质最低的图片地址
     */
    public static String getLowQualityImageUrl(ImagesEntity entity){
        if (!TextUtils.isEmpty(entity.getTeaser()))
            return entity.getTeaser();

        if (!TextUtils.isEmpty(entity.getNormal())) return entity.getNormal();

        if (!TextUtils.isEmpty(entity.getHidpi())) return entity.getHidpi();

        return "";
    }
}
