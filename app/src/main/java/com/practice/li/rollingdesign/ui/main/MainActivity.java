package com.practice.li.rollingdesign.ui.main;


import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.view.SimpleDraweeView;
import com.practice.li.rollingdesign.R;
import com.practice.li.rollingdesign.broadcast.NetBroadcastReceiver;
import com.practice.li.rollingdesign.common.CommonConstants;
import com.practice.li.rollingdesign.common.ConfigManager;
import com.practice.li.rollingdesign.entity.UserEntity;
import com.practice.li.rollingdesign.event.EventChangeLoginStatus;
import com.practice.li.rollingdesign.event.EventChangeTheme;
import com.practice.li.rollingdesign.event.EventChangeViewMode;
import com.practice.li.rollingdesign.mvpframe.base.BaseFrameActivity;
import com.practice.li.rollingdesign.mvpframe.base.BaseFrameListFragment;
import com.practice.li.rollingdesign.ui.setting.SettingActivity;
import com.practice.li.rollingdesign.ui.shots.list.ShotsListFragment;
import com.practice.li.rollingdesign.ui.user.MasterProfileActivity;
import com.practice.li.rollingdesign.ui.user.bucket.UserBucketFragment;
import com.practice.li.rollingdesign.ui.user.follow.UserFollowingFragment;
import com.practice.li.rollingdesign.ui.user.like.UserLikeFragment;
import com.practice.li.rollingdesign.ui.user.login.LoginActivity;
import com.practice.li.rollingdesign.ui.user.team.UserTeamsFragment;
import com.practice.li.rollingdesign.ui.widget.ThemeDialog;
import com.practice.li.rollingdesign.utils.FrescoUtils;
import com.practice.li.rollingdesign.utils.HtmlFormatUtils;
import com.practice.li.rollingdesign.utils.ThemeUtils;
import com.practice.li.rollingdesign.utils.UIUtils;
import com.practice.li.rollingdesign.utils.UserInfoUtils;
import com.practice.li.rollingdesign.utils.ViewModelUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseFrameActivity<MainPresenter, MainModel>
        implements MainContract.View, NavigationView.OnNavigationItemSelectedListener,
        ThemeDialog.OnThemeChangeListener {

    private long mExitTime;
    private Map<Class, BaseFrameListFragment> mFragmentMap;
    private UserEntity mUser;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.fab_main)
    FloatingActionButton fab;
    @BindView(R.id.toolbar_main)
    Toolbar toolbar;
    @BindView(R.id.nav_view)
    NavigationView navView;

    private TextView userName;
    private TextView userSign;
    private SimpleDraweeView userAvatar;
    private SimpleDraweeView loginIcon;
    private View loginLayout, notLoginLayout;
    private MenuItem viewModeItem;

    private ThemeDialog mThemeDialog;
    private final BroadcastReceiver mNetReceiver = new NetBroadcastReceiver();
    private final IntentFilter mFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
    private boolean mIsHome;
    private int normalTextColor; //正常情况下NavigationView 选项的字体颜色
    private final int CONTAINER_ID = R.id.fragment_container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        registerReceiver(mNetReceiver, mFilter);
        EventBus.getDefault().register(this);
    }

    @Override
    public void initData() {
        mThemeDialog = new ThemeDialog(this);
        mFragmentMap = new HashMap<>();
        mFragmentMap.put(ShotsListFragment.class, new ShotsListFragment());
        mFragmentMap.put(UserBucketFragment.class, new UserBucketFragment());
        mFragmentMap.put(UserLikeFragment.class, new UserLikeFragment());
        mFragmentMap.put(UserFollowingFragment.class, new UserFollowingFragment());
        mFragmentMap.put(UserTeamsFragment.class, new UserTeamsFragment());
        if (TextUtils.isEmpty(ConfigManager.Login.getRecentToken())) {
            if (ConfigManager.User.isFirstRun()) {
                //首次运行时直接显示登陆界面
                startActivityForResult(new Intent(this, LoginActivity.class), CommonConstants.Login.REQUEST_CODE_LOGIN);
                ConfigManager.User.setIsFirstRun(false);
            }
        } else if ((mUser = UserInfoUtils.getCurrentUser()) == null) {
            //更新当前使用者信息
            mPresenter.getUserInfo();
        }
    }

    @Override
    public void initView() {
        int accentColor = ThemeUtils.getCurrentAccentColor(this);
        normalTextColor = getResources().getColor(R.color.normalBlack);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("首页");
        fab.setBackgroundTintList(ColorStateList.valueOf(accentColor));
        //初始化NavigationView的各个控件
        navView.getChildAt(0).setVerticalScrollBarEnabled(false);
        View header = navView.getHeaderView(0);
        loginLayout = header.findViewById(R.id.main_login_layout);
        notLoginLayout = header.findViewById(R.id.main_not_login_layout);
        userName = (TextView) header.findViewById(R.id.tv_user_name);
        userSign = (TextView) header.findViewById(R.id.tv_user_sign);
        userAvatar = (SimpleDraweeView) header.findViewById(R.id.user_avatar);
        loginIcon = (SimpleDraweeView) header.findViewById(R.id.main_login_icon);
    }

    @Override
    public void initListener() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        userAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MasterProfileActivity.class);
                intent.putExtra(CommonConstants.User.EXTRA_PROFILE_AUTHOR, mUser);
                startActivity(intent);
            }
        });
        navView.setNavigationItemSelectedListener(this);
        loginIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(LoginActivity.class);
            }
        });
        mThemeDialog.setOnThemeChangeListener(this);
    }

    @Override
    public void initLoad() {
        mIsHome = true;
        showFragment(mFragmentMap.get(ShotsListFragment.class), CONTAINER_ID);
    }

    private void initUserInfo() {
        if (mUser != null) {
            notLoginLayout.setVisibility(View.GONE);
            loginLayout.setVisibility(View.VISIBLE);
            userName.setText(mUser.getName());
            userSign.setText(HtmlFormatUtils.Html2String(mUser.getBio()));
            //这里，头像的默认图片是一张gif，所以不能简单地用设置圆角将其变形，还要在xml文件里加一个圆形遮罩，且底色背景一致
            FrescoUtils.setAvatar(userAvatar, mUser.getAvatarUrl(), (int) getResources().getDimension(R.dimen.large_avatar_size));
        } else {
            notLoginLayout.setVisibility(View.VISIBLE);
            loginLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (UserInfoUtils.getCurrentUser() != null) {
            initUserInfo();
            mPresenter.getUserInfo();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoginStatusChanged(EventChangeLoginStatus status) {
        if (!status.isLogin) {
            mUser = null;
            initUserInfo();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                Snackbar.make(fab, "再次点击退出应用", Snackbar.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == CommonConstants.Login.REQUEST_CODE_LOGIN) {
            initUserInfo();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_toolbar, menu);
        viewModeItem = menu.findItem(R.id.view_mode);
        viewModeItem.setIcon(ViewModelUtils.getSavedModeIcon());
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        //在其他界面不显示视图模式的变化
        if (!mIsHome)
            menu.getItem(0).setVisible(false);
        else
            menu.getItem(0).setVisible(true);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case R.id.mode_small_with_info:
                EventBus.getDefault().post(new EventChangeViewMode(CommonConstants.Shots.VIEW_MODE_SMALL_WITH_INFO));
                Snackbar.make(fab, "浏览模式切换为：小图与简略信息", Snackbar.LENGTH_SHORT).show();
                viewModeItem.setIcon(R.mipmap.ic_small_info_white);
                break;
            case R.id.mode_large_with_info:
                EventBus.getDefault().post(new EventChangeViewMode(CommonConstants.Shots.VIEW_MODE_LARGE_WITH_INFO));
                Snackbar.make(fab, "浏览模式切换为：大图与详细信息", Snackbar.LENGTH_SHORT).show();
                viewModeItem.setIcon(R.mipmap.ic_large_info_white);
                break;
            case R.id.mode_small:
                EventBus.getDefault().post(new EventChangeViewMode(CommonConstants.Shots.VIEW_MODE_SMALL));
                Snackbar.make(fab, "浏览模式切换为：仅小图", Snackbar.LENGTH_SHORT).show();
                viewModeItem.setIcon(R.mipmap.ic_small_white);
                break;
            case R.id.mode_large:
                EventBus.getDefault().post(new EventChangeViewMode(CommonConstants.Shots.VIEW_MODE_LARGE));
                Snackbar.make(fab, "浏览模式切换为：仅大图", Snackbar.LENGTH_SHORT).show();
                viewModeItem.setIcon(R.mipmap.ic_large_white);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        switch (id) {
            case R.id.drawer_home:
                showFragment(mFragmentMap.get(ShotsListFragment.class), CONTAINER_ID);
                getSupportActionBar().setTitle("首页");
                mIsHome = true;
                /*改变相应的标识，然后刷新Toolbar的显示*/
                invalidateOptionsMenu();
                break;
            case R.id.drawer_works:

                break;
            case R.id.drawer_like:
                if (transFragment(UserLikeFragment.class)) {
                    getSupportActionBar().setTitle("喜欢");
                    mIsHome = false;
                    invalidateOptionsMenu();
                }
                break;
            case R.id.drawer_bucket:
                if (transFragment(UserBucketFragment.class)) {
                    getSupportActionBar().setTitle("收藏");
                    mIsHome = false;
                    invalidateOptionsMenu();
                }
                break;
            case R.id.drawer_following:
                if (transFragment(UserFollowingFragment.class)) {
                    getSupportActionBar().setTitle("关注");
                    mIsHome = false;
                    invalidateOptionsMenu();
                }
                break;
            case R.id.drawer_team:
                if (transFragment(UserTeamsFragment.class)) {
                    getSupportActionBar().setTitle("团队");
                    mIsHome = false;
                    invalidateOptionsMenu();
                }
                break;

            case R.id.drawer_setting:
                openActivity(SettingActivity.class);
                break;

            case R.id.drawer_theme:
                mThemeDialog.show();
            default:
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private boolean transFragment(Class fragment) {
        if (UserInfoUtils.getCurrentUser() != null) {
            showFragment(mFragmentMap.get(fragment), CONTAINER_ID);
            return true;
        } else {
            Snackbar.make(fab, "请先登录", Snackbar.LENGTH_SHORT).show();
            return false;
        }
    }

    @Override
    public void onChangeTheme(View view) {
        switch (view.getId()) {
            case R.id.theme_pink:
                setTheme(R.style.AppTheme_Pink);
                ThemeUtils.setTheme(CommonConstants.Theme.THEME_PINK);
                break;
            case R.id.theme_blue:
                setTheme(R.style.AppTheme_Blue);
                ThemeUtils.setTheme(CommonConstants.Theme.THEME_BLUE);
                break;
            case R.id.theme_red:
                setTheme(R.style.AppTheme_Red);
                ThemeUtils.setTheme(CommonConstants.Theme.THEME_RED);
                break;
            case R.id.theme_green:
                setTheme(R.style.AppTheme_Green);
                ThemeUtils.setTheme(CommonConstants.Theme.THEME_GREEN);
                break;
            default:
                setTheme(R.style.AppTheme);
                ThemeUtils.setTheme(CommonConstants.Theme.THEME_GREY);
                break;
        }
        changeTheme();
    }

    private void changeTheme() {
        TypedValue primaryColor = new TypedValue();
        TypedValue accentColor = new TypedValue();

        Resources.Theme theme = getTheme();
        theme.resolveAttribute(R.attr.colorPrimary, primaryColor, true);
        theme.resolveAttribute(R.attr.colorAccent, accentColor, true);
        int colorPrimary = getResources().getColor(primaryColor.resourceId);
        int colorAccent = getResources().getColor(accentColor.resourceId);

        //改变Toolbar的背景色
        getSupportActionBar().
                setBackgroundDrawable(new ColorDrawable(colorPrimary));

        //改变NavigationView的子项颜色
        ColorStateList colorList = new ColorStateList(CommonConstants.Theme.STATE_CHECKED,
                new int[]{normalTextColor, colorPrimary, normalTextColor});
        navView.getHeaderView(0).setBackgroundColor(colorPrimary);
        navView.setItemTextColor(colorList);
        navView.setItemIconTintList(colorList);

        //设置头像的圆形遮罩层与当前主题色一致
        RoundingParams params = RoundingParams.asCircle();
        params.setOverlayColor(colorPrimary);
        userAvatar.getHierarchy().setRoundingParams(params);

        fab.setBackgroundDrawable(new ColorDrawable(colorAccent));
        fab.setBackgroundTintList(ColorStateList.valueOf(colorAccent));

        UIUtils.setStatusBarColor(this, colorPrimary);

        //改变Spinner的颜色
        EventBus.getDefault().post(new EventChangeTheme(colorPrimary, ThemeUtils.getTheme()));
    }

    @Override
    public void onRequestStart() {
    }

    @Override
    public void onRequestError(String msg) {

    }

    @Override
    public void onRequestEnd() {
    }

    @Override
    public void setUser(UserEntity user) {
        mUser = user;
        initUserInfo();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        unregisterReceiver(mNetReceiver);
    }
}
