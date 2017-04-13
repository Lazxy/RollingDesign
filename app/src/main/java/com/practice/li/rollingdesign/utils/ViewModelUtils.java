package com.practice.li.rollingdesign.utils;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.practice.li.rollingdesign.R;
import com.practice.li.rollingdesign.common.CommonConstants;
import com.practice.li.rollingdesign.common.ConfigManager;

/**
 * 视图模式工具类
 */
public class ViewModelUtils {

    /**
     * 改变当前列表布局方式
     * @param rv 显示列表
     * @param viewMode 视图显示模式
     */
    public static void changeLayoutManager(RecyclerView rv, int viewMode) {
        switch (viewMode) {
            case CommonConstants.Shots.VIEW_MODE_SMALL_WITH_INFO:
            case CommonConstants.Shots.VIEW_MODE_SMALL:
                rv.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                break;
            case CommonConstants.Shots.VIEW_MODE_LARGE_WITH_INFO:
            case CommonConstants.Shots.VIEW_MODE_LARGE:
                rv.setLayoutManager(new LinearLayoutManager(rv.getContext()));
                break;
        }
        ConfigManager.Shot.saveViewMode(viewMode);
    }

    /**
     * 获取当前视图模式下的图标
     * @return 图标资源ID
     */
    public static int getSavedModeIcon(){
        int viewMode= ConfigManager.Shot.getViewMode();
        switch(viewMode){
            case CommonConstants.Shots.VIEW_MODE_SMALL_WITH_INFO:
                return R.mipmap.ic_small_info_white;
            case CommonConstants.Shots.VIEW_MODE_SMALL:
                return R.mipmap.ic_small_white;
            case CommonConstants.Shots.VIEW_MODE_LARGE_WITH_INFO:
                return R.mipmap.ic_large_info_white;
            case CommonConstants.Shots.VIEW_MODE_LARGE:
                return R.mipmap.ic_large_white;
            default:return R.mipmap.ic_small_info_white;
        }
    }
}