package com.practice.li.rollingdesign.mvpframe.base;

import android.os.Bundle;

import com.practice.li.rollingdesign.common.BaseActivity;
import com.practice.li.rollingdesign.mvpframe.TUtil;


/**
 * Created by Lazxy on 2017/2/21.
 * MVP Activity基类
 */

public abstract class BaseFrameActivity<P extends BasePresenter, M extends BaseModel> extends BaseActivity implements BaseView {
    public P mPresenter;

    public M mModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //通过泛型取得代理对象
        mPresenter = TUtil.getT(this, 0);
        mModel = TUtil.getT(this, 1);
        if (this instanceof BaseView) {
            mPresenter.setVM(this, mModel);
        }
    }

    @Override
    public void onDestroy() {
        mPresenter.detachView();
        super.onDestroy();
    }

}
