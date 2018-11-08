package com.qgstudio.aniwork.main;

import com.qgstudio.aniwork.common.PreLoading;
import com.qgstudio.aniwork.data.model.Organization;
import com.qgstudio.aniwork.mvp.BasePresenter;
import com.qgstudio.aniwork.mvp.BaseView;
import com.qgstudio.aniwork.notice.data.Notice;

import java.util.List;

public class HomeContract {
    interface HomeView extends BaseView, PreLoading {
        public void onMyClassGot(Organization organization);

        public void onNoticeGet(List<Notice> notices);

    }

    interface HomePresenter extends BasePresenter<HomeView> {
        public void getJoinOrganization();

        public void getNoticeNew();
//        public void postReadNew(String id);
    }
}
