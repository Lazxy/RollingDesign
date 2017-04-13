package com.practice.li.rollingdesign.entity;

import com.google.gson.annotations.SerializedName;

/**
 * 认证令牌实体类
 */
public class TokenEntity {

    @SerializedName("access_token")
    private String mAccessToken;
    @SerializedName("token_type")
    private String mTokenType;
    @SerializedName("scope")
    private String mScope;
    @SerializedName("created_at")
    private int mCreatedAt;

    public String getAccessToken() {
        return mAccessToken;
    }

    public void setAccessToken(String accessToken) {
        mAccessToken = accessToken;
    }

    public String getTokenType() {
        return mTokenType;
    }

    public void setTokenType(String tokenType) {
        mTokenType = tokenType;
    }

    public String getScope() {
        return mScope;
    }

    public void setScope(String scope) {
        mScope = scope;
    }

    public int getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(int createdAt) {
        mCreatedAt = createdAt;
    }
}
