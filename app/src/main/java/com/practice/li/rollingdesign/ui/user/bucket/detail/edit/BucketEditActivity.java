package com.practice.li.rollingdesign.ui.user.bucket.detail.edit;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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
 * Created by Lazxy on 2017/3/30.
 * 收藏夹编辑操作类
 */

public class BucketEditActivity extends BaseFrameActivity<BucketEditPresenter,BucketEditModel> implements
    BucketEditContract.View{

    @BindView(R.id.toolbar_eidt_bucket)
    Toolbar toolbar;
    @BindView(R.id.et_edit_bucket_title)
    EditText editTitle;
    @BindView(R.id.et_edit_bucket_des)
    EditText editDescription;
    @BindView(R.id.btn_edit_bucket_delete)
    Button deleteBucket;

    private String mTitle;
    private String mDescription;
    private int mBucketId;
    public static final int RESULT_UPDATE=0x16;
    public static final int RESULT_DELETE=0x32;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_bucket);
        ButterKnife.bind(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        menu.add("完成")
                .setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_ALWAYS)
                .setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                mPresenter.updateBucket(editTitle.getText().toString(),editDescription.getText().toString(),
                       mBucketId);
                return true;
            }
        });
        return true;
    }

    @Override
    public void initData(){
        mBucketId=getIntent().getIntExtra(CommonConstants.Bucket.EXTRA_BUCKET_ID,-1);
        mTitle=getIntent().getStringExtra(CommonConstants.Bucket.EXTRA_BUCKET_TITLE);
        mDescription=getIntent().getStringExtra(CommonConstants.Bucket.EXTRA_BUCKET_DESCRIPTION);
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
        editTitle.setText(mTitle);
        editTitle.setSelection(mTitle.length());
        editDescription.setText(mDescription);
    }

    @Override
    public void initListener(){
        deleteBucket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIUtils.showSimpleAlertDialog(BucketEditActivity.this, null,
                        "确定要删除该收藏夹吗？收藏夹内的数据将全部清除且不可恢复。", "删除", "取消",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //此处应该有相应的界面变化提示
                                mPresenter.deleteBucket(mBucketId);
                            }
                        },null);
            }
        });
    }

    @Override
    public void onRequestStart() {

    }

    @Override
    public void onRequestError(String msg) {

    }

    @Override
    public void onRequestEnd() {

    }

    @Override
    public void onUpdateBucketSuccess(BucketsEntity bucket) {
        Toast.makeText(this,"更新成功",Toast.LENGTH_SHORT).show();
        Intent intent=new Intent();
        intent.putExtra(CommonConstants.Bucket.EXTRA_BUCKET_TITLE,bucket.getName());
        setResult(RESULT_UPDATE,intent);
        finish();
    }

    @Override
    public void onDeleteBucketSuccess() {
        setResult(RESULT_DELETE,null);
        finish();
    }
}
