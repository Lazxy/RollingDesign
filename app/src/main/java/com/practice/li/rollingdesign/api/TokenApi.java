package com.practice.li.rollingdesign.api;

import com.practice.li.rollingdesign.entity.TokenEntity;

import java.util.Map;

import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by Lazxy on 2017/3/5.
 */

public interface TokenApi {
    @POST(ApiConstants.Path.TOKEN)
    Observable<TokenEntity> getToken(@QueryMap Map<String, String> params);
}
