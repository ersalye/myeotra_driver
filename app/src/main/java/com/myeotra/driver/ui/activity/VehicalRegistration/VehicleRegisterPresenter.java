package com.myeotra.driver.ui.activity.VehicalRegistration;

import com.myeotra.driver.base.BasePresenter;
import com.myeotra.driver.data.network.APIClient;

import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class VehicleRegisterPresenter<V extends VehicleRegisterIView> extends BasePresenter<V> implements VehicleRegisterIPresenter<V> {

    public void addvehicle(HashMap<String, Object> param) {
        getCompositeDisposable().add(APIClient
                .getAPIClient()
                .addVehicle(param)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getMvpView()::onSuccess, getMvpView()::onError));
    }

    @Override
    public void getSettings() {
        getCompositeDisposable().add(APIClient
                .getAPIClient()
                .getSettings()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getMvpView()::onSuccess, getMvpView()::onError));
    }
}
