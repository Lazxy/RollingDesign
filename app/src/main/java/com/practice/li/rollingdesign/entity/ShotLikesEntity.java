package com.practice.li.rollingdesign.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Lazxy on 2017/3/26.
 * 当前用户喜欢的作品实体类
 */

public class ShotLikesEntity {

    @SerializedName("id")
    int mId;
    @SerializedName("created_at")
    String mCreatedAt;
    @SerializedName("shot")
    ShotsEntity mShot;

    public void setId(int id){
        mId=id;
    }

    public int getId(){
        return mId;
    }

    public void setCreatedAt(String createdAt){
        mCreatedAt=createdAt;
    }

    public String getCreatedAt(){
        return mCreatedAt;
    }

    public void setShot(ShotsEntity shot){
        mShot=shot;
    }

    public ShotsEntity getShot(){
        return mShot;
    }
}
