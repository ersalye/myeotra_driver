package com.myeotra.driver.ui.activity.help;


import com.myeotra.driver.base.MvpPresenter;

public interface HelpIPresenter<V extends HelpIView> extends MvpPresenter<V> {

    void getHelp();
}
