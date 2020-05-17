package com.myeotra.driver.ui.activity.VehicalRegistration;

import com.myeotra.driver.base.MvpView;
import com.myeotra.driver.data.network.model.SettingsResponse;
import com.myeotra.driver.data.network.model.VehicleAddResponse;

public interface VehicleRegisterIView extends MvpView {

    void onSuccess(VehicleAddResponse response);

    void onSuccess(SettingsResponse response);

    void onError(Throwable e);

    void onSuccessLogout(Object object);
}
