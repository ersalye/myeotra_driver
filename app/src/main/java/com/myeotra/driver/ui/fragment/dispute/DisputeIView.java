package com.myeotra.driver.ui.fragment.dispute;

import com.myeotra.driver.base.MvpView;
import com.myeotra.driver.data.network.model.DisputeResponse;

import java.util.List;

public interface DisputeIView extends MvpView {

    void onSuccessDispute(List<DisputeResponse> responseList);

    void onSuccess(Object object);

    void onError(Throwable e);
}
