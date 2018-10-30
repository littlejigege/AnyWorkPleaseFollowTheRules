package com.qgstudio.anywork.collection;

import com.qgstudio.anywork.data.ResponseResult;
import com.qgstudio.anywork.data.model.Question;
import com.qgstudio.anywork.data.model.User;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;

public interface CollectionApi {
    @POST("quest/collect")
    @Headers("Content-Type:application/json")
    Observable<ResponseResult> collect(@Body Object object);

    @POST("quest/collect/delete")
    @Headers("Content-Type:application/json")
    Observable<ResponseResult> uncollect(@Body Object object);

    @POST("quest/collect/list")
    @Headers("Content-Type:application/json")
    Observable<ResponseResult<List<Question>>> getAllCollections();
}
