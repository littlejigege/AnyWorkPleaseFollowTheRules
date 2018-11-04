package com.qgstudio.anywork.workout.data;

import com.google.gson.JsonObject;
import com.qgstudio.anywork.data.ResponseResult;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Url;
import rx.Observable;

public interface ChapterApi {
    @POST
    @Headers("Content-Type:application/json")
    Observable<ResponseResult<List<Chapter>>> getChapter(@Url String url, @Body Object object);

    @POST
    @Headers("Content-Type:application/json")
    Observable<ResponseResult<List<Testpaper>>> getCatalog(@Url String url,@Body Object object);

}
