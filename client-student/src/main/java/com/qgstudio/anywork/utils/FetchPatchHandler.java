package com.qgstudio.anywork.utils;

import android.os.Handler;
import android.os.Message;

import com.tinkerpatch.sdk.TinkerPatch;

public class FetchPatchHandler extends Handler {
    public static final long HOUR_INTERVAL = 3600 * 1000;
    private long checkInterval;

    /**
     * 通过handler, 达到按照时间间隔轮训的效果
     * @param hour
     */
    public void fetchPatchWithInterval(int hour) {
        TinkerPatch.with().setFetchPatchIntervalByHours(hour);
        checkInterval = hour * HOUR_INTERVAL;
        sendEmptyMessage(0);
    }
    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);

        TinkerPatch.with().fetchPatchUpdate(true);
        //每隔一段时间都去访问后台, 增加10分钟的buffer时间
        sendEmptyMessageDelayed(0, checkInterval + 10 * 60 * 1000);
    }
}
