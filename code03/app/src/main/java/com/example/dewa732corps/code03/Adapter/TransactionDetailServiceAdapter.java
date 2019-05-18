package com.example.dewa732corps.code03.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dewa732corps.code03.Controller.ApiClient;
import com.example.dewa732corps.code03.Controller.Transaction;
import com.example.dewa732corps.code03.Controller.TransactionDetailServices;
import com.example.dewa732corps.code03.Controller.TransactionService;
import com.example.dewa732corps.code03.Fragment.Transcation.TransactionForm;
import com.example.dewa732corps.code03.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TransactionDetailServiceAdapter extends RecyclerView.Adapter<TransactionDetailServiceAdapter.MyViewHolder>{
    private List<TransactionService> TransactionServiceBundle = new ArrayList<>();
    private List<TransactionService> TransactionServiceListFilter = new ArrayList<>();
    private List<TransactionService> Detail;

    private Context context;
    ProgressDialog mProgress;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView service_name, license_number, sub_total;
        public ImageView btnEdit, btnDelete;

        public MyViewHolder(View v) {
            super(v);
            service_name = v.findViewById(R.id.txtNameService_TransactionDetailService);
            license_number = v.findViewById(R.id.txtPlatNomor_TransactionDetailService);
            sub_total = v.findViewById(R.id.txtSubTotal_TransactionDetailService);
        }
    }

    public TransactionDetailServiceAdapter(List<TransactionService> TransactionServiceBundle, Context context) {
        this.TransactionServiceBundle = TransactionServiceBundle;
        this.TransactionServiceListFilter = TransactionServiceBundle;
        this.context = context;

        mProgress = new ProgressDialog(context);
        mProgress.setMessage("Loading");
    }

    @NonNull
    @Override
    public TransactionDetailServiceAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        // create a new view
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.content_transaction_servicedetail, viewGroup, false);
        return new TransactionDetailServiceAdapter.MyViewHolder(v);
    }

    public void filter(String charText) {
        Log.d( "filter: ", charText);

        charText = charText.toLowerCase(Locale.getDefault());
        TransactionServiceListFilter.clear();
        if (charText.length() == 0) {
            TransactionServiceListFilter.addAll(TransactionServiceBundle);
        }
        else
        {
            for (TransactionService obj : TransactionServiceBundle) {
                if (obj.getPlateNumber().toLowerCase(Locale.getDefault()).contains(charText) || obj.getServiceName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    TransactionServiceListFilter.add(obj);
                }
            }
        }
        notifyDataSetChanged();
    }

    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    TransactionServiceListFilter = TransactionServiceBundle;
                } else {
                    List<TransactionService> filteredList = new ArrayList<>();
                    for (TransactionService obj : TransactionServiceBundle) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (obj.getPlateNumber().toLowerCase().contains(charString.toLowerCase()) || obj.getServiceName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(obj);
                        }
                    }

                    TransactionServiceListFilter = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = TransactionServiceListFilter;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                TransactionServiceListFilter = (ArrayList<TransactionService>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    @Override
    public void onBindViewHolder(@NonNull final TransactionDetailServiceAdapter.MyViewHolder vh, final int i) {

        final TransactionService data = TransactionServiceListFilter.get(i);

        final int ifinal = vh.getAdapterPosition();

        vh.service_name.setText(data.getServiceName());
        vh.license_number.setText(data.getPlateNumber());
        vh.sub_total.setText(data.getSubtotalTransactionService().toString());
    }

    @Override
    public int getItemCount() {
        return TransactionServiceListFilter.size();
    }
}
