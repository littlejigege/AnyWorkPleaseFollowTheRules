package com.qgstudio.anywork;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.qgstudio.anywork.core.Apis;
import com.qgstudio.anywork.data.ResponseResult;
import com.qgstudio.anywork.data.RetrofitClient;
import com.qgstudio.anywork.data.model.User;
import com.qgstudio.anywork.dialog.LoadingDialog;
import com.qgstudio.anywork.enter.EnterActivity;
import com.qgstudio.anywork.enter.login.LoginApi;
import com.qgstudio.anywork.enter.login.LoginContract;
import com.qgstudio.anywork.main.HomeActivity;
import com.qgstudio.anywork.utils.DataBaseUtil;
import com.qgstudio.anywork.utils.GsonUtil;
import com.qgstudio.anywork.utils.LogUtil;
import com.qgstudio.anywork.utils.MyOpenHelper;
import com.qgstudio.anywork.utils.SessionMaintainUtil;
import com.qgstudio.anywork.utils.ToastUtil;
import com.qgstudio.anywork.websocket.WebSocketHolder;
import com.qgstudio.anywork.widget.LoadingView;
import com.qgstudio.anywork.workout.WorkoutContainerActivity;
import com.qgstudio.anywork.workout.WorkoutType;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by chenyi on 2017/7/11.
 */

public class StartActivity extends AppCompatActivity {

    private LoginApi loginApi;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        //获得账号信息
        User user = getUser();
        if (user == null) {
            goToEnterActivity();
        } else {
            //自动登录
            login(user.getStudentId(), user.getPassword());
            Log.d("linzongzhan", "onCreate: " + user.getStudentId());
        }
        //run();
    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed(); 	不要调用父类的方法
//        Intent intent = new Intent(Intent.ACTION_MAIN);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        intent.addCategory(Intent.CATEGORY_HOME);
//        startActivity(intent);
        //直接退出
        finish();
    }

    /**
     * 登录操作，若登录失败则返回登录注册界面
     *
     * @param account  账号
     * @param password 密码
     */
    private void login(final String account, final String password) {
        //加载动画
//        final LoadingDialog loadingDialog = new LoadingDialog(this);
//        loadingDialog.show();

        loginApi = RetrofitClient.RETROFIT_CLIENT.getRetrofit().create(LoginApi.class);

        Map<String, String> loginInfo = new HashMap<>();
        loginInfo.put("valcode", "0");
//        loginInfo.put("email", account);
        loginInfo.put("studentId", account);
        loginInfo.put("password", password);

        loginApi.login(Apis.loginApi(), loginInfo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<ResponseResult<User>>() {
                .subscribe(new Observer<ResponseResult<User>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        //loadingDialog.dismiss();  //停止动画
                        ToastUtil.showToast("自动登录失败");
                        goToEnterActivity();  //跳转到登录注册界面
                    }

                    @Override
//                    public void onNext(ResponseResult<User> result) {
                    public void onNext(ResponseResult<User> result) {
                        //loadingDialog.dismiss();  //停止动画

                        if (result.getState() == 1) {
                            User user = result.getData();
                            App.getInstance().setUser(user);
                            user.setStudentId(account);
                            user.setPassword(password);
                            MyOpenHelper myOpenHelper = DataBaseUtil.getHelper();
                            myOpenHelper.save(user);
                            ToastUtil.showToast("自动登录成功");
                            goToHomeActivity();
                        } else {
                            ToastUtil.showToast("自动登录失败");
                            goToEnterActivity();  //跳转到登录注册界面
                        }
                        //开启定时任务
                        SessionMaintainUtil.start();
                    }
                });
    }

    /**
     * 启动登录注册界面
     */
    private void goToEnterActivity() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(StartActivity.this, EnterActivity.class);
                startActivity(intent);
                finish();
            }
        }, 1000);
    }

    private void goToHomeActivity() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(StartActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        }, 1000);
    }
    /**
     * 从数据库中读取用户登录信息
     *
     * @return User
     */
    public static User getUser() {
        List<User> users = DataBaseUtil.getHelper().queryAll(User.class);
        if (users != null) {
            return users.get(users.size() - 1);
        } else {
            return null;
        }
    }

    private void run() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient.Builder().build();
                final Request request = new Request.Builder().url("https://qgstudio.org").build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Log.d("htttps:  string", response.body().string());
                    }
                });
            }
        }).start();

    }
}
