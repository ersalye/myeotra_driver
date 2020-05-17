package com.myeotra.driver.ui.bottomsheetdialog.invoice_flow;

import android.util.Log;

import com.google.gson.Gson;
import com.myeotra.driver.base.BasePresenter;
import com.myeotra.driver.data.network.APIClient;

import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class InvoiceDialogPresenter<V extends InvoiceDialogIView> extends BasePresenter<V> implements InvoiceDialogIPresenter<V> {

    private static final String TAG = "AAAA";

    @Override
    public void statusUpdate(HashMap<String, Object> obj, Integer id) {
        Log.e("data", "statusUpdate : "+new Gson().toJson(obj)+" id "+id);
        getCompositeDisposable().add(APIClient
                .getAPIClient()
                .updateRequest(obj, id)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getMvpView()::onSuccess, getMvpView()::onError));
    }
}
