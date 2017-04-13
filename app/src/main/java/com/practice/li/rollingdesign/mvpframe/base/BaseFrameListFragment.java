package com.practice.li.rollingdesign.mvpframe.base;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.practice.li.rollingdesign.R;
import com.practice.li.rollingdesign.api.ApiConstants;
import com.practice.li.rollingdesign.ui.widget.CustomLoadMore;
import com.practice.li.rollingdesign.utils.ThemeUtils;

import java.util.List;

/**
 * Created by Lazxy on 2017/3/9.
 * MVP 列表型Fragment基类
 */

public class BaseFrameListFragment<P extends BasePresenter, M extends BaseModel> extends BaseFrameFragment<P, M> {

    protected SwipeRefreshLayout mRefreshLayout;
    protected BaseQuickAdapter mAdapter;
    protected RecyclerView mRecyclerView;

    private boolean mIsRefresh;
    protected int mPage;
    private boolean mIsLoading=false;

    private View mErrorView;
    private View mEmptyView;
    private TextView errorText;

    @Override
    public void initView() {
        LayoutInflater inflater = LayoutInflater.from(getActivity());

        mEmptyView = inflater.inflate(R.layout.layout_empty_view, this.container, false);
        ((TextView) (mEmptyView.findViewById(R.id.tv_empty_view_msg))).setText(getEmptyMessage());
        mErrorView = inflater.inflate(R.layout.layout_error_view, this.container, false);
        ((TextView) mErrorView.findViewById(R.id.tv_error_view_msg)).setText(getErrorMessage());
        errorText = ((TextView) (mErrorView.findViewById(R.id.tv_error_view_retry)));

        mRefreshLayout.setColorSchemeColors(ThemeUtils.getCurrentAccentColor(getActivity()));

        //通过该设置获取了加载框的布局以及各种加载情况的显示反馈，之后的loadMore*回调函数就进行了这些情况的切换
        mAdapter.setLoadMoreView(new CustomLoadMore());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void initListener() {
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestData(true);
            }
        });
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                requestData(false);
            }
        });
        errorText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ViewGroup) mAdapter.getEmptyView()).removeAllViews();
                requestData(true);
            }
        });
    }

    @Override
    public void initLoad() {
        requestData(true);
    }

    protected void setList(SwipeRefreshLayout refreshLayout, RecyclerView recyclerView, BaseQuickAdapter adapter) {
        mRecyclerView = recyclerView;
        mAdapter = adapter;
        mRefreshLayout = refreshLayout;
    }

    /**
     * 进行数据请求.
     *
     * @param isRefresh 判断是首页刷新还是加载更多页
     */
    protected void requestData(boolean isRefresh) {
        mIsRefresh = isRefresh;
        if (isRefresh) {
            mPage = 1;
            onRequestStart();
        } else {
            mPage++;
        }
    }

    protected <T> void setData(List<T> data) {
        if (mIsRefresh) {
            mAdapter.setNewData(data);
            if (data == null || data.size() == 0) {
                mAdapter.setEmptyView(mEmptyView);
                mAdapter.notifyDataSetChanged();
            }
        } else {
            mAdapter.addData(data);
        }
        if (data == null || data.size() < getItemPerPage())
            mAdapter.loadMoreEnd();
        else {
            mAdapter.loadMoreComplete();
        }
        onRequestEnd();
    }

    protected int getItemPerPage() {
        return ApiConstants.ParamValue.PAGE_SIZE;
    }

    protected String getEmptyMessage() {
        return "这里空空如也";
    }

    protected String getErrorMessage() {
        return "出错了";
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(hidden){
            //防止刷新过程中其他Fragment无法滑动的BUG出现
            mRefreshLayout.setRefreshing(false);
        }else if(mIsLoading){
            mRefreshLayout.setRefreshing(true);
        }
    }

    @Override
    public void onRequestStart() {
        mRefreshLayout.setRefreshing(true);
        mIsLoading=true;
    }

    @Override
    public void onRequestError(String msg) {
        if (mIsRefresh) {
            mAdapter.setNewData(null);
            mAdapter.setEmptyView(mErrorView);
            mAdapter.notifyDataSetChanged();
        } else {
            mAdapter.loadMoreFail();
        }
        onRequestEnd();
    }

    @Override
    public void onRequestEnd() {
        mRefreshLayout.setRefreshing(false);
        mIsLoading=false;
    }
}
