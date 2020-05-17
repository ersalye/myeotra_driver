package com.myeotra.driver.ui.activity.summary;


import com.myeotra.driver.base.MvpPresenter;

public interface SummaryIPresenter<V extends SummaryIView> extends MvpPresenter<V> {

    void getSummary();
}
