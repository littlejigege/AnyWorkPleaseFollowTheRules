package com.qgstudio.aniwork.user;

import com.qgstudio.aniwork.data.model.User;
import com.qgstudio.aniwork.mvp.BasePresenter;
import com.qgstudio.aniwork.mvp.BaseView;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class UserContract {
    interface View extends BaseView {
        void showSuccess(User user);
        void showError(String s);
        void setUser(User user);
        void changeImg();
        void showProgressDialog();
        void hidProgressDialog();
    }

    interface Presenter extends BasePresenter<View> {
        void changeInfo(User newUser);
        void changePic(String path);
    }
}
