package com.practice.li.rollingdesign.entity;

import java.io.Serializable;

/**
 * Created by Lazxy on 2017/3/28.
 * 作者实体类
 */

public interface AuthorEntity extends Serializable {

    int getId();

    void setId(int id);

    String getHtmlUrl();

    void setHtmlUrl(String htmlUrl);

    String getAvatarUrl();

    void setAvatarUrl(String avatarUrl);

    String getBio();

    void setBio(String bio);

    String getLocation();

    void setLocation(String location);

    LinksEntity getLinks();

    void setLinks(LinksEntity links);

    int getBucketsCount();

    void setBucketsCount(int bucketsCount);

    int getCommentsReceivedCount();

    void setCommentsReceivedCount(int commentsReceivedCount);

    int getFollowersCount();

    void setFollowersCount(int followersCount);

    int getFollowingsCount();

    void setFollowingsCount(int followingsCount);

    int getLikesCount();

    void setLikesCount(int likesCount);

    int getLikesReceivedCount();

    void setLikesReceivedCount(int likesReceivedCount);

    int getProjectsCount();

    void setProjectsCount(int projectsCount);

    int getReboundsReceivedCount();

    void setReboundsReceivedCount(int reboundsReceivedCount);

    int getShotsCount();

    void setShotsCount(int shotsCount);

    boolean isCanUploadShot();

    void setCanUploadShot(boolean canUploadShot);

    String getType();

    void setType(String type);

    boolean isPro();

    void setPro(boolean pro);

    String getBucketsUrl();

    void setBucketsUrl(String bucketsUrl);

    String getFollowersUrl();

    void setFollowersUrl(String followersUrl);

    String getFollowingUrl();

    void setFollowingUrl(String followingUrl);

    String getLikesUrl();

    void setLikesUrl(String likesUrl);

    String getShotsUrl();

    void setShotsUrl(String shotsUrl);

    String getCreatedAt();

    void setCreatedAt(String createdAt);

    String getUpdatedAt();

    void setUpdatedAt(String updatedAt);
}
