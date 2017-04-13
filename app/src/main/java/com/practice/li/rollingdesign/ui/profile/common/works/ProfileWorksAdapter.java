package com.practice.li.rollingdesign.ui.profile.common.works;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.practice.li.rollingdesign.R;
import com.practice.li.rollingdesign.common.CommonConstants;
import com.practice.li.rollingdesign.entity.ShotsEntity;
import com.practice.li.rollingdesign.ui.shots.detail.ShotDetailActivity;
import com.practice.li.rollingdesign.utils.FrescoUtils;
import com.practice.li.rollingdesign.utils.UrlUtils;

/**
 * Created by Lazxy on 2017/3/24.
 */

public class ProfileWorksAdapter extends BaseQuickAdapter<ShotsEntity,BaseViewHolder> {

    private Activity mActivity;

    public ProfileWorksAdapter(Activity activity){
        super(R.layout.item_shot_small);
        mActivity=activity;
    }

    @Override
    protected void convert(BaseViewHolder helper, ShotsEntity item) {
        SimpleDraweeView preview=helper.getView(R.id.shot_preview);
        if(item.isAnimated()) {
            helper.setVisible(R.id.iv_shot_gif, true);
            FrescoUtils.setAnimationDepends(preview,item.getImages());
        }else{
            FrescoUtils.setPreview(preview, UrlUtils.getLowQualityImageUrl(item.getImages()));
        }
        attachToShotDetail(preview,item);
    }

    private void attachToShotDetail(View view, final ShotsEntity shotsEntity){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mActivity,ShotDetailActivity.class);
                intent.putExtra(CommonConstants.Shots.EXTRA_SHOT_DETAIL,shotsEntity);
                mActivity.startActivity(intent);
            }
        });
    }
}
