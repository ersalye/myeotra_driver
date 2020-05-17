package com.myeotra.driver.ui.activity.wallet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.DigitsKeyListener;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.myeotra.driver.R;
import com.myeotra.driver.base.BaseActivity;
import com.myeotra.driver.common.Constants;
import com.myeotra.driver.common.SharedHelper;
import com.myeotra.driver.data.network.model.WalletMoneyAddedResponse;
import com.myeotra.driver.data.network.model.WalletResponse;
import com.myeotra.driver.ui.activity.payment.PaymentActivity;
import com.myeotra.driver.ui.activity.request_money.RequestMoneyActivity;
import com.myeotra.driver.ui.adapter.WalletAdapter;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.myeotra.driver.ui.activity.payment.PaymentActivity.PICK_PAYMENT_METHOD;

public class WalletActivity extends BaseActivity implements WalletIView {

    private static final String TAG = "AAAA";
    /* @BindView(R.id.toolbar)
         Toolbar toolbar;*/
    @BindView(R.id.tvWalletAmt)
    TextView tvWalletAmt;
    @BindView(R.id.rvWalletData)
    RecyclerView rvWalletData;
    /*@BindView(R.id.tvWalletPlaceholder)
    TextView tvWalletPlaceholder;
    @BindView(R.id.llWalletHistory)
    LinearLayout llWalletHistory;*/
    @BindView(R.id.ivRequestMoney)
    ImageView ivRequestMoney;
    @BindView(R.id.addAmount)
    Button addAmount;
    /*@BindView(R.id.etRequestAmt)
    EditText etRequestAmt;*/

    @BindView(R.id.etRequestAmt)
    EditText etRequestAmt;
    private WalletPresenter mPresenter = new WalletPresenter();
    private double walletAmt;

    @BindView(R.id._199)
    TextView _199;
    @BindView(R.id._599)
    TextView _599;
    @BindView(R.id._1099)
    TextView _1099;

    @Override
    public int getLayoutId() {
        return R.layout.activity_wallet;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
//        etRequestAmt.setKeyListener(new DigitsInputFilter());
        mPresenter.attachView(this);
        /*setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.wallet));*/

        _199.setText(SharedHelper.getKey(this, Constants.SharedPref.CURRENCY) + " " + getString(R.string._199));
        _599.setText(SharedHelper.getKey(this, Constants.SharedPref.CURRENCY) + " " + getString(R.string._599));
        _1099.setText(SharedHelper.getKey(this, Constants.SharedPref.CURRENCY) + " " + getString(R.string._1099));
        etRequestAmt.setTag(SharedHelper.getKey(this, Constants.SharedPref.CURRENCY));


        showLoading();
        if (SharedHelper.getIntKey(this, "card") == 0) ivRequestMoney.setVisibility(View.GONE);
        else ivRequestMoney.setVisibility(View.VISIBLE);
        mPresenter.getWalletData();
        rvWalletData.setLayoutManager(new LinearLayoutManager(activity(), LinearLayoutManager.VERTICAL, false));
        rvWalletData.setItemAnimator(new DefaultItemAnimator());
        rvWalletData.setHasFixedSize(true);
        rvWalletData.setNestedScrollingEnabled(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @OnClick({R.id.addAmount, R.id.menu, R.id._199, R.id._599, R.id._1099})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.addAmount:
                if (etRequestAmt.getText().toString().trim().isEmpty()) {
                    Toast.makeText(activity(), getString(R.string.invalid_amount), Toast.LENGTH_SHORT).show();
                    return;
                } else if (Float.parseFloat(etRequestAmt.getText().toString().trim()) == 0) {
                    Toast.makeText(getApplicationContext(),
                            getResources().getString(R.string.valid_amount), Toast.LENGTH_SHORT)
                            .show();
                    return;
                } else {
                    Intent intent = new Intent(activity(), PaymentActivity.class);
                    intent.putExtra("hideCash", true);
                    startActivityForResult(intent, PICK_PAYMENT_METHOD);
                }
                break;

            case R.id.menu:
                onBackPressed();
                break;

            case R.id._199:
                etRequestAmt.setText(getString(R.string._199));
                break;
            case R.id._599:
                etRequestAmt.setText(getString(R.string._599));
                break;
            case R.id._1099:
                etRequestAmt.setText(getString(R.string._1099));
                break;

            default:
                break;
        }
    }


    /*@OnClick({R.id._199, R.id._599, R.id._1099, R.id.add_amount, R.id.menu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id._199:
                amount.setText(getString(R.string._199));
                break;
            case R.id._599:
                amount.setText(getString(R.string._599));
                break;
            case R.id._1099:
                amount.setText(getString(R.string._1099));
                break;
            case R.id.menu:
                onBackPressed();
                break;
            case R.id.add_amount:
                if (!amount.getText().toString().trim().matches(regexNumber)) {
                    Toast.makeText(baseActivity(), getString(R.string.invalid_amount), Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(baseActivity(), PaymentActivity.class);
                intent.putExtra("hideCash", true);
                startActivityForResult(intent, PICK_PAYMENT_METHOD);
                break;
        }
    }*/

    @Override
    public void onSuccess(WalletResponse response) {
        hideLoading();


        Log.e(TAG, " wallettransaction : res " + " size " + response.getWalletTransation().size() + " :  " + new Gson().toJson(response.getWalletTransation()));
        walletAmt = response.getWalletBalance();
        tvWalletAmt.setText(String.format("%s %s", Constants.Currency, walletAmt));
        if (response.getWalletTransation() != null && response.getWalletTransation().size() > 0) {
            rvWalletData.setAdapter(new WalletAdapter(response.getWalletTransation()));
//            llWalletHistory.setVisibility(View.VISIBLE);
//            tvWalletPlaceholder.setVisibility(View.GONE);
        } else {
//            llWalletHistory.setVisibility(View.GONE);
//            tvWalletPlaceholder.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onSuccess(WalletMoneyAddedResponse response) {
        showLoading();
        etRequestAmt.setText(null);
        mPresenter.getWalletData();
    }

    @Override
    public void onError(Throwable e) {
        hideLoading();
        if (e != null)
            onErrorBase(e);
    }

    @OnClick(R.id.ivRequestMoney)
    public void onViewClicked() {
        startActivity(new Intent(this, RequestMoneyActivity.class).putExtra("WalletAmt", walletAmt));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_PAYMENT_METHOD && resultCode == Activity.RESULT_OK && data != null)
            if (data.getStringExtra("payment_mode").equals("CARD")) {
                HashMap<String, Object> map = new HashMap<>();
                String cardId = data.getStringExtra("card_id");
                map.put("amount", etRequestAmt.getText().toString());
                map.put("card_id", cardId);
                map.put("payment_mode", "CARD");
                map.put("user_type", "provider");
                showLoading();
                mPresenter.addMoney(map);
            }
    }

    public class DigitsInputFilter extends DigitsKeyListener {

        private int decimalPlaces = 2;

        DigitsInputFilter() {
            super(false, true);
        }

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest,
                                   int dstart, int dend) {
            CharSequence out = super.filter(source, start, end, dest, dstart, dend);
            if (out != null) {
                source = out;
                start = 0;
                end = out.length();
            }

            int length = end - start;
            if (length == 0)
                return source;

            if (dend == 0 && source.toString().equals("."))
                return "";

            int destLength = dest.length();
            for (int i = 0; i < dstart; i++) {
                if (dest.charAt(i) == '.')
                    return (destLength - (i + 1) + length > decimalPlaces) ?
                            "" : new SpannableStringBuilder(source, start, end);
            }

            for (int i = start; i < end; ++i) {
                if (source.charAt(i) == '.') {
                    if ((destLength - dend) + (end - (i + 1)) > decimalPlaces)
                        return "";
                    else
                        break;
                }
            }

            return new SpannableStringBuilder(source, start, end);
        }
    }
}
