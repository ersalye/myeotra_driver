package com.myeotra.driver.ui.activity.splash;

import com.myeotra.driver.base.MvpPresenter;

import java.util.HashMap;

public interface SplashIPresenter<V extends SplashIView> extends MvpPresenter<V> {

    void handlerCall();

    void getPlaces();

    void checkVersion(HashMap<String, Object> map);

}
