package com.practice.li.rollingdesign.mvpframe.base;

import android.widget.Toast;

import com.practice.li.rollingdesign.RollingDesign;
import com.practice.li.rollingdesign.utils.NetworkUtils;

import rx.Subscriber;

/**
 * Created by Lazxy on 2017/3/8.
 * RxJava 订阅者基类
 */

public abstract class BaseSubscriber<T> extends Subscriber<T> {

    private final BaseView mView;

    public BaseSubscriber(BaseView view) {
        mView = view;
    }

    public BaseSubscriber(BaseView view, boolean isShowLoading) {
        mView = view;
    }

    @Override
    public void onStart() {
        if (!NetworkUtils.getIsNetWorking())
            Toast.makeText(RollingDesign.applicationContext, "网络连接异常", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCompleted() {
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        mView.onRequestError(e.getMessage());
    }

    @Override
    public void onNext(T entity) {
        onSuccess(entity);
    }

    protected abstract void onSuccess(T t);

}
