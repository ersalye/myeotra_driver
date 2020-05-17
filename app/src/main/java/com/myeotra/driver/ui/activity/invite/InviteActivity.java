package com.myeotra.driver.ui.activity.invite;

import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.myeotra.driver.R;
import com.myeotra.driver.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class InviteActivity extends BaseActivity implements InviteIView {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.call)
    Button call;

    @BindView(R.id.menu)
    ImageView menu;

    InvitePresenter presenter = new InvitePresenter();

    @Override
    public int getLayoutId() {
        return R.layout.activity_invite;
    }

    @Override
    public void initView() {

        ButterKnife.bind(this);
        presenter.attachView(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //do whatever
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @OnClick({R.id.menu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.menu:
                onBackPressed();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
