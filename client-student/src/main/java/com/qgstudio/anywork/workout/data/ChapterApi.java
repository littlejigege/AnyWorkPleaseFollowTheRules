package com.qgstudio.anywork.workout.data;

import com.google.gson.JsonObject;
import com.qgstudio.anywork.data.ResponseResult;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;

public interface ChapterApi {
    @POST("test/chapter")
    @Headers("Content-Type:application/json")
    Observable<ResponseResult<List<Chapter>>> getChapter(@Body Object object);

    @POST("test/list")
    @Headers("Content-Type:application/json")
    Observable<ResponseResult<List<Testpaper>>> getCatalog(@Body Object object);

}
