package com.myeotra.driver.ui.activity.add_card;

import com.myeotra.driver.base.MvpView;

public interface AddCardIView extends MvpView {

    void onSuccess(Object card);

    void onError(Throwable e);
}
