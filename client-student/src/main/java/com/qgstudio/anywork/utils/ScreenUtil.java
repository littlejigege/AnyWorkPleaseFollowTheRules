package com.qgstudio.anywork.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * 获取屏幕属性的工具类
 * Created by chenyi on 2017/3/27.
 */

public class ScreenUtil {
    /**
     * 获取屏幕相关参数
     * @param context context
     * @return DisplayMetrics
     */
    public static DisplayMetrics getScreenSize(Context context) {
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(metrics);
        return metrics;
    }


    /**
     * 获取屏幕density
     * @param context context
     * @return density
     */
    public static float getDeviceDensity(Context context) {
        DisplayMetrics metrics = getScreenSize(context);
        return metrics.density;
    }
}
