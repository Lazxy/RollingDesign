package com.practice.li.rollingdesign.ui.shots.list;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.practice.li.rollingdesign.R;
import com.practice.li.rollingdesign.api.ApiConstants;
import com.practice.li.rollingdesign.common.ConfigManager;
import com.practice.li.rollingdesign.entity.ShotsEntity;
import com.practice.li.rollingdesign.event.EventChangeTheme;
import com.practice.li.rollingdesign.event.EventChangeViewMode;
import com.practice.li.rollingdesign.mvpframe.base.BaseFrameListFragment;
import com.practice.li.rollingdesign.utils.ThemeUtils;
import com.practice.li.rollingdesign.utils.ViewModelUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Lazxy on 2017/3/10.
 * 作品列表展示类
 */

public class ShotsListFragment extends BaseFrameListFragment<ShotsListPresenter, ShotsListModel> implements ShotsListContract.View,
        Spinner.OnItemSelectedListener {

    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.shots_list)
    RecyclerView recyclerView;
    private Map<String, Integer> shotsParams;
    private String[] sortArray, typeArray, timeFrameArray;
    private ArrayAdapter<String> sortAdapter, typeAdapter, timeFrameAdapter;
    private Spinner sortSpinner, typeSpinner, timeFrameSpinner;
    private boolean isChangingTheme;

    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.fragment_shots_list);
        ButterKnife.bind(this, getContentView());
        EventBus.getDefault().register(this);
    }

    @Override
    public void initData() {
        setList(refreshLayout, recyclerView, new ShotsAdapter(getActivity(), new ArrayList<ShotsEntity>()));
        shotsParams = mPresenter.getAllDefaultConfig();
        sortArray = getResources().getStringArray(R.array.sort_spinner);
        typeArray = getResources().getStringArray(R.array.type_spinner);
        timeFrameArray = getResources().getStringArray(R.array.time_frame_spinner);
    }

    @Override
    public void initView() {
        super.initView();
        int dropDownResource = ThemeUtils.getCurrentDropDownView(ThemeUtils.getTheme());
        ViewModelUtils.changeLayoutManager(mRecyclerView, ConfigManager.Shot.getViewMode());
        //初始化选择列表
        LinearLayout spinners = (LinearLayout) mContentView.findViewById(R.id.spinners);
        sortSpinner = (Spinner) spinners.findViewById(R.id.spinner_sort);
        sortAdapter = new ArrayAdapter<>(getActivity(), R.layout.text_spinner,
                sortArray);
        sortAdapter.setDropDownViewResource(dropDownResource);
        sortSpinner.setAdapter(sortAdapter);
        sortSpinner.setSelection(shotsParams.get(ApiConstants.ParamKey.SORT));
        typeSpinner = (Spinner) spinners.findViewById(R.id.spinner_type);
        typeAdapter = new ArrayAdapter<>(getActivity(), R.layout.text_spinner,
                typeArray);
        typeAdapter.setDropDownViewResource(dropDownResource);
        typeSpinner.setAdapter(typeAdapter);
        typeSpinner.setSelection(shotsParams.get(ApiConstants.ParamKey.TYPE));
        timeFrameSpinner = (Spinner) spinners.findViewById(R.id.spinner_time_frame);
        timeFrameAdapter = new ArrayAdapter<>(getActivity(), R.layout.text_spinner,
                timeFrameArray);
        timeFrameAdapter.setDropDownViewResource(dropDownResource);
        timeFrameSpinner.setAdapter(timeFrameAdapter);
        timeFrameSpinner.setSelection(shotsParams.get(ApiConstants.ParamKey.TIME_FRAME));
    }

    @Override
    public void initListener() {
        super.initListener();
        sortSpinner.setOnItemSelectedListener(this);
        typeSpinner.setOnItemSelectedListener(this);
        timeFrameSpinner.setOnItemSelectedListener(this);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                //控制滚动时图片不加载
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    Fresco.getImagePipeline().resume();
                } else if (newState == RecyclerView.SCROLL_STATE_SETTLING) {
                    Fresco.getImagePipeline().pause();
                }
            }
        });
    }

    @Override
    public void initLoad() {
        super.initLoad();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void changeViewMode(EventChangeViewMode viewMode) {
        ViewModelUtils.changeLayoutManager(mRecyclerView, viewMode.viewMode);
    }

    @Override
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void changeTheme(EventChangeTheme changedTheme) {
        int dropDownResource = ThemeUtils.getCurrentDropDownView(changedTheme.themeName);
        isChangingTheme=true;
        sortSpinner.setBackgroundColor(changedTheme.primaryColor);
        typeSpinner.setBackgroundColor(changedTheme.primaryColor);
        timeFrameSpinner.setBackgroundColor(changedTheme.primaryColor);
        sortAdapter.setDropDownViewResource(dropDownResource);
        sortSpinner.setAdapter(sortAdapter);
        sortSpinner.setSelection(shotsParams.get(ApiConstants.ParamKey.SORT));
        typeAdapter.setDropDownViewResource(dropDownResource);
        typeSpinner.setAdapter(typeAdapter);
        typeSpinner.setSelection(shotsParams.get(ApiConstants.ParamKey.TYPE));
        timeFrameAdapter.setDropDownViewResource(dropDownResource);
        timeFrameSpinner.setAdapter(timeFrameAdapter);
        timeFrameSpinner.setSelection(shotsParams.get(ApiConstants.ParamKey.TIME_FRAME));
        isChangingTheme=false;
    }

    @Override
    public void updateList(List<ShotsEntity> shotsEntities) {
        enableSpinner(true);
        setData(shotsEntities);
    }

    @Override
    protected void requestData(boolean isRefresh) {
        super.requestData(isRefresh);
        enableSpinner(false);
        String sort = ApiConstants.ParamValue.SORT_VALUES[shotsParams.get(ApiConstants.ParamKey.SORT)];
        String type = ApiConstants.ParamValue.LIST_VALUES[shotsParams.get(ApiConstants.ParamKey.TYPE)];
        String timeFrame = ApiConstants.ParamValue.TIME_VALUES[shotsParams.get(ApiConstants.ParamKey.TIME_FRAME)];
        mPresenter.getShotsList(sort, type, timeFrame, mPage);
    }

    @Override
    public void onRequestError(String msg) {
        super.onRequestError(msg);
        enableSpinner(true);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(isChangingTheme)
            return;
        if (parent == sortSpinner) {
            if (position != shotsParams.get(ApiConstants.ParamKey.SORT)) {
                shotsParams.put(ApiConstants.ParamKey.SORT, position);
                ConfigManager.Shot.setShotSort(position);
                requestData(true);
            }
        } else if (parent == typeSpinner) {
            if (position != shotsParams.get(ApiConstants.ParamKey.TYPE)) {
                shotsParams.put(ApiConstants.ParamKey.TYPE, position);
                ConfigManager.Shot.setShotType(position);
                requestData(true);
            }
        } else if (parent == timeFrameSpinner) {
            if (position != shotsParams.get(ApiConstants.ParamKey.TIME_FRAME)) {
                shotsParams.put(ApiConstants.ParamKey.TIME_FRAME, position);
                ConfigManager.Shot.setShotTimeFrame(position);
                requestData(true);
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    /**
     * 控制选择列表在刷新作品列表时不可用，防止重复请求
     *
     * @param enable
     */
    private void enableSpinner(boolean enable) {
        typeSpinner.setEnabled(enable);
        sortSpinner.setEnabled(enable);
        timeFrameSpinner.setEnabled(enable);
    }

    @Override
    public void onStop() {
        super.onStop();
    }

}
