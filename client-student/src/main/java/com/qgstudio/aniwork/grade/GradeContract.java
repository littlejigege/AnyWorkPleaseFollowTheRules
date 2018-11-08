package com.qgstudio.aniwork.grade;

import com.qgstudio.aniwork.data.model.StudentAnswerAnalysis;
import com.qgstudio.aniwork.mvp.BasePresenter;
import com.qgstudio.aniwork.mvp.BaseView;

/**
 * Created by chenyi on 2017/7/10.
 */

public interface GradeContract {

    interface View extends BaseView {
        void showSuccess(StudentAnswerAnalysis analysis);
        void showError(String s);
    }

    interface Presenter extends BasePresenter<View> {
        void getDetail(int id);
    }
}
