package com.practice.li.rollingdesign.event;

/**
 * Created by Lazxy on 2017/4/9.
 * 登录状态改变事件
 */

public class EventChangeLoginStatus {

    public boolean isLogin;//表示是否处于登录状态

    public EventChangeLoginStatus(boolean isLogin) {
        this.isLogin = isLogin;
    }
}
