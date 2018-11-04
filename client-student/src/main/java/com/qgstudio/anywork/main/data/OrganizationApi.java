package com.qgstudio.anywork.main.data;

import com.qgstudio.anywork.data.ResponseResult;
import com.qgstudio.anywork.data.model.Organization;

import java.util.List;
import java.util.Map;

import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Url;
import rx.Observable;

/**
 * @author Yason 2017/4/14.
 */

public interface OrganizationApi {

    @POST
    Observable<ResponseResult<List<Organization>>> getJoinOrganization(@Url String url);

    @POST
    Observable<ResponseResult<List<Organization>>> getAllOrganization(@Url String url,@Body Map<String, String> organizationName);

    @POST
    Observable<ResponseResult> joinOrganization(@Url String url,@Body Map<String, String> organizationInfo);

    @POST
    Observable<ResponseResult> leaveOrganization(@Url String url,@Body Map<String, Integer> organizationId);
}
