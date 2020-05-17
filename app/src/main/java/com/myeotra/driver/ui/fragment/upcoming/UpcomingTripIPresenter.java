package com.myeotra.driver.ui.fragment.upcoming;


import com.myeotra.driver.base.MvpPresenter;

public interface UpcomingTripIPresenter<V extends UpcomingTripIView> extends MvpPresenter<V> {

    void getUpcoming();

}
