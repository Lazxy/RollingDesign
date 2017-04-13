package com.practice.li.rollingdesign.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Lazxy on 2017/3/26.
 */

public class FollowingEntity {
    @SerializedName("id")
    private int mId;
    @SerializedName("created_at")
    private String mCreatedAt;
    @SerializedName("followee")
    private UserEntity mFollowing;

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

    public UserEntity getFollowing() {
        return mFollowing;
    }

    public void setFollowing(UserEntity follower) {
        mFollowing = follower;
    }
}
