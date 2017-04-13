package com.practice.li.rollingdesign.ui.shots.detail.comment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.practice.li.rollingdesign.R;
import com.practice.li.rollingdesign.common.CommonConstants;
import com.practice.li.rollingdesign.entity.CommentEntity;
import com.practice.li.rollingdesign.entity.ShotsEntity;
import com.practice.li.rollingdesign.mvpframe.base.BaseFrameListFragment;
import com.practice.li.rollingdesign.ui.widget.ListDecoration;
import com.practice.li.rollingdesign.utils.UIUtils;
import com.practice.li.rollingdesign.utils.UserInfoUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Lazxy on 2017/3/18.
 * 评论查看类
 */

public class CommentFragment extends BaseFrameListFragment<CommentPresenter, CommentModel> implements CommentContract.View {

    @BindView(R.id.comment_refresh_layout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.comment_list)
    RecyclerView recyclerView;
    private EditText commentEdit;
    private TextView commentCommit;

    private ShotsEntity mShot;
    private CommentEditDialog mEditDialog;
    private String commitedComment;
    private DialogInterface.OnCancelListener mListener = new DialogInterface.OnCancelListener() {
        @Override
        public void onCancel(DialogInterface dialog) {
            CommentEditDialog editDialog = (CommentEditDialog) dialog;
            if (editDialog != null) {
                if (editDialog.isCommit()) {
                    commitedComment = ((CommentEditDialog) dialog).getCommentContent();
                    mPresenter.createComment(commitedComment);
                    editDialog.clearCommitStatus();
                }
                commentEdit.setText(editDialog.getCommentContent());
            }
        }
    };

    public static CommentFragment newInstance(ShotsEntity shotsEntity, boolean shouldLogin) {
        CommentFragment fragment = new CommentFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(CommonConstants.Shots.EXTRA_SHOT_DETAIL, shotsEntity);
        bundle.putBoolean(CommonConstants.User.EXTRA_SHOULD_LOGIN, shouldLogin);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle onSavedInstanceState) {
        super.onCreate(onSavedInstanceState);
        setContentView(R.layout.fragment_shots_comment);
        ButterKnife.bind(this, getContentView());
    }

    @Override
    public void initData() {
        mShot = (ShotsEntity) getArguments().getSerializable(CommonConstants.Shots.EXTRA_SHOT_DETAIL);
        boolean shouldLogin = getArguments().getBoolean(CommonConstants.User.EXTRA_SHOULD_LOGIN);
        mPresenter.setShotId(mShot.getId());
        mPresenter.setShouldLogin(shouldLogin);
        setList(refreshLayout, recyclerView, new CommentAdapter(mPresenter));
        mEditDialog = new CommentEditDialog(getActivity(), mListener);
    }

    @Override
    public void initView() {
        super.initView();
        commentEdit = (EditText) getContentView().findViewById(R.id.et_comment_edit);
        commentCommit = (TextView) getContentView().findViewById(R.id.tv_comment_commit);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new ListDecoration(getActivity(), ListDecoration.VERTICAL_LIST));
        if (null == UserInfoUtils.getCurrentUser()) {
            commentEdit.setEnabled(false);
            commentCommit.setEnabled(false);
            commentCommit.setHint("请登陆后评论");
            commentCommit.setTextColor(getResources().getColor(R.color.material_grey_600));
        }
    }

    @Override
    public void initListener() {
        super.initListener();
        commentEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                //由于编辑框不能正常随输入法弹起，故使用一个相同的Dialog代替
                if (hasFocus) {
                    mEditDialog.show();
                    v.clearFocus();
                }
            }
        });
        commentCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(commentEdit.getText())) {
                    UIUtils.showSimpleAlertDialog(getActivity(), null, "确定提交该评论吗？", "提交", "取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            commitedComment = commentEdit.getText().toString();
                            mPresenter.createComment(commitedComment);
                        }
                    }, null);
                }
            }
        });
    }

    @Override
    public void initLoad() {
        super.initLoad();
    }

    @Override
    public void requestData(boolean isRefresh) {
        super.requestData(isRefresh);
        mPresenter.getCommentList(mShot.getId(), mPage);
    }

    @Override
    public void setCommentData(List<CommentEntity> comments) {
        setData(comments);
    }

    @Override
    public void onChangeStatusSuccess(boolean isLike, CommentEntity comment) {
        comment.setIsLiked(isLike);
    }

    @Override
    public void onCreateCommentSuccess(CommentEntity entity) {
        Toast.makeText(getActivity(), "评论成功", Toast.LENGTH_SHORT).show();
        commentEdit.setText("");
        requestData(true);
    }

    @Override
    public void onCreateCommentFail() {
        Toast.makeText(getActivity(), "评论失败", Toast.LENGTH_SHORT).show();
        mEditDialog.setCommentContent(commitedComment);
    }

    @Override
    public void onDeleteCommentSuccess() {
        Toast.makeText(getActivity(), "评论删除成功", Toast.LENGTH_SHORT).show();
        requestData(true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
