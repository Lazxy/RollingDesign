package com.practice.li.rollingdesign.ui.profile.common.follow;

import com.practice.li.rollingdesign.mvpframe.base.BaseModel;
import com.practice.li.rollingdesign.mvpframe.base.BasePresenter;
import com.practice.li.rollingdesign.mvpframe.base.BaseView;

import okhttp3.RequestBody;
import retrofit2.Call;
import rx.Observable;

/**
 * Created by Lazxy on 2017/3/28.
 */

public interface ProfileFollowContract {

    interface Model extends BaseModel {
        Call<RequestBody> checkFollowStatus(int id);
        Observable<String> changeFollowStatus(int id,boolean isFollow);
    }

    interface View extends BaseView {
        void onCheckFollowStatusSuccess(boolean isFollowed);
        void onChangeFollowStatusSuccess(String result);
    }

    abstract class Presenter extends BasePresenter<Model,View>{
        public abstract void checkFollowStatus(int id);
        public abstract void changeFollowStatus(int id, boolean isFollow);
    }
}
