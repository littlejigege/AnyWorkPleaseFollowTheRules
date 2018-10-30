package com.qgstudio.anywork.exam.data;

import com.qgstudio.anywork.data.model.StudentPaper;
import com.qgstudio.anywork.exam.ExamView;
import com.qgstudio.anywork.mvp.BasePresenter;

/**
 * Created by Yason on 2017/8/13.
 */

public interface ExamPresenter extends BasePresenter<ExamView> {
    void getTestpaper(int textpaperId,int state);
    void submitTestPaper(StudentPaper studentPaper);
}
