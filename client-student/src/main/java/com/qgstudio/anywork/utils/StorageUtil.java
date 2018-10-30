package com.qgstudio.anywork.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * @author Yason
 * @since 2017/9/23
 */

public class StorageUtil {

    private StorageUtil(){}

    /* 检查外部存储是否能够读和写 */
    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* 检查外部存储是否可读 */
    public static boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    /* 创建外部存储公有图片目录 */
    public static File getStoragePublicDir(String albumName) {
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), albumName);
        if (!file.exists()) {
            if(!file.mkdirs()){
                return null;
            }
        }
        return file;
    }

    /* 创建外部存储私有图片目录 */
    public static File getStoragePrivateDir(Context context, String albumName) {
        File file = new File(context.getExternalFilesDir(
                Environment.DIRECTORY_PICTURES), albumName);
        if (!file.exists()) {
            if(!file.mkdirs()){
                return null;
            }
        }
        return file;
    }

}
