package com.practice.li.rollingdesign;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;

import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.backends.okhttp3.OkHttpImagePipelineConfigFactory;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.practice.li.rollingdesign.api.ApiConstants;
import com.practice.li.rollingdesign.utils.FileCacheUtils;
import com.practice.li.rollingdesign.utils.NetworkUtils;
import com.practice.li.rollingdesign.utils.OKHttpUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by Lazxy on 2017/3/7.
 */

public class RollingDesign extends Application {

    public static Context applicationContext;

    @Override
    public void onCreate(){
        super.onCreate();
        applicationContext=this;
        initFresco();
        initNetworkStatus();
    }

    /**
     * 配置Fresco 包括图片缓存路径与图片加载设置
     */
    private void initFresco(){

        DiskCacheConfig diskCacheConfig = DiskCacheConfig.newBuilder(this)
                .setBaseDirectoryPath(FileCacheUtils.getCacheDirectory(this))
                .build();
        //配置OkHttpClient代替默认的HttpUrlConnection框架，使之在能在5.0以下系统中正常加载图片
        OkHttpClient client = new OkHttpClient.Builder()
                .sslSocketFactory(OKHttpUtils.getSSLSocketFactory())
                .retryOnConnectionFailure(true)
                .connectTimeout(ApiConstants.ParamValue.TIME_OUT_SECONDS, TimeUnit.SECONDS)
                .build();

        ImagePipelineConfig config = OkHttpImagePipelineConfigFactory.newBuilder(this,client)
                .setMainDiskCacheConfig(diskCacheConfig)
                .setSmallImageDiskCacheConfig(diskCacheConfig)
                .setDownsampleEnabled(true)
                .build();

        //初始化 Fresco 图片缓存库
        Fresco.initialize(this, config);
    }

    /**
     * 初始化网络情况，Wifi优先
     */
    private void initNetworkStatus(){
        if(NetworkUtils.getWifiConnectingStatus(this)){
            NetworkUtils.setIsNetWorking(true);
            NetworkUtils.setCurrentNetworkType(ConnectivityManager.TYPE_WIFI);
        }else if(NetworkUtils.getMobileConnectingStatus(this)){
            NetworkUtils.setIsNetWorking(true);
            NetworkUtils.setCurrentNetworkType(ConnectivityManager.TYPE_MOBILE);
        }else{
            NetworkUtils.setIsNetWorking(false);
            NetworkUtils.setCurrentNetworkType(-1);//表示无网络
        }
    }

}
