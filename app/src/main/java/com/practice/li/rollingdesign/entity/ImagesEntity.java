package com.practice.li.rollingdesign.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 作品地址实体类
 */
public class ImagesEntity implements Serializable {

    @SerializedName("hidpi")
    private String mHidpi;
    @SerializedName("normal")
    private String mNormal;
    @SerializedName("teaser")
    private String mTeaser;

    public String getHidpi() {
        return mHidpi;
    }

    public void setHidpi(String hidpi) {
        mHidpi = hidpi;
    }

    public String getNormal() {
        return mNormal;
    }

    public void setNormal(String normal) {
        mNormal = normal;
    }

    public String getTeaser() {
        return mTeaser;
    }

    public void setTeaser(String teaser) {
        mTeaser = teaser;
    }
}
