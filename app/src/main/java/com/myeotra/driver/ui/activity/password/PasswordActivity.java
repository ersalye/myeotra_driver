package com.myeotra.driver.ui.activity.password;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.gson.Gson;
import com.myeotra.driver.BuildConfig;
import com.myeotra.driver.R;
import com.myeotra.driver.base.BaseActivity;
import com.myeotra.driver.common.Constants;
import com.myeotra.driver.common.SharedHelper;
import com.myeotra.driver.common.Utilities;
import com.myeotra.driver.data.network.model.ForgotResponse;
import com.myeotra.driver.data.network.model.User;
import com.myeotra.driver.ui.activity.VehicalRegistration.VehicleRegisterActivity;
import com.myeotra.driver.ui.activity.add_card.AddCardActivity;
import com.myeotra.driver.ui.activity.document.DocumentActivity;
import com.myeotra.driver.ui.activity.main.MainActivity;
import com.myeotra.driver.ui.activity.regsiter.RegisterActivity;
import com.myeotra.driver.ui.activity.reset_password.ResetActivity;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

import static com.myeotra.driver.common.Constants.User.Account.PENDING_CARD;
import static com.myeotra.driver.common.Constants.User.Account.PENDING_DOCUMENT;
import static com.myeotra.driver.common.Constants.User.Account.VEHICLE;


public class PasswordActivity extends BaseActivity implements PasswordIView {

    PasswordPresenter presenter = new PasswordPresenter();

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.password)
    TextInputEditText password;

    @BindView(R.id.email)
    TextInputEditText email;


    @BindView(R.id.sign_up)
    TextView sign_up;
    @BindView(R.id.forgot_password)
    TextView forgotPassword;
    @BindView(R.id.next)
    Button next;
    //    String email = "";
    public static String TAG = "AAAA";

    @Override
    public int getLayoutId() {
        return R.layout.activity_password;
    }

    @Override
    protected void onResume() {

        sign_up.setEnabled(true);
        super.onResume();
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
        presenter.attachView(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
//            email = extras.getString(Constants.SharedPref.EMAIL);
//            Utilities.printV("EMAIL===>", email);
            Utilities.printV("EMAIL===>", SharedHelper.getKeyFCM(this, Constants.SharedPref.DEVICE_TOKEN));
            Utilities.printV("EMAIL===>", SharedHelper.getKeyFCM(this, Constants.SharedPref.DEVICE_ID));
        }
        if (BuildConfig.DEBUG) password.setText("123456");


        if (SharedHelper.getKeyFCM(this, Constants.SharedPref.DEVICE_TOKEN).isEmpty()) {
            FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(task -> {
                if (!task.isSuccessful()) {
                    Log.w("PasswordActivity", "getInstanceId failed", task.getException());
                    return;
                }
                Log.d("FCM_TOKEN", task.getResult().getToken());

                SharedHelper.putKeyFCM(PasswordActivity.this, Constants.SharedPref.DEVICE_TOKEN, task.getResult().getToken());
            });
        }

        @SuppressLint("HardwareIds")
        String deviceId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

        Log.d("DEVICE_ID: ", deviceId);
        SharedHelper.putKeyFCM(this, Constants.SharedPref.DEVICE_ID, deviceId);


    }

    @OnClick({R.id.back, R.id.sign_up, R.id.forgot_password, R.id.next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                activity().onBackPressed();
                break;
            case R.id.sign_up:
                sign_up.setEnabled(false);
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            case R.id.forgot_password:

                if (email.getText().toString().isEmpty()) {
                    Toast.makeText(this, "Please Enter Email Address", Toast.LENGTH_SHORT).show();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
                    Toast.makeText(this, getString(R.string.invalid_email_address), Toast.LENGTH_SHORT).show();
                } else {
                    showLoading();
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("email", email.getText().toString().trim());
                    presenter.forgot(map);
                }
//                startActivity(new Intent(this, ForgotActivity.class));
                break;
            case R.id.next:
                login();
                break;
            default:
                break;
        }
    }

    private void login() {
        if (email.getText().toString().isEmpty()) {
            Toasty.error(this, getString(R.string.invalid_email), Toast.LENGTH_SHORT, true).show();
            return;
        }
        if (password.getText().toString().isEmpty()) {
            Toasty.error(this, getString(R.string.invalid_password), Toast.LENGTH_SHORT, true).show();
            return;
        }

        deviceToken = SharedHelper.getKeyFCM(this, Constants.SharedPref.DEVICE_TOKEN);
        deviceId = SharedHelper.getKeyFCM(this, Constants.SharedPref.DEVICE_ID);


        if (Utilities.isConnected()) {
            if (TextUtils.isEmpty(deviceToken))
                FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(this, new OnSuccessListener<InstanceIdResult>() {
                    @Override
                    public void onSuccess(InstanceIdResult instanceIdResult) {
                        deviceToken = instanceIdResult.getToken();
                        SharedHelper.putKeyFCM(PasswordActivity.this, Constants.SharedPref.DEVICE_TOKEN, deviceToken);
                    }
                });

            if (TextUtils.isEmpty(deviceId)) {
                deviceId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
                SharedHelper.putKeyFCM(this, Constants.SharedPref.DEVICE_ID, deviceId);
            }

            showLoading();
            HashMap<String, Object> map = new HashMap<>();
            map.put("email", email.getText().toString().trim());
            map.put("password", password.getText().toString().trim());
            map.put("device_id", deviceId);
            map.put("device_type", BuildConfig.DEVICE_TYPE);
            map.put("device_token", deviceToken);
            presenter.login(map);
        } else {
            showAToast(getString(R.string.no_internet_connection));
        }

    }

    @Override
    public void onSuccess(ForgotResponse forgotResponse) {
        Log.e(TAG, "forgotpass res : " + new Gson().toJson(forgotResponse));
        hideLoading();
        if (forgotResponse.getDStatus() == 0) {
            Toasty.error(this, forgotResponse.getDMessage(), Toast.LENGTH_SHORT, true).show();
        } else {
            SharedHelper.putKey(this, Constants.SharedPref.TXT_EMAIL, email.getText().toString().trim());
            SharedHelper.putKey(this, Constants.SharedPref.OTP, String.valueOf(forgotResponse.getProvider().getOtp()));
            SharedHelper.putKey(this, Constants.SharedPref.ID, String.valueOf(forgotResponse.getProvider().getId()));
            Toasty.success(this, forgotResponse.getMessage(), Toast.LENGTH_SHORT, true).show();
            startActivity(new Intent(this, ResetActivity.class));
        }


    }

    @Override
    public void onSuccess(User user) {
        hideLoading();
        Log.e(TAG, " login res: " + new Gson().toJson(user));

        if (user.getDStatus() == 0) {
//            Toasty.error(activity(), user.getDMessage(), Toast.LENGTH_SHORT).show();
        } else {

            SharedHelper.putKey(this, Constants.SharedPref.ACCESS_TOKEN, user.getAccessToken());
            SharedHelper.putKey(this, Constants.SharedPref.USER_ID, String.valueOf(user.getId()));
            SharedHelper.putKey(this, Constants.SharedPref.LOGGGED_IN, "true");
            Toasty.success(activity(), getString(R.string.login_out_success), Toast.LENGTH_SHORT).show();

            if (user.getStatus().equalsIgnoreCase(VEHICLE)) {
                startActivity(new Intent(this, VehicleRegisterActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
            } else if (user.getStatus().equalsIgnoreCase(PENDING_DOCUMENT)) {
                startActivity(new Intent(this, DocumentActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
            } else if (user.getStatus().equalsIgnoreCase(PENDING_CARD)) {
                startActivity(new Intent(this, AddCardActivity.class));
            } else {
                startActivity(new Intent(this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
            }
        }


    }

    @Override
    public void onError(Throwable e) {

        Log.e(TAG, "password onError : " + e.getMessage());
        hideLoading();
        if (e != null)
            onErrorBase(e);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }
}
