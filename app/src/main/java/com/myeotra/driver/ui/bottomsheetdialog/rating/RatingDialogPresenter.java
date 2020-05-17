package com.myeotra.driver.ui.bottomsheetdialog.rating;

import android.util.Log;

import com.google.gson.Gson;
import com.myeotra.driver.base.BasePresenter;
import com.myeotra.driver.data.network.APIClient;

import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RatingDialogPresenter<V extends RatingDialogIView> extends BasePresenter<V> implements RatingDialogIPresenter<V> {
    private static final String TAG = "AAAA";

    @Override
    public void rate(HashMap<String, Object> obj, Integer id) {

        Log.e(TAG, "rate req : "+new Gson().toJson(obj)+" id "+id);
        getCompositeDisposable().add(APIClient
                .getAPIClient()
                .ratingRequest(obj, id)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getMvpView()::onSuccess, getMvpView()::onError));
    }

    public void getComment() {
        getCompositeDisposable().add(APIClient
                .getAPIClient()
                .getComment()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(getMvpView()::onSuccess, getMvpView()::onError));
    }
}
