package com.qgstudio.anywork.feedback;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.qgstudio.anywork.core.Apis;
import com.qgstudio.anywork.data.ResponseResult;
import com.qgstudio.anywork.data.RetrofitClient;
import com.qgstudio.anywork.data.model.User;
import com.qgstudio.anywork.feedback.data.FeedBack;
import com.qgstudio.anywork.feedback.utils.FileRequestBody;
import com.qgstudio.anywork.mvp.BasePresenterImpl;
import com.qgstudio.anywork.utils.LogUtil;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Multipart;
import rx.Observer;
import rx.Scheduler;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class FeedbackPresenter extends BasePresenterImpl<FeedbackContract.View> implements FeedbackContract.Presenter {

    private FeedbackApi feedbackApi;



    @Override
    public Subscription uploadFeedback(FeedBack feedBack, String imagePath) {
//        mView.showLoad();
        if (feedbackApi == null) {
            feedbackApi = RetrofitClient.RETROFIT_CLIENT.getRetrofit().create(FeedbackApi.class);
        }

//        feedBack.buildOutput();
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);

        File file;
        if (imagePath == null) {
            file = null;
        } else {
            file = new File(imagePath);
        }
//        RequestBody body = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        final FileRequestBody body = new FileRequestBody(MediaType.parse("multipart/form-data"), file);
        body.setWritedListener(new FileRequestBody.WritedListener() {
            @Override
            public void hasSend(long length) {
                mView.updateUploadProgress(body.contentLength(), length);
            }
        });
        builder.addFormDataPart("description", feedBack.toString());
        builder.addFormDataPart("file", file.getName(), body);
        List<MultipartBody.Part> parts = builder.build().parts();

        Subscription subscription = feedbackApi.uploadFeedbackWithPicture(Apis.uploadFeedbackApi(),parts)
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
//                        mView.stopLoad();
                    }

                    @Override
                    public void onNext(ResponseResult<User> userResponseResult) {
                        if (userResponseResult.getState() == 1) {
                            mView.showSuccess();
                        }
//                        mView.stopLoad();
                    }
                });
        return subscription;
    }
}
