package com.practice.li.rollingdesign.ui.widget;

import android.text.TextPaint;
import android.text.style.UnderlineSpan;

/**
 * 无下划线的链接文字段
 */
public class NoLineSpan extends UnderlineSpan {

    public static final NoLineSpan CREATOR = new NoLineSpan();

    @Override
    public void updateDrawState(TextPaint ds) {
        ds.setUnderlineText(false);
    }
}
 