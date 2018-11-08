package com.qgstudio.aniwork.workout.data;

import com.qgstudio.aniwork.data.ResponseResult;

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
