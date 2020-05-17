package com.myeotra.driver.ui.activity.VehicalRegistration;

import android.app.AlertDialog;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatSpinner;

import com.google.gson.Gson;
import com.myeotra.driver.R;
import com.myeotra.driver.base.BaseActivity;
import com.myeotra.driver.common.Constants;
import com.myeotra.driver.common.SharedHelper;
import com.myeotra.driver.common.Utilities;
import com.myeotra.driver.data.network.model.ServiceType;
import com.myeotra.driver.data.network.model.SettingsResponse;
import com.myeotra.driver.data.network.model.VehicleAddResponse;
import com.myeotra.driver.ui.activity.document.DocumentActivity;
import com.myeotra.driver.ui.activity.main.MainActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Nullable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

import static com.myeotra.driver.common.Constants.User.Account.PENDING_DOCUMENT;

public class VehicleRegisterActivity extends BaseActivity implements VehicleRegisterIView {

    private static final String TAG = "AAAA";
    @BindView(R.id.next)
    Button next;

    @BindView(R.id.tvskip)
    TextView tvskip;

    @BindView(R.id.spinnerServiceType)
    AppCompatSpinner spinnerServiceType;

    @BindView(R.id.edtvehiclemake)
    EditText edtvehiclemake;

    @BindView(R.id.edtvehiclemodel)
    EditText edtvehiclemodel;

    @BindView(R.id.edtvehiclecolor)
    EditText edtvehiclecolor;

    @BindView(R.id.edtvehiclenumber)
    EditText edtvehiclenumber;

    @BindView(R.id.edtinsurancenumber)
    EditText edtinsurancenumber;
    private VehicleRegisterPresenter presenter = new VehicleRegisterPresenter();

    private List<ServiceType> lstServiceTypes = new ArrayList<>();
    private int selected_pos = -1;
    private String setting;
    private boolean isFromSettings = false;


    @Override
    public int getLayoutId() {
        return R.layout.activity_vehical_register;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
        presenter.attachView(this);
        presenter.getSettings();

        try {
            setting = getIntent().getStringExtra("setting");
            isFromSettings = getIntent().getExtras().getBoolean("isFromSettings");
        } catch (Exception e) {
            e.printStackTrace();
            isFromSettings = false;
        }


        spinnerServiceType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected_pos = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @Override
    public void onSuccess(VehicleAddResponse response) {

        Log.e(TAG, "vehical add res : " + new Gson().toJson(response));
        SharedHelper.putKey(this, Constants.SharedPref.USER_ID, String.valueOf(response.getId()));
//        SharedHelper.putKey(this, Constants.SharedPref.ACCESS_TOKEN, response.getAccessToken());
        SharedHelper.putKey(this, Constants.SharedPref.LOGGGED_IN, "true");
//        Toasty.success(this, response.getMessage(), Toast.LENGTH_SHORT, true).show();
        if (response.getStatus().equalsIgnoreCase(PENDING_DOCUMENT)) {
            startActivity(new Intent(this, DocumentActivity.class));
            finish();
        } else {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }


    }

    @Override
    public void onSuccess(SettingsResponse response) {

        lstServiceTypes = response.getServiceTypes();
        setupSpinner(response);

    }


    @Override
    public void onError(Throwable e) {

        Log.e(TAG, "onError: " + e.getMessage());

    }


    private void setupSpinner(@Nullable SettingsResponse response) {
        ArrayList<String> lstNames = new ArrayList<>(response != null ? response.getServiceTypes().size() : 0);
        if (response != null) for (ServiceType serviceType : response.getServiceTypes())
            lstNames.add(serviceType.getName());

        Log.e(TAG, "setupSpinner: " + new Gson().toJson(lstNames));
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, lstNames);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerServiceType.setAdapter(dataAdapter);
    }



    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehical_register);
    }*/

    @OnClick({R.id.next, R.id.icBack})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.next:
                if (validation()) if (Utilities.isConnected()) {

                    HashMap<String, Object> param = new HashMap<>();
                    param.put("service_type", lstServiceTypes.get(selected_pos).getId() + "");
                    param.put("service_number", edtvehiclenumber.getText().toString().trim());
                    param.put("service_model", edtvehiclemodel.getText().toString().trim());
                    param.put("insurance_number", edtinsurancenumber.getText().toString().trim());
                    param.put("service_make", edtvehiclemake.getText().toString().trim());
                    param.put("service_color", edtvehiclecolor.getText().toString().trim());
                    presenter.addvehicle(param);


                } else {
                    showAToast(getString(R.string.no_internet_connection));
                }
                break;

            case R.id.icBack:
                onBackPressed();
                break;
        }
    }

    private boolean validation() {
        if (selected_pos == -1) {
            Toasty.error(this, getString(R.string.invalid_service_type), Toast.LENGTH_SHORT, true).show();
            return false;
        } else if (edtvehiclemake.getText().toString().isEmpty()) {
            Toasty.error(this, "Enter Vehical Make", Toast.LENGTH_SHORT, true).show();
            return false;
        } else if (edtvehiclemodel.getText().toString().trim().isEmpty()) {
            Toasty.error(this, "Enter Vehical Model", Toast.LENGTH_SHORT, true).show();
            return false;
        } else if (edtvehiclecolor.getText().toString().trim().isEmpty()) {
            Toasty.error(this, "Enter Vehical Color", Toast.LENGTH_SHORT, true).show();
            return false;
        } else if (edtvehiclenumber.getText().toString().trim().isEmpty()) {
            Toasty.error(this, getString(R.string.invalid_car_number), Toast.LENGTH_SHORT, true).show();
            return false;
        } /*else if (edtinsurancenumber.getText().toString().trim().isEmpty()) {
            Toasty.error(this, "Enter Insurance Number", Toast.LENGTH_SHORT, true).show();
            return false;
        }*/ else return true;
    }


    @Override
    public void onBackPressed() {
        if (setting != null && !setting.equalsIgnoreCase("") &&
                setting.equalsIgnoreCase("isClick")) {
            super.onBackPressed();
        } else {
            showPopUp();
        }
    }


    public void showPopUp() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder
                .setMessage(getString(R.string.log_out_title))
                .setCancelable(false)
                .setPositiveButton(getString(R.string.ok), (dialog, id) -> {
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("id", SharedHelper.getKey(activity(),
                            Constants.SharedPref.USER_ID) + "");
                    presenter.logout(map);
                }).setNegativeButton(getString(R.string.cancel), (dialog, id) -> {
            String user_id = SharedHelper.getKey(activity(), Constants.SharedPref.USER_ID);
            Utilities.printV("USER_ID===>", user_id);
            dialog.cancel();
        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }
}
