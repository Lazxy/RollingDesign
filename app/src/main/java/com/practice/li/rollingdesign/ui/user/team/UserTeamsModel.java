package com.practice.li.rollingdesign.ui.user.team;

import com.practice.li.rollingdesign.api.ApiClient;
import com.practice.li.rollingdesign.api.UserApi;
import com.practice.li.rollingdesign.entity.TeamEntity;

import java.util.List;

import rx.Observable;

/**
 * Created by Lazxy on 2017/3/27.
 */

public class UserTeamsModel implements UserTeamsContract.Model {
    @Override
    public Observable<List<TeamEntity>> getTeamsList() {
        return ApiClient.getForInit(UserApi.class).getCurrentTeamsList();
    }
}
