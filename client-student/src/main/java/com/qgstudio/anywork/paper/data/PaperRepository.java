package com.qgstudio.anywork.paper.data;


import android.content.Context;

import com.qgstudio.anywork.data.RetrofitClient;
import com.qgstudio.anywork.data.RetrofitSubscriber;
import com.qgstudio.anywork.workout.data.Testpaper;
import com.qgstudio.anywork.paper.PaperFragView;
import com.qgstudio.anywork.mvp.BasePresenterImpl;
import com.qgstudio.anywork.utils.LogUtil;

import java.net.ConnectException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Retrofit;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * @author Yason 2017/4/12.
 */

public class PaperRepository extends BasePresenterImpl<PaperFragView> implements PaperPresenter {

    private static final String TAG = "PaperRepository";
    private PaperApi mPaperApi;

    public PaperRepository() {
        Retrofit retrofit = RetrofitClient.RETROFIT_CLIENT.getRetrofit();
        mPaperApi = retrofit.create(PaperApi.class);
    }

    public void getPracticePaper(int organizationId) {
        mView.hideImageError();
        prepareLoading();

        Map<String, String> map = new HashMap();
        map.put("organizationId", organizationId + "");
        mPaperApi.getPracticePaper(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RetrofitSubscriber<List<Testpaper>>() {
                    @Override
                    protected void onSuccess(List<Testpaper> data) {
                        LogUtil.d(TAG, "[getPracticePaper] " + "onSuccess -> " + data);

                        if (data.size() <= 0) {
                            mView.showImageBlank();
                            return;
                        }

                        mView.addPracticePapers(data);
                        afterLoading();
                    }

                    @Override
                    protected void onFailure(String info) {
                        LogUtil.d(TAG, "[getPracticePaper] " + "onFailure -> " + info);

                        handleGetTestPaperError();
                        afterLoading();
                    }

                    @Override
                    protected void onMistake(Throwable t) {
                        LogUtil.d(TAG, "[getPracticePaper] " + "onMistake -> " + t.getMessage());

                        if (t instanceof ConnectException) {
                            mView.showToast("无法连接到服务器");
                            mView.showImageError();
                        } else {
                            handleGetTestPaperError();
                        }

                        afterLoading();
                    }
                });
    }

    public void getExaminationPaper(int organizationId) {
        mView.hideImageError();
        prepareLoading();

        Map<String, String> map = new HashMap();
        map.put("organizationId", organizationId + "");
        mPaperApi.getExaminationPaper(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RetrofitSubscriber<List<Testpaper>>() {
                    @Override
                    protected void onSuccess(List<Testpaper> data) {
                        LogUtil.d(TAG, "[getExaminationPaper] " + "onSuccess -> " + data);

                        if (data.size() <= 0) {
                            mView.showImageBlank();
                            return;
                        }

                        mView.addExaminationPapers(data);
                        afterLoading();
                    }

                    @Override
                    protected void onFailure(String info) {
                        LogUtil.d(TAG, "[getExaminationPaper] " + "onFailure -> " + info);

                        handleGetTestPaperError();
                        afterLoading();
                    }

                    @Override
                    protected void onMistake(Throwable t) {
                        LogUtil.d(TAG, "[getExaminationPaper] " + "onMistake -> " + t.getMessage());

                        if (t instanceof ConnectException) {
                            mView.showToast("无法连接到服务器");
                            mView.showImageError();
                        } else {
                            handleGetTestPaperError();
                        }

                        afterLoading();
                    }
                });
    }

    private void handleGetTestPaperError() {
        mView.showToast("获取试卷失败");
        mView.showImageError();
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
        mView = new PaperFragView() {

            @Override
            public void addPracticePapers(List<Testpaper> testpapers) {

            }

            @Override
            public void addExaminationPapers(List<Testpaper> testpapers) {

            }

            @Override
            public void showImageError() {

            }

            @Override
            public void hideImageError() {

            }

            @Override
            public void showImageBlank() {

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
