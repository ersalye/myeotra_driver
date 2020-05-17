package com.myeotra.driver.ui.activity.main;

import com.myeotra.driver.base.MvpView;
import com.myeotra.driver.data.network.model.SettingsResponse;
import com.myeotra.driver.data.network.model.TripResponse;
import com.myeotra.driver.data.network.model.UserResponse;

public interface MainIView extends MvpView {
    void onSuccess(UserResponse user);

//    void onlogoutError(Throwable e);

    void onError(Throwable e);

//    void ongetTripLocationUpdateError(Throwable e);

//    void ongetTripError(Throwable e);

//    void ongetProfileError(Throwable e);

    void onSuccessLogout(Object object);

    void onSuccess(TripResponse tripResponse);

    void onSuccess(SettingsResponse response);

    void onSettingError(Throwable e);

    void onSuccessProviderAvailable(Object object);

    void onSuccessFCM(Object object);

    void onSuccessLocationUpdate(TripResponse tripResponse);

}
