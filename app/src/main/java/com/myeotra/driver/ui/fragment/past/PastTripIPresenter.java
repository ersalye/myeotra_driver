package com.myeotra.driver.ui.fragment.past;


import com.myeotra.driver.base.MvpPresenter;

public interface PastTripIPresenter<V extends PastTripIView> extends MvpPresenter<V> {

    void getHistory();

}
