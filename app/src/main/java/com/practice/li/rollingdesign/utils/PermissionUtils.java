package com.practice.li.rollingdesign.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by lazxy on 2016/12/31.
 * 运行时权限相关工具类
 */

public class PermissionUtils {
    public static final int PERMISSION_REQUEST_CODE = 0x1;
    public static final int OVERLAY_PERMISSION_REQ_CODE = 0x10;
    public static final int USAGE_STATS_REQ_CODE = 0x100;
    private static PermissionListener mListener;

    /**
     * 申请相关权限
     *
     * @param mContext    当前上下文
     * @param permissions 需要申请的权限
     * @param listener    权限申请结果监听
     * @return 是否拥有所有需要申请的权限
     */
    public static boolean requestPermission(Context mContext, String[] permissions, PermissionListener listener) {
        mListener = listener;
        List<String> deniedPermissions = new ArrayList<String>();
        for (String permission : permissions) {
            if (ActivityCompat.checkSelfPermission(mContext, permission) != PackageManager.PERMISSION_GRANTED) {
                deniedPermissions.add(permission);
            }
        }
        if (deniedPermissions.size() != 0) {
            ActivityCompat.requestPermissions((Activity) mContext, deniedPermissions.toArray(new String[deniedPermissions.size()]), PERMISSION_REQUEST_CODE);
            return false;
        }
        return true;
    }

    /**
     * 权限申请结果监听
     *
     * @param requestCode  申请码
     * @param permissions  申请权限列表
     * @param grantResults 申请结果
     */
    public static void onPermissionRequestResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE && mListener != null) {
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_DENIED)
                    mListener.onDenied(permissions[i]);
                else
                    mListener.onGranted();
            }
        }
    }

    /**
     * 显示权限申请失败的对话框
     *
     * @param mContext   当前上下文
     * @param permission 申请失败的权限
     * @return
     */
    public static AlertDialog.Builder showMissingPermissionDialog(final Context mContext, final String permission) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("权限提示");
        builder.setMessage("权限申请失败，请点击确定按钮再次申请该权限或前往设置手动开启该权限");

        builder.setNegativeButton("退出", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (mContext instanceof Activity) {
                    ((Activity) mContext).finish();
                }
            }
        });

        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                requestPermissionWithJudge(mContext, permission);
            }
        });

        return builder;
    }

    /**
     * 再次申请权限，若被禁止再显示权限申请对话框则直接跳转设置页，否则显示权限申请对话框
     *
     * @param mContext   当前上下文
     * @param permission 需要申请的权限
     */
    private static void requestPermissionWithJudge(Context mContext, String permission) {
        if (ActivityCompat.checkSelfPermission(mContext, permission) == PackageManager.PERMISSION_DENIED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale((Activity) mContext, permission)) {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.setData(Uri.parse("package:" + mContext.getPackageName()));
                mContext.startActivity(intent);
            } else {
                requestPermission(mContext, new String[]{permission}, mListener);
            }
        }

    }

    /**
     * 获得应用信息获取权限
     *
     * @param activity 当前Activity
     * @return 是否拥有应用信息获取权限
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static boolean getAppUsageAccessPermission(final Activity activity) {
        AppOpsManager appOps = (AppOpsManager)
                activity.getSystemService(Context.APP_OPS_SERVICE);
        int mode;
        mode = appOps.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS,
                android.os.Process.myUid(), activity.getPackageName());
        if (mode != AppOpsManager.MODE_ALLOWED) {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setMessage("使用该应用需要开启相关权限，请点击\"确定\"在设置中手动开启，开启后可双击返回键返回此应用")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            activity.startActivityForResult(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS), USAGE_STATS_REQ_CODE);
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(activity,
                                    "需要开启该权限时请到设置->安全->有权查看使用情况的应用 中手动开启", Toast.LENGTH_SHORT).show();
                        }
                    }).show();
            return false;
        }
        return true;
    }

    /**
     * 打开允许全局窗口权限
     *
     * @param activity 当前Activity
     */
    public static void setDrawOverFlow(Activity activity) {
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + activity.getPackageName()));
        activity.startActivityForResult(intent, OVERLAY_PERMISSION_REQ_CODE);
    }

    /**
     * 检查是否需要申请运行时权限
     *
     * @return 是否需要申请运行时权限
     */
    public static boolean needToRequestPermission() {
        return Build.VERSION.SDK_INT >= 23;
    }

    /**
     * 自定义权限申请监听接口，一般用来代理系统默认回调接口
     */
    public interface PermissionListener {
        void onGranted();

        void onDenied(String permission);
    }
}
