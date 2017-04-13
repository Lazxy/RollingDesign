package com.practice.li.rollingdesign.ui.user.bucket.detail;

import com.practice.li.rollingdesign.entity.ShotsEntity;
import com.practice.li.rollingdesign.mvpframe.base.BaseSubscriber;

import java.util.HashSet;
import java.util.List;

/**
 * Created by Lazxy on 2017/3/29.
 */

public class BucketDetailPresenter extends BucketDetailContract.Presenter {

    private int mCounter;

    @Override
    void removeShotsFromBucket(final HashSet<Integer> shots, int id) {
        if(shots!=null) {
            mCounter=0;
            for (int item : shots) {
                subscribe(mModel.removeShotFromBucket(item, id), new BaseSubscriber<String>(mView) {
                    @Override
                    protected void onSuccess(String s) {
                        if(++mCounter==shots.size())
                            mView.onRemoveShotsSuccess();
                    }

                    @Override
                    public void onError(Throwable t){
                        //这里的失败应该有其他提示，不过鉴于其出现于API不完整的情况，故先暂时放弃。
                        if(++mCounter==shots.size())
                            mView.onRemoveShotsSuccess();
                    }
                });
            }
        }
    }

    @Override
    void getShotsFromBucket(int id, int page) {
        subscribe(mModel.getShotsFromBucket(id, page), new BaseSubscriber<List<ShotsEntity>>(mView) {
            @Override
            protected void onSuccess(List<ShotsEntity> shotsEntities) {
                if(shotsEntities!=null)
                    mView.updateData(shotsEntities);
            }
        });
    }
}
