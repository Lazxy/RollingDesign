package com.practice.li.rollingdesign.utils;


import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.facebook.common.executors.CallerThreadExecutor;
import com.facebook.common.internal.Closeables;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.BaseDataSubscriber;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.memory.PooledByteBuffer;
import com.facebook.imagepipeline.memory.PooledByteBufferInputStream;
import com.facebook.imagepipeline.postprocessors.IterativeBoxBlurPostProcessor;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.practice.li.rollingdesign.common.ConfigManager;
import com.practice.li.rollingdesign.entity.ImagesEntity;
import com.practice.li.rollingdesign.ui.widget.CircleProgressDrawable;


import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by Lazxy on 2017/3/9.
 * Fresco封装工具类
 */

public class FrescoUtils {

    private static final String KEY = "GIF";
    private static Map<String, Boolean> mShouldRunAnimateAuto;//是否需要自动播放动图

    /**
     * 设置头像图片
     *
     * @param view 头像显示控件
     * @param url  头像的图片地址
     * @param size 显示图片的重放缩大小
     */
    public static void setAvatar(SimpleDraweeView view, String url, int size) {
        setAvatar(view, Uri.parse(url), size);
    }

    /**
     * 设置头像图片
     *
     * @param view 头像显示控件
     * @param uri  头像的图片地址
     * @param size 显示图片的重放缩大小
     */
    public static void setAvatar(SimpleDraweeView view, Uri uri, int size) {
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                .setLocalThumbnailPreviewsEnabled(true)
                .setProgressiveRenderingEnabled(true)
                .setResizeOptions(new ResizeOptions(size, size))
                .build();
        PipelineDraweeController controller = (PipelineDraweeController) Fresco.newDraweeControllerBuilder()
                .setUri(uri)
                .setTapToRetryEnabled(true)
                .setImageRequest(request)
                .setOldController(view.getController())
                .build();
        view.setController(controller);
    }

    /**
     * 以默认设置设置图像预览
     *
     * @param view 预览图像控件
     * @param url  图像地址
     */
    public static void setPreview(SimpleDraweeView view, String url) {
        setPreview(view, url, false);
    }

    /**
     * 设置图像预览是否自动播放
     *
     * @param view              预览图像控件
     * @param url               图像地址
     * @param forceAutoAnimated 是否在无视设置的条件下强制播放动图
     */
    public static void setPreview(SimpleDraweeView view, String url, boolean forceAutoAnimated) {
        setPreview(view, Uri.parse(url), forceAutoAnimated, null);
    }

    /**
     * 设置图片预览
     *
     * @param view              预览图像控件
     * @param uri               图像地址
     * @param forceAutoAnimated 是否在无视设置的条件下强制播放动图
     * @param listener          图片加载过程监听
     */
    public static void setPreview(SimpleDraweeView view, Uri uri, boolean forceAutoAnimated, ControllerListener<ImageInfo> listener) {
        boolean runAnimated;
        runAnimated = getLoadConfig();
        runAnimated = forceAutoAnimated || runAnimated;
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                .setLocalThumbnailPreviewsEnabled(true) //设置本地缩略图显示
                .setProgressiveRenderingEnabled(true) //设置进度条显示
                .build();
        PipelineDraweeController controller = (PipelineDraweeController) Fresco.newDraweeControllerBuilder()
                .setAutoPlayAnimations(runAnimated) //设置自动播放动画
                .setOldController(view.getController()) //设置上一个Controller，据说能提高加载性能
                .setImageRequest(request) //设置图片请求，由此取得uri信息
                .setControllerListener(listener) //设置图像加载监听
                .build();
        view.setAspectRatio(1.33f); //设置控件长宽比
        view.setController(controller); //设置controller 加载过程由此开始
    }

    /**
     * 设置高斯模糊化的图片背景
     *
     * @param view     图片显示控件
     * @param uri      图片地址
     * @param listener 图片加载监听
     */
    public static void setBlurBackground(SimpleDraweeView view, Uri uri, ControllerListener<ImageInfo> listener) {
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                .setPostprocessor(new IterativeBoxBlurPostProcessor(5))//设置高斯模糊处理者
                .build();

        PipelineDraweeController controller = (PipelineDraweeController) Fresco.newDraweeControllerBuilder()
                .setOldController(view.getController())
                .setImageRequest(request)
                .setControllerListener(listener)
                .build();

        view.setAspectRatio(1.33f);
        view.setController(controller);
    }

    /**
     * 改变动图加载设置
     *
     * @param enable
     */
    public static void changeLoadConfig(boolean enable) {
        if (mShouldRunAnimateAuto != null)
            mShouldRunAnimateAuto.put(KEY, enable);
    }

    /**
     * 获取动图加载设置
     */
    private static boolean getLoadConfig() {
        boolean runAnimated;
        if (mShouldRunAnimateAuto != null)
            runAnimated = mShouldRunAnimateAuto.get(KEY);
        else {
            runAnimated = ConfigManager.Shot.isAutoAnimated();
            mShouldRunAnimateAuto = new HashMap<>();
            mShouldRunAnimateAuto.put(KEY, runAnimated);
        }
        return runAnimated;
    }

    /**
     * 根据当前动图设置取得相应分辨率的图片
     *
     * @param preview
     * @param entity
     */
    public static void setAnimationDepends(SimpleDraweeView preview, ImagesEntity entity) {
        if (getLoadConfig())
            FrescoUtils.setPreview(preview, UrlUtils.getHighQualityImageUrl(entity));
        else
            FrescoUtils.setPreview(preview, UrlUtils.getLowQualityImageUrl(entity));
    }

    /**
     * 保存图片
     *
     * @param url      图片地址
     * @param title    图片名称
     * @param listener 图片加载监听
     */
    public static void saveImage(String url, final String title, @Nullable final ImageLoadListener listener) {
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(url)).build();
        ImagePipeline pipeline = Fresco.getImagePipeline();
        //取得未解码的图片
        DataSource<CloseableReference<PooledByteBuffer>> dataSource = pipeline.fetchEncodedImage(request, null);
        //开始通过观察者模式监听图片获取
        dataSource.subscribe(new BaseDataSubscriber<CloseableReference<PooledByteBuffer>>() {

            @Override
            protected void onNewResultImpl(DataSource<CloseableReference<PooledByteBuffer>> dataSource) {
                if (!dataSource.isFinished()) {
                    /*异常报告*/
                    return;
                }
                CloseableReference<PooledByteBuffer> ref = dataSource.getResult();
                InputStream is = null;
                if (ref != null) {
                    try {
                        PooledByteBuffer image = ref.get();
                        is = new PooledByteBufferInputStream(image);//获取图片流，存入文件
                        File result = FileUtils.saveImage(is, title);
                        if (listener != null) {
                            if (result != null)
                                listener.loadSuccess(result);//图片获取监听回调
                            else
                                listener.loadFail("文件读取错误");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        Closeables.closeQuietly(is);
                    }
                }
            }

            @Override
            protected void onFailureImpl(DataSource<CloseableReference<PooledByteBuffer>> dataSource) {
                if (listener != null)
                    listener.loadFail("图片获取错误");
            }
        }, CallerThreadExecutor.getInstance());
    }

    /**
     * 设置静态图片到普通控件
     *
     * @param url      目标图片地址
     * @param view     目标图片控件
     * @param listener 图片加载监听
     */
    public static void setStaticBitmap(String url, final ImageView view, final ImageLoadListener listener) {
        final CircleProgressDrawable progressDrawable = new CircleProgressDrawable();
        view.setImageDrawable(progressDrawable);//设置加载进度条
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(url)).build();
        ImagePipeline pipeline = Fresco.getImagePipeline();
        //获取已解码的订阅源
        DataSource<CloseableReference<CloseableImage>> dataSource = pipeline.fetchDecodedImage(request, null);
        dataSource.subscribe(new BaseBitmapDataSubscriber() {
            @Override
            protected void onFailureImpl(DataSource<CloseableReference<CloseableImage>> dataSource) {
                listener.loadFail("图片获取错误");
            }

            @Override
            protected void onNewResultImpl(Bitmap bitmap) {
                if (bitmap != null && !bitmap.isRecycled()) {
                    //通过Handler将图片传回主线程设置
                    Message msg = ((Handler) view.getTag()).obtainMessage();
                    msg.obj = bitmap;
                    ((Handler) view.getTag()).sendMessage(msg);
                }
            }

            @Override
            public void onProgressUpdate(DataSource<CloseableReference<CloseableImage>> dataSource) {
                int progress = (int) (dataSource.getProgress() * 10000);
                ((Handler) view.getTag()).sendEmptyMessage(progress);
            }
        }, CallerThreadExecutor.getInstance());
    }

    /**
     * 自定义图片加载回调接口
     */
    public interface ImageLoadListener {
        void loadSuccess(Object object);

        void loadFail(String msg);
    }
}
