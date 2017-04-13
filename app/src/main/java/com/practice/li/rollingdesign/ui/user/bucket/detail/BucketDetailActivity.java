package com.practice.li.rollingdesign.ui.user.bucket.detail;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.practice.li.rollingdesign.R;
import com.practice.li.rollingdesign.common.CommonConstants;
import com.practice.li.rollingdesign.entity.BucketsEntity;
import com.practice.li.rollingdesign.entity.ShotsEntity;
import com.practice.li.rollingdesign.mvpframe.base.BaseFrameListActivity;
import com.practice.li.rollingdesign.ui.user.bucket.detail.edit.BucketEditActivity;
import com.practice.li.rollingdesign.utils.UIUtils;

import java.util.HashSet;
import java.util.List;

/**
 * Created by Lazxy on 2017/3/29.
 * 收藏夹具体内容展示类
 */

public class BucketDetailActivity extends BaseFrameListActivity< BucketDetailPresenter, BucketDetailModel> implements BucketDetailContract.View{

    private Toolbar toolbar;
    private Button deleteButton;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;

    private HashSet<Integer> mSelections;
    private BucketsEntity mBucket;

    private final int REQUEST_CODE=0x128;

    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_bucket_detail);
    }

    @Override
    public void onBackPressed(){
        if(deleteButton.getVisibility()==View.VISIBLE)
            toggleSelectedStatus(false);
        else
            super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.bucket_detail_toolbar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id=item.getItemId();
        switch(id){
            case R.id.bucket_detail_manage_shot:
                ((BucketDetailAdapter)mAdapter).setShowCheckBox(true);
                toggleSelectedStatus(true);
                break;
            case R.id.bucket_detail_edit_bucket:
                //进入编辑收藏的页面
                Intent intent=new Intent(this, BucketEditActivity.class);
                intent.putExtra(CommonConstants.Bucket.EXTRA_BUCKET_ID,mBucket.getId());
                intent.putExtra(CommonConstants.Bucket.EXTRA_BUCKET_TITLE,mBucket.getName());
                intent.putExtra(CommonConstants.Bucket.EXTRA_BUCKET_DESCRIPTION,mBucket.getDescription());
                startActivityForResult(intent,REQUEST_CODE);
                break;
            default:break;
        }
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void initData(){
        mSelections=new HashSet<>();
        mBucket=(BucketsEntity) getIntent().getSerializableExtra(CommonConstants.Bucket.EXTRA_BUCKET);
    }

    @Override
    public void initView(){
        deleteButton=(Button)findViewById(R.id.btn_bucket_shot_delete);
        toolbar=(Toolbar)findViewById(R.id.toolbar_bucket_detail);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(mBucket.getName());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        refreshLayout=(SwipeRefreshLayout)findViewById(R.id.base_refresh_layout);
        recyclerView=(RecyclerView)findViewById(R.id.base_recycle_list);
        setList(refreshLayout,recyclerView,new BucketDetailAdapter(this,mSelections));
        super.initView();
    }

    @Override
    public void initListener(){
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mSelections.size()>0&&mSelections.size()<20){
                    UIUtils.showSimpleAlertDialog(BucketDetailActivity.this, null,
                            "确定要删除这 " + mSelections.size() + " 项吗？",
                            "删除", "取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    mPresenter.removeShotsFromBucket(mSelections,mBucket.getId());
                                    List<ShotsEntity> shots=(List<ShotsEntity>)mAdapter.getData();
                                    for(int i=0;i<shots.size();i++){
                                        if(mSelections.contains(shots.get(i).getId())) {
                                            shots.remove(i);
                                            i--;
                                        }
                                    }
                                    mAdapter.notifyDataSetChanged();
                                }
                            },null);

                }else if(mSelections.size()>=20){
                    //为了保证有足够的API请求次数
                    Toast.makeText(BucketDetailActivity.this,"选择项不能大于20项",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(BucketDetailActivity.this,"请选择待删除的选项",Toast.LENGTH_SHORT).show();
                }
            }
        });
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void requestData(boolean isRefresh){
        super.requestData(isRefresh);
        mPresenter.getShotsFromBucket(mBucket.getId(),mPage);
    }

    @Override
    public void onRemoveShotsSuccess() {
        Toast.makeText(this,"删除完成",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateData(List<ShotsEntity> shots) {
        setData(shots);
    }

    /**
     * 切换普通模式与选择模式
     * @param isInSelected
     */
    private void toggleSelectedStatus(boolean isInSelected){
        int visibility=isInSelected?View.VISIBLE:View.GONE;
        String title=isInSelected?"请勾选需要删除的项":mBucket.getName();
        ((BucketDetailAdapter)mAdapter).setShowCheckBox(isInSelected);
        deleteButton.setVisibility(visibility);
        getSupportActionBar().setTitle(title);
        mSelections.clear();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode ,Intent data){
        if(resultCode==BucketEditActivity.RESULT_UPDATE){
            getSupportActionBar().setTitle(data.getStringExtra(CommonConstants.Bucket.EXTRA_BUCKET_TITLE));
        }else if(resultCode==BucketEditActivity.RESULT_DELETE){
            finish();
        }
    }
}
