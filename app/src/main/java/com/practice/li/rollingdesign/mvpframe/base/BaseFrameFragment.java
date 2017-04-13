package com.practice.li.rollingdesign.mvpframe.base;

import android.os.Bundle;

import com.practice.li.rollingdesign.common.BaseFragment;
import com.practice.li.rollingdesign.mvpframe.TUtil;


/**
 * Created by Lazxy on 2017/2/21.
 * MVP Fragment 基类
 */

public abstract class BaseFrameFragment<P extends BasePresenter, M extends BaseModel> extends BaseFragment implements BaseView {
    public P mPresenter;

    public M mModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = TUtil.getT(this, 0);
        mModel = TUtil.getT(this, 1);
        //建立起了Presenter和Model、View的联系
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
