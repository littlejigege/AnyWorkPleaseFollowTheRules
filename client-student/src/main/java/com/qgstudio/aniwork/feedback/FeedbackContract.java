package com.qgstudio.aniwork.feedback;

import com.qgstudio.aniwork.feedback.data.FeedBack;
import com.qgstudio.aniwork.mvp.BasePresenter;
import com.qgstudio.aniwork.mvp.BaseView;

import rx.Subscription;

public class FeedbackContract {
    interface View extends BaseView {

        void showError(String errorInfo);

        void showSuccess();

        void showLoad();

        void stopLoad();

        void updateUploadProgress(long length, long hasWrited);
    }

    interface Presenter extends BasePresenter<View> {
        Subscription uploadFeedback(FeedBack feedBack, String imagePath);
    }
}
