package com.practice.li.rollingdesign.ui.profile.common.works;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.practice.li.rollingdesign.R;
import com.practice.li.rollingdesign.common.CommonConstants;
import com.practice.li.rollingdesign.entity.AuthorEntity;
import com.practice.li.rollingdesign.entity.ShotsEntity;
import com.practice.li.rollingdesign.entity.TeamEntity;
import com.practice.li.rollingdesign.entity.UserEntity;
import com.practice.li.rollingdesign.mvpframe.base.BaseFrameListFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Lazxy on 2017/3/24.
 * 简介作品显示类
 */

public class ProfileWorksFragment extends BaseFrameListFragment<ProfileWorksPresenter, ProfileWorksModel> implements ProfileWorksContract.View {

    @BindView(R.id.base_refresh_layout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.base_recycle_list)
    RecyclerView recyclerView;

    private AuthorEntity mAuthor;
    private boolean isUser;

    public static ProfileWorksFragment getInstance(AuthorEntity authorEntity) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(CommonConstants.User.EXTRA_PROFILE_AUTHOR, authorEntity);
        ProfileWorksFragment fragment = new ProfileWorksFragment();
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
        mAuthor = (AuthorEntity) getArguments().getSerializable(CommonConstants.User.EXTRA_PROFILE_AUTHOR);
        isUser = mAuthor instanceof UserEntity;
        if (isUser && mAuthor.getType().equals(CommonConstants.User.USER_TYPE_PLAYER)) {
            /*取得单个team的信息，API未提供，而照上面的判断会出现一些得不到Team的作品之类的BUG*/
        }
        setList(refreshLayout, recyclerView, new ProfileWorksAdapter(getActivity()));
    }

    @Override
    public void initView() {
        super.initView();
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
    }

    @Override
    public void initLoad() {
        super.initLoad();
    }

    @Override
    public void requestData(boolean isRefresh) {
        super.requestData(isRefresh);
        mPresenter.getShotList(mAuthor.getId(), mPage, isUser);
    }

    /**
     * 将返回的作品补全作者信息并填入列表
     *
     * @param shots 当前作者的作品
     */
    @Override
    public void updateList(List<ShotsEntity> shots) {
        for (ShotsEntity item : shots) {
            if (isUser) {
                if (item.getUser() == null) {
                    item.setUser((UserEntity) mAuthor);
                }
            } else {
                if (item.getTeam() == null) {
                    item.setTeam((TeamEntity) mAuthor);
                }
            }
        }
        setData(shots);
    }
}
