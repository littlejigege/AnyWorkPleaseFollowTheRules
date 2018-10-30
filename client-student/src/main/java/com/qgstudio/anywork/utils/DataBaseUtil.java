package com.qgstudio.anywork.utils;

import com.qgstudio.anywork.App;

/**
 * 用过 MyOpenHelper 进行数据库操作。
 * Created by chenyi on 2017/7/11.
 */

public class DataBaseUtil {
    private static MyOpenHelper mHelper;

    private DataBaseUtil(){
    }

    public static MyOpenHelper getHelper(){
        if(mHelper == null){
            synchronized (MyOpenHelper.class) {
                if (mHelper == null) {
                    mHelper = new MyOpenHelper(App.getInstance(), "anywork.ab");
                }
            }
        }
        return mHelper;
    }
}
