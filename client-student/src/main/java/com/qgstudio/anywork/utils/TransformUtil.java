package com.qgstudio.anywork.utils;

import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

import com.qgstudio.anywork.App;

/**
 * Created by chenyi on 2017/7/12.
 */

public class TransformUtil {

    /**
     * 在4.4之后的，包括4.4的版本，返回的 Uri 有可能是以下的一种:
     * content://com.android.providers.media.documents/document/image%3A8302
     * content://com.android.providers.downloads.documents/document/5
     * content://media/external/images/media/8302
     * 转换时需要对其进行翻译
     *
     * @param uri
     * @return uri 对于的路径 path
     */
    public static String uri2Path (Uri uri) {
        String path = null;

        // 判断是否是 Document 类型的 Uri
        if (DocumentsContract.isDocumentUri(App.getInstance(), uri)) {
            String docId = DocumentsContract.getDocumentId(uri);
            // 判断是否是 media 类型
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                Uri mediaUri = null;
                String id = docId.split(":")[1];
                String selection = null;
                if (docId.contains("image")) {
                    mediaUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                    selection = MediaStore.Images.Media._ID + "=" + id;
                } else if (docId.contains("video")) {
                    mediaUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                    selection = MediaStore.Video.Media._ID + "=" + id;
                } else if (docId.contains("audio")) {
                    mediaUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                    selection = MediaStore.Audio.Media._ID + "=" + id;
                }
                path = getmediaPath(mediaUri, selection);
            }
            // 判断是否在系统下载 path 下的文件
            else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"),
                        Long.valueOf(docId));
                path = getmediaPath(contentUri, null);
            }
        }
        //属于普通的Uri，包含path
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            path = getmediaPath(uri, null);
        }
        else {
            path = uri.getPath();
        }
        return path;
    }

    // 在4.4之前的版本，返回的 Uri 如下:content://media/external/images/media/8302
    // 通过 ContentResolver 的查询方法来获取路径
    private static String getmediaPath(Uri uri, String selection) {
        String path = null;
        Cursor cursor = App.getInstance().getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }
}
