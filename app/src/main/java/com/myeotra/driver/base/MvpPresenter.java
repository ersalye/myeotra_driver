package com.myeotra.driver.base;

import android.app.Activity;

import com.myeotra.driver.MvpApplication;

import java.util.HashMap;

public interface MvpPresenter<V extends MvpView> {

    Activity activity();

    MvpApplication appContext();

    void attachView(V mvpView);

    void detachView();

    void refreshToken();

    void logout(HashMap<String, Object> obj);

}
