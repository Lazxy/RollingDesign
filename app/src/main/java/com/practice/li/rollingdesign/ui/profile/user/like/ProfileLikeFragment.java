package com.practice.li.rollingdesign.ui.profile.user.like;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.practice.li.rollingdesign.R;
import com.practice.li.rollingdesign.common.CommonConstants;
import com.practice.li.rollingdesign.entity.ShotsEntity;
import com.practice.li.rollingdesign.mvpframe.base.BaseFrameListFragment;
import com.practice.li.rollingdesign.ui.user.like.LikedShotsAdapter;
import com.practice.li.rollingdesign.ui.user.like.UserLikeContract;
import com.practice.li.rollingdesign.ui.user.like.UserLikeModel;
import com.practice.li.rollingdesign.ui.user.like.UserLikePresenter;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Lazxy on 2017/3/25.
 * 单人用户喜欢的作品类
 */

public class ProfileLikeFragment extends BaseFrameListFragment<UserLikePresenter, UserLikeModel> implements UserLikeContract.View {

    @BindView(R.id.base_refresh_layout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.base_recycle_list)
    RecyclerView recyclerView;

    private int mUserId;

    public static ProfileLikeFragment getInstance(int id) {
        Bundle bundle = new Bundle();
        bundle.putInt(CommonConstants.User.EXTRA_PROFILE_USER_ID, id);
        ProfileLikeFragment fragment = new ProfileLikeFragment();
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
        super.initData();
        mUserId = getArguments().getInt(CommonConstants.User.EXTRA_PROFILE_USER_ID);
        setList(refreshLayout, recyclerView, new LikedShotsAdapter(getActivity()));
    }

    @Override
    public void initView() {
        super.initView();
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
    }

    @Override
    public void requestData(boolean isRefresh) {
        super.requestData(isRefresh);
        mPresenter.getLikesList(mUserId, mPage);
    }

    @Override
    public void updateList(List<ShotsEntity> shots) {
        setData(shots);
    }
}
