package com.qgstudio.anywork.main.data;

import com.qgstudio.anywork.data.ResponseResult;
import com.qgstudio.anywork.data.model.Organization;

import java.util.List;
import java.util.Map;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * @author Yason 2017/4/14.
 */

public interface OrganizationApi {

    @POST("organization/me")
    Observable<ResponseResult<List<Organization>>> getJoinOrganization();

    @POST("organization/search")
    Observable<ResponseResult<List<Organization>>> getAllOrganization(@Body Map<String, String> organizationName);

    @POST("organization/join")
    Observable<ResponseResult> joinOrganization(@Body Map<String, String> organizationInfo);

    @POST("organization/leave")
    Observable<ResponseResult> leaveOrganization(@Body Map<String, Integer> organizationId);
}
