package com.myeotra.driver.ui.activity.upcoming_detail;


import com.myeotra.driver.base.MvpPresenter;

public interface UpcomingTripDetailIPresenter<V extends UpcomingTripDetailIView> extends MvpPresenter<V> {

    void getUpcomingDetail(String request_id);

}
