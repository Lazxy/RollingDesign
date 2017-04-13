package com.practice.li.rollingdesign.ui.profile.team.members;

import com.practice.li.rollingdesign.api.ApiClient;
import com.practice.li.rollingdesign.api.ApiConstants;
import com.practice.li.rollingdesign.api.TeamApi;
import com.practice.li.rollingdesign.entity.UserEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;

/**
 * Created by Lazxy on 2017/3/28.
 */

public class ProfileTeamMemberModel implements ProfileTeamMemberContract.Model {
    @Override
    public Observable<List<UserEntity>> getMembersList(int id, int page) {
        Map<String,String> params=new HashMap<>();
        params.put(ApiConstants.ParamKey.PAGE,page+"");
        params.put(ApiConstants.ParamKey.PER_PAGE,ApiConstants.ParamValue.PAGE_SIZE+"");
        return ApiClient.getForInit(TeamApi.class).getMembers(id+"",params);
    }
}
