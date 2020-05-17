package com.myeotra.driver.ui.activity.help;

import com.myeotra.driver.base.MvpView;
import com.myeotra.driver.data.network.model.Help;

public interface HelpIView extends MvpView {

    void onSuccess(Help object);

    void onError(Throwable e);
}
