package com.practice.li.rollingdesign.utils;

import android.text.Html;
import android.text.Spannable;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import com.practice.li.rollingdesign.ui.widget.NoLineSpan;

/**
 * Html转换工具类
 */
public class HtmlFormatUtils {

    /**
     * 除去Html字符串中的段落
     * @param textView 目标文本控件
     * @param htmlStr 目标目标Html字符串
     */
    public static void Html2StringNoP(TextView textView, String htmlStr) {
        htmlStr = htmlStr.replace("<p>", "");
        htmlStr = htmlStr.replace("</p>", "");
        Html2String(textView, htmlStr);
    }

    /**
     * 将Html转化为Span形式传入文本控件
     * @param textView 目标文本控件
     * @param htmlStr 目标目标Html字符串
     */
    public static void Html2String(TextView textView, String htmlStr) {
        textView.setText(Html.fromHtml(htmlStr));
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        Spannable spannable = (Spannable) textView.getText();
        NoLineSpan noLineSpan = new NoLineSpan();
        spannable.setSpan(noLineSpan, 0, spannable.length(), Spanned.SPAN_MARK_MARK);
    }

    public static String Html2String(String htmlStr){
        return Html.fromHtml(htmlStr).toString();
    }

    /**
     * 设置特定字体段加粗字体
     * @param text 加粗字体段
     * @param normalText 正常字体段
     * @return
     */
    public static String setupBold(String text, String normalText) {
        return Html.fromHtml("<b>" + text + "</b> " + normalText).toString();
    }

    public static String setupBold(int text, String normalText) {
        return setupBold(text + "", normalText);
    }
}
