package com.practice.li.rollingdesign.utils;

import java.math.BigDecimal;

/**
 * Created by Lazxy on 2017/3/13.
 * 字符串转化类
 */

public class StrConvertUtils {
    /**
     * 将超过1000的数字转化为带单位的字符串
     *
     * @param number 待转化数字
     * @return 格式化的字符串
     */
    public static String formatNumber(int number) {
        double kilo = ((double) number) / 1000;
        if (kilo > 1) {
            BigDecimal result = new BigDecimal(Double.toString(kilo));
            return result.setScale(1, BigDecimal.ROUND_HALF_UP).toPlainString() + "k";
        } else {
            return number + "";
        }
    }
}
