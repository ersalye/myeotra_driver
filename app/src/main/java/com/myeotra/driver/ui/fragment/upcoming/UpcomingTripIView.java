package com.myeotra.driver.ui.fragment.upcoming;

import com.myeotra.driver.base.MvpView;
import com.myeotra.driver.data.network.model.HistoryList;

import java.util.List;

public interface UpcomingTripIView extends MvpView {

    void onSuccess(List<HistoryList> historyList);
    void onError(Throwable e);
}
