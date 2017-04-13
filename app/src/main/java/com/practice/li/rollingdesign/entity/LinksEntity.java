package com.practice.li.rollingdesign.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 链接实体类
 */
public class LinksEntity implements Serializable {

    @SerializedName("web")
    private String mWeb;
    @SerializedName("twitter")
    private String mTwitter;

    public String getWeb() {
        return mWeb;
    }

    public void setWeb(String web) {
        mWeb = web;
    }

    public String getTwitter() {
        return mTwitter;
    }

    public void setTwitter(String twitter) {
        mTwitter = twitter;
    }
}
