package com.qgstudio.anywork.utils;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * 负责添加 Fragment 到 Activity 中
 * @author Yason 2017/4/2.
 */

public class ActivityUtil {
    public static void addFragmentToActivity(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment,
                                             int frameId, String fragmentTag) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(frameId, fragment, fragmentTag)
                .addToBackStack(fragmentTag)
                .commit();
    }
}
