package com.practice.li.rollingdesign.ui.profile.common.info;

import com.practice.li.rollingdesign.api.ApiClient;
import com.practice.li.rollingdesign.api.UserApi;
import com.practice.li.rollingdesign.entity.TeamEntity;

import java.util.List;

import rx.Observable;

/**
 * Created by Lazxy on 2017/3/27.
 */

public class ProfileInfoModel implements ProfileInfoContract.Model {
    @Override
    public Observable<List<TeamEntity>> getTeamInfo(int id) {
        return ApiClient.getForInit(UserApi.class).getTeamsList(id+"");
    }
}
