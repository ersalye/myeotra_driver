package com.myeotra.driver.ui.fragment.status_flow;

import com.myeotra.driver.base.MvpView;
import com.myeotra.driver.data.network.model.TimerResponse;

public interface StatusFlowIView extends MvpView {

    void onSuccess(Object object);

    void onWaitingTimeSuccess(TimerResponse object);

    void onError(Throwable e);
}
