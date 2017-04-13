package com.practice.li.rollingdesign.ui.user.bucket.create;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.practice.li.rollingdesign.R;
import com.practice.li.rollingdesign.common.CommonConstants;
import com.practice.li.rollingdesign.entity.BucketsEntity;
import com.practice.li.rollingdesign.mvpframe.base.BaseFrameActivity;
import com.practice.li.rollingdesign.utils.UIUtils;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Lazxy on 2017/4/2.
 * 收藏夹创建类
 */

public class BucketCreateActivity extends BaseFrameActivity<BucketCreatePresenter,BucketCreateModel>
        implements BucketCreateContract.View{

    @BindView(R.id.toolbar_create_bucket)
    Toolbar toolbar;
    @BindView(R.id.et_create_bucket_title)
    EditText editName;
    @BindView(R.id.et_create_bucket_des)
    EditText editDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_bucket);
        ButterKnife.bind(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        menu.add("完成")
                .setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_ALWAYS)
                .setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        String name=editName.getText().toString();
                        if(!TextUtils.isEmpty(name))
                            mPresenter.createBucket(name,
                                editDescription.getText().toString());
                        return true;
                    }
                });
        return true;
    }

    @Override
    public void initView(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                UIUtils.showInputMethod(BucketCreateActivity.this);
            }
        },200);
    }

    @Override
    public void onRequestStart() {
        //这里应该加一个进度条显示
    }

    @Override
    public void onRequestError(String msg) {

    }

    @Override
    public void onRequestEnd() {

    }

    @Override
    public void onCreateBucketSuccess(BucketsEntity bucket) {
        Toast.makeText(this,"创建成功",Toast.LENGTH_SHORT).show();
        Intent intent=new Intent();
        intent.putExtra(CommonConstants.Bucket.EXTRA_BUCKET,bucket);
        setResult(RESULT_OK,intent);
        finish();
    }
}
