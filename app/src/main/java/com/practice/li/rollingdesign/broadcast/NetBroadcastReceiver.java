package com.practice.li.rollingdesign.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.widget.Toast;

import com.practice.li.rollingdesign.RollingDesign;
import com.practice.li.rollingdesign.utils.NetworkUtils;

/**
 * Created by Lazxy on 2017/4/2.
 * 网络情况监听类
 */

public class NetBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        boolean isWifiConnecting, isMobileConnecting;
        isWifiConnecting = NetworkUtils.getWifiConnectingStatus(context);
        isMobileConnecting = NetworkUtils.getMobileConnectingStatus(context);
        if (isWifiConnecting && !isMobileConnecting) {
            NetworkUtils.setIsNetWorking(true);
            NetworkUtils.setCurrentNetworkType(ConnectivityManager.TYPE_WIFI);
            Toast.makeText(RollingDesign.applicationContext, "已处于WIFI网络", Toast.LENGTH_SHORT).show();
        } else if (isMobileConnecting && !isWifiConnecting) {
            NetworkUtils.setIsNetWorking(true);
            NetworkUtils.setCurrentNetworkType(ConnectivityManager.TYPE_MOBILE);
            Toast.makeText(RollingDesign.applicationContext, "已处于移动网络", Toast.LENGTH_SHORT).show();
        } else if (!isWifiConnecting) {
            NetworkUtils.setIsNetWorking(false);
            NetworkUtils.setCurrentNetworkType(-1);
            Toast.makeText(RollingDesign.applicationContext, "请注意，网络连接已断开", Toast.LENGTH_SHORT).show();
        }
    }
}
