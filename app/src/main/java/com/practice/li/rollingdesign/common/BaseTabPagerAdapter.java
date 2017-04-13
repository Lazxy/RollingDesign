package com.practice.li.rollingdesign.common;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Lazxy on 2017/3/14.
 * 适用于TabLayout+ViewPager的适配器基类
 */

public class BaseTabPagerAdapter<T extends BaseFragment> extends PagerAdapter {

    private final FragmentManager mManager;
    private List<T> mFragments;
    private String[] mTitle;

    public BaseTabPagerAdapter(FragmentManager manager, List<T> fragments, String[] title) {
        mManager = manager;
        mFragments = fragments;
        mTitle = title;
    }

    @Override
    public View instantiateItem(ViewGroup container, int position) {
        Fragment fragment = mFragments.get(position);
        if (!fragment.isAdded()) {
            //.commitAllowingStateLoss()允许在Activity保存状态后commit
            mManager.beginTransaction().add(fragment, fragment.getClass().getName()).commitAllowingStateLoss();
            mManager.executePendingTransactions();
        }
        View view = fragment.getView();
        if (view.getParent() == null)
            container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mFragments.get(position).getView());
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitle != null ? mTitle[position] : super.getPageTitle(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
