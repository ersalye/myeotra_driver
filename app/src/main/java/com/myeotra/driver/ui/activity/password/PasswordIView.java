package com.myeotra.driver.ui.activity.password;

import com.myeotra.driver.base.MvpView;
import com.myeotra.driver.data.network.model.ForgotResponse;
import com.myeotra.driver.data.network.model.User;

public interface PasswordIView extends MvpView {

    void onSuccess(ForgotResponse forgotResponse);

    void onSuccess(User object);

    void onError(Throwable e);
}
