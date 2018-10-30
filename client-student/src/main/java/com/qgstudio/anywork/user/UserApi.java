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
import rx.Observable;

/**
 * 用户模块的网络接口
 * Created by chenyi on 2017/4/13.
 */

public interface UserApi {

    @POST("user/update")
    @Headers("Content-Type:application/json;charset=UTF-8")
    Observable<ResponseResult<User>> changeInfo(@Body Object o);

    @POST("user/password/change")
    @Headers("Content-Type:application/json;charset=UTF-8")
    Observable<ResponseResult> changePassword(@Body Object o);

    @Multipart
    @POST("user/upload")
    Observable<ResponseResult> changePic(@Part("file") RequestBody pictureName, @Part MultipartBody.Part picture);

    @POST("user/exit")
    Observable<ResponseResult> logout();
}
