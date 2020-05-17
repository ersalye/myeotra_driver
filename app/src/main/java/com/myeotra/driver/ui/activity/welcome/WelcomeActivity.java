package com.myeotra.driver.ui.activity.welcome;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.myeotra.driver.R;
import com.myeotra.driver.base.BaseActivity;
import com.myeotra.driver.common.Constants;
import com.myeotra.driver.common.SharedHelper;
import com.myeotra.driver.common.Utilities;
import com.myeotra.driver.ui.activity.password.PasswordActivity;
import com.myeotra.driver.ui.activity.regsiter.RegisterActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WelcomeActivity extends BaseActivity implements
        WelcomeIView {


    @BindView(R.id.sign_in)
    Button sign_in;
    @BindView(R.id.textCreateaccount)
    TextView textCreateaccount;


    @Override
    public int getLayoutId() {
        return R.layout.activity_welcome;
    }

    @Override
    public void initView() {

        ButterKnife.bind(this);

        WelcomePresenter presenter = new WelcomePresenter();
        presenter.attachView(this);


        Utilities.printV("TOKEN===>", SharedHelper.getKeyFCM(this, Constants.SharedPref.DEVICE_TOKEN));
        Utilities.printV("TOKEN ID===>", SharedHelper.getKeyFCM(this, Constants.SharedPref.DEVICE_ID));
    }


    @OnClick({R.id.sign_in, R.id.textCreateaccount})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.sign_in:
                sign_in.setEnabled(false);
                startActivity(new Intent(this, PasswordActivity.class));
                break;
            case R.id.textCreateaccount:
                textCreateaccount.setEnabled(false);
                startActivity(new Intent(this, RegisterActivity.class));
                break;

        }
    }


    @Override
    protected void onResume() {
        sign_in.setEnabled(true);
        textCreateaccount.setEnabled(true);

        super.onResume();
    }
}
