package com.myeotra.driver.ui.activity.notification_manager;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.myeotra.driver.R;
import com.myeotra.driver.base.BaseActivity;
import com.myeotra.driver.data.network.model.NotificationManager;
import com.myeotra.driver.ui.adapter.NotificationAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NotificationManagerActivity extends BaseActivity implements NotificationManagerIView {

    @BindView(R.id.rvNotificationManager)
    RecyclerView rvNotificationManager;

    private NotificationManagerPresenter<NotificationManagerActivity> presenter = new NotificationManagerPresenter<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_notification_manager;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
        presenter.attachView(this);
//        setTitle(getString(R.string.notification_manager));
        presenter.getNotificationManager();
    }

    @Override
    public void onSuccess(List<NotificationManager> managers) {
        rvNotificationManager.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvNotificationManager.setAdapter(new NotificationAdapter(managers));
    }

    @Override
    public void onError(Throwable e) {
        hideLoading();
        if (e != null)
            onErrorBase(e);
    }

    @OnClick({R.id.menu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.menu:
                onBackPressed();
                break;
        }
    }
}
