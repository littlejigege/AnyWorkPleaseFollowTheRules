package com.qgstudio.anywork.enter.login;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.qgstudio.anywork.App;
import com.qgstudio.anywork.data.ResponseResult;
import com.qgstudio.anywork.data.RetrofitClient;
import com.qgstudio.anywork.data.model.User;
import com.qgstudio.anywork.mvp.BasePresenterImpl;
import com.qgstudio.anywork.utils.DataBaseUtil;
import com.qgstudio.anywork.utils.GsonUtil;
import com.qgstudio.anywork.utils.LogUtil;
import com.qgstudio.anywork.utils.MyOpenHelper;
import com.qgstudio.anywork.utils.SessionMaintainUtil;
import com.qgstudio.anywork.utils.ToastUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class LoginPresenter extends BasePresenterImpl<LoginContract.View> implements LoginContract.Presenter{

    public static final String TAG = "LoginPresenter";

    @Override
    public void detachView() {
        mView = new LoginContract.View() {
            @Override
            public void showError(String errorInfo) {

            }

            @Override
            public void showSuccess() {
            }

            @Override
            public void showLoad() {

            }

            @Override
            public void stopLoad() {

            }

            @Override
            public Context getContext() {
                return null;
            }
        };

    }

    private LoginApi loginApi;

    @Override
    public User getUser() {
        List<User> users = DataBaseUtil.getHelper().queryAll(User.class);
        if (users != null) {
            return users.get(users.size() - 1);
        } else {
            return new User();
        }
    }

    @Override
    public void login(final String account, final String password) {
        mView.showLoad();
        if (loginApi == null) {
            loginApi = RetrofitClient.RETROFIT_CLIENT.getRetrofit().create(LoginApi.class);
        }

        Map<String, String> loginInfo = new HashMap<>();
        loginInfo.put("valcode", "0");
//        loginInfo.put("email", account);
        loginInfo.put("studentId", account);
        loginInfo.put("password", password);

        LogUtil.d(TAG, "[login] " + "loginInfo -> " + GsonUtil.GsonString(loginInfo));
        loginApi.login(loginInfo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseResult<User>>() {
//                .subscribe(new Observer<ResponseResult<User1>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        mView.showError("网络连接错误，请检查是否已连接校园网和IP地址是否正确");
                        LogUtil.e(TAG, "[login] " + "onError");
                        mView.stopLoad();
                    }

                    @Override
//                    public void onNext(ResponseResult<User> result) {
                      public void onNext(ResponseResult<User> result) {

                        if (result.getState() == 1) {
                            User user = result.getData();
//                            User1 user1 = result.getData();
//                            User user = new User(user1.getUserId(),user1.getUserName(),user1.getEmail(),
//                                    user1.getPassword(), user1.getPhone(), user1.getMark());

                            user.setPassword(password);
                            App.getInstance().setUser(user);
                            LogUtil.d(TAG, "[login] " + "onNext：" + "user -> " + GsonUtil.GsonString(user));
                            mView.showSuccess();

//                            user.setEmail(account);
//                            user.setPassword(password);
                            MyOpenHelper myOpenHelper = DataBaseUtil.getHelper();
                            myOpenHelper.save(user);
                            ToastUtil.showToast(result.getStateInfo());
                        } else {
                            mView.showError(result.getStateInfo());
                        }
                        mView.stopLoad();

                        //开启定时任务
                        SessionMaintainUtil.start();

                    }
                });
    }
}
