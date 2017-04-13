package com.practice.li.rollingdesign.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import java.lang.reflect.Method;

/**
 * Created by Lazxy on 2017/2/8.
 * UI相关类
 */

public class UIUtils {

    /**
     * 显示一个简单的默认布局对话框
     * @param context 当前上下文
     * @param title 对话框标题
     * @param message 对话框内容
     * @param positiveSelection 对话框确定选项文字
     * @param negativeSelection 对话框取消选项文字
     * @param positiveListener 确定选项点击事件
     * @param negativeListener 取消选项点击事件
     */
    public static void showSimpleAlertDialog(Context context, String title, String message, String positiveSelection,
                                             String negativeSelection, DialogInterface.OnClickListener positiveListener,
                                             DialogInterface.OnClickListener negativeListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(positiveSelection, positiveListener)
                .setNegativeButton(negativeSelection, negativeListener)
                .create().show();
    }

    /**
     * 强制在PopupWindow中显示图标
     * @param menu 菜单对象
     * @param enable 是否显示图标
     */
    public static void setIconEnable(Menu menu, boolean enable) {
        try {
            Class<?> clazz = Class.forName("android.support.v7.view.menu.MenuBuilder");
            Method m = clazz.getDeclaredMethod("setOptionalIconsVisible", boolean.class);
            m.setAccessible(true);
            //传入参数
            m.invoke(menu, enable);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 测量文字显示占宽度
     * @param text 待测字符串
     * @param textSize 字符串大小
     * @return
     */
    public static int measureTextWidth(String text, int textSize) {
        Paint fontPaint = new Paint();
        fontPaint.setTextSize(textSize);
        Rect rect = new Rect();
        fontPaint.getTextBounds(text, 0, text.length(), rect);
        return rect.width();
    }

    /**
     * 设置输入法弹出
     * @param context
     */
    public static void showInputMethod(Context context) {
        //自动弹出键盘
        InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        //强制隐藏Android输入法窗口
        // inputManager.hideSoftInputFromWindow(edit.getWindowToken(),0);
    }

    public static void setStatusBarColor(Activity activity, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(color);
        }
    }

    public static void translucentStatusBar(Activity activity){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //透明状态栏
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

}
