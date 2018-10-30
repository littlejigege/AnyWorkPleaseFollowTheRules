package com.qgstudio.anywork.common;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.qgstudio.anywork.dialog.BaseDialog;
import com.qgstudio.anywork.dialog.NewBaseDialog;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * 自动对dialog进行管理的基类Activity
 * @author Yason 2017/8/16
 */

public abstract class DialogManagerActivity extends AppCompatActivity {

    private Set<NewBaseDialog> mDialogs;

    /**
     * 仅供BaseDialog反向注册使用
     * @param dialog
     */
    public void addDialog(NewBaseDialog dialog) {
        if (mDialogs == null) {
            mDialogs = new HashSet<>();
        }
        mDialogs.add(dialog);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置不允许翻转
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    protected void onDestroy() {
        if (mDialogs == null || mDialogs.size() == 0) {
            super.onDestroy();
            return;
        }

        //退出前关闭dialog，避免窗体泄漏
        Iterator iterator = mDialogs.iterator();
        while (iterator.hasNext()) {
            NewBaseDialog dialog = (NewBaseDialog) iterator.next();
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
        }

        mDialogs.clear();
        mDialogs = null;

        super.onDestroy();
    }
}
