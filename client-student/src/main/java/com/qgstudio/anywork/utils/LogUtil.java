package com.qgstudio.anywork.utils;

import android.util.Log;

import com.qgstudio.anywork.BuildConfig;

/**
 * @author Yason 2017/5/13.
 */

public class LogUtil {
    private LogUtil() {
    }

//    public static boolean flag = BuildConfig.DEBUG;
    public static boolean flag = true;

    public static void e(String tag, String msg) {
        if (flag) {
            Log.e(tag, msg);
        }
    }

    public static void i(String tag, String msg) {
        if (flag) {
            Log.i(tag, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (flag) {
            Log.d(tag, msg);
        }
    }

    public static void w(String tag, String msg) {
        if (flag) {
            Log.w(tag, msg);
        }
    }

    public static void v(String tag, String msg) {
        if (flag) {
            Log.v(tag, msg);
        }
    }

    public static void e2(String className, String methodName, String msg) {
        e(className, "[" + methodName + "]：" + msg);
    }

    public static void i2(String className, String methodName, String msg) {
        i(className, "[" + methodName + "]：" + msg);
    }

    public static void d2(String className, String methodName, String msg) {
        d(className, "[" + methodName + "]：" + msg);
    }

    public static void w2(String className, String methodName, String msg) {
        w(className, "[" + methodName + "]：" + msg);
    }

    public static void v2(String className, String methodName, String msg) {
        v(className, "[" + methodName + "] " + msg);
    }

}
