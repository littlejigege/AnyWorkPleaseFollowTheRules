package com.qgstudio.anywork.feedback;

import com.qgstudio.anywork.data.ResponseResult;
import com.qgstudio.anywork.data.model.User;

import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;

public interface FeedbackApi {
    @POST("suggest/add")
    @Headers("Content-Type:application/json")
    Observable<ResponseResult<User>> uploadFeedback(@Body Object object);
}
