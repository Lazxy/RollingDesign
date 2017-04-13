package com.practice.li.rollingdesign.ui.shots.detail.info;

import android.text.TextUtils;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.practice.li.rollingdesign.R;
import com.practice.li.rollingdesign.entity.BucketsEntity;
import com.practice.li.rollingdesign.utils.HtmlFormatUtils;
import com.practice.li.rollingdesign.utils.TimeUtils;

import java.util.List;

/**
 * Created by Lazxy on 2017/3/18.
 */

public class BucketInfoAdapter extends BaseQuickAdapter<BucketsEntity,BaseViewHolder> {

    List<Integer> mSelections;

    public BucketInfoAdapter(List<Integer> selections){
        super(R.layout.item_bucket_select);
        mSelections=selections;
    }

    @Override
    protected void convert(BaseViewHolder helper, BucketsEntity item) {
        helper.setText(R.id.tv_bucket_select_title,item.getName());
        helper.setText(R.id.tv_bucket_select_item_count,item.getShotsCount()+" :项目");
        helper.setText(R.id.tv_bucket_select_update, TimeUtils.getTimeFromISO8601(item.getUpdatedAt())+"更新");
        String des= TextUtils.isEmpty(item.getDescription())?"": HtmlFormatUtils.Html2String(item.getDescription());
        helper.setText(R.id.tv_bucket_select_des,des);
        CheckBox checkBox=helper.getView(R.id.cb_bucket_select_check);
        attachToSelect(checkBox,item.getId());
    }
    private void attachToSelect(final CheckBox box, final int itemId){
        box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isChecked){
                    box.setChecked(false);
                    mSelections.remove((Integer)itemId);
                }else{
                    box.setChecked(true);
                    mSelections.add(itemId);
                }

            }
        } );
    }
}
