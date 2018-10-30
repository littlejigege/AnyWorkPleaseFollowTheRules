package com.qgstudio.anywork.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.qgstudio.anywork.R;
import com.victor.loading.newton.NewtonCradleLoading;

/**
 * Created by chenyi on 17-8-20.
 */

public class LoadingDialog extends DialogFragment {

    Dialog mDialog;

    NewtonCradleLoading loading;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (mDialog == null) {
            mDialog = new Dialog(getActivity(), R.style.loading_dialog);
            mDialog.setContentView(R.layout.loading_view);
            mDialog.setCanceledOnTouchOutside(true);
            Window window = mDialog.getWindow();
            assert window != null;
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.gravity = Gravity.CENTER;
            lp.width = ViewGroup.LayoutParams.WRAP_CONTENT;//宽高可设置具体大小
            lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            mDialog.getWindow().setAttributes(lp);

            View view = mDialog.getWindow().getDecorView();

            loading = (NewtonCradleLoading) view.findViewById(R.id.newton_cradle_loading);
        }

        return mDialog;
    }

    @Override
    public void onResume() {
        super.onResume();
        loading.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (loading.isStart())
            loading.stop();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        mDialog = null;
        System.gc();
    }
}
