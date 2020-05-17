package com.myeotra.driver.ui.activity.email;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.myeotra.driver.R;
import com.myeotra.driver.base.BaseActivity;
import com.myeotra.driver.common.Constants;
import com.myeotra.driver.common.SharedHelper;
import com.myeotra.driver.ui.activity.password.PasswordActivity;
import com.myeotra.driver.ui.activity.regsiter.RegisterActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

public class EmailActivity extends BaseActivity implements EmailIView {

    @BindView(R.id.txtEmail)
    TextInputEditText txtEmail;
    @BindView(R.id.sign_up)
    TextView signUp;
    @BindView(R.id.next)
    Button next;

    EmailIPresenter presenter = new EmailPresenter();

    @Override
    public int getLayoutId() {
        return R.layout.activity_email;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
        presenter.attachView(this);
//        if (BuildConfig.DEBUG) email.setText("divyesh@taxi.com");
    }

    @OnClick({R.id.back, R.id.sign_up, R.id.next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                activity().onBackPressed();
                break;
            case R.id.sign_up:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            case R.id.next:
                if (txtEmail.getText().toString().isEmpty()) {
                    Toasty.error(this, getString(R.string.invalid_email), Toast.LENGTH_SHORT, true).show();
                    return;
                }
                Intent i = new Intent(this, PasswordActivity.class);
                i.putExtra(Constants.SharedPref.EMAIL, txtEmail.getText().toString());
                SharedHelper.putKey(this, Constants.SharedPref.TXT_EMAIL, txtEmail.getText().toString());
                startActivity(i);
                break;
        }
    }
}
