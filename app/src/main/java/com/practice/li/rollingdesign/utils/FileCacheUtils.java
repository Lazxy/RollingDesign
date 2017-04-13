package com.practice.li.rollingdesign.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;

import java.io.File;
import java.math.BigDecimal;

/**
 * 缓存文件工具类
 */
public class FileCacheUtils {

    /**
     * 获取缓存目录
     * @param context
     * @return 缓存目录文件对象
     */
    public static File getCacheDirectory(Context context){
        File appCacheDir = null;

        //若存储卡已挂载且取得了文件读写权限，则选择外部缓存目录
        if ("mounted".equals(Environment.getExternalStorageState()) &&
                (context.checkCallingOrSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE")== PackageManager.PERMISSION_GRANTED)) {
            appCacheDir = context.getExternalCacheDir();
        }

        //否则选择不需要读写权限的缓存目录
        if (appCacheDir == null || !appCacheDir.exists() && !appCacheDir.mkdirs()) {
            appCacheDir = context.getCacheDir();
        }

        return appCacheDir;
    }

    /**
     * 获取本应用的全部缓存大小
     * @param context
     * @return
     */
    public static String getTotalCacheSize(Context context) {
        try {
            long cacheSize = getFolderSize(context.getApplicationContext().getCacheDir());//获取应用缓存目录的缓存大小
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
                //获取存储卡缓存目录的大小
                cacheSize += getFolderSize(context.getApplicationContext().getExternalCacheDir());
            return getFormatSize(cacheSize);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 清除所有目录的缓存
     * @param context
     */
    public static void clearAllCache(Context context) {
        deleteDir(context.getCacheDir());
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
            deleteDir(context.getExternalCacheDir());
    }

    /**
     * 递归遍历目标文件的所有文件夹并删除
     * @param dir 目标文件夹
     * @return 文件夹删除是否成功
     */
    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (String aChildren : children) {
                boolean success = deleteDir(new File(dir, aChildren));
                if (!success) return false;
            }
        }
        return dir.delete();
    }

    /**
     * 递归遍历所有目标文件目录下的文件，获取目标文件目录的大小
     * @param file 目标文件目录
     * @return
     */
    public static long getFolderSize(File file){
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (File aFileList : fileList) {
                if (aFileList.isDirectory()) size = size + getFolderSize(aFileList);
                else size = size + aFileList.length();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    /**
     * 格式化单位
     */
    public static String getFormatSize(double size) {
        double kiloByte = size / 1024;
        if (kiloByte < 1) return "0K";

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";
    }
}