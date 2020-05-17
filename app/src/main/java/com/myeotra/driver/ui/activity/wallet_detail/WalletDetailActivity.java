package com.myeotra.driver.ui.activity.wallet_detail;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.MenuItem;

import com.myeotra.driver.R;
import com.myeotra.driver.base.BaseActivity;
import com.myeotra.driver.data.network.model.Transaction;
import com.myeotra.driver.ui.adapter.WalletDetailAdapter;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WalletDetailActivity extends BaseActivity implements WalletDetailIView {


    @BindView(R.id.rvWalletDetailData)
    RecyclerView rvWalletDetailData;


    WalletDetailPresenter<WalletDetailActivity> presenter = new WalletDetailPresenter();

    @Override
    public int getLayoutId() {
        return R.layout.activity_wallet_detail;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
        presenter.attachView(this);
        ArrayList<Transaction> mTransactionArrayList = (ArrayList<Transaction>) getIntent().getSerializableExtra("transaction_list");
        String alias = getIntent().getStringExtra("alias");
        Objects.requireNonNull(getSupportActionBar()).setTitle(alias);
        rvWalletDetailData.setLayoutManager(new LinearLayoutManager(activity(), LinearLayoutManager.VERTICAL, false));
        rvWalletDetailData.setItemAnimator(new DefaultItemAnimator());
        rvWalletDetailData.setHasFixedSize(true);
        presenter.setAdapter(mTransactionArrayList);
    }

    @Override
    public void setAdapter(ArrayList<Transaction> myList) {
        rvWalletDetailData.setAdapter(new WalletDetailAdapter(myList));
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
}
