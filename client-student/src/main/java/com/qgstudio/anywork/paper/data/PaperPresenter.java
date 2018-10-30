package com.qgstudio.anywork.paper.data;

import com.qgstudio.anywork.paper.PaperFragView;
import com.qgstudio.anywork.mvp.BasePresenter;

/**
 * @author Yason 2017/8/13.
 */

public interface PaperPresenter extends BasePresenter<PaperFragView>  {
    void getExaminationPaper(int organizationId);
    void getPracticePaper(int organizationId);
}
