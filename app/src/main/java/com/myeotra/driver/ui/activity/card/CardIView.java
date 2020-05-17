package com.myeotra.driver.ui.activity.card;

import com.myeotra.driver.base.MvpView;
import com.myeotra.driver.data.network.model.Card;

import java.util.List;

public interface CardIView extends MvpView {

    void onSuccess(Object card);

    void onSuccess(List<Card> cards);

    void onError(Throwable e);

    void onSuccessChangeCard(Object card);
    
}
