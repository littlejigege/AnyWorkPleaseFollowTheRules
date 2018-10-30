package com.qgstudio.anywork.enter.login;

import com.qgstudio.anywork.data.ResponseResult;
import com.qgstudio.anywork.data.model.User;

import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;

/**
 * 登录的网络请求接口
 * Created by chenyi on 2017/3/31.
 */

public interface LoginApi {

    @POST("user/login")
    @Headers("Content-Type:application/json")
    Observable<ResponseResult<User>> login(@Body Object object);
}
