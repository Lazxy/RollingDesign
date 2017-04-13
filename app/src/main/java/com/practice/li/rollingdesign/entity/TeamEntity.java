package com.practice.li.rollingdesign.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Lazxy on 2017/3/7.
 * 团队实体类
 */

public class TeamEntity implements AuthorEntity {
    @SerializedName("id")
    private int mId;
    @SerializedName("name")
    private String mTeamName;
    @SerializedName("username")
    private String mUsername;
    @SerializedName("html_url")
    private String mHtmlUrl;
    @SerializedName("avatar_url")
    private String mAvatarUrl;
    @SerializedName("bio")
    private String mBio;
    @SerializedName("location")
    private String mLocation;
    @SerializedName("links")
    private LinksEntity mLinks;
    @SerializedName("buckets_count")
    private int mBucketsCount;
    @SerializedName("comments_received_count")
    private int mCommentsReceivedCount;
    @SerializedName("followers_count")
    private int mFollowersCount;
    @SerializedName("followings_count")
    private int mFollowingsCount;
    @SerializedName("likes_count")
    private int mLikesCount;
    @SerializedName("likes_received_count")
    private int mLikesReceivedCount;
    @SerializedName("members_count")
    private int mMembersCount;
    @SerializedName("projects_count")
    private int mProjectsCount;
    @SerializedName("rebounds_received_count")
    private int mReboundsReceivedCount;
    @SerializedName("shots_count")
    private int mShotsCount;
    @SerializedName("can_upload_shot")
    private boolean mCanUploadShot;
    @SerializedName("type")
    private String mType;
    @SerializedName("pro")
    private boolean mPro;
    @SerializedName("buckets_url")
    private String mBucketsUrl;
    @SerializedName("followers_url")
    private String mFollowersUrl;
    @SerializedName("following_url")
    private String mFollowingUrl;
    @SerializedName("likes_url")
    private String mLikesUrl;
    @SerializedName("members_url")
    private String mMembersUrl;
    @SerializedName("shots_url")
    private String mShotsUrl;
    @SerializedName("team_shots_url")
    private String mTeamShotsUrl;
    @SerializedName("created_at")
    private String mCreatedAt;
    @SerializedName("updated_at")
    private String mUpdatedAt;

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getTeamName() {
        return mTeamName;
    }

    public void setTeamName(String name) {
        mTeamName = name;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        mUsername = username;
    }

    public String getHtmlUrl() {
        return mHtmlUrl;
    }

    public void setHtmlUrl(String htmlUrl) {
        mHtmlUrl = htmlUrl;
    }

    public String getAvatarUrl() {
        return mAvatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        mAvatarUrl = avatarUrl;
    }

    public String getBio() {
        return mBio;
    }

    public void setBio(String bio) {
        mBio = bio;
    }

    public String getLocation() {
        return mLocation;
    }

    public void setLocation(String location) {
        mLocation = location;
    }

    public LinksEntity getLinks() {
        return mLinks;
    }

    public void setLinks(LinksEntity links) {
        mLinks = links;
    }

    public int getBucketsCount() {
        return mBucketsCount;
    }

    public void setBucketsCount(int bucketsCount) {
        mBucketsCount = bucketsCount;
    }

    public int getCommentsReceivedCount() {
        return mCommentsReceivedCount;
    }

    public void setCommentsReceivedCount(int commentsReceivedCount) {
        mCommentsReceivedCount = commentsReceivedCount;
    }

    public int getFollowersCount() {
        return mFollowersCount;
    }

    public void setFollowersCount(int followersCount) {
        mFollowersCount = followersCount;
    }

    public int getFollowingsCount() {
        return mFollowingsCount;
    }

    public void setFollowingsCount(int followingsCount) {
        mFollowingsCount = followingsCount;
    }

    public int getLikesCount() {
        return mLikesCount;
    }

    public void setLikesCount(int likesCount) {
        mLikesCount = likesCount;
    }

    public int getLikesReceivedCount() {
        return mLikesReceivedCount;
    }

    public void setLikesReceivedCount(int likesReceivedCount) {
        mLikesReceivedCount = likesReceivedCount;
    }

    public int getProjectsCount() {
        return mProjectsCount;
    }

    public void setProjectsCount(int projectsCount) {
        mProjectsCount = projectsCount;
    }

    public int getReboundsReceivedCount() {
        return mReboundsReceivedCount;
    }

    public void setReboundsReceivedCount(int reboundsReceivedCount) {
        mReboundsReceivedCount = reboundsReceivedCount;
    }

    public int getShotsCount() {
        return mShotsCount;
    }

    public void setShotsCount(int shotsCount) {
        mShotsCount = shotsCount;
    }

    public int getMembersCount() {
        return mMembersCount;
    }

    public void setMembersCount(int membersCount) {
        mMembersCount = membersCount;
    }

    public boolean isCanUploadShot() {
        return mCanUploadShot;
    }

    public void setCanUploadShot(boolean canUploadShot) {
        mCanUploadShot = canUploadShot;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

    public boolean isPro() {
        return mPro;
    }

    public void setPro(boolean pro) {
        mPro = pro;
    }

    public String getBucketsUrl() {
        return mBucketsUrl;
    }

    public void setBucketsUrl(String bucketsUrl) {
        mBucketsUrl = bucketsUrl;
    }

    public String getFollowersUrl() {
        return mFollowersUrl;
    }

    public void setFollowersUrl(String followersUrl) {
        mFollowersUrl = followersUrl;
    }

    public String getFollowingUrl() {
        return mFollowingUrl;
    }

    public void setFollowingUrl(String followingUrl) {
        mFollowingUrl = followingUrl;
    }

    public String getLikesUrl() {
        return mLikesUrl;
    }

    public void setLikesUrl(String likesUrl) {
        mLikesUrl = likesUrl;
    }

    public String getTeamShotsUrl() {
        return mTeamShotsUrl;
    }

    public void setTeamShotsUrl(String url) {
        mTeamShotsUrl = url;
    }

    public String getShotsUrl() {
        return mShotsUrl;
    }

    public void setShotsUrl(String shotsUrl) {
        mShotsUrl = shotsUrl;
    }

    public String getMembersUrl() {
        return mMembersUrl;
    }

    public void setMembersUrl(String membersUrl) {
        mMembersUrl = membersUrl;
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
}
