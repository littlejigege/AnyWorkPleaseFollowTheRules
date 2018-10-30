package com.qgstudio.anywork.paper;


import com.qgstudio.anywork.common.PreLoading;
import com.qgstudio.anywork.workout.data.Testpaper;
import com.qgstudio.anywork.mvp.BaseView;

import java.util.List;

public interface PaperFragView extends BaseView, PreLoading {
    void addPracticePapers(List<Testpaper> testpapers);
    void addExaminationPapers(List<Testpaper> testpapers);
    void showImageError();
    void hideImageError();
    void showImageBlank();
//    void hideImageBlank();
}
