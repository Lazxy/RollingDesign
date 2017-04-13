package com.practice.li.rollingdesign.ui.widget;

import com.chad.library.adapter.base.loadmore.LoadMoreView;
import com.practice.li.rollingdesign.R;

/**
 * Created by Lazxy on 2017/3/9.
 * BaseQuickAdapter 所需控件，根据下拉或上拉加载的情况提供不同的布局
 */

public class CustomLoadMore extends LoadMoreView {

    @Override
    public int getLayoutId() {
        return R.layout.layout_load_more;
    }

    @Override
    protected int getLoadingViewId() {
        return R.id.lay_load_more_loading_view;
    }

    @Override
    protected int getLoadFailViewId() {
        return R.id.lay_load_more_load_fail_view;
    }

    @Override
    protected int getLoadEndViewId() {return R.id.tv_load_more_no_more;}

    @Override
    public boolean isLoadEndGone() {
        return false;
    }
}
