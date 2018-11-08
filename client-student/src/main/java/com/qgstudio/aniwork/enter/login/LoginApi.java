package com.qgstudio.aniwork.enter.login;

import com.qgstudio.aniwork.data.ResponseResult;
import com.qgstudio.aniwork.data.model.User;

import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Url;
import rx.Observable;

/**
 * 登录的网络请求接口
 * Created by chenyi on 2017/3/31.
 */
public interface LoginApi {

    @POST
    @Headers("Content-Type:application/json")
    Observable<ResponseResult<User>> login(@Url String url , @Body Object object);

}
