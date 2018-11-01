package com.qgstudio.anywork.feedback.utils;

import android.util.Log;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;

public class FileRequestBody extends RequestBody{
    private MediaType mediaType;
    /* 需要上传的文件 */
    private File file;
    /* 已经发送的字节数 */
    private long writen = 0L;

    public interface WritedListener {
        void hasSend(long length);
    }

    private WritedListener writedListener;

    public void setWritedListener(WritedListener writedListener) {
        this.writedListener = writedListener;
    }

    /**
     * 构造FileRequestBody
     * @param mediaType     上传文件的类型
     * @param file          上传的文件
     */
    public FileRequestBody(MediaType mediaType, File file) {
        this.mediaType = mediaType;
        this.file = file;
    }

    @Override
    public long contentLength() {
        return file.length();
    }

    @Override
    public MediaType contentType() {
        return mediaType;
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        BufferedSource bufferedSource = Okio.buffer(Okio.source(file));
        byte[] buffer = new byte[8192];
        long len = 0;

        while((len = bufferedSource.read(buffer)) > 0){
            sink.write(buffer, 0, (int) len);
            writen += len;
            //监听已发送长度的接口
            writedListener.hasSend(writen);
        }
    }
}
