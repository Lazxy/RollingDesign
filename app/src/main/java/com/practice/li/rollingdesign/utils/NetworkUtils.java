package com.practice.li.rollingdesign.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;

/**
 * Created by Lazxy on 2017/4/5.
 * 网络监听相关工具类
 */
public class NetworkUtils {

    public static boolean mIsNetWorking;
    public static int mNetworkType;

    /**
     * Check the connecting status of wifi.
     * @param context
     * @return true if wifi is connected or connecting (maybe can't connect to net),false otherwise.
     */
    public static boolean getWifiConnectingStatus(Context context){
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP){
            NetworkInfo wifiInfo=connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            return wifiInfo.isConnected();
        }else{
            Network[] networks=connManager.getAllNetworks();
            for(Network network : networks){
                NetworkInfo info = connManager.getNetworkInfo(network);
                if(info.getType() == ConnectivityManager.TYPE_WIFI)
                    return info.isConnected();
            }
        }
        return false;
    }

    /**
     * Check the connecting status of mobile network.
     * @param context
     * @return true if network is connected or connecting (maybe can't connect to net),false otherwise.
     */
    public static boolean getMobileConnectingStatus(Context context){
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP){
            NetworkInfo wifiInfo=connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            return wifiInfo.isConnected();
        }else{
            Network[] networks=connManager.getAllNetworks();
            for(Network network : networks){
                NetworkInfo info = connManager.getNetworkInfo(network);
                if(info.getType() == ConnectivityManager.TYPE_MOBILE)
                    return info.isConnected();
            }
        }
        return false;
    }

    public static void setIsNetWorking(boolean isWorking){
        mIsNetWorking=isWorking;
    }

    public static boolean getIsNetWorking(){
        return mIsNetWorking;
    }

    public static int getCurrentNetworkType(){
        return mNetworkType;
    }

    public static void setCurrentNetworkType(int type){
        mNetworkType=type;
    }
}
