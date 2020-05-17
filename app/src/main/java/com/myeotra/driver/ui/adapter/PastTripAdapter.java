package com.myeotra.driver.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.myeotra.driver.MvpApplication;
import com.myeotra.driver.R;
import com.myeotra.driver.common.Constants;
import com.myeotra.driver.data.network.model.HistoryList;

import java.util.List;
import java.util.StringTokenizer;

public class PastTripAdapter extends RecyclerView.Adapter<PastTripAdapter.MyViewHolder> {

    private static final String TAG = "AAAA";
    private List<HistoryList> list;
    private Context context;

    private ClickListener clickListener;

    public PastTripAdapter(List<HistoryList> list, Context con) {
        this.list = list;
        this.context = con;
    }

    public void setList(List<HistoryList> list) {
        this.list = list;
    }

    public void setClickListener(PastTripAdapter.ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_past_trip, parent, false);

        return new MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        HistoryList historyList = list.get(position);

        StringTokenizer tk = new StringTokenizer(historyList.getFinishedAt());

        Log.e(TAG, "onBindViewHolder: " + tk.nextToken() + " : " + tk.nextToken());

        holder.finishedAt.setText(historyList.getFinishedAt());
        holder.bookingId.setText(historyList.getBookingId());
        if (historyList.getPayment() != null) {

            holder.payable.setText(Constants.Currency + " " +
                            MvpApplication.getInstance().getNewNumberFormat(historyList.getPayment().getTotal())
                    /*numberFormat.format(historyList.getPayment().getTotal())*/);

        } else holder.payable.setText(Constants.Currency + " " + "0.00");
        if (historyList.getServicetype() != null)
            holder.lblServiceName.setText(historyList.getServicetype().getName());
        Glide.with(context)
                .load(historyList.getStaticMap())
                .apply(RequestOptions.placeholderOf(R.drawable.ic_launcher_background)
                        .dontAnimate().error(R.drawable.ic_launcher_background)
                        .diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(holder.staticMap);

        if (historyList.getPaymentMode().equalsIgnoreCase("CARD")) {

            Glide.with(context)
                    .load(R.drawable.ic_card)
                    .apply(RequestOptions.placeholderOf(R.drawable.ic_card)
                            .dontAnimate().error(R.drawable.ic_card)
                            .diskCacheStrategy(DiskCacheStrategy.ALL))
                    .into(holder.ivPayment);
            holder.tvPaymentType.setText(historyList.getPaymentMode());
        } else {
            Glide.with(context)
                    .load(R.drawable.ic_cash)
                    .apply(RequestOptions.placeholderOf(R.drawable.ic_cash)
                            .dontAnimate().error(R.drawable.ic_cash)
                            .diskCacheStrategy(DiskCacheStrategy.ALL))
                    .into(holder.ivPayment);
            holder.tvPaymentType.setText(historyList.getPaymentMode());
        }

        holder.rating.setRating((float) historyList.getRating().getUserRating());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface ClickListener {
        void redirectClick(HistoryList historyList, ImageView staticMap);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        public ImageView staticMap, ivPayment;
        private RelativeLayout itemView;
        private TextView bookingId, payable, finishedAt, lblServiceName, tvPaymentType;

        RatingBar rating;

        private MyViewHolder(View view) {
            super(view);

            itemView = view.findViewById(R.id.item_view);
            bookingId = view.findViewById(R.id.booking_id);
            payable = view.findViewById(R.id.payable);
            lblServiceName = view.findViewById(R.id.lblServiceName);
            finishedAt = view.findViewById(R.id.finished_at);
            staticMap = view.findViewById(R.id.static_map);
            rating = view.findViewById(R.id.rating);
            ivPayment = view.findViewById(R.id.ivPayment);
            tvPaymentType = view.findViewById(R.id.tvPaymentType);

            itemView.setOnClickListener(v -> {
                if (clickListener != null)
                    clickListener.redirectClick(list.get(getAdapterPosition()), staticMap);
            });
        }
    }
}