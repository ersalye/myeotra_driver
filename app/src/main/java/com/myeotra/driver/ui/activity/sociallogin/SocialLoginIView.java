package com.myeotra.driver.ui.activity.sociallogin;

import com.myeotra.driver.base.MvpView;
import com.myeotra.driver.data.network.model.Token;

public interface SocialLoginIView extends MvpView {

    void onSuccess(Token token);
    void onError(Throwable e);
}
