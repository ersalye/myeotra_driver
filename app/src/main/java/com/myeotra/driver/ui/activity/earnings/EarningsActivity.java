package com.myeotra.driver.ui.activity.earnings;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.myeotra.driver.MvpApplication;
import com.myeotra.driver.R;
import com.myeotra.driver.base.BaseActivity;
import com.myeotra.driver.common.CircularProgressBar;
import com.myeotra.driver.common.Constants;
import com.myeotra.driver.data.network.model.EarningsList;
import com.myeotra.driver.data.network.model.Payment;
import com.myeotra.driver.data.network.model.Ride;
import com.myeotra.driver.ui.adapter.EarningsTripAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.myeotra.driver.common.Utilities.animateTextView;

public class EarningsActivity extends BaseActivity implements EarningsIView {

    private static final String TAG = "AAAA";
    EarningsPresenter presenter = new EarningsPresenter();
    /*@BindView(R.id.toolbar)
    Toolbar toolbar;*/
    /*@BindView(R.id.earnings_bar)
    CircularProgressBar earningsBar;*/
    /*@BindView(R.id.target_txt)
    TextView targetTxt;*/
    @BindView(R.id.lblEarnings)
    TextView lblEarnings;
    @BindView(R.id.rides_rv)
    RecyclerView ridesRv;
    @BindView(R.id.error_image)
    ImageView errorImage;
    @BindView(R.id.errorLayout)
    RelativeLayout errorLayout;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @BindView(R.id.menu)
    ImageView menu;

    List<Ride> list = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_earnings;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
        presenter.attachView(this);

//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setTitle(getString(R.string.earnings));
        ridesRv.setLayoutManager(new LinearLayoutManager(activity(), LinearLayoutManager.VERTICAL, false));
        ridesRv.setItemAnimator(new DefaultItemAnimator());
        ridesRv.setHasFixedSize(true);
        showLoading();
        presenter.getEarnings();
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

    @Override
    public void onSuccess(EarningsList earningsLists) {

        Log.e(TAG, "Erning : res : " + new Gson().toJson(earningsLists));

//        Utilities.printV("SIZE", earningsLists.getRides().size() + "");
        hideLoading();
        list.clear();
        list.addAll(earningsLists.getRides());
        loadAdapter(earningsLists);
    }

    @Override
    public void onError(Throwable e) {
        hideLoading();
        if (e != null)
            onErrorBase(e);
    }


    @SuppressLint("SetTextI18n")
    private void loadAdapter(EarningsList earningsLists) {

        //ProgressBar Animation
//        earningsBar.setProgressWithAnimation(10, 1500);
        //Animated TextView
//        animateTextView(0, earningsLists.getRidesCount(), Integer.parseInt(earningsLists.getTarget()), targetTxt);
        //Set Total Amount Value
        double total_price = 0;
        double tripTotalAmt = 0;
        for (Ride ride : earningsLists.getRides()) {
            Payment payment = ride.getPayment();
            if (payment != null) {
                tripTotalAmt = payment.getProviderPay();
                // tripTotalAmt = payment.getFixed() + payment.getDistance() + payment.getTax();
                total_price += tripTotalAmt;
            }
        }

        String currency = Constants.Currency;
        lblEarnings.setText(currency + " " + MvpApplication.getInstance().getNewNumberFormat(
                Double.parseDouble(String.valueOf(total_price))));

        if (list.size() > 0) {
            EarningsTripAdapter adapter = new EarningsTripAdapter(list/*, activity()*/);
            ridesRv.setAdapter(adapter);
            ridesRv.setVisibility(View.VISIBLE);
            errorLayout.setVisibility(View.GONE);
        } else {
            ridesRv.setVisibility(View.GONE);
            errorLayout.setVisibility(View.VISIBLE);
        }
    }

}
