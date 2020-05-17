package com.myeotra.driver.ui.activity.summary;


import com.myeotra.driver.base.MvpView;
import com.myeotra.driver.data.network.model.Summary;

public interface SummaryIView extends MvpView {

    void onSuccess(Summary object);

    void onError(Throwable e);
}
