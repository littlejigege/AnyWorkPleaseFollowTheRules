package com.qgstudio.aniwork.exam.data;

import com.qgstudio.aniwork.data.model.StudentPaper;
import com.qgstudio.aniwork.exam.ExamView;
import com.qgstudio.aniwork.mvp.BasePresenter;

/**
 * Created by Yason on 2017/8/13.
 */

public interface ExamPresenter extends BasePresenter<ExamView> {
    void getTestpaper(int textpaperId,int state);
    void submitTestPaper(StudentPaper studentPaper);
}
