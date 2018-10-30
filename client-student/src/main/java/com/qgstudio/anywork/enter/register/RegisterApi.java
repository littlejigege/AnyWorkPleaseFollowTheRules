package com.qgstudio.anywork.enter.register;

import com.qgstudio.anywork.data.ResponseResult;

import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by chenyi on 2017/4/11.
 */

interface RegisterApi {

    @POST("user/register")
    @Headers("Content-Type:application/json")
    Observable<ResponseResult<Integer>> register(@Body Object object);
}
