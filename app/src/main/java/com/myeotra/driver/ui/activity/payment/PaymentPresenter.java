package com.myeotra.driver.ui.activity.payment;

import com.myeotra.driver.base.BasePresenter;
import com.myeotra.driver.data.network.APIClient;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class PaymentPresenter<V extends PaymentIView> extends BasePresenter<V> implements PaymentIPresenter<V> {

    @Override
    public void deleteCard(String cardId) {

        getCompositeDisposable().add(APIClient.getAPIClient().deleteCard(cardId, "DELETE")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(deleteResponse -> getMvpView().onSuccess(deleteResponse),
                        throwable -> getMvpView().onError(throwable)));
    }

    @Override
    public void card() {
        getCompositeDisposable().add(APIClient.getAPIClient()
                .card()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(cards -> getMvpView().onSuccess(cards),
                        throwable -> getMvpView().onError(throwable)));
    }

    @Override
    public void addCard(String cardId) {
        getCompositeDisposable().add(APIClient
                .getAPIClient()
                .addcard(cardId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(getMvpView()::onAddCardSuccess, getMvpView()::onError));
    }
}
