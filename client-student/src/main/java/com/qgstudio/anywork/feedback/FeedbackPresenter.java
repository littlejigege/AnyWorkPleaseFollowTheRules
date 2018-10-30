package com.qgstudio.anywork.feedback;

import com.qgstudio.anywork.data.ResponseResult;
import com.qgstudio.anywork.data.RetrofitClient;
import com.qgstudio.anywork.data.model.User;
import com.qgstudio.anywork.feedback.data.FeedBack;
import com.qgstudio.anywork.mvp.BasePresenterImpl;
import com.qgstudio.anywork.utils.LogUtil;

import rx.Observer;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class FeedbackPresenter extends BasePresenterImpl<FeedbackContract.View> implements FeedbackContract.Presenter {

    private FeedbackApi feedbackApi;

    @Override
    public void uploadFeedback(FeedBack feedBack) {
        mView.showLoad();
        if (feedbackApi == null) {
            feedbackApi = RetrofitClient.RETROFIT_CLIENT.getRetrofit().create(FeedbackApi.class);
        }

        feedBack.buildOutput();
        feedbackApi.uploadFeedback(feedBack)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseResult<User>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        mView.showError("网络连接错误");
                        mView.stopLoad();
                    }

                    @Override
                    public void onNext(ResponseResult<User> userResponseResult) {
                        if (userResponseResult.getState() == 1) {
                            mView.showSuccess();
                        }
                        mView.stopLoad();
                    }
                });
    }
}
