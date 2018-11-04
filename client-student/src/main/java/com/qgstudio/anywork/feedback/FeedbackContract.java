package com.qgstudio.anywork.feedback;

import com.qgstudio.anywork.feedback.data.FeedBack;
import com.qgstudio.anywork.mvp.BasePresenter;
import com.qgstudio.anywork.mvp.BaseView;

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
