package com.myeotra.driver.ui.bottomsheetdialog.rating;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.myeotra.driver.BuildConfig;
import com.myeotra.driver.R;
import com.myeotra.driver.base.BaseFragment;
import com.myeotra.driver.common.SharedHelper;
import com.myeotra.driver.data.network.model.RateCommentResponse;
import com.myeotra.driver.data.network.model.Rating;
import com.myeotra.driver.data.network.model.Request_;
import com.myeotra.driver.ui.adapter.RateCommentAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.myeotra.driver.MvpApplication.DATUM;

public class RatingDialogFragment extends BaseFragment implements RatingDialogIView, RateCommentAdapter.CommentClickListner {

    private static final String TAG = "AAAA";
    @BindView(R.id.rate_with_txt)
    TextView rateWithTxt;

    @BindView(R.id.textProvidername)
    TextView textProvidername;


    @BindView(R.id.user_img)
    ImageView userImg;
    @BindView(R.id.user_rating)
    RatingBar userRating;
    @BindView(R.id.comments)
    EditText comments;
    @BindView(R.id.btnSubmit)
    Button btnSubmit;
    Unbinder unbinder;

    RatingDialogPresenter presenter;

    @BindView(R.id.rvComment)
    RecyclerView rvComment;

    private RateCommentAdapter adapter;
    private List<RateCommentResponse.RateComment> commentlist;


    @Override
    public int getLayoutId() {
        return R.layout.fragment_rating;
    }

    @Override
    public View initView(View view) {
        unbinder = ButterKnife.bind(this, view);
        // setCancelable(false);
        presenter = new RatingDialogPresenter();
        presenter.attachView(this);
        presenter.getComment();

        textProvidername.setText(SharedHelper.getKey(getActivity(), "Username"));
        init();
        return view;
    }

    @SuppressLint("SetTextI18n")
    private void init() {

        Request_ data = DATUM;

//        textProvidername.setText;

        try {
            rateWithTxt.setText("Rate Your Trip with " +
                    data.getUser().getFirstName() + " " + data.getUser().getLastName());
//            userRating.setRating(Float.parseFloat(data.getUser().getRating()));


        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            if (data.getUser().getPicture() != null)
                Glide.with(activity()).load(BuildConfig.BASE_IMAGE_URL +
                        data.getUser().getPicture()).
                        apply(RequestOptions.placeholderOf(R.drawable.ic_user_placeholder).
                                dontAnimate().error(R.drawable.ic_user_placeholder)).into(userImg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.btnSubmit)
    public void onViewClicked() {
        if (DATUM != null) {
            Request_ datum = DATUM;
            HashMap<String, Object> map = new HashMap<>();
            if (userRating.getRating() == 0.0) {
                map.put("rating", Math.round(0.0));
            } else {
                map.put("rating", Math.round(userRating.getRating()));
            }
            map.put("comment", comments.getText().toString());
            showLoading();
            presenter.rate(map, datum.getId());

        }
    }

    @Override
    public void onSuccess(Rating rating) {

        Log.e(TAG, "rating res : " + new Gson().toJson(rating));

        hideLoading();
        activity().sendBroadcast(new Intent("INTENT_FILTER"));
    }

    @Override
    public void onSuccess(RateCommentResponse object) {
        Log.e(TAG, "getComment : res : " + new Gson().toJson(object));


        commentlist = new ArrayList<>();
        commentlist = object.getRateComment();

        rvComment.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        if (object.getRateComment() != null && object.getRateComment().size() > 0) {
            adapter = new RateCommentAdapter(getActivity(), object.getRateComment());
            adapter.RegisterInterface(this);
            rvComment.setAdapter(adapter);
        }
    }

    @Override
    public void onError(Throwable e) {
        hideLoading();
        if (e != null) try {
            onErrorBase(e);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public void commentListClick(int position) {
        comments.setText(commentlist.get(position).getComment());
    }

    /*@Override
    public void initView(View view) {
        unbinder = ButterKnife.bind(this, view);
        setCancelable(false);
        presenter = new RatingDialogPresenter();
        presenter.attachView(this);
        init();

    }

    private void init() {

        Request_ data = DATUM;

        rateWithTxt.setText(getString(R.string.rate_your_trip)+ " " +
                data.getUser().getFirstName() + " " + data.getUser().getLastName());
        userRating.setRating(Float.parseFloat(data.getUser().getRating()));

        if (data.getUser().getPicture() != null)
            Glide.with(activity()).load(BuildConfig.BASE_IMAGE_URL +
                    data.getUser().getPicture()).
                    apply(RequestOptions.placeholderOf(R.drawable.ic_user_placeholder).
                    dontAnimate().error(R.drawable.ic_user_placeholder)).into(userImg);
    }

    @OnClick(R.ID.btnSubmit)
    public void onViewClicked() {
        if (DATUM != null) {
            Request_ datum = DATUM;
            HashMap<String, Object> map = new HashMap<>();
            map.put("rating", Math.round(userRating.getRating()));
            map.put("comment", comments.getText().toString());
            showLoading();
            presenter.rate(map, datum.getId());

        }
    }

    @Override
    public void onSuccess(Rating rating) {
        dismissAllowingStateLoss();
        hideLoading();
        activity().sendBroadcast(new Intent("INTENT_FILTER"));
    }

    @Override
    public void onError(Throwable e) {

    }*/
}
