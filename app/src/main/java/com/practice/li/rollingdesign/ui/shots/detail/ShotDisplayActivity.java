package com.practice.li.rollingdesign.ui.shots.detail;

import android.Manifest;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.imagepipeline.image.ImageInfo;
import com.practice.li.rollingdesign.R;
import com.practice.li.rollingdesign.common.BaseActivity;
import com.practice.li.rollingdesign.common.CommonConstants;
import com.practice.li.rollingdesign.ui.profile.common.SimpleControllerListener;
import com.practice.li.rollingdesign.ui.widget.CircleProgressDrawable;
import com.practice.li.rollingdesign.utils.FrescoUtils;
import com.practice.li.rollingdesign.utils.PermissionUtils;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.relex.photodraweeview.PhotoDraweeView;
import uk.co.senab.photoview.PhotoView;

/**
 * Created by Lazxy on 2017/3/20.
 */

public class ShotDisplayActivity extends BaseActivity implements PermissionUtils.PermissionListener,
        FrescoUtils.ImageLoadListener {

    @BindView(R.id.shot_display_gif)
    PhotoDraweeView photoAnimated;
    @BindView(R.id.shot_display_static)
    PhotoView photoStatic;
    @BindView(R.id.shot_display_rotate)
    ImageView rotateImage;
    @BindView(R.id.shot_display_download)
    ImageView downloadImage;

    private String mImageUrl;
    private boolean mIsAnimated;
    private boolean mLoadingCompleted=false;

    private final int LOAD_SUCCESS=10001;
    private final int LOAD_FAIL=10002;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==LOAD_SUCCESS){
                //图片保存成功
                Toast.makeText(ShotDisplayActivity.this, "图片已保存", Toast.LENGTH_SHORT).show();
            }else if(msg.what==LOAD_FAIL){
                //图片保存失败
                Toast.makeText(ShotDisplayActivity.this, "图片加载失败，请确认当前网络状况是否良好",
                        Toast.LENGTH_SHORT).show();
            }else if (msg.obj == null) {
                //PhotoView进度条改变
                photoStatic.getDrawable().setLevel(msg.what);
                photoStatic.setFocusableInTouchMode(false);
            } else {
                //加载图片完成
                photoStatic.setTag(null);
                photoStatic.setImageBitmap((Bitmap) msg.obj);
                mLoadingCompleted=true;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_shot_display);
        ButterKnife.bind(this);
    }

    public void initData() {
        //获得图片加载信息
        mImageUrl = getIntent().getExtras().getString(CommonConstants.Shots.EXTRA_SHOT_IMAGE_URL);
        mIsAnimated = getIntent().getExtras().getBoolean(CommonConstants.Shots.EXTRA_SHOT_GIF);
    }

    public void initView() {
        //分开加载GIF图与静态图
        if (mIsAnimated) {
            photoAnimated.getHierarchy().setProgressBarImage(new CircleProgressDrawable(),
                    ScalingUtils.ScaleType.CENTER_INSIDE);
            photoAnimated.setAdjustViewBounds(true);
            FrescoUtils.setPreview(photoAnimated, Uri.parse(mImageUrl), true, mListener);
            photoStatic.setVisibility(View.GONE);
        } else {
            photoStatic.setTag(mHandler);
            FrescoUtils.setStaticBitmap(mImageUrl, photoStatic, this);
            photoAnimated.setVisibility(View.GONE);
        }
    }

    public void initListener() {
        //这里的监听其实可以直接用OnClick绑定
        rotateImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rotateImage();
            }
        });
        downloadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadImage();
            }
        });
    }

    /**
     * 旋转屏幕方向
     */
    private void rotateImage() {
        if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
    }

    /**
     * 下载图片到本地
     */
    private void downloadImage() {
        if (mLoadingCompleted) {
            FrescoUtils.saveImage(mImageUrl, System.currentTimeMillis() + "", this);
        } else {
            Toast.makeText(this, "下载未完成", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            rotateImage();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.shot_display_toolbar, menu);
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionUtils.onPermissionRequestResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onGranted() {
        FrescoUtils.saveImage(mImageUrl, System.currentTimeMillis() + "", ShotDisplayActivity.this);
    }

    @Override
    public void onDenied(String permission) {
        PermissionUtils.showMissingPermissionDialog(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .show();
    }

    @Override
    public void loadSuccess(Object object) {
        if (object instanceof File) {
            //由于这个回调是在子线程被执行的，所以需要Handler代为传递
            mHandler.sendEmptyMessage(LOAD_SUCCESS);
        }
    }

    @Override
    public void loadFail(String msg) {
        //由于这个回调是在子线程被执行的，所以需要Handler代为传递
        mHandler.sendEmptyMessage(LOAD_FAIL);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }

    //GIF图下载监听，用来防止占位图被随意放缩导致显示问题
    private SimpleControllerListener mListener=new SimpleControllerListener(){

        @Override
        public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
            photoAnimated.setEnabled(true);
            mLoadingCompleted=true;
        }

        @Override
        public void onSubmit(String id, Object callerContext) {
            photoAnimated.setEnabled(false);
        }

    };
}
