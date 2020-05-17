package com.myeotra.driver.ui.activity.upcoming_detail;


import com.myeotra.driver.base.MvpView;
import com.myeotra.driver.data.network.model.HistoryDetail;

public interface UpcomingTripDetailIView extends MvpView {

    void onSuccess(HistoryDetail historyDetail);
    void onError(Throwable e);
}
