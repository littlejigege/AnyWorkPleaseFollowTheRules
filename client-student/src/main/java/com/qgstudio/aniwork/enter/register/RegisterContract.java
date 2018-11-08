package com.qgstudio.aniwork.enter.register;

import com.qgstudio.aniwork.mvp.BasePresenter;
import com.qgstudio.aniwork.mvp.BaseView;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class RegisterContract {
    interface View extends BaseView {

        void showError(String errorInfo);

        void showSuccess();

        void showLoad();

        void stopLoad();
    }

    interface  Presenter extends BasePresenter<View> {
        void register(String email, String password, String name, String phone);
    }
}
