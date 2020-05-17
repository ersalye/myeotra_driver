package com.myeotra.driver.ui.activity.main;

import android.util.Log;

import com.google.gson.Gson;
import com.myeotra.driver.base.BasePresenter;
import com.myeotra.driver.data.network.APIClient;

import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainPresenter<V extends MainIView> extends BasePresenter<V> implements MainIPresenter<V> {

    private String TAG = "MainPresenter";

    @Override
    public void getProfile() {
        getCompositeDisposable().add(APIClient
                .getAPIClient()
                .getProfile()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getMvpView()::onSuccess, getMvpView()::onError));
    }

    @Override
    public void logout(HashMap<String, Object> obj) {
        getCompositeDisposable().add(APIClient
                .getAPIClient()
                .logout(obj)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getMvpView()::onSuccessLogout, getMvpView()::onError));
    }

    @Override
    public void getTrip(HashMap<String, Object> params) {
        Log.e(TAG, "getTrip: " + new Gson().toJson(params));
        getCompositeDisposable().add(APIClient
                .getAPIClient()
                .getTrip(params)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getMvpView()::onSuccess, getMvpView()::onError));
    }

    @Override
    public void providerAvailable(HashMap<String, Object> obj) {

        Log.e(TAG, "providerAvailable: "+new Gson().toJson(obj) );
        getCompositeDisposable().add(APIClient.getAPIClient()
                .providerAvailable(obj)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getMvpView()::onSuccessProviderAvailable,
                        getMvpView()::onError));
    }

//    @Override
//    public void sendFCM(JsonObject jsonObject) {
//        getCompositeDisposable().add(APIClient
//                .getFcmRetrofit()
//                .create(ApiInterface.class)
//                .sendFcm(jsonObject)
//                .subscribeOn(Schedulers.computation())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(getMvpView()::onSuccessFCM, getMvpView()::onError));
//    }

    @Override
    public void getTripLocationUpdate(HashMap<String, Object> params) {
        Log.e(TAG, "getTripLocationUpdate: " + new Gson().toJson(params));
        getCompositeDisposable().add(APIClient
                .getAPIClient()
                .getTrip(params)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getMvpView()::onSuccessLocationUpdate, getMvpView()::onError));
    }

    @Override
    public void getSettings() {
        getCompositeDisposable().add(APIClient
                .getAPIClient()
                .getSettings()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getMvpView()::onSuccess, getMvpView()::onSettingError));
    }


}
