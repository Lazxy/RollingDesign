package com.practice.li.rollingdesign.ui.profile.team.members;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.practice.li.rollingdesign.R;
import com.practice.li.rollingdesign.common.CommonConstants;
import com.practice.li.rollingdesign.entity.UserEntity;
import com.practice.li.rollingdesign.mvpframe.base.BaseFrameListFragment;
import com.practice.li.rollingdesign.ui.widget.ListDecoration;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Lazxy on 2017/3/28.
 * 团队成员展示类
 */

public class ProfileTeamMemberFragment extends BaseFrameListFragment<ProfileTeamMemberPresenter, ProfileTeamMemberModel>
        implements ProfileTeamMemberContract.View {

    @BindView(R.id.base_refresh_layout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.base_recycle_list)
    RecyclerView recyclerView;

    private int mTeamId;

    public static ProfileTeamMemberFragment getInstance(int teamId) {
        Bundle bundle = new Bundle();
        bundle.putInt(CommonConstants.User.Extra_PROFILE_TEAM_ID, teamId);
        ProfileTeamMemberFragment fragment = new ProfileTeamMemberFragment();
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
        mTeamId = getArguments().getInt(CommonConstants.User.Extra_PROFILE_TEAM_ID);
        setList(refreshLayout, recyclerView, new ProfileTeamMemberAdapter(getActivity()));
    }

    @Override
    public void initView() {
        super.initView();
        recyclerView.addItemDecoration(new ListDecoration(getActivity(), ListDecoration.VERTICAL_LIST));
    }

    @Override
    public void requestData(boolean isRefresh) {
        super.requestData(isRefresh);
        mPresenter.getMembersList(mTeamId, mPage);
    }

    @Override
    public void updateData(List<UserEntity> members) {
        setData(members);
    }
}
