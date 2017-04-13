package com.practice.li.rollingdesign.entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.google.gson.annotations.SerializedName;
import com.practice.li.rollingdesign.common.ConfigManager;


import java.io.Serializable;
import java.util.List;

/**
 * 作品实体类
 */
public class ShotsEntity implements Serializable, MultiItemEntity {

    @SerializedName("id")
    private int mId;
    @SerializedName("title")
    private String mTitle;
    @SerializedName("description")
    private String mDescription;
    @SerializedName("width")
    private int mWidth;
    @SerializedName("height")
    private int mHeight;
    @SerializedName("images")
    private ImagesEntity mImages;
    @SerializedName("views_count")
    private int mViewsCount;
    @SerializedName("likes_count")
    private int mLikesCount;
    @SerializedName("comments_count")
    private int mCommentsCount;
    @SerializedName("attachments_count")
    private int mAttachmentsCount;
    @SerializedName("rebounds_count")
    private int mReboundsCount;
    @SerializedName("buckets_count")
    private int mBucketsCount;
    @SerializedName("created_at")
    private String mCreatedAt;
    @SerializedName("updated_at")
    private String mUpdatedAt;
    @SerializedName("html_url")
    private String mHtmlUrl;
    @SerializedName("attachments_url")
    private String mAttachmentsUrl;
    @SerializedName("buckets_url")
    private String mBucketsUrl;
    @SerializedName("comments_url")
    private String mCommentsUrl;
    @SerializedName("likes_url")
    private String mLikesUrl;
    @SerializedName("projects_url")
    private String mProjectsUrl;
    @SerializedName("rebounds_url")
    private String mReboundsUrl;
    @SerializedName("animated")
    private boolean mAnimated;
    @SerializedName("user")
    private UserEntity mUser;
    @SerializedName("team")
    private TeamEntity mTeam;
    @SerializedName("tags")
    private List<String> mTags;

    @Override
    public int getItemType() {
        return ConfigManager.Shot.getViewMode();
    }

    public static class Builder {

        private int id;
        private String title;
        private String description;
        private int width;
        private int height;
        private ImagesEntity images;
        private int views_count;
        private int likes_count;
        private int comments_count;
        private int attachments_count;
        private int rebounds_count;
        private int buckets_count;
        private String created_at;
        private String updated_at;
        private String html_url;
        private String attachments_url;
        private String buckets_url;
        private String comments_url;
        private String likes_url;
        private String projects_url;
        private String rebounds_url;
        private boolean animated;
        private List<String> tags;
        private UserEntity user;
        private TeamEntity team;

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setWidth(int width) {
            this.width = width;
            return this;
        }

        public Builder setHeight(int height) {
            this.height = height;
            return this;
        }

        public Builder setImages(ImagesEntity images) {
            this.images = images;
            return this;
        }

        public Builder setViewsCount(int views_count) {
            this.views_count = views_count;
            return this;
        }

        public Builder setLikesCount(int likes_count) {
            this.likes_count = likes_count;
            return this;
        }

        public Builder setCommentsCount(int comments_count) {
            this.comments_count = comments_count;
            return this;
        }

        public Builder setAttachmentsCount(int attachments_count) {
            this.attachments_count = attachments_count;
            return this;
        }

        public Builder setReboundsCount(int rebounds_count) {
            this.rebounds_count = rebounds_count;
            return this;
        }

        public Builder setBucketsCount(int buckets_count) {
            this.buckets_count = buckets_count;
            return this;
        }

        public Builder setCreatedAt(String created_at) {
            this.created_at = created_at;
            return this;
        }

        public Builder setUpdatedAt(String updated_at) {
            this.updated_at = updated_at;
            return this;
        }

        public Builder setHtmlUrl(String html_url) {
            this.html_url = html_url;
            return this;
        }

        public Builder setAttachmentsUrl(String attachments_url) {
            this.attachments_url = attachments_url;
            return this;
        }

        public Builder setBucketsUrl(String buckets_url) {
            this.buckets_url = buckets_url;
            return this;
        }

        public Builder setCommentsUrl(String comments_url) {
            this.comments_url = comments_url;
            return this;
        }

        public Builder setLikesUrl(String likes_url) {
            this.likes_url = likes_url;
            return this;
        }

        public Builder setProjectsUrl(String projects_url) {
            this.projects_url = projects_url;
            return this;
        }

        public Builder setReboundsUrl(String rebounds_url) {
            this.rebounds_url = rebounds_url;
            return this;
        }

        public Builder setAnimated(boolean animated) {
            this.animated = animated;
            return this;
        }

        public Builder setTags(List<String> tags) {
            this.tags = tags;
            return this;
        }

        public Builder setUser(UserEntity user) {
            this.user = user;
            return this;
        }

        public Builder setTeam(TeamEntity team) {
            this.team = team;
            return this;
        }

        public ShotsEntity build() {
            return new ShotsEntity(id, title, description, width, height, images, views_count, likes_count,
                    comments_count, attachments_count, rebounds_count, buckets_count, created_at, updated_at, html_url,
                    attachments_url, buckets_url, comments_url, likes_url, projects_url, rebounds_url, animated, user,
                    team, tags);
        }
    }

    public ShotsEntity(int id, String title, String description, int width, int height, ImagesEntity images,
                       int viewsCount, int likesCount, int commentsCount, int attachmentsCount, int reboundsCount,
                       int bucketsCount, String createdAt, String updatedAt, String htmlUrl, String attachmentsUrl,
                       String bucketsUrl, String commentsUrl, String likesUrl, String projectsUrl, String reboundsUrl,
                       boolean animated, UserEntity user, TeamEntity team, List<String> tags) {
        mId = id;
        mTitle = title;
        mDescription = description;
        mWidth = width;
        mHeight = height;
        mImages = images;
        mViewsCount = viewsCount;
        mLikesCount = likesCount;
        mCommentsCount = commentsCount;
        mAttachmentsCount = attachmentsCount;
        mReboundsCount = reboundsCount;
        mBucketsCount = bucketsCount;
        mCreatedAt = createdAt;
        mUpdatedAt = updatedAt;
        mHtmlUrl = htmlUrl;
        mAttachmentsUrl = attachmentsUrl;
        mBucketsUrl = bucketsUrl;
        mCommentsUrl = commentsUrl;
        mLikesUrl = likesUrl;
        mProjectsUrl = projectsUrl;
        mReboundsUrl = reboundsUrl;
        mAnimated = animated;
        mUser = user;
        mTeam = team;
        mTags = tags;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public int getWidth() {
        return mWidth;
    }

    public void setWidth(int width) {
        mWidth = width;
    }

    public int getHeight() {
        return mHeight;
    }

    public void setHeight(int height) {
        mHeight = height;
    }

    public ImagesEntity getImages() {
        return mImages;
    }

    public void setImages(ImagesEntity images) {
        mImages = images;
    }

    public int getViewsCount() {
        return mViewsCount;
    }

    public void setViewsCount(int viewsCount) {
        mViewsCount = viewsCount;
    }

    public int getLikesCount() {
        return mLikesCount;
    }

    public void setLikesCount(int likesCount) {
        mLikesCount = likesCount;
    }

    public int getCommentsCount() {
        return mCommentsCount;
    }

    public void setCommentsCount(int commentsCount) {
        mCommentsCount = commentsCount;
    }

    public int getAttachmentsCount() {
        return mAttachmentsCount;
    }

    public void setAttachmentsCount(int attachmentsCount) {
        mAttachmentsCount = attachmentsCount;
    }

    public int getReboundsCount() {
        return mReboundsCount;
    }

    public void setReboundsCount(int reboundsCount) {
        mReboundsCount = reboundsCount;
    }

    public int getBucketsCount() {
        return mBucketsCount;
    }

    public void setBucketsCount(int bucketsCount) {
        mBucketsCount = bucketsCount;
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

    public String getHtmlUrl() {
        return mHtmlUrl;
    }

    public void setHtmlUrl(String htmlUrl) {
        mHtmlUrl = htmlUrl;
    }

    public String getAttachmentsUrl() {
        return mAttachmentsUrl;
    }

    public void setAttachmentsUrl(String attachmentsUrl) {
        mAttachmentsUrl = attachmentsUrl;
    }

    public String getBucketsUrl() {
        return mBucketsUrl;
    }

    public void setBucketsUrl(String bucketsUrl) {
        mBucketsUrl = bucketsUrl;
    }

    public String getCommentsUrl() {
        return mCommentsUrl;
    }

    public void setCommentsUrl(String commentsUrl) {
        mCommentsUrl = commentsUrl;
    }

    public String getLikesUrl() {
        return mLikesUrl;
    }

    public void setLikesUrl(String likesUrl) {
        mLikesUrl = likesUrl;
    }

    public String getProjectsUrl() {
        return mProjectsUrl;
    }

    public void setProjectsUrl(String projectsUrl) {
        mProjectsUrl = projectsUrl;
    }

    public String getReboundsUrl() {
        return mReboundsUrl;
    }

    public void setReboundsUrl(String reboundsUrl) {
        mReboundsUrl = reboundsUrl;
    }

    public boolean isAnimated() {
        return mAnimated;
    }

    public void setAnimated(boolean animated) {
        mAnimated = animated;
    }

    public UserEntity getUser() {
        return mUser;
    }

    public void setUser(UserEntity user) {
        mUser = user;
    }

    public TeamEntity getTeam() {
        return mTeam;
    }

    public void setTeam(TeamEntity team) {
        mTeam = team;
    }

    public List<String> getTags() {
        return mTags;
    }

    public void setTags(List<String> tags) {
        mTags = tags;
    }

}
