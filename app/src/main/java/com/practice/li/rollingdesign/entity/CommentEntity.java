package com.practice.li.rollingdesign.entity;


import com.google.gson.annotations.SerializedName;

/**
 * 评论实体类
 */
public class CommentEntity {

    @SerializedName("id")
    private int mId;
    @SerializedName("body")
    private String mBody;
    @SerializedName("likes_count")
    private int mLikesCount;
    @SerializedName("likes_url")
    private String mLikesUrl;
    @SerializedName("created_at")
    private String mCreatedAt;
    @SerializedName("updated_at")
    private String mUpdatedAt;
    @SerializedName("user")
    private UserEntity mUser;

    private boolean isLiked;

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getBody() {
        return mBody;
    }

    public void setBody(String body) {
        mBody = body;
    }

    public int getLikesCount() {
        return mLikesCount;
    }

    public void setLikesCount(int likesCount) {
        mLikesCount = likesCount;
    }

    public String getLikesUrl() {
        return mLikesUrl;
    }

    public void setLikesUrl(String likesUrl) {
        mLikesUrl = likesUrl;
    }

    public String getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(String createdAt) {
        mCreatedAt = createdAt;
    }

    public String getUpdatedAt() {
        return mUpdatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        mUpdatedAt = updatedAt;
    }

    public UserEntity getUser() {
        return mUser;
    }

    public void setUser(UserEntity user) {
        mUser = user;
    }

    public void setIsLiked(boolean isLiked){this.isLiked=isLiked;}

    public boolean getIsLiked(){
        return this.isLiked;
    }

}
