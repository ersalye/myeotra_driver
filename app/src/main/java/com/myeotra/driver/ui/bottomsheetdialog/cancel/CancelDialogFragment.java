package com.myeotra.driver.ui.bottomsheetdialog.cancel;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatSpinner;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.myeotra.driver.R;
import com.myeotra.driver.base.BaseBottomSheetDialogFragment;
import com.myeotra.driver.common.Constants;
import com.myeotra.driver.common.SharedHelper;
import com.myeotra.driver.data.network.model.CancelResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class CancelDialogFragment extends BaseBottomSheetDialogFragment implements CancelDialogIView, AdapterView.OnItemSelectedListener {


    private static final String TAG = "AAAA";
    @BindView(R.id.txtComments)
    EditText comments;
    @BindView(R.id.btnSubmit)
    Button btnSubmit;
    Unbinder unbinder;
    @BindView(R.id.rcvReason)
    RecyclerView rcvReason;


    @BindView(R.id.spinnerServiceType)
    AppCompatSpinner spinnerServiceType;


    private CancelDialogPresenter presenter;

    private List<CancelResponse> cancelResponseList = new ArrayList<>();
    //    private ReasonAdapter adapter;
//    private int last_selected_location = -1;
    private int selected_pos = -1;


    private String comment = "";

    @Override
    public int getLayoutId() {
        return R.layout.fragment_cancel;
    }

    @Override
    public void initView(View view) {
        unbinder = ButterKnife.bind(this, view);
        presenter = new CancelDialogPresenter();
        presenter.attachView(this);
//        adapter = new ReasonAdapter(cancelResponseList);
//        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager
//                (getActivity(), LinearLayoutManager.VERTICAL, false);
//        rcvReason.setLayoutManager(mLayoutManager);
//        rcvReason.setItemAnimator(new DefaultItemAnimator());
//        rcvReason.setAdapter(adapter);

        presenter.getReasons();

        spinnerServiceType.setOnItemSelectedListener(this);

    }


    @Override
    public void onSuccess(List<CancelResponse> response) {
        cancelResponseList.addAll(response);
        addCommonReason();

        setupSpinner(cancelResponseList);
    }

    private void setupSpinner(List<CancelResponse> response) {


        ArrayList<String> lstNames = new ArrayList<>(response != null ? response.size() : 0);
        if (response != null) for (CancelResponse serviceType : response)
            lstNames.add(serviceType.getReason());

//        Log.e(TAG, "setupSpinner: " + new Gson().toJson(lstNames));
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),
                R.layout.item_spinner, lstNames);

        dataAdapter.setDropDownViewResource(R.layout.item_spinner
        );
        spinnerServiceType.setAdapter(dataAdapter);
    }

    @Override
    public void onReasonError(Throwable e) {
        addCommonReason();
    }

    private void addCommonReason() {
        //Common reason added here
        CancelResponse commonReason = new CancelResponse();
        commonReason.setReason("Other Reason");
        commonReason.setType("USER");
        commonReason.setStatus(1);
        commonReason.setId(0);
        cancelResponseList.add(commonReason);
        if (cancelResponseList.size() == 1) {
            selected_pos = 0;
            comments.setVisibility(View.VISIBLE);
        }
//        adapter.notifyDataSetChanged();


    }


    @OnClick(R.id.btnSubmit)
    public void onViewClicked() {
        if (selected_pos == -1) {
            Toast.makeText(getContext(), getString(R.string.invalid_selection), Toast.LENGTH_SHORT).show();
            return;
        }

        cancelRequest();
    }

    private void cancelRequest() {

        if (comments.getText().toString().isEmpty() && (selected_pos == cancelResponseList.size() - 1)) {
            Toast.makeText(getContext(), getString(R.string.please_enter_cancel_reason), Toast.LENGTH_SHORT).show();
            return;
        } else {
            comment = cancelResponseList.get(selected_pos).getReason();
        }

        HashMap<String, Object> map = new HashMap<>();
        map.put("id", SharedHelper.getKey(getContext(), Constants.SharedPref.CANCEL_ID));
        map.put("cancel_reason", comment);
        showLoading();
        presenter.cancelRequest(map);
    }

    @Override
    public void onSuccessCancel(Object object) {

        Log.e(TAG, "cancel_request : res : " + new Gson().toJson(object));
        dismissAllowingStateLoss();
        hideLoading();
        activity().sendBroadcast(new Intent("INTENT_FILTER"));
    }

    @Override
    public void onError(Throwable e) {

        Log.e(TAG, "cancel dialog : onError :  " + e.getMessage());

        hideLoading();
        if (e != null)
            onErrorBase(e);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selected_pos = position;

        if (cancelResponseList.get(selected_pos).getReason().equalsIgnoreCase("Other Reason")) {
            comments.setVisibility(View.VISIBLE);
        } else {
            comment = cancelResponseList.get(selected_pos).getReason();
            comments.setVisibility(View.GONE);

//            Toast.makeText(getActivity(), "click" + cancelResponseList.get(selected_pos).getReason(), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

   /* private class ReasonAdapter extends RecyclerView.Adapter<CancelDialogFragment.ReasonAdapter.MyViewHolder> {

        private List<CancelResponse> list;
        private Context mContext;


        public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            LinearLayout llItemView;
            TextView tvReason;
            CheckBox cbItem;

            MyViewHolder(View view) {
                super(view);
                llItemView = view.findViewById(R.id.llItemView);
                tvReason = view.findViewById(R.id.tvReason);
                cbItem = view.findViewById(R.id.cbItem);

                llItemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View view) {
                int position = getAdapterPosition();
                if (position == list.size() - 1) {
                    comments.setVisibility(View.VISIBLE);
                } else {
                    comments.setVisibility(View.GONE);
                }
                last_selected_location = position;
                notifyDataSetChanged();
            }
        }

        private ReasonAdapter(List<CancelResponse> list) {
            this.list = list;
        }

        @NonNull
        @Override
        public CancelDialogFragment.ReasonAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            mContext = parent.getContext();
            return new CancelDialogFragment.ReasonAdapter.MyViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.cancel_reasons_inflate, parent, false));
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull CancelDialogFragment.ReasonAdapter.MyViewHolder holder, int position) {

            CancelResponse data = list.get(position);
            holder.tvReason.setText(data.getReason());

            if (last_selected_location == position) {
                holder.cbItem.setChecked(true);
            } else {
                holder.cbItem.setChecked(false);
            }

        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }*/
}
