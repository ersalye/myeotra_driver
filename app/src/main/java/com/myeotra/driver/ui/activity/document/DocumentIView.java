package com.myeotra.driver.ui.activity.document;

import com.myeotra.driver.base.MvpView;
import com.myeotra.driver.data.network.model.DriverDocumentResponse;

public interface DocumentIView extends MvpView {

    void onSuccess(DriverDocumentResponse response);

    void onDocumentSuccess(DriverDocumentResponse response);

    void onError(Throwable e);

    void onSuccessLogout(Object object);

}
