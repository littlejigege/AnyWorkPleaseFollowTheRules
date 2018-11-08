package com.qgstudio.aniwork.grade;

import com.qgstudio.aniwork.data.ResponseResult;
import com.qgstudio.aniwork.data.model.StudentAnswerAnalysis;

import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by chenyi on 17-7-28.
 */

public interface GradeApi {

    @POST
    @Headers("Content-Type:application/json;charset=UTF-8")
    Observable<ResponseResult<StudentAnswerAnalysis>> changeInfo(@Url String url, @Body Object o);
}
