package com.practice.li.rollingdesign.ui.user.like;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.practice.li.rollingdesign.R;
import com.practice.li.rollingdesign.entity.ShotsEntity;
import com.practice.li.rollingdesign.mvpframe.base.BaseFrameListFragment;
import com.practice.li.rollingdesign.utils.UserInfoUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Lazxy on 2017/3/25.
 * 喜欢作品类
 */

public class UserLikeFragment extends BaseFrameListFragment<UserLikePresenter,UserLikeModel> implements UserLikeContract.View{

    @BindView(R.id.base_refresh_layout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.base_recycle_list)
    RecyclerView recyclerView;

    private int mUserId;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_list_layout);
        ButterKnife.bind(this,getContentView());
    }

    @Override
    public void initData(){
        super.initData();
        if(null!=UserInfoUtils.getCurrentUser()){
            mUserId=UserInfoUtils.getCurrentUser().getId();
        }
        setList(refreshLayout,recyclerView,new LikedShotsAdapter(getActivity()));
    }

    @Override
    public void initView(){
        super.initView();
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
    }

    @Override
    public void requestData(boolean isRefresh){
        super.requestData(isRefresh);
        if(mUserId!=0)
            mPresenter.getLikesList(mUserId, mPage);
    }

    @Override
    public void updateList(List<ShotsEntity> shots) {
        setData(shots);
    }
}
