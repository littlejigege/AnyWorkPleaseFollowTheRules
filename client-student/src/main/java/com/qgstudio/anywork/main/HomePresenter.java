package com.qgstudio.anywork.main;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.qgstudio.anywork.data.RetrofitClient;
import com.qgstudio.anywork.data.RetrofitSubscriber;
import com.qgstudio.anywork.data.model.Organization;
import com.qgstudio.anywork.main.data.OrganizationApi;
import com.qgstudio.anywork.mvp.BasePresenterImpl;
import com.qgstudio.anywork.notice.NoticeApi;
import com.qgstudio.anywork.notice.data.Notice;
import com.qgstudio.anywork.utils.LogUtil;

import java.util.HashMap;
import java.util.List;

import retrofit2.Retrofit;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class HomePresenter extends BasePresenterImpl<HomeContract.HomeView> implements HomeContract.HomePresenter {
    private OrganizationApi mOrganizationApi;
    private NoticeApi mNoticeApi;
    public static final String TAG = "HomePresenter";

    public HomePresenter() {
        Retrofit retrofit = RetrofitClient.RETROFIT_CLIENT.getRetrofit();
        mOrganizationApi = retrofit.create(OrganizationApi.class);
        mNoticeApi = retrofit.create(NoticeApi.class);
    }

    @Override
    public void attachView(HomeContract.HomeView view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    @Override
    public void getJoinOrganization() {
        prepareLoading();
        mOrganizationApi.getJoinOrganization()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RetrofitSubscriber<List<Organization>>() {
                    @Override
                    protected void onSuccess(List<Organization> data) {
                        LogUtil.d2(TAG, "getJoinOrganization", "onSuccess -> " + data);

                        afterLoading();
                        if (mView != null) {
                            mView.onMyClassGot(data.isEmpty() ? null : data.get(0));
                        }
                    }

                    @Override
                    protected void onFailure(String info) {
                        LogUtil.d2(TAG, "getJoinOrganization", "onFailure -> " + info);

                        afterLoading();
                        if (mView != null) {
                            mView.showToast("获取信息失败");
                        }
                    }

                    @Override
                    protected void onMistake(Throwable t) {
                        LogUtil.d2(TAG, "getJoinOrganization", "onMistake -> " + t.getMessage());

                        afterLoading();
                        if (mView != null) {
                            mView.showToast("获取信息失败");
                        }

                    }
                });
    }

    @Override
    public void getNoticeNew() {
        mNoticeApi.getNotice(buildRequestParam())
                .subscribeOn(Schedulers.io())
                .observeOn((AndroidSchedulers.mainThread()))
                .subscribe(new RetrofitSubscriber<JsonObject>() {
                    @Override
                    protected void onSuccess(JsonObject data) {
                        List<Notice> noticeList = new Gson().fromJson(data
                                        .get("list")
                                , new TypeToken<List<Notice>>() {}.getType());
                        mView.onNoticeGet(noticeList);
                    }

                    @Override
                    protected void onFailure(String info) {

                    }

                    @Override
                    protected void onMistake(Throwable t) {
                        mView.onNoticeGet(null);
                    }
                });
    }

    private Object buildRequestParam() {
        HashMap info = new HashMap();
        info.put("status", 2);
        info.put("pageSize", 5);
        info.put("pageNum", 1);
        return info;
    }

    private void afterLoading() {
        if (mView != null) {
            mView.hideLoading();
        }
    }

    private void prepareLoading() {
        mView.showLoading();
    }
}
