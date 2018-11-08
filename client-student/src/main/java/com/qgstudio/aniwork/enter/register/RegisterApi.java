package com.qgstudio.aniwork.enter.register;

import com.qgstudio.aniwork.data.ResponseResult;

import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by chenyi on 2017/4/11.
 */

interface RegisterApi {

    @POST
    @Headers("Content-Type:application/json")
    Observable<ResponseResult<Integer>> register(@Url String url, @Body Object object);
}
