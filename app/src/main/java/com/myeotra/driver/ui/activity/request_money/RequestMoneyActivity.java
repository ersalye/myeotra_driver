package com.myeotra.driver.ui.activity.request_money;

import android.app.AlertDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.myeotra.driver.R;
import com.myeotra.driver.base.BaseActivity;
import com.myeotra.driver.data.network.model.RequestDataResponse;
import com.myeotra.driver.data.network.model.RequestedDataItem;
import com.myeotra.driver.ui.adapter.RequestAmtAdapter;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RequestMoneyActivity extends BaseActivity implements RequestMoneyIView {

    private RequestMoneyPresenter mPresenter = new RequestMoneyPresenter();

    /* @BindView(R.id.toolbar)
     Toolbar toolbar;*/
    @BindView(R.id.etRequestAmt)
    EditText etRequestAmt;
    @BindView(R.id.rvRequestedData)
    RecyclerView rvRequestedData;
    @BindView(R.id.llHistoryContainer)
    LinearLayout llHistoryContainer;
    @BindView(R.id.tvHistoryPlaceholder)
    TextView tvHistoryPlaceholder;
    @BindView(R.id.menu)
    ImageView menu;

    private Double walletAmt;

    @Override
    public int getLayoutId() {
        return R.layout.activity_request_money;
    }

    @Override
    public void initView() {
        walletAmt = Objects.requireNonNull(getIntent().getExtras()).getDouble("WalletAmt");
        ButterKnife.bind(this);
        mPresenter.attachView(this);
//        setSupportActionBar(toolbar);
//        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        //        getSupportActionBar().setTitle(getString(R.string.transaction));
        etRequestAmt.clearFocus();
        showLoading();
        mPresenter.getRequestedData();
        rvRequestedData.setLayoutManager(new LinearLayoutManager(activity(), LinearLayoutManager.VERTICAL, false));
        rvRequestedData.setItemAnimator(new DefaultItemAnimator());
        rvRequestedData.setHasFixedSize(true);
    }

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }*/

    @Override
    public void onSuccess(RequestDataResponse response) {
        hideLoading();
        if (response.getPendingList() != null && response.getPendingList().size() > 0) {
            llHistoryContainer.setVisibility(View.VISIBLE);
            tvHistoryPlaceholder.setVisibility(View.GONE);
            loadAdapter(response.getPendingList());
        } else {
            llHistoryContainer.setVisibility(View.GONE);
            tvHistoryPlaceholder.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onSuccess(Object response) {
        mPresenter.getRequestedData();
    }

    @Override
    public void onError(Throwable e) {
        hideLoading();
        if (e != null)
            onErrorBase(e);
    }

    private void loadAdapter(List<RequestedDataItem> requestedDataItems) {
        rvRequestedData.setAdapter(new RequestAmtAdapter(requestedDataItems, mItemListener));
    }

    @OnClick({R.id.tvSubmit, R.id.menu})
    public void onViewClicked(View view) {

        switch (view.getId()) {
            case R.id.menu:
                onBackPressed();
                break;

            case R.id.tvSubmit:

                String amount = etRequestAmt.getText().toString().trim();


                if (amount.trim().length() == 0 && amount.equalsIgnoreCase("0")) {
                    Toast.makeText(this, "Insufficient amount", Toast.LENGTH_SHORT).show();

                } else if (Double.valueOf(amount) <= walletAmt)
                    new AlertDialog.Builder(this)
                            .setMessage(getString(R.string.confirm_request_amt))
                            .setPositiveButton(getString(R.string.confirm), (dialogInterface, i) -> {
                                showLoading();
                                mPresenter.requestMoney(Double.valueOf(amount));
                                Toast.makeText(this, "Request Sent", Toast.LENGTH_SHORT).show();
                                etRequestAmt.setText("");
                            }).setNegativeButton(getString(R.string.cancel), (dialogInterface, i) -> etRequestAmt.requestFocus()).create()
                            .show();
                else {
                    Toast.makeText(this, "Insufficient amount", Toast.LENGTH_SHORT).show();
                    etRequestAmt.setText("");
                    etRequestAmt.requestFocus();
                }
                break;
        }


    }

    public interface RequestedItem {
        void onDelete(int id);
    }

    private RequestedItem mItemListener = (int id) -> {
        showLoading();
        mPresenter.removeRequestMoney(id);
    };
}
