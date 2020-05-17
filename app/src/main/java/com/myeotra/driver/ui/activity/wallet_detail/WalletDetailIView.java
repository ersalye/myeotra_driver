package com.myeotra.driver.ui.activity.wallet_detail;

import com.myeotra.driver.base.MvpView;
import com.myeotra.driver.data.network.model.Transaction;

import java.util.ArrayList;

public interface WalletDetailIView extends MvpView {
    void setAdapter(ArrayList<Transaction> myList);
}
