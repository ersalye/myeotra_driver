package com.myeotra.driver.ui.activity.invite_friend;

import com.myeotra.driver.base.MvpView;
import com.myeotra.driver.data.network.model.UserResponse;

public interface InviteFriendIView extends MvpView {

    void onSuccess(UserResponse response);
    void onError(Throwable e);

}
