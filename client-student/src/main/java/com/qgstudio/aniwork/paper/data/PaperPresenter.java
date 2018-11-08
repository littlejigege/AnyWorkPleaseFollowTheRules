package com.qgstudio.aniwork.paper.data;

import com.qgstudio.aniwork.paper.PaperFragView;
import com.qgstudio.aniwork.mvp.BasePresenter;

/**
 * @author Yason 2017/8/13.
 */

public interface PaperPresenter extends BasePresenter<PaperFragView>  {
    void getExaminationPaper(int organizationId);
    void getPracticePaper(int organizationId);
}
