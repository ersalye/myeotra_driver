package com.myeotra.driver.ui.activity.wallet_detail;

import com.myeotra.driver.base.MvpPresenter;
import com.myeotra.driver.data.network.model.Transaction;

import java.util.ArrayList;

public interface WalletDetailIPresenter<V extends WalletDetailIView> extends MvpPresenter<V> {
    void setAdapter(ArrayList<Transaction> myList);
}
