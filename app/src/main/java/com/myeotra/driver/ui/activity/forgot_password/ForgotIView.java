package com.myeotra.driver.ui.activity.forgot_password;

import com.myeotra.driver.base.MvpView;
import com.myeotra.driver.data.network.model.ForgotResponse;

public interface ForgotIView extends MvpView {

    void onSuccess(ForgotResponse forgotResponse);
    void onError(Throwable e);
}
