package com.qgstudio.anywork;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.JsonObject;
import com.mobile.utils.Utils;
import com.qgstudio.anywork.core.Apis;
import com.qgstudio.anywork.data.ResponseResult;
import com.qgstudio.anywork.data.model.User;
import com.qgstudio.anywork.main.HomeActivity;
import com.qgstudio.anywork.notice.NoticeApi;
import com.qgstudio.anywork.user.AppService;
import com.qgstudio.anywork.utils.FetchPatchHandler;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.tinker.loader.app.ApplicationLike;
import com.tinkerpatch.sdk.TinkerPatch;
import com.tinkerpatch.sdk.loader.TinkerPatchApplicationLike;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import rx.Observable;

/**
 * Created by Yason on 2017/4/14.
 */

public class App extends Application {
    private static Context context;
    private static String ok = "308202b73082019fa003020102020453be2738300d06092a864886f70d01010b0500300c310a3008060355040313016c301e170d3138303732393133313832375a170d3433303732333133313832375a300c310a3008060355040313016c30820122300d06092a864886f70d01010105000382010f003082010a0282010100e4b8f19933e14421d4ee84fc989b8dfbf8bf712c4a1802cb0b966e8a27591065e790bc232937b4aca9eb03ac968b53d3b6aa4e69e45eceaf16e4d7937e74d539fc59333a8a235cdd817f2e150e504d37521ea0a32871fcc63672eb70931a23b64976c850ebf3d6d0011b23115e9c543cb8741b6f20a3ac782140788cb0f551cc9eea10a13275137fdeeb74fc0a61fdcba13b88d87183241130f8fcbc42ad0263ca10ec464899f33213a3304ad5c0b79bace818d335d957dc74f95379aa3a10f032510dee86a61330534e20ac8d28f6b2532016e321c949f14b2feb3bd29cc8744e67913450758b5f992410ae7dfd08247d079eab22e7ae467b28c5f9c08b32570203010001a321301f301d0603551d0e04160414bdc1fab83a1c58c4684cbfe5ac0a45b441241252300d06092a864886f70d01010b05000382010100296dae28bc3d5b6aa9afe0e6c9ca40e66c385c9d8dff1e32723d8b6a0f5f7b9d81549c43f4bc840c9817e499fc8e5b8787596303f63cf559617b990b7a7caf10294f9767633b40392af262d3a032ae2c657abba3ce958431f5f465b84362d31516750a662be31857c3c27277d6081bf979aeed872cefcf408c4ac20e72e80ccb2d78f5d01f561a3f5030ffea5027ff94a5644e3467f5ea989a8055bd2ae15e5f00177088ebbb68208d1c211f852547755a94b0560e94290a6639cf6386c1edc91938fa20c95e0c7c97f5269f7d02cff2b092a829723267f8bc870381ed00bd73bcbbc53609c1c0ec29040c5af75d926e20227e3e283c1b961c62a8b6c2227a95";
    private static String no = "308201dd30820146020101300d06092a864886f70d010105050030373116301406035504030c0d416e64726f69642044656275673110300e060355040a0c07416e64726f6964310b3009060355040613025553301e170d3138303330363039323835345a170d3438303232373039323835345a30373116301406035504030c0d416e64726f69642044656275673110300e060355040a0c07416e64726f6964310b300906035504061302555330819f300d06092a864886f70d010101050003818d0030818902818100b7002da03966164bca0569fde27dd0cd0f02039de1ff3bbce6530dfbf280ab8d7f79c2b3ba52eeaf37d852f64f110bf3810e48e571c03a127081afa7ba1eec341c269c1de3942fd5d6e895771289ce4c08fc398ffd5a1ff952862d12a59bb51d8011a2a0fee67d827f77c27cdb7112b5959eb708db1df4add5a8011a1663f1f10203010001300d06092a864886f70d01010505000381810084a56c667f58880eab375ba684ae0b4c52f18cb99b5ec363e7232b0ccf985b41858ecc6999d774767aa75387bbf5f4ab60925cbab833f5e56e0c60843fabd82f5d26f4f45d27f524dcfec846bd8155ef9ba3630f81f545cf2939828d00eb46fb47076031360cc324f5684d50e13042ad216e8a0beefa2422def42ae6cd6e56d6";
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
