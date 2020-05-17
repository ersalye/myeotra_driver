package com.myeotra.driver.ui.activity.notification_manager;

import com.myeotra.driver.base.MvpView;
import com.myeotra.driver.data.network.model.NotificationManager;

import java.util.List;

public interface NotificationManagerIView extends MvpView {

    void onSuccess(List<NotificationManager> managers);

    void onError(Throwable e);

}