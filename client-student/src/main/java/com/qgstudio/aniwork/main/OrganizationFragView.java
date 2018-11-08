package com.qgstudio.aniwork.main;


import com.qgstudio.aniwork.common.PreLoading;
import com.qgstudio.aniwork.data.model.Organization;
import com.qgstudio.aniwork.mvp.BaseView;

import java.util.List;

public interface OrganizationFragView extends BaseView, PreLoading {
    void addOrganization(Organization organization);
    void addOrganizations(List<Organization> organizations);
    void removeOrganization(int position);
    void startPaperAty(int organizatonId);
    void updateItemJoinStatus(int position, boolean isJoin);
    void sendUpdateBroadCast();
    void destroySelf();
}
