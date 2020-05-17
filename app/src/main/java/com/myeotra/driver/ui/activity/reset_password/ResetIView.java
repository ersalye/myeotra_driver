package com.myeotra.driver.ui.activity.reset_password;

import com.myeotra.driver.base.MvpView;

public interface ResetIView extends MvpView{

    void onSuccess(Object object);
    void onError(Throwable e);
}
