package com.myeotra.driver.ui.bottomsheetdialog.rating;

import com.myeotra.driver.base.MvpPresenter;

import java.util.HashMap;

public interface RatingDialogIPresenter<V extends RatingDialogIView> extends MvpPresenter<V> {

    void rate(HashMap<String, Object> obj, Integer id);
}
