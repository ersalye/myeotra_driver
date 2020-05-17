package com.myeotra.driver.ui.activity.wallet;

import com.myeotra.driver.base.MvpPresenter;

import java.util.HashMap;

public interface WalletIPresenter<V extends WalletIView> extends MvpPresenter<V> {

    void getWalletData();
    void addMoney(HashMap<String, Object> obj);
}
