package com.qgstudio.anywork.utils;

import android.app.AlarmManager;
import android.os.HandlerThread;

import com.qgstudio.anywork.data.ResponseResult;
import com.qgstudio.anywork.data.RetrofitClient;
import com.qgstudio.anywork.data.model.User;
import com.qgstudio.anywork.paper.data.PaperApi;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import retrofit2.Retrofit;
import retrofit2.http.GET;
import rx.Observable;


/**
 * @author Yason
 * @since 2017/9/23
 */

public class SessionMaintainUtil {

    private static AccessApi accessApi;
    private static ScheduledExecutorService executors;

    private SessionMaintainUtil(){}

    static{
        Retrofit retrofit = RetrofitClient.RETROFIT_CLIENT.getRetrofit();
        accessApi = retrofit.create(AccessApi.class);
    }

    public static void start () {
        executors = Executors.newScheduledThreadPool(1);
        executors.scheduleAtFixedRate(new AccessTask(), 5, 5, TimeUnit.SECONDS);
    }

    public static void stop(){
        if (executors != null && !executors.isTerminated()) {
            executors.shutdownNow();
        }
    }


    private interface AccessApi{
        @GET("user/myinfo")
        Observable<ResponseResult<User>> access();
    }

    private static class AccessTask implements Runnable {
        @Override
        public void run() {
            accessApi.access()
                    .subscribe();
        }
    }

}
