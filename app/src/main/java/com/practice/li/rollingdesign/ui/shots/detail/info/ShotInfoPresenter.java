package com.practice.li.rollingdesign.ui.shots.detail.info;

import android.text.TextUtils;
import android.widget.Toast;

import com.practice.li.rollingdesign.entity.CheckLikeEntity;
import com.practice.li.rollingdesign.entity.ShotsEntity;
import com.practice.li.rollingdesign.mvpframe.base.BaseSubscriber;

import java.util.List;

/**
 * Created by Lazxy on 2017/3/16.
 */

public class ShotInfoPresenter extends ShotInfoContract.Presenter {
    @Override
    void checkShotsLike(final int id) {
        subscribe(mModel.checkShotsLike(id), new BaseSubscriber<CheckLikeEntity>(mView) {
            @Override
            protected void onSuccess(CheckLikeEntity checkLikeEntity) {
                if(checkLikeEntity!=null){
                    boolean isLike=!TextUtils.isEmpty(checkLikeEntity.getCreatedAt());
                    if(isLike)
                        getShotDetail(id);
                    else
                        mView.checkShotsLikeOnSuccess(false , -1);
                }
            }
        });
    }

    @Override
    void changeShotsStatus(int id, final boolean isLike) {
        subscribe(mModel.changeShotsStatus(id, isLike), new BaseSubscriber<CheckLikeEntity>(mView) {
            @Override
            protected void onSuccess(CheckLikeEntity checkLikeEntity) {
                if(checkLikeEntity!=null){
                    mView.changeShotsLikeStatusOnSuccess(true);
                }else{
                    mView.changeShotsLikeStatusOnSuccess(false);
                }
            }
        });
    }

    @Override
    void addShotsToBuckets(final List<Integer> bucketsId, int shotsId) {
        final int size=bucketsId.size();
        for(int i=0;i<size;i++) {
            int id=bucketsId.get(i);
            subscribe(mModel.addShotsToBuckets(id, shotsId), new BaseSubscriber<String>(mView) {
                @Override
                protected void onSuccess(String s) {
                    mView.addShotsToBucketsOnSuccess(size);
                }
                @Override
                public void onError(Throwable e){
                    if(e.getMessage().contains("timeout")){
                        Toast.makeText(getContext(),"请求超时,添加收藏失败",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    @Override
    void getShotDetail(int id) {
        subscribe(mModel.getShotDetail(id), new BaseSubscriber<ShotsEntity>(mView) {
            @Override
            protected void onSuccess(ShotsEntity shotsEntity) {
                if(shotsEntity!=null){
                    mView.checkShotsLikeOnSuccess(true , shotsEntity.getLikesCount());
                }
            }
        });
    }
}
