package com.qgstudio.anywork.user;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.qgstudio.anywork.utils.ToastUtil;
import com.qgstudio.anywork.websocket.ThreadMode;

public class AppService extends Service {
    public AppService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    System.out.println("666666666666666666666666666666666666666666666666666666666");
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        return super.onStartCommand(intent, flags, startId);
    }
}
