package com.practice.li.rollingdesign.ui.shots.detail.info;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.practice.li.rollingdesign.R;
import com.practice.li.rollingdesign.api.ApiClient;
import com.practice.li.rollingdesign.api.ApiConstants;
import com.practice.li.rollingdesign.api.BucketApi;
import com.practice.li.rollingdesign.common.CommonConstants;
import com.practice.li.rollingdesign.entity.BucketsEntity;
import com.practice.li.rollingdesign.event.EventSelectBucket;
import com.practice.li.rollingdesign.mvpframe.base.BaseSubscriber;
import com.practice.li.rollingdesign.mvpframe.base.BaseView;
import com.practice.li.rollingdesign.ui.user.bucket.create.BucketCreateActivity;
import com.practice.li.rollingdesign.ui.widget.ListDecoration;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Lazxy on 2017/3/17.
 * 收藏时收藏夹选择类
 */

public class BucketSelectFragment extends BottomSheetDialogFragment implements BaseView {

    @BindView(R.id.bucket_select_add_buckets)
    TextView addBuckets;
    @BindView(R.id.bucket_select_done)
    Button selectConfirm;
    @BindView(R.id.bucket_select_list)
    RecyclerView bucketList;
    @BindView(R.id.bucket_select_progress)
    ProgressBar progressBar;

    private View mRootView;
    //    private List<BucketsEntity> mBuckets;
    private List<Integer> mSelections;
    private BaseQuickAdapter mAdapter;
    private View mEmptyView, mErrorView;
    private BottomSheetBehavior mBehavior;
    private final int REQUEST_CODE = 0x256;

    /*这里也可以直接选择重写onCreateView，但这样不能直接获得Behavior的值，
    因为在这个函数返回View后才会将其加入CoordinatorLayout中*/
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        BottomSheetDialog dialog = new BottomSheetDialog(getContext(), getTheme());
        mRootView = View.inflate(getActivity(), R.layout.fragment_bucket_select, null);
        mEmptyView = View.inflate(getActivity(), R.layout.layout_empty_view, null);
        mErrorView = View.inflate(getActivity(), R.layout.layout_error_view, null);
        dialog.setContentView(mRootView);
        mBehavior = BottomSheetBehavior.from((View) mRootView.getParent());
        ButterKnife.bind(this, mRootView);
        initData();
        initView();
        initListener();
        initLoad();
        return dialog;
    }

    //    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState){
//        //这里的mRootView其实就是根布局RelativeLayout,在这个布局被返回后,会被添加到一个FrameLayout下,而它是
//        //一个CoordinatorLayout的子布局
//        mRootView=inflater.inflate(R.layout.fragment_bucket_select,container,false);
//        mEmptyView=inflater.inflate(R.layout.layout_empty_view,container,false);
//        mErrorView=inflater.inflate(R.layout.layout_error_view,container,false);
//        ButterKnife.bind(this,mRootView);
//        initData();
//        initView();
//        initListener();
//        initLoad();
//        return mRootView;
//    }
    public void initData() {
//        mBuckets = new ArrayList<>();
        mSelections = new ArrayList<>();
        mAdapter = new BucketInfoAdapter(mSelections);
    }

    public void initView() {
        mBehavior.setPeekHeight((int) getResources().getDimension(R.dimen.peek_height));
        /*初始化RecyclerView，设置Adapter,里面包括checkBox的监听，初步计划是直接把mSelections传进去*/
        bucketList.setAdapter(mAdapter);
        bucketList.setLayoutManager(new LinearLayoutManager(getActivity()));
        bucketList.addItemDecoration(new ListDecoration(getActivity(), ListDecoration.VERTICAL_LIST));
    }

    public void initListener() {
        mRootView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                /*这里的监听是为了第一次加载完列表时能正确显示按钮，否则列表会将按钮覆盖*/
                if (mBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED)
                    selectConfirm.layout(0, mBehavior.getPeekHeight() - selectConfirm.getHeight(), mRootView.getRight(), mBehavior.getPeekHeight());
                else if (mBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED)
                    selectConfirm.layout(0, mRootView.getBottom() - selectConfirm.getHeight(), mRootView.getRight(), mRootView.getBottom());
            }
        });
        /*设置添加bucket的点击监听和完成的点击监听*/
        addBuckets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*添加一个收藏夹，这里需要一个新的DialogFragment或者一个AlertDialog，再说吧*/
                Intent intent = new Intent(getActivity(), BucketCreateActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
        selectConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSelections.isEmpty())
                    Toast.makeText(getActivity(), "未选择任何项", Toast.LENGTH_SHORT).show();
                else {
                    EventBus.getDefault().post(new EventSelectBucket(mSelections));
                    dismiss();
                }
            }
        });
        mErrorView.findViewById(R.id.tv_error_view_retry).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRequestStart();
                //不再显示EmptyView以避免控件与进度条重叠
                ((ViewGroup) mAdapter.getEmptyView()).removeAllViews();
                getBucketList();
            }
        });
        mBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                    dismiss();
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                //保证Fragment伸展过程中底部按钮和进度条的位置正确
                if (slideOffset >= 0) {
                    int bottom = (int) (mBehavior.getPeekHeight() +
                            (mRootView.getHeight() - mBehavior.getPeekHeight()) * slideOffset);
                    selectConfirm.layout(0, bottom - selectConfirm.getHeight(), mRootView.getRight(), bottom);
                    if (progressBar.getVisibility() == View.VISIBLE) {
                        int halfBottom = bottom / 2;
                        progressBar.layout(progressBar.getLeft(), halfBottom - progressBar.getHeight(),
                                progressBar.getRight(), halfBottom);
                    }
                }
            }
        });
    }

    public void initLoad() {
        //获取bucketList
        onRequestStart();
        getBucketList();
    }

    private void getBucketList() {
        Map<String, String> params = new HashMap<>();
        params.put(ApiConstants.ParamKey.PAGE, 1 + "");
        params.put(ApiConstants.ParamKey.PER_PAGE, ApiConstants.ParamValue.PAGE_SIZE + "");
        ApiClient.getForInit(BucketApi.class).getUserBucketsList(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<List<BucketsEntity>>(this) {
                    @Override
                    protected void onSuccess(List<BucketsEntity> bucketsEntities) {
                        onRequestEnd();
                        if (bucketsEntities != null) {
                            if (bucketsEntities.isEmpty()) {
                                mAdapter.setEmptyView(mEmptyView);
                                mAdapter.notifyDataSetChanged();
                            } else {
//                                mBuckets.addAll(bucketsEntities);
                                mAdapter.setNewData(bucketsEntities);
                                //这里本来应该显示是否添加了当前的Shot的，但是API未提供相应方法，获取每个buckets的
                                //shot列表然后遍历又太耗时，所以暂时不添加该功能
                            }
                        }
                    }

                });
    }

    @Override
    public void onRequestStart() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onRequestError(String msg) {
        mAdapter.setEmptyView(mErrorView);
        mAdapter.notifyDataSetChanged();
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onRequestEnd() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == FragmentActivity.RESULT_OK) {
            BucketsEntity bucket = (BucketsEntity) data.getSerializableExtra(CommonConstants.Bucket.EXTRA_BUCKET);
            mAdapter.addData(bucket);
        }
    }
}
