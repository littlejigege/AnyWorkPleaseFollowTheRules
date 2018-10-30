package com.qgstudio.anywork.user;

import android.content.Context;
import android.util.Log;

import com.qgstudio.anywork.data.ResponseResult;
import com.qgstudio.anywork.data.RetrofitClient;
import com.qgstudio.anywork.data.model.User;
import com.qgstudio.anywork.mvp.BasePresenterImpl;
import com.qgstudio.anywork.utils.GsonUtil;
import com.qgstudio.anywork.utils.LogUtil;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class UserPresenter extends BasePresenterImpl<UserContract.View> implements UserContract.Presenter {

    public static final String TAG = "UserPresenter";

    @Override
    public void detachView() {
        mView = new UserContract.View() {
            @Override
            public void showSuccess(User user) {

            }

            @Override
            public void showError(String s) {

            }

            @Override
            public void setUser(User user) {

            }

            @Override
            public void changeImg() {

            }

            @Override
            public void showProgressDialog() {

            }

            @Override
            public void hidProgressDialog() {

            }

            @Override
            public Context getContext() {
                return null;
            }
        };
    }

    private UserApi userApi;

    @Override
    public void changeInfo(final User user) {
        if (userApi == null) {
            userApi = RetrofitClient.RETROFIT_CLIENT.getRetrofit().create(UserApi.class);
        }

        Map<String, String> info = new HashMap<>();
//        info.put("userName", user.getUserName());
        info.put("phone", user.getPhone());
        info.put("email", user.getEmail());

        Log.i(TAG, "changeInfo: " + GsonUtil.GsonString(info));
        userApi.changeInfo(info)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseResult<User>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        mView.showError("网络连接错误");
                        mView.hidProgressDialog();
                    }

                    @Override
                    public void onNext(ResponseResult<User> result) {
                        assert result != null;

                        if (result.getState() == 1) {
                            mView.showSuccess(user);
                            mView.hidProgressDialog();
                        } else {
                            mView.showError(result.getStateInfo());
                        }
                    }
                });
    }

    private static final String MULTIPART_FORM_DATA = "multipart/form-data";

    @Override
    public void changePic(final String path) {
        mView.showProgressDialog();
        RequestBody pictureNameBody = RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), "file");
        File picture = new File(path);
        final RequestBody requestFile = RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), picture);
        // MultipartBody.Part 借助文件名上传
        MultipartBody.Part picturePart = MultipartBody.Part.createFormData("file", picture.getName(), requestFile);
        //调接口
        if (userApi == null) {
            userApi = RetrofitClient.RETROFIT_CLIENT.getRetrofit().create(UserApi.class);
        }

        userApi.changePic(pictureNameBody, picturePart)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e2(TAG, "changePic", "onError：" + e.getMessage());
                        mView.showError("网络连接错误");
                        mView.hidProgressDialog();
                    }

                    @Override
                    public void onNext(ResponseResult responseResult) {
                        LogUtil.d2(TAG, "changePic", "onNext: " + responseResult.getState() + responseResult.getStateInfo());
                        if (responseResult.getState() == 1) {
                            mView.changeImg();
                        } else {
                            mView.showError(responseResult.getStateInfo());
                        }
                        mView.hidProgressDialog();
                    }
                });
    }
}
