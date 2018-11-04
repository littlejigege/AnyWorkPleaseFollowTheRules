package com.qgstudio.anywork.feedback;

import com.qgstudio.anywork.data.ResponseResult;
import com.qgstudio.anywork.data.model.User;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import rx.Observable;

public interface FeedbackApi {
    @POST("suggest/add")
    @Headers("Content-Type:application/json")
    Observable<ResponseResult<User>> uploadFeedback(@Body Object object);

    @Multipart
    @POST("suggest/add")
    Observable<ResponseResult<User>> uploadFeedbackWithPicture(@Part List<MultipartBody.Part> partList);
}
