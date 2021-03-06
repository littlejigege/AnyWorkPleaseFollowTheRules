package com.qgstudio.aniwork;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.Log;

import com.mobile.utils.Utils;
import com.qgstudio.aniwork.core.Apis;
import com.qgstudio.aniwork.data.model.User;
import com.qgstudio.aniwork.main.HomeActivity;
import com.qgstudio.aniwork.utils.FetchPatchHandler;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.tinker.loader.app.ApplicationLike;
import com.tinkerpatch.sdk.TinkerPatch;
import com.tinkerpatch.sdk.loader.TinkerPatchApplicationLike;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Yason on 2017/4/14.
 */

public class App extends Application {
    private static Context context;
    private static App app;

    private ApplicationLike tinkerApplicationLike;

    public static App getInstance() {
        return app;
    }

    /**
     * 用于获得全局上下文
     *
     * @return
     */
    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        app = this;
        String packageName = context.getPackageName();
        String processName = getProcessName(android.os.Process.myPid());
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(context);
        strategy.setUploadProcess(processName == null || processName.equals(packageName));
//        CrashReport.initCrashReport(getApplicationContext(), "a4ce5a4993", true);
        Beta.canShowUpgradeActs.add(HomeActivity.class);
        Beta.autoInit = true;
        Beta.autoCheckUpgrade = true;
        Beta.upgradeCheckPeriod = 60 * 1000;
        Bugly.init(getApplicationContext(), "9cc1e08f57", true);

        if (BuildConfig.TINKER_ENABLE) {

            // 我们可以从这里获得Tinker加载过程的信息
            tinkerApplicationLike = TinkerPatchApplicationLike.getTinkerPatchApplicationLike();

            // 初始化TinkerPatch SDK, 更多配置可参照API章节中的,初始化SDK
            TinkerPatch.init(tinkerApplicationLike)
                    .reflectPatchLibrary()
                    .setPatchRollbackOnScreenOff(true)
                    .setPatchRestartOnSrceenOff(true);

            // 每隔3个小时去访问后台时候有更新,通过handler实现轮训的效果
            new FetchPatchHandler().fetchPatchWithInterval(3);
        }
        Utils.Companion.init(this);

//        //获取应用签名
//        try {
//            PackageInfo info =  getPackageManager().getPackageInfo(getPackageName(),PackageManager.GET_SIGNATURES);
//            System.out.println(info.signatures[0].toCharsString());
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        }


        //startService(new Intent(this, AppService.class));
        Log.d("checkcheckchekc", Apis.initLib());
    }

    private User user;

    public User getUser() {
        if (user == null) {
            user = new User();
        }
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    /**
     * 获得进程名
     *
     * @param pid
     * @return
     */
    private static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }


}
