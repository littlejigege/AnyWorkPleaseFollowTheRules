package com.qgstudio.anywork.common;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.qgstudio.anywork.utils.ToastUtil;

/**
 * @author Yason 2017/8/18
 */

public class NetworkStatusReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        checkNetwordStatus(context);
    }

    private void checkNetwordStatus(Context context) {
        if (isNetworkConnect(context)) {
            ToastUtil.showToast("网络已连接");
        } else {
            ToastUtil.showToast("网络连接已断开");
        }
    }

    private boolean isNetworkConnect(Context context)
    {
        boolean flag = false;
        //获取网络连接管理对象
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        //得到所有网络连接的信息
        NetworkInfo[] mInfo = manager.getAllNetworkInfo();
        if(mInfo != null){
            for (int i = 0; i < mInfo.length; i++) {
                //判断是否有已经连接的网络
                NetworkInfo.State mState = mInfo[i].getState();
                if (mState == NetworkInfo.State.CONNECTED) {
                    flag = true;
                    return flag;
                }
            }
        }
        return flag;
    }

}
