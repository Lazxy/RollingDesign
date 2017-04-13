package com.practice.li.rollingdesign.api;

import android.text.TextUtils;

import com.practice.li.rollingdesign.common.ConfigManager;
import com.practice.li.rollingdesign.utils.OKHttpUtils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Lazxy on 2017/3/6.
 * 网络请求构造类
 */

public class ApiClient {

    private static boolean IS_DEBUG = true;
    private static Retrofit sOAuthRetrofit;
    private static Retrofit initialRetrofit;

    /**
     * 初始化整个网络获取框架，配置初始参数
     *
     * @param apiType 需要构造的请求接口
     * @param <T>     接口泛型
     * @return 返回的构造对象
     */
    public synchronized static <T> T getForInit(Class<T> apiType) {
        if (initialRetrofit == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();

            if (IS_DEBUG) {
                //测试时用来Log出请求和回应的内容。
                HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
                interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                builder.addInterceptor(interceptor);
            }

            String token = ConfigManager.Login.getRecentToken();
            final String access_token = "Bearer " + (TextUtils.isEmpty(token) ? ApiConstants.ParamValue.TOKEN : token);
            Interceptor tokenInterceptor = new Interceptor() {
                //这里拦截了请求并给它加上了Authorization头来表示Token的来源，如果配置文件中存在Token的话就用配置文件中的，
                // 否则用申请应用时得到的Token
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request().newBuilder().header("Authorization", access_token).build();
                    return chain.proceed(request);
                }
            };
            builder.addNetworkInterceptor(tokenInterceptor);
            builder.sslSocketFactory(OKHttpUtils.getSSLSocketFactory());
            builder.retryOnConnectionFailure(true).connectTimeout(ApiConstants.ParamValue.TIME_OUT_SECONDS, TimeUnit.SECONDS);

            initialRetrofit = new Retrofit.Builder().baseUrl(ApiConstants.Url.BASE_URL)
                    .client(builder.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
        }

        return initialRetrofit.create(apiType);
    }

    /**
     * @return 登陆验证时所用的接口对象
     */
    public synchronized static TokenApi getForOAuth() {
        if (sOAuthRetrofit == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();

            if (IS_DEBUG) {
                HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
                interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                builder.addInterceptor(interceptor);
            }

            builder.retryOnConnectionFailure(true).connectTimeout(ApiConstants.ParamValue.TIME_OUT_SECONDS, TimeUnit.SECONDS);
            builder.sslSocketFactory(OKHttpUtils.getSSLSocketFactory());

            sOAuthRetrofit = new Retrofit.Builder().baseUrl(ApiConstants.Url.OAUTH_URL)
                    .client(builder.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
        }

        return sOAuthRetrofit.create(TokenApi.class);
    }

    /**
     * 这个函数在获得过Token之后调用，使下一次需要用到sRestRetrofit的时候重新注入Token
     */
    public synchronized static void resetApiClient() {
        if (initialRetrofit != null) initialRetrofit = null;
    }
}
