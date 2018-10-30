package com.qgstudio.anywork.main.data;

import android.content.Context;

import com.qgstudio.anywork.data.ResponseResult;
import com.qgstudio.anywork.data.RetrofitClient;
import com.qgstudio.anywork.data.RetrofitSubscriber;
import com.qgstudio.anywork.data.model.Organization;
import com.qgstudio.anywork.main.NewOrganizationActivity;
import com.qgstudio.anywork.main.OrganizationFragView;
import com.qgstudio.anywork.mvp.BasePresenterImpl;
import com.qgstudio.anywork.utils.GsonUtil;
import com.qgstudio.anywork.utils.LogUtil;
import com.qgstudio.anywork.utils.ToastUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Retrofit;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * @author Yason 2017/4/14.
 */

public class OrganizationRepository extends BasePresenterImpl<OrganizationFragView> implements OrganizationPresenter {

    public static final String TAG = "OrganizationRepository";

    private OrganizationApi mOrganizationApi;

    public OrganizationRepository() {
        Retrofit retrofit = RetrofitClient.RETROFIT_CLIENT.getRetrofit();
        mOrganizationApi = retrofit.create(OrganizationApi.class);
    }

    @Override
    public void getAllOrganization() {
        prepareLoading();

        Map<String, String> map = new HashMap<>();
        map.put("organizationName", "");
        mOrganizationApi.getAllOrganization(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RetrofitSubscriber<List<Organization>>() {
                    @Override
                    protected void onSuccess(List<Organization> data) {
                        LogUtil.d2(TAG, "getAllOrganization", "onSuccess -> " + data);

                        afterLoading();
                        mView.addOrganizations(data);
                    }

                    @Override
                    protected void onFailure(String info) {
                        LogUtil.d2(TAG, "getAllOrganization", "onFailure -> " + info);

                        afterLoading();
                        mView.showToast("获取信息失败");
                    }

                    @Override
                    protected void onMistake(Throwable t) {
                        LogUtil.d2(TAG, "getAllOrganization", "onMistake -> " + t.getMessage());

                        afterLoading();
                        mView.showToast("获取信息失败");
                    }
                });
    }

    @Override
    public void getJoinOrganization(){
        prepareLoading();
        updateJoinOrganization();
    }

    @Override
    public void updateJoinOrganization() {
//        prepareLoading();

        mOrganizationApi.getJoinOrganization()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RetrofitSubscriber<List<Organization>>() {
                    @Override
                    protected void onSuccess(List<Organization> data) {
                        LogUtil.d2(TAG, "getJoinOrganization", "onSuccess -> " + data);
                        NewOrganizationActivity.myOrganization = null;
                        afterLoading();
                        mView.addOrganizations(data);
                    }

                    @Override
                    protected void onFailure(String info) {
                        LogUtil.d2(TAG, "getJoinOrganization", "onFailure -> " + info);

                        afterLoading();
                        mView.showToast("获取信息失败");
                    }

                    @Override
                    protected void onMistake(Throwable t) {
                        LogUtil.d2(TAG, "getJoinOrganization", "onMistake -> " + t.getMessage());

                        afterLoading();
                        mView.showToast("获取信息失败");
                    }
                });
    }

    @Override
    public void joinOrganization(final int organizationId, String password, final int position) {
        Map<String, String> info = new HashMap<>();
        info.put("organizationId", organizationId + "");
        info.put("token", password);

        mOrganizationApi.joinOrganization(info)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d2(TAG, "joinOrganization", "onError -> " + e.getMessage());
                        mView.showToast("加入组织失败");
                    }

                    @Override
                    public void onNext(ResponseResult responseResult) {
                        if (responseResult.getState() == 1) {
                            LogUtil.d2(TAG, "joinOrganization", "onNext -> joinSuccess");

                            mView.updateItemJoinStatus(position, true);
                            mView.destroySelf();
//                            mView.startPaperAty(organizationId);
                            mView.showToast("加入班级成功");

//                            mView.sendUpdateBroadCast();
                        } else {
                            LogUtil.d2(TAG, "joinOrganization", "onNext -> joinFailure，" + responseResult.getStateInfo());
                            mView.showToast("加入班级失败");
                        }
                    }
                });
    }

    @Override
    public void leaveOrganization(int organizationId, final NewOrganizationActivity activity) {
        Map<String, Integer> organizationInfo = new HashMap<>();
        organizationInfo.put("organizationId", organizationId);

        LogUtil.d2(TAG, "leaveOrganization", "organizationInfo -> " + GsonUtil.GsonString(organizationInfo));
        mOrganizationApi.leaveOrganization(organizationInfo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d2(TAG, "leaveOrganization", "onError -> " + e.getMessage());
//                        mView.showToast("退出班级失败");
                    }

                    @Override
                    public void onNext(ResponseResult responseResult) {
                        if (responseResult.getState() == 1) {
                            LogUtil.d2(TAG, "leaveOrganization", "onNext -> leaveSuccess");
                            ToastUtil.showToast("退出班级成功");
                            activity.leaveSucceed();
//                            mView.updateItemJoinStatus(position, false);
//                            mView.showToast("退出班级成功");
//
//                            mView.sendUpdateBroadCast();
                        } else {
                            LogUtil.d2(TAG, "leaveOrganization", "onNext -> leaveFailure，" + responseResult.getStateInfo());
                            ToastUtil.showToast("退出班级失败");
//                            mView.showToast("退出班级失败");
                        }
                    }
                });
    }

    private void afterLoading() {
        mView.hideLoading();
    }

    private void prepareLoading() {
        mView.showLoading();
    }

    @Override
    public void detachView() {
        super.detachView();
        mView = new OrganizationFragView() {
            @Override
            public void addOrganization(Organization organization) {

            }

            @Override
            public void addOrganizations(List<Organization> organizations) {

            }

            @Override
            public void removeOrganization(int position) {

            }

            @Override
            public void startPaperAty(int organizatonId) {

            }

            @Override
            public void updateItemJoinStatus(int position, boolean isJoin) {

            }

            @Override
            public void sendUpdateBroadCast() {

            }

            @Override
            public void destroySelf() {

            }

            @Override
            public Context getContext() {
                return null;
            }

            @Override
            public void showLoading() {

            }

            @Override
            public void hideLoading() {

            }

            @Override
            public void showToast(String s) {

            }
        };
    }
}
