package com.qgstudio.aniwork.feedback;

import com.qgstudio.aniwork.core.Apis;
import com.qgstudio.aniwork.data.ResponseResult;
import com.qgstudio.aniwork.data.RetrofitClient;
import com.qgstudio.aniwork.data.model.User;
import com.qgstudio.aniwork.feedback.data.FeedBack;
import com.qgstudio.aniwork.feedback.utils.FileRequestBody;
import com.qgstudio.aniwork.mvp.BasePresenterImpl;
import com.qgstudio.aniwork.utils.LogUtil;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import rx.Observer;
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
        //图片路径不为空，则上传图片
        if (imagePath != null) {
            LogUtil.d("wtf","6666");
            file = new File(imagePath);
            final FileRequestBody body = new FileRequestBody(MediaType.parse("multipart/form-data"), file);
            body.setWritedListener(new FileRequestBody.WritedListener() {
                @Override
                public void hasSend(long length) {
                    mView.updateUploadProgress(body.contentLength(), length);
                }
            });
            builder.addFormDataPart("file", file.getName(), body);
        }
//        RequestBody body = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        builder.addFormDataPart("description", feedBack.toString());
        builder.addFormDataPart("file", "");
        List<MultipartBody.Part> parts = builder.build().parts();

        Subscription subscription = feedbackApi.uploadFeedbackWithPicture(Apis.uploadFeedbackApi(), parts)
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
