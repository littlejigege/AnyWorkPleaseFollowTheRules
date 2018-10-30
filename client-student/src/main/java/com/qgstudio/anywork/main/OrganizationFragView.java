package com.qgstudio.anywork.main;


import com.qgstudio.anywork.common.PreLoading;
import com.qgstudio.anywork.data.model.Organization;
import com.qgstudio.anywork.mvp.BaseView;

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
