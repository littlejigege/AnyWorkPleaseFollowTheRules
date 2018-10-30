package com.qgstudio.anywork.exam.data;

import com.qgstudio.anywork.data.ResponseResult;
import com.qgstudio.anywork.data.model.Question;
import com.qgstudio.anywork.data.model.StudentPaper;
import com.qgstudio.anywork.data.model.StudentTestResult;

import java.util.List;
import java.util.Map;

import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Yason on 2017/4/14.
 */

public interface ExamApi {

//    @POST("test")
//    Observable<ResponseResult<List<Question>>> getTestpaper(@Body() Map<String, String> testpaperId);

    @POST("test/none/detail")
    @Headers("Content-Type:application/json")
    Observable<ResponseResult<List<Question>>> getTestpaper(@Body Object o);

    @POST("test/submit")
    @Headers("Content-Type:application/json")
    Observable<ResponseResult<StudentTestResult>> submitTestpaper(@Body StudentPaper studentPaper);

}
