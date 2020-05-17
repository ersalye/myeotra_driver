package com.myeotra.driver.ui.activity.request_money;

import com.myeotra.driver.base.MvpView;
import com.myeotra.driver.data.network.model.RequestDataResponse;

public interface RequestMoneyIView extends MvpView {

    void onSuccess(RequestDataResponse response);
    void onSuccess(Object response);
    void onError(Throwable e);

}
