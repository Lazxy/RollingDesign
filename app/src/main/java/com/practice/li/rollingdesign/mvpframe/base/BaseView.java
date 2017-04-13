package com.practice.li.rollingdesign.mvpframe.base;

/**
 * Created by Lazxy on 2017/2/21.
 * MVP View基类
 */

public interface BaseView {

    void onRequestStart();

    void onRequestError(String msg);

    void onRequestEnd();
}
