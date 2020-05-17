package com.myeotra.driver.ui.bottomsheetdialog.invoice_flow;

import com.myeotra.driver.base.MvpView;

public interface InvoiceDialogIView extends MvpView {

    void onSuccess(Object object);
    void onError(Throwable e);
}
