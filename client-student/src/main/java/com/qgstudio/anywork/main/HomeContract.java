package com.qgstudio.anywork.main;

import com.qgstudio.anywork.common.PreLoading;
import com.qgstudio.anywork.data.model.Organization;
import com.qgstudio.anywork.mvp.BasePresenter;
import com.qgstudio.anywork.mvp.BaseView;
import com.qgstudio.anywork.notice.data.Notice;

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
