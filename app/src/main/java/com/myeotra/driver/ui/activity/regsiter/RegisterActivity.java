package com.myeotra.driver.ui.activity.regsiter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.facebook.accountkit.Account;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.AccountKitCallback;
import com.facebook.accountkit.AccountKitError;
import com.facebook.accountkit.AccountKitLoginResult;
import com.facebook.accountkit.PhoneNumber;
import com.facebook.accountkit.ui.AccountKitActivity;
import com.facebook.accountkit.ui.AccountKitConfiguration;
import com.facebook.accountkit.ui.LoginType;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.myeotra.driver.BuildConfig;
import com.myeotra.driver.R;
import com.myeotra.driver.base.BaseActivity;
import com.myeotra.driver.common.Constants;
import com.myeotra.driver.common.SharedHelper;
import com.myeotra.driver.common.Utilities;
import com.myeotra.driver.data.network.model.ServiceType;
import com.myeotra.driver.data.network.model.SettingsResponse;
import com.myeotra.driver.data.network.model.User;
import com.myeotra.driver.ui.activity.VehicalRegistration.VehicleRegisterActivity;
import com.myeotra.driver.ui.activity.main.MainActivity;
import com.myeotra.driver.ui.activity.password.PasswordActivity;
import com.myeotra.driver.ui.countrypicker.Country;
import com.myeotra.driver.ui.countrypicker.CountryPicker;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;
import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import static com.myeotra.driver.common.Constants.APP_REQUEST_CODE;
import static com.myeotra.driver.common.Constants.MULTIPLE_PERMISSION;
import static com.myeotra.driver.common.Constants.RC_MULTIPLE_PERMISSION_CODE;
import static com.myeotra.driver.common.Constants.User.Account.VEHICLE;

public class RegisterActivity extends BaseActivity implements RegisterIView {

    @BindView(R.id.icBack)
    ImageView icBack;

    @BindView(R.id.txtEmail)
    EditText txtEmail;
    @BindView(R.id.txtFirstName)
    EditText txtFirstName;
    @BindView(R.id.txtLastName)
    EditText txtLastName;
    @BindView(R.id.txtPassword)
    EditText txtPassword;
    @BindView(R.id.txtConfirmPassword)
    EditText txtConfirmPassword;
    @BindView(R.id.chkTerms)
    CheckBox chkTerms;

    @BindView(R.id.txtSignin)
    TextView txtSignin;

    @BindView(R.id.countryImage)
    ImageView countryImage;
    @BindView(R.id.countryNumber)
    TextView countryNumber;
    @BindView(R.id.phoneNumber)
    EditText phoneNumber;


    @BindView(R.id.imgProfile)
    CircleImageView imgProfile;


    private String countryDialCode = "+91";
    private String countryCode = "IN";
    private CountryPicker mCountryPicker;

    private RegisterPresenter presenter;
    private int selected_pos = -1;
    private List<ServiceType> lstServiceTypes = new ArrayList<>();

    private boolean isEmailAvailable = true;
    private boolean isPhoneNumberAvailable = true;
    private String TAG = "AAAA";

    private File imgFile = null;

    @Override
    public int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void onResume() {
        txtSignin.setEnabled(true);

        super.onResume();
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
        presenter = new RegisterPresenter();
        presenter.attachView(this);
        setupSpinner(null);
        presenter.getSettings();


//        clickFunctions();

        setCountryList();

        if (SharedHelper.getKey(this, Constants.SharedPref.DEVICE_TOKEN).isEmpty()) {
            FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(task -> {
                if (!task.isSuccessful()) {
                    Log.w("PasswordActivity", "getInstanceId failed", task.getException());
                    return;
                }
                Log.d("FCM_TOKEN", task.getResult().getToken());
                SharedHelper.putKey(RegisterActivity.this, Constants.SharedPref.DEVICE_TOKEN, task.getResult().getToken());
            });
        }

        @SuppressLint("HardwareIds")
        String deviceId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

        Log.d("DEVICE_ID: ", deviceId);
        SharedHelper.putKeyFCM(RegisterActivity.this, Constants.SharedPref.DEVICE_ID, deviceId);


        txtEmail.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {

                isEmailAvailable = true;
                if (!TextUtils.isEmpty(txtEmail.getText().toString()))
                    if (Patterns.EMAIL_ADDRESS.matcher(txtEmail.getText().toString()).matches())
                        presenter.verifyEmail(txtEmail.getText().toString().trim());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
            }
        });


        phoneNumber.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {

                isPhoneNumberAvailable = true;
                if (!TextUtils.isEmpty(phoneNumber.getText().toString()))
                    presenter.verifyCredentials(countryDialCode, phoneNumber.getText().toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
            }
        });
    }


    /*private void clickFunctions() {
        txtEmail.setOnFocusChangeListener((v, hasFocus) -> {
            isEmailAvailable = true;
            if (!hasFocus && !TextUtils.isEmpty(txtEmail.getText().toString()))
                if (Patterns.EMAIL_ADDRESS.matcher(txtEmail.getText().toString()).matches())
                    presenter.verifyEmail(txtEmail.getText().toString().trim());
        });

        phoneNumber.setOnFocusChangeListener((v, hasFocus) -> {
            isPhoneNumberAvailable = true;
            *//*if (!hasFocus && !TextUtils.isEmpty(phoneNumber.getText().toString()))
                presenter.verifyCredentials(countryDialCode, phoneNumber.getText().toString());*//*
        });
    }*/

    @SuppressLint("CheckResult")
    @Override
    public void onSuccessPhoneNumber(Object object) {
        Toasty.success(this, getString(R.string.mobile_available));
        isPhoneNumberAvailable = false;
    }

    @Override
    public void onVerifyPhoneNumberError(Throwable e) {
        isPhoneNumberAvailable = true;
        showErrorMessage(phoneNumber, getString(R.string.mobile_number_already_exist));
    }

    @SuppressLint("CheckResult")
    @Override
    public void onSuccess(Object verifyEmail) {
        Toasty.success(this, getString(R.string.email_available));
        hideLoading();
        isEmailAvailable = false;
    }

    @Override
    public void onVerifyEmailError(Throwable e) {
        isEmailAvailable = true;
        showErrorMessage(txtEmail, getString(R.string.email_already_exist));
    }


    private void setCountryList() {
        mCountryPicker = CountryPicker.newInstance("Select Country");
        List<Country> countryList = Country.getAllCountries();
        Collections.sort(countryList, (s1, s2) -> s1.getName().compareToIgnoreCase(s2.getName()));
        mCountryPicker.setCountriesList(countryList);

        setListener();
    }

    private void setListener() {
        mCountryPicker.setListener((name, code, dialCode, flagDrawableResID) -> {
            countryNumber.setText(dialCode);
            countryDialCode = dialCode;
            countryImage.setImageResource(flagDrawableResID);
            mCountryPicker.dismiss();
        });

        countryImage.setOnClickListener(v -> mCountryPicker.show(getSupportFragmentManager(), "COUNTRY_PICKER"));

        countryNumber.setOnClickListener(v -> mCountryPicker.show(getSupportFragmentManager(), "COUNTRY_PICKER"));

        getUserCountryInfo();
    }

    private void getUserCountryInfo() {
        Country country = getDeviceCountry(RegisterActivity.this);


        countryImage.setImageResource(country.getFlag());
        countryNumber.setText(country.getDialCode());
        countryDialCode = country.getDialCode();
        countryCode = country.getCode();


    }

    private boolean validation() {
        if (txtEmail.getText().toString().isEmpty()) {
            Toasty.error(this, getString(R.string.invalid_email), Toast.LENGTH_SHORT, true).show();
            return false;
        } else if (txtFirstName.getText().toString().trim().isEmpty()) {
            Toasty.error(this, getString(R.string.invalid_first_name), Toast.LENGTH_SHORT, true).show();
            return false;
        } else if (txtLastName.getText().toString().trim().isEmpty()) {
            Toasty.error(this, getString(R.string.invalid_last_name), Toast.LENGTH_SHORT, true).show();
            return false;
        } /*else if (selected_pos == -1) {
            Toasty.error(this, getString(R.string.invalid_service_type), Toast.LENGTH_SHORT, true).show();
            return false;

        }*/ else if (phoneNumber.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, getString(R.string.invalid_phone_number), Toast.LENGTH_SHORT).show();
            return false;
        } else if (phoneNumber.getText().toString().trim().length() < 6) {
            Toast.makeText(this, getString(R.string.valid_phone_number), Toast.LENGTH_SHORT).show();
            return false;
        } else if (txtPassword.getText().toString().length() < 6) {
            Toasty.error(this, getString(R.string.invalid_password_length), Toast.LENGTH_SHORT, true).show();
            return false;
        } else if (txtConfirmPassword.getText().toString().trim().isEmpty()) {
            Toasty.error(this, getString(R.string.invalid_confirm_password), Toast.LENGTH_SHORT, true).show();
            return false;
        } else if (!txtPassword.getText().toString().equals(txtConfirmPassword.getText().toString())) {
            Toasty.error(this, getString(R.string.password_should_be_same), Toast.LENGTH_SHORT, true).show();
            return false;
        } else if (!chkTerms.isChecked()) {
            Toasty.error(this, getString(R.string.please_accept_terms_conditions), Toast.LENGTH_SHORT, true).show();
            return false;
        } else if (isEmailAvailable) {
            showErrorMessage(txtEmail, getString(R.string.email_already_exist));
            return false;
        } else if (isPhoneNumberAvailable) {
//            showErrorMessage(txtEmail, getString(R.string.email_already_exist));
            showErrorMessage(phoneNumber, getString(R.string.phone_already_exist));
            return false;
        } else if (imgFile == null) {
            Toasty.error(this, getString(R.string.profile_pic_upload), Toast.LENGTH_SHORT, true).show();
            return false;
        } else return true;
    }

    private void showErrorMessage(EditText view, String message) {
        Toasty.error(this, message, Toast.LENGTH_SHORT).show();
       /* view.requestFocus();
        view.setText(null);*/
    }

    private void register(String countryCode, String phoneNumber) {
        Map<String, RequestBody> map = new HashMap<>();
        map.put("first_name", toRequestBody(txtFirstName.getText().toString()));
        map.put("last_name", toRequestBody(txtLastName.getText().toString()));
        map.put("email", toRequestBody(txtEmail.getText().toString()));
        map.put("mobile", toRequestBody(phoneNumber));
        map.put("country_code", toRequestBody(countryCode));
        map.put("password", toRequestBody(txtPassword.getText().toString()));
        map.put("password_confirmation", toRequestBody(txtConfirmPassword.getText().toString()));
        map.put("device_token", toRequestBody(SharedHelper.getKeyFCM(this, Constants.SharedPref.DEVICE_TOKEN)));
        map.put("device_id", toRequestBody(SharedHelper.getKeyFCM(this, Constants.SharedPref.DEVICE_ID)));
        map.put("device_type", toRequestBody(BuildConfig.DEVICE_TYPE));

//        List<MultipartBody.Part> parts = new ArrayList<>();
////
////        MultipartBody.Part filePart = null;
////        if (imgFile != null)
////            try {
////                File compressedImageFile = new Compressor(this).compressToFile(imgFile);
////                filePart = MultipartBody.Part.createFormData("avatar", compressedImageFile.getName(),
////                        RequestBody.create(MediaType.parse("image*//*"), compressedImageFile));
////            } catch (IOException e) {
////                e.printStackTrace();
////            }
////
////        parts.add(filePart);


        MultipartBody.Part filePart = null;
        if (imgFile != null)
            try {
                File compressedImageFile = new Compressor(this).compressToFile(imgFile);
                filePart = MultipartBody.Part.createFormData("avatar", compressedImageFile.getName(),
                        RequestBody.create(MediaType.parse("image*//*"), compressedImageFile));
            } catch (IOException e) {
                e.printStackTrace();
            }
        showLoading();
        presenter.register(map, filePart);
    }

    @OnClick({R.id.next, R.id.icBack, R.id.chkTerms, R.id.txtSignin, R.id.imgProfile})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.next:
                if (validation()) if (Utilities.isConnected()) {
//                    fbPhoneLogin(countryCode, countryDialCode, phoneNumber.getText().toString());

                    register(countryNumber.getText().toString().trim(), phoneNumber.getText().toString().trim());

                    SharedHelper.putKey(RegisterActivity.this, Constants.SharedPref.DIAL_CODE,
                            countryNumber.getText().toString().trim());
                    SharedHelper.putKey(RegisterActivity.this, Constants.SharedPref.MOBILE,
                            phoneNumber.getText().toString().trim());
                } else {
                    showAToast(getString(R.string.no_internet_connection));
                }
                break;
            case R.id.chkTerms:
                showTermsConditionsDialog();
                break;
            case R.id.icBack:
                onBackPressed();
                break;
            case R.id.txtSignin:
                txtSignin.setEnabled(false);
                startActivity(new Intent(this, PasswordActivity.class));
                finish();
                break;

            case R.id.imgProfile:
                MultiplePermissionTask();
                break;
        }
    }

    private boolean hasMultiplePermission() {
        return EasyPermissions.hasPermissions(this, MULTIPLE_PERMISSION);
    }

    @AfterPermissionGranted(RC_MULTIPLE_PERMISSION_CODE)
    void MultiplePermissionTask() {
        if (hasMultiplePermission()) pickImage();
        else EasyPermissions.requestPermissions(
                this, getString(R.string.please_accept_permission),
                RC_MULTIPLE_PERMISSION_CODE,
                MULTIPLE_PERMISSION);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }


    private void showTermsConditionsDialog() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(getText(R.string.terms_and_conditions));
        WebView wv = new WebView(this);
        wv.loadUrl(BuildConfig.TERMS_CONDITIONS);
        wv.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        alert.setView(wv);
        alert.setNegativeButton("Close", (dialog, id) -> dialog.dismiss());
        alert.show();
    }

    @Override
    public void onSuccess(User user) {

        Log.e(TAG, " res : register " + new Gson().toJson(user));
        hideLoading();

        if (user.getDStatus() == 0) {
//            Toasty.error(this, user.getDMessage(), Toast.LENGTH_SHORT, true).show();
        } else {
            SharedHelper.putKey(this, Constants.SharedPref.USER_ID, String.valueOf(user.getId()));
            SharedHelper.putKey(this, Constants.SharedPref.ACCESS_TOKEN, user.getAccessToken());
            SharedHelper.putKey(this, Constants.SharedPref.LOGGGED_IN, "true");
            Toasty.success(this, getString(R.string.register_success), Toast.LENGTH_SHORT, true).show();

            if (user.getStatus().equalsIgnoreCase(VEHICLE)) {
                startActivity(new Intent(this, VehicleRegisterActivity.class));
                finish();
            } else {
                startActivity(new Intent(this, MainActivity.class));
                finish();
            }
        }


    }


    @Override
    public void onSuccess(SettingsResponse response) {

        Log.e(TAG, " res : settings " + new Gson().toJson(response));

        lstServiceTypes = response.getServiceTypes();
        setupSpinner(response);
    }

    private void setupSpinner(@Nullable SettingsResponse response) {
        ArrayList<String> lstNames = new ArrayList<>(response != null ? response.getServiceTypes().size() : 0);
        if (response != null) for (ServiceType serviceType : response.getServiceTypes())
            lstNames.add(serviceType.getName());

        Log.e(TAG, "setupSpinner: " + new Gson().toJson(lstNames));
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, lstNames);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinnerServiceType.setAdapter(dataAdapter);
    }

    @Override
    public void onError(Throwable e) {

        Log.e(TAG, "onError: " + e.getMessage());
        hideLoading();
        if (e != null)
            onErrorBase(e);
    }

    public void fbPhoneLogin(String strCountryCode, String strCountryISOCode, String strPhoneNumber) {
        final Intent intent = new Intent(this, AccountKitActivity.class);
        AccountKitConfiguration.AccountKitConfigurationBuilder mBuilder =
                new AccountKitConfiguration.AccountKitConfigurationBuilder(
                        LoginType.PHONE, AccountKitActivity.ResponseType.TOKEN);
        mBuilder.setReadPhoneStateEnabled(true);
        mBuilder.setReceiveSMS(true);
        PhoneNumber phoneNumber = new PhoneNumber(strCountryISOCode, strPhoneNumber, strCountryCode);
        intent.putExtra(AccountKitActivity.ACCOUNT_KIT_ACTIVITY_CONFIGURATION,
                mBuilder.setInitialPhoneNumber(phoneNumber).
                        build());
        startActivityForResult(intent, APP_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        EasyImage.handleActivityResult(requestCode, resultCode, data, RegisterActivity.this,
                new DefaultCallback() {
                    @Override
                    public void onImagesPicked(@NonNull List<File> imageFiles,
                                               EasyImage.ImageSource source, int type) {
                        imgFile = imageFiles.get(0);
                        Glide.with(activity())
                                .load(Uri.fromFile(imgFile))
                                .apply(RequestOptions
                                        .placeholderOf(R.drawable.ic_user_placeholder)
                                        .dontAnimate()
                                        .error(R.drawable.ic_user_placeholder))
                                .into(imgProfile);
                    }
                });


        if (requestCode == APP_REQUEST_CODE && data != null) {
            AccountKitLoginResult loginResult = data.getParcelableExtra(AccountKitLoginResult.RESULT_KEY);
            if (!loginResult.wasCancelled()) {
                AccountKit.getCurrentAccount(new AccountKitCallback<Account>() {
                    @Override
                    public void onSuccess(Account account) {
                        Log.d("AccountKit", "onSuccess: Account Kit" + AccountKit.getCurrentAccessToken().getToken());
                        if (AccountKit.getCurrentAccessToken().getToken() != null) {
                            PhoneNumber phoneNumber = account.getPhoneNumber();
                            SharedHelper.putKey(RegisterActivity.this, Constants.SharedPref.DIAL_CODE,
                                    "+" + phoneNumber.getCountryCode());
                            SharedHelper.putKey(RegisterActivity.this, Constants.SharedPref.MOBILE,
                                    phoneNumber.getPhoneNumber());


                            /*register(SharedHelper.getKey(RegisterActivity.this, Constants.SharedPref.DIAL_CODE),
                                    SharedHelper.getKey(RegisterActivity.this, Constants.SharedPref.MOBILE));*/
                        }
                    }

                    @Override
                    public void onError(AccountKitError accountKitError) {
                        Log.e("AccountKit", "onError: Account Kit" + accountKitError);
                    }
                });
            }
        }
    }


}
