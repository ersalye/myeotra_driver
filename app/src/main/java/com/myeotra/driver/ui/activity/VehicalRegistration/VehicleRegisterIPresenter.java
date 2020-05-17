package com.myeotra.driver.ui.activity.VehicalRegistration;

import com.myeotra.driver.base.MvpPresenter;

import java.util.HashMap;

interface VehicleRegisterIPresenter<V extends VehicleRegisterIView> extends MvpPresenter<V> {
    void logout(HashMap<String, Object> obj);

    void getSettings();

    void addvehicle(HashMap<String, Object> param);
}
