package com.qgstudio.aniwork.enter.login;

import com.qgstudio.aniwork.data.ResponseResult;

import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;

public interface ForgetApi {

    @POST("user/forget")
    @Headers("Content-Type:application/json")
    Observable<ResponseResult> forget(@Body Object object);
}
