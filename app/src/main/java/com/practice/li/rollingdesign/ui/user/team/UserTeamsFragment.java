package com.practice.li.rollingdesign.ui.user.team;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.practice.li.rollingdesign.R;
import com.practice.li.rollingdesign.entity.TeamEntity;
import com.practice.li.rollingdesign.mvpframe.base.BaseFrameListFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Lazxy on 2017/3/27.
 * 当前用户团队信息类
 */

public class UserTeamsFragment extends BaseFrameListFragment<UserTeamsPresenter,UserTeamsModel> implements UserTeamsContract.View{

    @BindView(R.id.base_refresh_layout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.base_recycle_list)
    RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle savedStateInstance){
        super.onCreate(savedStateInstance);
        setContentView(R.layout.base_list_layout);
        ButterKnife.bind(this,getContentView());
    }

    @Override
    public void initData(){
        super.initData();
        setList(refreshLayout,recyclerView,new UserTeamsAdapter(getActivity()));
    }

    @Override
    public void initView(){
        super.initView();
    }

    @Override
    public void requestData(boolean isRefresh){
        super.requestData(isRefresh);
        mPresenter.getTeamsList();
    }

    @Override
    public void updateData(List<TeamEntity> teams) {
        setData(teams);
    }
}
