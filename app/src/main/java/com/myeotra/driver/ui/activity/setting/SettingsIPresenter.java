package com.myeotra.driver.ui.activity.setting;

import com.myeotra.driver.base.MvpPresenter;

public interface SettingsIPresenter<V extends SettingsIView> extends MvpPresenter<V> {
    void changeLanguage(String languageID);
}
