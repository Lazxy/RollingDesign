package com.practice.li.rollingdesign.ui.profile.common.follow;


import com.practice.li.rollingdesign.common.CommonConstants;
import com.practice.li.rollingdesign.mvpframe.base.BaseSubscriber;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Lazxy on 2017/3/28.
 */

public class ProfileFollowPresenter extends ProfileFollowContract.Presenter {
    @Override
    public void checkFollowStatus(int id) {
        mModel.checkFollowStatus(id).enqueue(new Callback<RequestBody>() {
            @Override
            public void onResponse(Call<RequestBody> call, Response<RequestBody> response) {
                if (mView != null) {
                    if (response.code() == 204)
                        mView.onCheckFollowStatusSuccess(true);
                    else
                        mView.onCheckFollowStatusSuccess(false);
                }
                call.cancel();
            }

            @Override
            public void onFailure(Call<RequestBody> call, Throwable t) {
                if (mView != null) {
                    mView.onRequestError(CommonConstants.Error.ERROR_MESSAGE_CHECK_FOLLOW);
                }
            }
        });
    }

    @Override
    public void changeFollowStatus(int id, final boolean isFollow) {
        subscribe(mModel.changeFollowStatus(id, isFollow), new BaseSubscriber<String>(mView) {
            @Override
            protected void onSuccess(String s) {
                String result = isFollow ? "关注成功" : "取消关注成功";
                mView.onChangeFollowStatusSuccess(result);
            }

            @Override
            public void onError(Throwable t) {
                mView.onRequestError(CommonConstants.Error.ERROR_MESSAGE_CHANGE_FOLLOW_STATUS);
            }
        });
    }
}
