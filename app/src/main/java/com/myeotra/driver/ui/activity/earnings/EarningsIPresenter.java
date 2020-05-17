package com.myeotra.driver.ui.activity.earnings;


import com.myeotra.driver.base.MvpPresenter;

public interface EarningsIPresenter<V extends EarningsIView> extends MvpPresenter<V> {

    void getEarnings();
}
