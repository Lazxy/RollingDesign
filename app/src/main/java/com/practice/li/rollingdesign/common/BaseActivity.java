package com.practice.li.rollingdesign.common;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.practice.li.rollingdesign.utils.ThemeUtils;


/**
 * Created by Lazxy on 2017/2/21.
 * Activity 模板型基类
 */

public class BaseActivity extends AppCompatActivity implements BaseFunc {

    public FragmentManager mFragmentManager;
    protected Fragment mCurrentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        initTheme();
        super.onCreate(savedInstanceState);
        mFragmentManager = getFragmentManager();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        initData();
        initView();
        initListener();
        initLoad();
    }

    public void openActivity(Class<? extends BaseActivity> toActivity) {
        openActivity(toActivity, null);
    }

    /**
     * 打开新的Activity
     *
     * @param toActivity 目标Activity
     * @param parameter  需要携带的Bundle参数
     */
    public void openActivity(Class<? extends BaseActivity> toActivity, Bundle parameter) {
        Intent intent = new Intent(this, toActivity);
        if (parameter != null) {
            intent.putExtras(parameter);
        }
        startActivity(intent);

    }

    protected void showShortToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 初始化当前主题
     */
    protected void initTheme() {
        String theme = ThemeUtils.getTheme();
        setTheme(ThemeUtils.getThemeResource(theme));
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {

    }

    @Override
    public void initListener() {

    }


    public void initLoad() {

    }


    /*以下方法只适用于Fragment只有一个容器的情况*/

    public boolean isFragmentAdded() {
        return mCurrentFragment != null && mCurrentFragment.isAdded();
    }

    /**
     * @return 当前碎片是否正在显示
     */
    public boolean isFragmentShowing() {
        return mCurrentFragment != null && !mCurrentFragment.isHidden();
    }

    /**
     * 显示一个被隐藏的碎片或者添加一个新的碎片
     *
     * @param fragment    目标碎片
     * @param containerId 碎片容器ID
     * @param <T>         碎片类型
     */
    public <T extends BaseFragment> void showFragment(T fragment, int containerId) {
        if (fragment.isAdded() && fragment.isHidden()) {
            mFragmentManager.beginTransaction()
                    .hide(mCurrentFragment)
                    .show(fragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit();
            mCurrentFragment = fragment;
        } else if (!fragment.isAdded()) {
            addFragment(fragment, containerId);
        }
    }

    /**
     * 隐藏一个碎片
     *
     * @param fragment 隐藏目标碎片
     * @param <T>
     */
    public <T extends BaseFragment> void hideFragment(T fragment) {
        mFragmentManager.beginTransaction().hide(fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();
    }

    /**
     * 添加一个碎片
     *
     * @param fragment    目标碎片
     * @param containerId 碎片容器ID
     * @param <T>         碎片类型
     */
    public <T extends BaseFragment> void addFragment(T fragment, int containerId) {
        if (isFragmentShowing()) {
            mFragmentManager.beginTransaction()
                    .hide(mCurrentFragment)
                    .add(containerId, fragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit();
        } else {
            mFragmentManager.beginTransaction()
                    .add(containerId, fragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit();
        }
        mCurrentFragment = fragment;
    }
}
