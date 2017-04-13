package com.practice.li.rollingdesign.ui.user.bucket.detail;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.View;
import android.widget.CompoundButton;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.practice.li.rollingdesign.R;
import com.practice.li.rollingdesign.common.CommonConstants;
import com.practice.li.rollingdesign.entity.ShotsEntity;
import com.practice.li.rollingdesign.ui.shots.detail.ShotDetailActivity;
import com.practice.li.rollingdesign.utils.FrescoUtils;

import java.util.HashSet;

/**
 * Created by Lazxy on 2017/3/29.
 */

public class BucketDetailAdapter extends BaseQuickAdapter<ShotsEntity,BaseViewHolder> {

    private Activity mActivity;
    private boolean isShowCheckBox;
    private HashSet<Integer> mSelections;

    public BucketDetailAdapter(Activity activity, HashSet<Integer> selections) {
        super(R.layout.item_bucket_detail);
        mActivity=activity;
        mSelections=selections;
    }

    @Override
    protected void convert(BaseViewHolder helper, ShotsEntity item) {
        FrescoUtils.setPreview((SimpleDraweeView) helper.getView(R.id.bucket_shot_preview),item.getImages().getNormal());
        helper.setText(R.id.tv_bucket_shot_title,item.getTitle());
        helper.setText(R.id.tv_bucket_shot_author_name,"创作于 "+item.getUser().getName());
        helper.setVisible(R.id.iv_bucket_shot_gif,item.isAnimated());
        helper.getView(R.id.cb_bucket_shots_select_check);
        AppCompatCheckBox checkBox=helper.getView(R.id.cb_bucket_shots_select_check);
        if(isShowCheckBox) {
            checkBox.setVisibility(View.VISIBLE);
            attachCheckListener(checkBox, item.getId());
            checkBox.setChecked(mSelections.contains(item.getId()));
        }else{
            checkBox.setVisibility(View.GONE);
        }
        attachToShotDetail(helper.getView(R.id.layout_bucket_shot_detail),item);
    }

    private void attachToShotDetail(View view,final ShotsEntity shotsEntity){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mActivity,ShotDetailActivity.class);
                intent.putExtra(CommonConstants.Shots.EXTRA_SHOT_DETAIL,shotsEntity);
                mActivity.startActivity(intent);
            }
        });
    }

    private void attachCheckListener(AppCompatCheckBox checkBox, final int shotId){
        checkBox.setOnCheckedChangeListener(new AppCompatCheckBox.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    mSelections.add(shotId);
                else{
                    mSelections.remove(shotId);
                }
            }
        });
    }

    void setShowCheckBox(boolean showCheckBox){
        this.isShowCheckBox=showCheckBox;
        notifyDataSetChanged();
    }
}
