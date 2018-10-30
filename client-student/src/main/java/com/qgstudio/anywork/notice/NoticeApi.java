package com.qgstudio.anywork.notice;

import com.google.gson.JsonObject;
import com.qgstudio.anywork.data.ResponseResult;
import com.qgstudio.anywork.data.model.User;

import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;

public interface NoticeApi {
    /**
     * 获取一页公告
     * {
     "status": "状态",  // int，1代表要获取已读公告，0代表要获取未读公告
     “pageNum”: "查看哪一页",
     "pageSize": "一页的数目"
     }
     * @param object
     * @return
     */
    @POST("message/show")
    @Headers("Content-Type:application/json")
    Observable<ResponseResult<JsonObject>> getNotice(@Body Object object);


    /**
     * 标记公告已读
     * {
     "messageId": "公告ID"
     }
     * @param object
     * @return
     */
    @POST("message/change")
    @Headers("Content-Type:application/json")
    Observable<ResponseResult> markWatched(@Body Object object);
}
