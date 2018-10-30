package com.qgstudio.anywork.main.data;

import com.qgstudio.anywork.main.NewOrganizationActivity;
import com.qgstudio.anywork.main.OrganizationFragView;
import com.qgstudio.anywork.mvp.BasePresenter;

/**
 * @author Yason 2017/8/13.
 */

public interface OrganizationPresenter extends BasePresenter<OrganizationFragView> {
    void getAllOrganization();
    void getJoinOrganization();
    void updateJoinOrganization();
    void joinOrganization(int organizationId, String password, int position);
    void leaveOrganization(int organizationId, NewOrganizationActivity activity);
}
