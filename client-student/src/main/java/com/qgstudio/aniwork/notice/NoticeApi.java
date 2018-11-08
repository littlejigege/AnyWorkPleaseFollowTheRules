package com.qgstudio.aniwork.notice;

import com.google.gson.JsonObject;
import com.qgstudio.aniwork.data.ResponseResult;
import com.qgstudio.aniwork.notice.data.NoticeContainer;

import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Url;
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
    @POST
    @Headers("Content-Type:application/json")
    Observable<ResponseResult<NoticeContainer>> getNotice(@Url String url, @Body Object object);


    /**
     * 标记公告已读
     * {
     "messageId": "公告ID"
     }
     * @param object
     * @return
     */
    @POST
    @Headers("Content-Type:application/json")
    Observable<ResponseResult> markWatched(@Url String url,@Body Object object);
}
