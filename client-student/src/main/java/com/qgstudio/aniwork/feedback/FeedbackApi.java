package com.qgstudio.aniwork.feedback;

import com.qgstudio.aniwork.data.ResponseResult;
import com.qgstudio.aniwork.data.model.User;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Url;
import rx.Observable;

public interface FeedbackApi {
    @POST("suggest/add")
    @Headers("Content-Type:application/json")
    Observable<ResponseResult<User>> uploadFeedback(@Body Object object);

    @Multipart
    @POST
    Observable<ResponseResult<User>> uploadFeedbackWithPicture(@Url String url, @Part List<MultipartBody.Part> partList);
}
