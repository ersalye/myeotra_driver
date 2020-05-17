package com.myeotra.driver.ui.activity.earnings;


import com.myeotra.driver.base.MvpView;
import com.myeotra.driver.data.network.model.EarningsList;

public interface EarningsIView extends MvpView {

    void onSuccess(EarningsList earningsLists);

    void onError(Throwable e);
}
