package com.myeotra.driver.ui.activity.change_password;

import com.myeotra.driver.base.MvpView;

public interface ChangePasswordIView extends MvpView {


    void onSuccess(Object object);
    void onError(Throwable e);
}
