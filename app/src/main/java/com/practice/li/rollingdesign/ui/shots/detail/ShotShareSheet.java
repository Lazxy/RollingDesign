package com.practice.li.rollingdesign.ui.shots.detail;

import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.practice.li.rollingdesign.R;
import com.practice.li.rollingdesign.common.CommonConstants;
import com.practice.li.rollingdesign.entity.ShotsEntity;
import com.practice.li.rollingdesign.utils.FrescoUtils;
import com.practice.li.rollingdesign.utils.IntentUtils;
import com.practice.li.rollingdesign.utils.UrlUtils;

import java.io.File;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Lazxy on 2017/3/15.
 */

public class ShotShareSheet extends BottomSheetDialogFragment{




    public static ShotShareSheet newInstance(ShotsEntity shot){
        Bundle args=new Bundle();
        args.putString(CommonConstants.Shots.EXTRA_SHOT_URL,shot.getHtmlUrl());
        args.putString(CommonConstants.Shots.EXTRA_SHOT_IMAGE_URL, UrlUtils.getHighQualityImageUrl(shot.getImages()));
        ShotShareSheet sheet=new ShotShareSheet();
        sheet.setArguments(args);
        return sheet;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle saveInstanceState){
        View view=inflater.inflate(R.layout.sheets_share_shots,container,false);
        ButterKnife.bind(this,view);
        return view;
    }

    @OnClick(R.id.tv_sheets_share_image)
    void shareImage(){
        String imageUrl=getArguments().getString(CommonConstants.Shots.EXTRA_SHOT_IMAGE_URL);
        String[] temp=imageUrl.split("/");//解析结果最后两个字符串分别为文件名（带格式）和id
        String id=temp[temp.length-2];
        Toast.makeText(getActivity(),"文件准备中，请等待",Toast.LENGTH_LONG).show();
        FrescoUtils.saveImage(imageUrl,id, new FrescoUtils.ImageLoadListener() {
            @Override
            public void loadSuccess(Object file) {
                IntentUtils.startActivityToShare(getActivity(),(File)file);
            }

            @Override
            public void loadFail(String msg) {
                getActivity().onBackPressed();
                Toast.makeText(getActivity(),msg,Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick(R.id.tv_sheets_share_link)
    void shareLink(){
        IntentUtils.startActivityToBrowser(getActivity(),
                getArguments().getString(CommonConstants.Shots.EXTRA_SHOT_URL));
    }

}
