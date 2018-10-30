package com.qgstudio.anywork.grade;

import com.qgstudio.anywork.data.model.Question;
import com.qgstudio.anywork.data.model.StudentAnswerAnalysis;
import com.qgstudio.anywork.mvp.BasePresenter;
import com.qgstudio.anywork.mvp.BaseView;

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
