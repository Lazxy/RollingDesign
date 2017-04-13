package com.practice.li.rollingdesign.ui.profile.user.follower;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.practice.li.rollingdesign.R;
import com.practice.li.rollingdesign.common.CommonConstants;
import com.practice.li.rollingdesign.entity.FollowerEntity;
import com.practice.li.rollingdesign.mvpframe.base.BaseFrameListFragment;
import com.practice.li.rollingdesign.ui.widget.ListDecoration;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Lazxy on 2017/3/24.
 * 单人用户粉丝类
 */

public class ProfileFollowerFragment extends BaseFrameListFragment<ProfileFollowerPresenter,
        ProfileFollowerModel> implements ProfileFollowerConstract.View {

    @BindView(R.id.base_refresh_layout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.base_recycle_list)
    RecyclerView recyclerView;

    private int mUserId;

    public static ProfileFollowerFragment getInstance(int id) {
        Bundle bundle = new Bundle();
        bundle.putInt(CommonConstants.User.EXTRA_PROFILE_USER_ID, id);
        ProfileFollowerFragment fragment = new ProfileFollowerFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_list_layout);
        ButterKnife.bind(this, getContentView());
    }

    @Override
    public void initData() {
        mUserId = getArguments().getInt(CommonConstants.User.EXTRA_PROFILE_USER_ID);
        setList(refreshLayout, recyclerView, new FollowerAdapter(getActivity()));
    }

    @Override
    public void initView() {
        super.initView();
        recyclerView.addItemDecoration(new ListDecoration(getActivity(), ListDecoration.VERTICAL_LIST));
    }

    @Override
    public void initLoad() {
        super.initLoad();
    }

    @Override
    public void requestData(boolean isRefresh) {
        super.requestData(isRefresh);
        mPresenter.getFollowers(mUserId, mPage);
    }


    @Override
    public void updateList(List<FollowerEntity> followers) {
        setData(followers);
    }
}
