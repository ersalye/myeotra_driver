package com.myeotra.driver.ui.activity.setting;

import com.myeotra.driver.base.MvpView;

public interface SettingsIView extends MvpView {

    void onSuccess(Object o);

    void onError(Throwable e);

}
