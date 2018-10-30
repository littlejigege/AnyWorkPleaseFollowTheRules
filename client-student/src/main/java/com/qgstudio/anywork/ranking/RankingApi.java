package com.qgstudio.anywork.ranking;

import com.qgstudio.anywork.data.ResponseResult;
import com.qgstudio.anywork.data.model.RankingMessage;

import java.util.ArrayList;

import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;

public interface RankingApi {

    @POST("leaderboard/show")
    @Headers("Content-Type:application/json")
    Observable<ResponseResult<ArrayList<RankingMessage>>> getTotalRanking(@Body Object o);

    @POST("leaderboard/paper/show")
    @Headers("Content-Type:application/json")
    Observable<ResponseResult<ArrayList<RankingMessage>>> getRanking(@Body Object o);
}
