package com.qgstudio.aniwork.exam;


import com.qgstudio.aniwork.common.PreLoading;
import com.qgstudio.aniwork.data.model.Question;
import com.qgstudio.aniwork.data.model.StudentAnswerResult;
import com.qgstudio.aniwork.mvp.BaseView;

import java.util.List;

public interface ExamView extends BaseView, PreLoading {
    void addQuestions(List<Question> questions);

    void startGradeAty(double socre, List<StudentAnswerResult> analysis);

    void destroySelf();

    void submitDone();

    void saveDone();

}
