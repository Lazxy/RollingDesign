package com.practice.li.rollingdesign.ui.profile.common;

import android.graphics.drawable.Animatable;

import com.facebook.drawee.controller.ControllerListener;
import com.facebook.imagepipeline.image.ImageInfo;

/**
 * Created by Lazxy on 2017/3/28.
 */

public class SimpleControllerListener implements ControllerListener<ImageInfo> {
    @Override
    public void onSubmit(String id, Object callerContext) {

    }

    @Override
    public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {

    }

    @Override
    public void onIntermediateImageSet(String id, ImageInfo imageInfo) {

    }


    @Override
    public void onIntermediateImageFailed(String id, Throwable throwable) {

    }

    @Override
    public void onFailure(String id, Throwable throwable) {

    }

    @Override
    public void onRelease(String id) {

    }
}
