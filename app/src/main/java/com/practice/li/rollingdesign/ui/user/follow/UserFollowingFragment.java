package com.practice.li.rollingdesign.ui.user.follow;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.practice.li.rollingdesign.R;
import com.practice.li.rollingdesign.entity.FollowingEntity;
import com.practice.li.rollingdesign.event.EventUnFollowUser;
import com.practice.li.rollingdesign.mvpframe.base.BaseFrameListFragment;
import com.practice.li.rollingdesign.utils.UserInfoUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Lazxy on 2017/3/25.
 * 关注者类
 */

public class UserFollowingFragment extends BaseFrameListFragment<UserFollowingPresenter, UserFollowingModel>
        implements UserFollowingContract.View {

    @BindView(R.id.base_refresh_layout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.base_recycle_list)
    RecyclerView recyclerView;

    private boolean isLogin;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_list_layout);
        ButterKnife.bind(this, getContentView());
        EventBus.getDefault().register(this);
    }

    @Override
    public void initData() {
        super.initData();
        isLogin = !(null == UserInfoUtils.getCurrentUser());
        setList(refreshLayout, recyclerView, new UserFollowingAdapter(getActivity()));
    }

    @Override
    public void initView() {
        super.initView();
    }

    @Override
    public void requestData(boolean isRefresh) {
        super.requestData(isRefresh);
        if (isLogin)
            mPresenter.getFollowings(mPage);
    }

    @Override
    public void updateList(List<FollowingEntity> followings) {
        setData(followings);
    }

    @Override
    public void onUnFollowUserSuccess(int position) {
        mAdapter.remove(position);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void unFollowUser(EventUnFollowUser event) {
        mPresenter.unFollowUser(event.followingId, event.position);
        //这里应该改变按钮的颜色或者状态
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
