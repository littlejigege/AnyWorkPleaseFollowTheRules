package com.qgstudio.anywork.user;


import com.qgstudio.anywork.data.ResponseResult;
import com.qgstudio.anywork.data.model.User;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Url;
import rx.Observable;

/**
 * 用户模块的网络接口
 * Created by chenyi on 2017/4/13.
 */

public interface UserApi {

    @POST
    @Headers("Content-Type:application/json;charset=UTF-8")
    Observable<ResponseResult<User>> changeInfo(@Url String url, @Body Object o);

    @POST
    @Headers("Content-Type:application/json;charset=UTF-8")
    Observable<ResponseResult> changePassword(@Url String url,@Body Object o);

    @Multipart
    @POST
    Observable<ResponseResult> changePic(@Url String url,@Part("file") RequestBody pictureName, @Part MultipartBody.Part picture);

    @POST
    Observable<ResponseResult> logout(@Url String url);
}
