package com.myeotra.driver.ui.bottomsheetdialog.rating;

import com.myeotra.driver.base.MvpView;
import com.myeotra.driver.data.network.model.RateCommentResponse;
import com.myeotra.driver.data.network.model.Rating;

public interface RatingDialogIView extends MvpView {

    void onSuccess(Rating rating);

    void onSuccess(RateCommentResponse object);

    void onError(Throwable e);
}
