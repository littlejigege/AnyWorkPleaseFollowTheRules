package com.qgstudio.aniwork.paper;


import com.qgstudio.aniwork.common.PreLoading;
import com.qgstudio.aniwork.workout.data.Testpaper;
import com.qgstudio.aniwork.mvp.BaseView;

import java.util.List;

public interface PaperFragView extends BaseView, PreLoading {
    void addPracticePapers(List<Testpaper> testpapers);
    void addExaminationPapers(List<Testpaper> testpapers);
    void showImageError();
    void hideImageError();
    void showImageBlank();
//    void hideImageBlank();
}
