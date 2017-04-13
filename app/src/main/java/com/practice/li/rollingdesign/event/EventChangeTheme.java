package com.practice.li.rollingdesign.event;

/**
 * Created by Lazxy on 2017/4/9.
 * 主题改变事件
 */

public class EventChangeTheme {

    public int primaryColor;//改变后主题的主色

    public String themeName;//改变后主题的名字

    public EventChangeTheme(int primaryColor, String themeName) {
        this.primaryColor = primaryColor;
        this.themeName = themeName;
    }
}
