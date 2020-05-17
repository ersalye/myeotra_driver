package com.myeotra.driver.ui.activity.regsiter;

import android.util.Log;

import com.google.gson.Gson;
import com.myeotra.driver.base.BasePresenter;
import com.myeotra.driver.data.network.APIClient;

import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

public class RegisterPresenter<V extends RegisterIView> extends BasePresenter<V> implements RegisterIPresenter<V> {

    private static final String TAG = "AAAA";

    /*@Override
    public void register(@PartMap Map<String, RequestBody> params, @Part List<MultipartBody.Part> files) {
        Log.e(TAG, "register: req 1: "+new Gson().toJson(params));
        Log.e(TAG, "register: req 2: "+new Gson().toJson(files));
        getCompositeDisposable().add(
                APIClient
                        .getAPIClient()
                        .register(params, files)
                        .subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(getMvpView()::onSuccess, getMvpView()::onError));
    }*/

    @Override
    public void register(@PartMap Map<String, RequestBody> params, @Part MultipartBody.Part files) {
        Log.e(TAG, "register: req 1: "+new Gson().toJson(params));
        Log.e(TAG, "register: req 2: "+new Gson().toJson(files));
        getCompositeDisposable().add(
                APIClient
                        .getAPIClient()
                        .register(params, files)
                        .subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(getMvpView()::onSuccess, getMvpView()::onError));
    }


    @Override
    public void verifyEmail(String email) {
        getCompositeDisposable().add(
                APIClient
                        .getAPIClient()
                        .verifyEmail(email)
                        .subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(getMvpView()::onSuccess, getMvpView()::onVerifyEmailError));
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

    @Override
    public void verifyCredentials(String countryCode, String phoneNumber) {
        getCompositeDisposable().add(APIClient
                .getAPIClient()
                .verifyCredentials(countryCode,phoneNumber)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(getMvpView()::onSuccessPhoneNumber, getMvpView()::onVerifyPhoneNumberError));
    }
}
