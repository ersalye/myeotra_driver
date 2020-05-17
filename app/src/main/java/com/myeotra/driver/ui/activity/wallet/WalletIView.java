package com.myeotra.driver.ui.activity.wallet;

import com.myeotra.driver.base.MvpView;
import com.myeotra.driver.data.network.model.WalletMoneyAddedResponse;
import com.myeotra.driver.data.network.model.WalletResponse;

public interface WalletIView extends MvpView {

    void onSuccess(WalletResponse response);

    void onSuccess(WalletMoneyAddedResponse response);

    void onError(Throwable e);
}
