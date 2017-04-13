package com.practice.li.rollingdesign.ui.widget;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

import com.facebook.drawee.drawable.CloneableDrawable;
import com.facebook.drawee.drawable.DrawableUtils;
import com.practice.li.rollingdesign.RollingDesign;
import com.practice.li.rollingdesign.utils.ScreenUtils;

/**
 * Created by Lazxy on 2017/3/22.
 * 圆形进度条控件
 */

public class CircleProgressDrawable extends Drawable implements CloneableDrawable {

    private final Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Path mPath = new Path();
    private final RectF mRect = new RectF();
    private int mBackgroundColor = 0xFF000000;
    private int mColor = 0xFFFFFFFF;
    private int mPadding;
    private int mRadius;
    private int mStrokeWidth;
    private int mInnerRadius;
    private int mLevel = 0;
    private boolean mHideWhenZero = false;
    private final String PERCENT = " %";

    public CircleProgressDrawable() {
        float density = ScreenUtils.getDensity(RollingDesign.applicationContext);
        mPadding = (int) (3 * density);
        mRadius = (int) (25 * density);
        mStrokeWidth = (int) (2 * density);
        mInnerRadius = (int) (20 * density);
    }

    /**
     * Sets the progress bar color.
     */
    public void setColor(int color) {
        if (mColor != color) {
            mColor = color;
            invalidateSelf();
        }
    }

    /**
     * Gets the progress bar color.
     */
    public int getColor() {
        return mColor;
    }

    /**
     * Sets the progress bar background color.
     */
    public void setBackgroundColor(int backgroundColor) {
        if (mBackgroundColor != backgroundColor) {
            mBackgroundColor = backgroundColor;
            invalidateSelf();
        }
    }

    /**
     * Gets the progress bar background color.
     */
    public int getBackgroundColor() {
        return mBackgroundColor;
    }

    /**
     * Sets the progress bar padding.
     */
    public void setPadding(int padding) {
        if (mPadding != padding) {
            mPadding = padding;
            invalidateSelf();
        }
    }

    /**
     * Gets the progress bar padding.
     */
    @Override
    public boolean getPadding(Rect padding) {
        padding.set(mPadding, mPadding, mPadding, mPadding);
        return mPadding != 0;
    }

    /**
     * The progress bar will be displayed as a rounded , sets the radius here.
     */
    public void setRadius(int radius) {
        if (mRadius != radius) {
            mRadius = radius;
            invalidateSelf();
        }
    }

    /**
     * Gets the radius of the progress bar.
     */
    public int getRadius() {
        return mRadius;
    }

    public void setInnerRadius(int radius) {
        if (mInnerRadius != radius) {
            mInnerRadius = radius;
            invalidateSelf();
        }
    }

    /**
     * Gets the radius of the progress bar.
     */
    public int getInnerRadius() {
        return mInnerRadius;
    }

    /**
     * Sets whether the progress bar should be hidden when the progress is 0.
     */
    public void setHideWhenZero(boolean hideWhenZero) {
        mHideWhenZero = hideWhenZero;
    }

    public int getStrokeWidth() {
        return mStrokeWidth;
    }

    public void setStrokeWidth(int width) {
        if (mStrokeWidth != width) {
            mStrokeWidth = width;
            invalidateSelf();
        }
    }

    /**
     * Gets whether the progress bar should be hidden when the progress is 0.
     */
    public boolean getHideWhenZero() {
        return mHideWhenZero;
    }

    @Override
    protected boolean onLevelChange(int level) {
        mLevel = level;
        invalidateSelf();
        return true;
    }

    @Override
    public void draw(Canvas canvas) {
        if (mHideWhenZero && mLevel == 0) {
            return;
        }
        mPaint.setColor(mColor);
        mPaint.setStrokeWidth(mStrokeWidth);
        Rect bounds = getBounds();
        int centerX = getBounds().centerX();
        int centerY = getBounds().centerY();
        mRect.set(centerX - mInnerRadius, centerY - mInnerRadius, centerX + mInnerRadius, centerY + mInnerRadius);

        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(bounds.centerX(), bounds.centerY(), mRadius, mPaint);

        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawArc(mRect, -90, mLevel * 360 / 10000f, true, mPaint);
    }


    @Override
    public void setAlpha(int alpha) {
        mPaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        mPaint.setColorFilter(cf);
    }

    @Override
    public int getOpacity() {
        return DrawableUtils.getOpacityFromColor(mPaint.getColor());
    }

    @Override
    public Drawable cloneDrawable() {
        final CircleProgressDrawable copy = new CircleProgressDrawable();
        copy.mBackgroundColor = mBackgroundColor;
        copy.mColor = mColor;
        copy.mPadding = mPadding;
        copy.mLevel = mLevel;
        copy.mRadius = mRadius;
        copy.mHideWhenZero = mHideWhenZero;
        return copy;
    }
}
