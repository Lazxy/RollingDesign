package com.practice.li.rollingdesign.utils;

import com.practice.li.rollingdesign.api.socket.TLSSocketFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;

/**
 * Created by Lazxy on 2017/4/12.
 * OKHttp相关工具类
 */

public class OKHttpUtils {

    /**
     * 为5.0版本以下的设备获得能够进行TLSv1.2、TLSv1.1验证的SocketFactory
     * @return TLS加密的SocketFactory
     */
    public static SSLSocketFactory getSSLSocketFactory() {
        SSLContext sslcontext = null;
        try {
            sslcontext = SSLContext.getInstance("TLSv1");
            sslcontext.init(null, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        SSLSocketFactory TLSFactory = new TLSSocketFactory(sslcontext.getSocketFactory());
        return TLSFactory;
    }
}
