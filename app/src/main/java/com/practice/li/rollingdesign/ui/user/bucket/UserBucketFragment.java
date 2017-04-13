package com.practice.li.rollingdesign.ui.user.bucket;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.practice.li.rollingdesign.R;
import com.practice.li.rollingdesign.entity.BucketsEntity;
import com.practice.li.rollingdesign.entity.ShotsEntity;
import com.practice.li.rollingdesign.mvpframe.base.BaseFrameListFragment;
import com.practice.li.rollingdesign.ui.widget.ListDecoration;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Lazxy on 2017/3/29.
 */

public class UserBucketFragment extends BaseFrameListFragment<UserBucketPresenter, UserBucketModel> implements UserBucketContract.View {

    @BindView(R.id.base_refresh_layout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.base_recycle_list)
    RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_list_layout);
        ButterKnife.bind(this, getContentView());
    }

    @Override
    public void onResume() {
        super.onResume();
        requestData(true);
    }

    @Override
    public void initData() {
        setList(refreshLayout, recyclerView, new UserBucketAdapter(getActivity()));
    }

    @Override
    public void initView() {
        super.initView();
        mRecyclerView.addItemDecoration(new ListDecoration(getActivity(), ListDecoration.VERTICAL_LIST));
    }

    @Override
    public void requestData(boolean isRefresh) {
        super.requestData(isRefresh);
        mPresenter.getBucketsList(mPage);
    }

    @Override
    public void updateData(List<BucketsEntity> buckets) {
        for (int i = 0; i < buckets.size(); i++) {
            if (buckets.get(i).getShotsCount() > 0) {
                mPresenter.getShotsFromBucket(buckets.get(i).getId(), i);
            }
        }
        setData(buckets);
    }

    /**
     * 加载收藏夹封面
     * @param shot 获取到的第一张收藏作品图片
     * @param position 收藏夹在列表中的位置
     */
    @Override
    public void loadFirstShot(ShotsEntity shot, int position) {
        ((BucketsEntity) mAdapter.getItem(position)).setFirstShot(shot);
        mAdapter.notifyDataSetChanged();
    }
}
