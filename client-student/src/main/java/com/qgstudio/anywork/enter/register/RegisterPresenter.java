package com.qgstudio.anywork.enter.register;

import android.content.Context;

import com.qgstudio.anywork.data.ResponseResult;
import com.qgstudio.anywork.data.RetrofitClient;
import com.qgstudio.anywork.mvp.BasePresenterImpl;

import java.util.HashMap;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class RegisterPresenter extends BasePresenterImpl<RegisterContract.View> implements RegisterContract.Presenter{

    @Override
    public void detachView() {
        mView = new RegisterContract.View() {

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

    private RegisterApi registerApi;

    @Override
    public void register(String account, String password, String name, String phone) {
        mView.showLoad();
        if (registerApi == null) {
            registerApi = RetrofitClient.RETROFIT_CLIENT.getRetrofit().create(RegisterApi.class);
        }

        HashMap<String, String> user = new HashMap<>();
        user.put("email", account);
        user.put("password", password);
//        user.put("userName", name);
        user.put("studentId", name);
        user.put("phone", phone);
        user.put("mark", "0");
        user.put("valcode", "0");

        registerApi.register(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseResult<Integer>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        mView.stopLoad();
                        mView.showError("网络连接错误，请检查是否已连接校园网和IP地址是否正确");
                    }

                    @Override
                    public void onNext(ResponseResult<Integer> result) {

                        if (result.getState() == 1) {
                            mView.stopLoad();
                            mView.showSuccess();
                        } else {
                            mView.stopLoad();
                            mView.showError(result.getStateInfo());
                        }
                    }
                });
    }
}
