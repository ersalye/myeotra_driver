package com.myeotra.driver.ui.activity.instant_ride;

import com.myeotra.driver.base.MvpView;
import com.myeotra.driver.data.network.model.EstimateFare;
import com.myeotra.driver.data.network.model.TripResponse;

public interface InstantRideIView extends MvpView {

    void onSuccess(EstimateFare estimateFare);

    void onSuccess(TripResponse response);

    void onError(Throwable e);

}
