package com.practice.li.rollingdesign.ui.user.bucket;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.practice.li.rollingdesign.R;
import com.practice.li.rollingdesign.common.CommonConstants;
import com.practice.li.rollingdesign.entity.BucketsEntity;
import com.practice.li.rollingdesign.ui.user.bucket.detail.BucketDetailActivity;
import com.practice.li.rollingdesign.utils.FrescoUtils;
import com.practice.li.rollingdesign.utils.TimeUtils;
import com.practice.li.rollingdesign.utils.UrlUtils;

/**
 * Created by Lazxy on 2017/3/29.
 */

public class UserBucketAdapter extends BaseQuickAdapter<BucketsEntity,BaseViewHolder> {

    private Activity mActivity;

    public UserBucketAdapter(Activity activity){
        super(R.layout.item_user_bucket);
        mActivity=activity;
    }

    @Override
    protected void convert(BaseViewHolder helper, BucketsEntity item) {
        SimpleDraweeView preview=helper.getView(R.id.user_bucket_shot_preview);
        if(item.getFirstShot()!=null)
            FrescoUtils.setPreview(preview, UrlUtils.getLowQualityImageUrl(item.getFirstShot().getImages()));
        helper.setText(R.id.tv_user_bucket_title,item.getName());
        helper.setText(R.id.tv_user_bucket_item_count,item.getShotsCount()+" 作品");
        helper.setText(R.id.tv_user_bucket_update, TimeUtils.getTimeFromISO8601(item.getUpdatedAt())+" 更新");
        attachToBucketDetails(helper.getView(R.id.layout_user_bucket),item);
    }

    private void attachToBucketDetails(View view,final BucketsEntity bucket){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mActivity, BucketDetailActivity.class);
                intent.putExtra(CommonConstants.Bucket.EXTRA_BUCKET,bucket);
                mActivity.startActivity(intent);
            }
        });
    }
}
