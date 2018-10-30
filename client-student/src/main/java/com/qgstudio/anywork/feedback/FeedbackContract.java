package com.qgstudio.anywork.feedback;

import com.qgstudio.anywork.feedback.data.FeedBack;
import com.qgstudio.anywork.mvp.BasePresenter;
import com.qgstudio.anywork.mvp.BaseView;

public class FeedbackContract {
    interface View extends BaseView {

        void showError(String errorInfo);

        void showSuccess();

        void showLoad();

        void stopLoad();
    }

    interface Presenter extends BasePresenter<View> {
        void uploadFeedback(FeedBack feedBack);
    }
}
