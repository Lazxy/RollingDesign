package com.practice.li.rollingdesign.ui.profile.common.info;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.practice.li.rollingdesign.R;
import com.practice.li.rollingdesign.common.CommonConstants;
import com.practice.li.rollingdesign.entity.AuthorEntity;
import com.practice.li.rollingdesign.entity.TeamEntity;
import com.practice.li.rollingdesign.entity.UserEntity;
import com.practice.li.rollingdesign.mvpframe.base.BaseFrameFragment;
import com.practice.li.rollingdesign.ui.profile.team.TeamProfileActivity;
import com.practice.li.rollingdesign.utils.FrescoUtils;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Lazxy on 2017/3/24.
 * 简介信息类
 */

public class ProfileInfoFragment extends BaseFrameFragment<ProfileInfoPresenter, ProfileInfoModel> implements ProfileInfoContract.View {

    @BindView(R.id.tv_profile_detail_shots)
    TextView shotsCount;
    @BindView(R.id.tv_profile_detail_buckets)
    TextView bucketsCount;
    @BindView(R.id.tv_profile_detail_like)
    TextView likesCount;
    @BindView(R.id.tv_profile_detail_members)
    TextView membersCount;
    @BindView(R.id.tv_profile_detail_follower)
    TextView followersCount;
    @BindView(R.id.tv_profile_detail_following)
    TextView followingsCount;
    @BindView(R.id.tv_profile_detail_project)
    TextView projectsCount;
    @BindView(R.id.tv_profile_detail_twitter)
    TextView twitterAddress;
    @BindView(R.id.tv_profile_detail_web)
    TextView webAddress;
    @BindView(R.id.tv_profile_detail_load_team)
    TextView loadTeam;
    @BindView(R.id.layout_profile_detail_team)
    LinearLayout teamLayout;

    private AuthorEntity mAuthor;

    public static ProfileInfoFragment getInstance(AuthorEntity entity) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(CommonConstants.User.EXTRA_PROFILE_AUTHOR, entity);
        ProfileInfoFragment fragment = new ProfileInfoFragment();
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_profile_detail);
        ButterKnife.bind(this, getContentView());
    }

    @Override
    public void initData() {
        mAuthor = (AuthorEntity) getArguments().getSerializable(CommonConstants.User.EXTRA_PROFILE_AUTHOR);
    }

    @Override
    public void initView() {
        //初始化基本信息，PS:信息栏应该再加上账号创建时间
        shotsCount.setText(mAuthor.getShotsCount() + " 作品");
        bucketsCount.setText(mAuthor.getBucketsCount() + " 收藏");
        likesCount.setText(mAuthor.getLikesCount() + " 喜欢");
        followersCount.setText(mAuthor.getFollowersCount() + " 粉丝");
        followingsCount.setText(mAuthor.getFollowingsCount() + " 关注");
        projectsCount.setText(mAuthor.getProjectsCount() + " 项目");
        String twitterUrl = mAuthor.getLinks().getTwitter();
        twitterUrl = TextUtils.isEmpty(twitterUrl) ? "未知" : twitterUrl;
        twitterAddress.setText(twitterUrl);
        String webUrl = mAuthor.getLinks().getWeb();
        webUrl = TextUtils.isEmpty(webUrl) ? "未知" : webUrl;
        webAddress.setText(webUrl);
        if (mAuthor.getType().equals(CommonConstants.User.USER_TYPE_PLAYER))
            loadTeam.setClickable(false);//加载团队信息完成前禁止点击按钮
        else if (mAuthor.getType().equals(CommonConstants.User.USER_TYPE_TEAM)) {
            teamLayout.setVisibility(View.GONE);
            //因为Comment未提供团队数据，故可能会出现团队账号以UserEntity的方式访问但标识为Team的情况出现
            try {
                int count = ((TeamEntity) mAuthor).getMembersCount();
                membersCount.setVisibility(View.VISIBLE);
                membersCount.setText(count + " 成员");
            } catch (Exception e) {
            }
        }

    }

    @Override
    public void initListener() {
        loadTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.getTeamInfo(mAuthor.getId());
                loadTeam.setText("加载中...");
                loadTeam.setClickable(false);
                loadTeam.setTextColor(getResources().getColor(R.color.text_grey));
            }
        });
    }

    @Override
    public void initLoad() {
        if (teamLayout.getVisibility() == View.VISIBLE) {
            if (((UserEntity) mAuthor).getTeamsCount() > 0)
                mPresenter.getTeamInfo(mAuthor.getId());
            else
                loadTeam.setText("未加入任何团队");
        }
    }

    @Override
    public void onRequestStart() {

    }

    @Override
    public void onRequestError(String msg) {
        //这里由于宿主Activity已经关闭的缘故，可能会出异常，并且异常信息会被onError调用失败的异常覆盖
        if (getActivity() != null) {
            loadTeam.setText("加载失败，点击重试");
            loadTeam.setTextColor(getResources().getColor(R.color.text_blue));
            loadTeam.setClickable(true);
        }
    }

    @Override
    public void onRequestEnd() {

    }

    /**
     * 若信息存在，则遍历团队信息，并将信息依次添加入Layout，否则显示无团队
     *
     * @param teams 获取到的团队信息
     */
    @Override
    public void updateTeamInfo(List<TeamEntity> teams) {
        if (teams.size() > 0) {
            teamLayout.removeViewAt(1);
            for (final TeamEntity item : teams) {
                if (getActivity() == null)
                    break;
                View view = getActivity().getLayoutInflater()
                        .inflate(R.layout.item_simple_team_info, container, false);
                ((TextView) (view.findViewById(R.id.profile_team_name))).setText(item.getTeamName());
                FrescoUtils.setAvatar((SimpleDraweeView) view.findViewById(R.id.profile_team_avatar),
                        item.getAvatarUrl(), (int) getActivity().getResources().getDimension(R.dimen.small_avatar_size));
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), TeamProfileActivity.class);
                        intent.putExtra(CommonConstants.Shots.EXTRA_SHOT_AUTHOR, item);
                        startActivity(intent);
                    }
                });
                teamLayout.addView(view);
            }
        } else {
            loadTeam.setTextColor(getResources().getColor(R.color.text_grey));
            loadTeam.setClickable(false);
            loadTeam.setText("未加入任何团队");
        }
    }
}
