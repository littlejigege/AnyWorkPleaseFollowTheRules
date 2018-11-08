package com.qgstudio.aniwork.collection;

import com.qgstudio.aniwork.data.ResponseResult;
import com.qgstudio.aniwork.data.model.Question;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Url;
import rx.Observable;

public interface CollectionApi {
    @POST
    @Headers("Content-Type:application/json")
    Observable<ResponseResult> collect(@Url String url,@Body Object object);

    @POST
    @Headers("Content-Type:application/json")
    Observable<ResponseResult> uncollect(@Url String url,@Body Object object);

    @POST
    @Headers("Content-Type:application/json")
    Observable<ResponseResult<List<Question>>> getAllCollections(@Url String url);
}
