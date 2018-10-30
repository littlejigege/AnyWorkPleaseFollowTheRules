package com.qgstudio.anywork.grade;

import com.qgstudio.anywork.data.ResponseResult;
import com.qgstudio.anywork.data.model.StudentAnswerAnalysis;

import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by chenyi on 17-7-28.
 */

public interface GradeApi {

    @POST("test/detail")
    @Headers("Content-Type:application/json;charset=UTF-8")
    Observable<ResponseResult<StudentAnswerAnalysis>> changeInfo(@Body Object o);
}
