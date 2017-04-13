package com.practice.li.rollingdesign.utils;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * 时间格式化类
 */
public class TimeUtils {

    public final static String FORMAT_YEAR = "yyyy";
    public final static String FORMAT_MONTH_DAY = "MM月dd日";

    public final static String FORMAT_DATE = "yyyy-MM-dd";
    public final static String FORMAT_TIME = "HH:mm";
    public final static String FORMAT_MONTH_DAY_TIME = "MM月dd日 hh:mm";

    public final static String FORMAT_DATE_TIME = "yyyy-MM-dd HH:mm";
    public final static String FORMAT_DATE_TIME_SECOND = "yyyy-MM-dd HH:mm:ss";

    public final static String FORMAT_ISO_8601 = "yyyy-MM-dd'T'HH:mm:ss'Z'";

    private static final int YEAR = 365 * 24 * 60 * 60;
    private static final int MONTH = 30 * 24 * 60 * 60;
    private static final int DAY = 24 * 60 * 60;
    private static final int HOUR = 60 * 60;
    private static final int MINUTE = 60;

    private static SimpleDateFormat sFormat = (SimpleDateFormat) SimpleDateFormat.getInstance();

    /**
     * 根据时间戳获取描述性时间，如3分钟前，1天前
     *
     * @param timestamp 时间戳 单位为秒
     * @return 时间字符串
     */
    public static String getTimeFromTimestamp(long timestamp) {
        long currentTime = System.currentTimeMillis() / 1000;
        long timeGap = (currentTime - timestamp / 1000);

        String time;
        if (timeGap > YEAR) {
            time = timeGap / YEAR + "年前";
        } else if (timeGap > MONTH) {
            time = timeGap / MONTH + "个月前";
        } else if (timeGap > DAY) {
            time = timeGap / DAY + "天前";
        } else if (timeGap > HOUR) {
            time = timeGap / HOUR + "小时前";
        } else if (timeGap > MINUTE) {
            time = timeGap / MINUTE + "分钟前";
        } else {
            time = "刚刚";
        }
        return time;
    }

    public static String getTimeFromISO8601(String time) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(FORMAT_ISO_8601, Locale.US);
            format.setTimeZone(TimeZone.getTimeZone("GMT"));
            return getTimeFromTimestamp(format.parse(time).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * @param timeFormat 指定的日期时间格式，如果为 null 或 "" 则使用默认的格式"yyyy-MM-dd HH:mm:ss"
     * @param date       如果为 null 则返回当前时间的字符串格式
     */
    public static String dateToStr(String timeFormat, Date date) {
        if (TextUtils.isEmpty(timeFormat)) {
            sFormat.applyPattern(FORMAT_DATE_TIME_SECOND);
        } else {
            sFormat.applyPattern(timeFormat);
        }
        if (date == null) {
            date = new Date();
        }
        return sFormat.format(date);
    }

    /**
     * @param timeFormat 指定的日期时间格式，如果为 null 或 "" 则使用默认的格式"yyyy-MM-dd HH:mm:ss"
     */
    public static Date strToDate(String timeFormat, String time) {
        if (TextUtils.isEmpty(timeFormat)) {
            sFormat.applyPattern(FORMAT_DATE_TIME_SECOND);
        } else {
            sFormat.applyPattern(timeFormat);
        }
        Date date = null;
        try {
            date = sFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

}
