package com.myeotra.driver.ui.fragment.offline;

import com.myeotra.driver.base.MvpView;

public interface OfflineIView extends MvpView {

    void onSuccess(Object object);
    void onError(Throwable e);
}
