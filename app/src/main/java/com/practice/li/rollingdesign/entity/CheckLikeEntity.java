package com.practice.li.rollingdesign.entity;

import com.google.gson.annotations.SerializedName;

public class CheckLikeEntity {

    @SerializedName("id")
    private int mId;
    @SerializedName("created_at")
    private String mCreatedAt;

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(String createdAt) {
        mCreatedAt = createdAt;
    }
}
