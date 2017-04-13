package com.practice.li.rollingdesign.utils;

import android.os.Environment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Lazxy on 2017/3/15.
 * 文件操作工具类
 */

public class FileUtils {

    //下载图片保存路径
    public static String FILE_DIRECTORY = Environment.getExternalStorageDirectory() + "/dribbble";

    /**
     * 将图片流存储入对应文件夹
     * @param is 获取的文件流
     * @param name 目标文件名
     * @return 存储完毕的文件
     * @throws IOException
     */
    public static File saveImage(InputStream is, String name) throws IOException {
        File image;
        byte[] data = new byte[1024];
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        int temp;
        while ((temp = is.read(data)) != -1) {
            bos.write(data, 0, temp);
        }
        if (bos.size() != 0) {
            boolean isAnimated = isGifFile(bos.toByteArray());
            String fileName = isAnimated ? name + ".gif" : name + ".jpg";
            File directory = new File(FILE_DIRECTORY);
            if (!(directory.exists() || directory.mkdir()))
                return null;
            image = new File(FILE_DIRECTORY, fileName);
            OutputStream os = new FileOutputStream(image);
            try {
                bos.writeTo(os);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                bos.close();
                os.close();
            }
            return image;
        }
        return null;
    }

    /**
     * 检测图片是否为GIF图
     * @param image 图片的字节数组表示
     * @return true if the image is a gif ,false otherwise
     */
    public static boolean isGifFile(byte[] image) {
        return image[0] == 0x47 && image[1] == 0x49 && image[2] == 0x46;
    }
}
