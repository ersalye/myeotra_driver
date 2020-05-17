package com.myeotra.driver.ui.activity.notification_manager;

import com.myeotra.driver.base.MvpPresenter;

public interface NotificationManagerIPresenter<V extends NotificationManagerIView> extends MvpPresenter<V> {
    void getNotificationManager();
}
