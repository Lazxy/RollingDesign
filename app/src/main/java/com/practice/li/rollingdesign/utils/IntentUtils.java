package com.practice.li.rollingdesign.utils;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import java.io.File;

/**
 * 界面跳转相关工具类
 */
public class IntentUtils {

    /**
     * 以关联动画打开相应Activity，
     * @param activity 当前Activity，需要设置android.view.Window#FEATURE_ACTIVITY_TRANSITIONS
     * @param v 关联的控件，需要在XML中设置android:transitionName="@string/XXX"
     * @param intent 目标Activity指向
     */
    public static void startActivity(Activity activity, View v, Intent intent) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(activity, v, "shareTransition").toBundle();
            activity.startActivity(intent, bundle);
        } else {
            activity.startActivity(intent);
        }
    }

    /**
     * 发起打开本地浏览器的链接请求
     * @param context 当前上下文
     * @param url 需要打开的链接地址
     */
    public static void startActivityToBrowser(Context context, String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        Intent chooserIntent = Intent.createChooser(intent, "选择一个应用打开该链接");
        if (chooserIntent == null) return;
        context.startActivity(chooserIntent);
    }

    /**
     * 发起分享的请求
     * @param context 当前上下文
     * @param saveFile 分享文件
     */
    public static void startActivityToShare(Context context,File saveFile){
        if(context!=null) {
            Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
            shareIntent.setType("image/*");
            shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(saveFile));
            context.startActivity(Intent.createChooser(shareIntent, "分享到"));
        }
    }
}
