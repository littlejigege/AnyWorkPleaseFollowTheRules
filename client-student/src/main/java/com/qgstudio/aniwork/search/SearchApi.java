package com.qgstudio.aniwork.search;

import com.qgstudio.aniwork.data.ResponseResult;
import com.qgstudio.aniwork.workout.data.Testpaper;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;

public interface SearchApi {
    @POST("test/list")
    @Headers("Content-Type:application/json")
    Observable<ResponseResult<List<Testpaper>>> searchTestPaper(@Body Object object);
}
