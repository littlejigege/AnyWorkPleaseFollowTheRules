package com.qgstudio.aniwork.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.qgstudio.aniwork.R;

/**
 * Created by chenyi on 17-8-20.
 */

public class LoadingDialog {

    Dialog mDialog;

    public LoadingDialog(Context context) {
        createDialog(context);
    }

    public void show() {
        if (mDialog != null) {
            mDialog.show();
        }
    }

    public void dismiss() {
        if (mDialog != null) {
            mDialog.dismiss();
        }
    }

    private Dialog createDialog(Context context) {
        if (mDialog == null) {
            mDialog = new Dialog(context, R.style.loading_dialog);
            mDialog.setContentView(R.layout.loading_view);
            mDialog.setCanceledOnTouchOutside(false);
            Window window = mDialog.getWindow();
            window.setDimAmount(0f);//去掉遮罩
            assert window != null;
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.gravity = Gravity.CENTER;
            lp.width = ViewGroup.LayoutParams.WRAP_CONTENT;//宽高可设置具体大小
            lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            mDialog.getWindow().setAttributes(lp);
        }
        return mDialog;
    }


}
