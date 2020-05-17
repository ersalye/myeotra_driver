package com.myeotra.driver.ui.fragment.past;


import com.myeotra.driver.base.MvpView;
import com.myeotra.driver.data.network.model.HistoryList;

import java.util.List;

public interface PastTripIView extends MvpView {

    void onSuccess(List<HistoryList> historyList);
    void onError(Throwable e);
}
