package com.example.dewa732corps.code03.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dewa732corps.code03.Controller.ApiClient;
import com.example.dewa732corps.code03.Controller.Customer;
import com.example.dewa732corps.code03.Controller.Sales;
import com.example.dewa732corps.code03.Controller.Sparepart;
import com.example.dewa732corps.code03.Controller.SparepartProcurementDetail;
import com.example.dewa732corps.code03.Controller.Transaction;
import com.example.dewa732corps.code03.Controller.TransactionList;
import com.example.dewa732corps.code03.Fragment.BerandaFragment;
import com.example.dewa732corps.code03.Fragment.Sales.SalesForm;
import com.example.dewa732corps.code03.Fragment.Sparepart.SparepartForm;
import com.example.dewa732corps.code03.Fragment.Supplier.SupplierForm;
import com.example.dewa732corps.code03.Fragment.Transcation.TransactionForm;
import com.example.dewa732corps.code03.R;

import okhttp3.ResponseBody;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.MyViewHolder>{
    private List<Transaction> TransactionBundle = new ArrayList<>();
    private List<Transaction> TransactionListFilter = new ArrayList<>();

    private Context context;
    ProgressDialog mProgress;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView IdTransaction, NameCustomer, StatusProcess;
        public ImageView btnEdit, btnDelete;

        public MyViewHolder(View v) {
            super(v);
            IdTransaction = v.findViewById(R.id.txtIdTransaksi_Transaksi);
            NameCustomer = v.findViewById(R.id.txtNamaCustomer_Transaksi);
            StatusProcess = v.findViewById(R.id.txtProsesStatus_Transaksi);

            btnEdit = v.findViewById(R.id.btnEditTransaction);
            btnDelete = v.findViewById(R.id.btnDeleteTransaction);

//            bottomWraper = v.findViewById(R.id.bottom_wrapper);
//            topWraper = v.findViewById(R.id.top_wrapper);
        }
    }

    public TransactionAdapter(List<Transaction> TransactionBundle , Context context) {
        this.TransactionBundle = TransactionBundle;
        this.TransactionListFilter = TransactionBundle;
        this.context = context;

        mProgress = new ProgressDialog(context);
        mProgress.setMessage("Loading");
    }

    @NonNull
    @Override
    public TransactionAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        // create a new view
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.content_transaction, viewGroup, false);
        return new TransactionAdapter.MyViewHolder(v);
    }

    public void filter(String charText) {
        Log.d( "filter: ", charText);

        charText = charText.toLowerCase(Locale.getDefault());
        TransactionListFilter.clear();
        if (charText.length() == 0) {
            TransactionListFilter.addAll(TransactionBundle);
        }
        else
        {
            for (Transaction obj : TransactionBundle) {
                if (obj.getIdTransaction().toLowerCase(Locale.getDefault()).contains(charText) || obj.getNameCustomer().toLowerCase(Locale.getDefault()).contains(charText) || obj.getStatusProcess().toLowerCase(Locale.getDefault()).contains(charText)) {
                    TransactionListFilter.add(obj);
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
                    TransactionListFilter = TransactionBundle;
                } else {
                    List<Transaction> filteredList = new ArrayList<>();
                    for (Transaction obj : TransactionBundle) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (obj.getIdTransaction().toLowerCase().contains(charString.toLowerCase()) || obj.getNameCustomer().toLowerCase().contains(charString.toLowerCase()) || obj.getStatusProcess().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(obj);
                        }
                    }

                    TransactionListFilter = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = TransactionListFilter;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                TransactionListFilter = (ArrayList<Transaction>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    @Override
    public void onBindViewHolder(@NonNull final TransactionAdapter.MyViewHolder vh, final int i) {

        final Transaction data = TransactionListFilter.get(i);

        final int ifinal = vh.getAdapterPosition();

        vh.IdTransaction.setText(data.getIdTransaction());
        vh.NameCustomer.setText(data.getNameCustomer());
        vh.StatusProcess.setText(data.getStatusProcess());

        if(data.getStatusProcess().equalsIgnoreCase("On Process")){
            vh.StatusProcess.setBackgroundColor(Color.parseColor("#f6d82d"));
        }
        else if(data.getStatusProcess().equalsIgnoreCase("Unprocessed"))
        {
            vh.StatusProcess.setBackgroundColor(Color.RED);
        }
        else if(data.getStatusProcess().equalsIgnoreCase("Finish"))
        {
            vh.StatusProcess.setBackgroundColor(Color.GREEN);
        }

        vh.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), TransactionForm.class);
                intent.putExtra("mode", i); // FUNGSI PUT EXTRA ITU UTK NGAMBIL DATA YANG SUDAH DISET SEBELUMNYA, CONTOHNYA UNTUK EDIT KAN SEBELUMNYA UDAH ADA DATA BIAR MUNCUL

                intent.putExtra("id", data.getIdTransaction());
                intent.putExtra("nama_customer", data.getNameCustomer());
                intent.putExtra("status_proses", data.getStatusProcess());

                Log.d("ID Transaksi: ", data.getIdTransaction());
                Log.d("Nama Customer: ", data.getNameCustomer());
                Log.d("Status Proses: ", data.getStatusProcess());


//                intent.putExtra("supplier", data.getSupplier());

                v.getContext().startActivity(intent);
            }
        });

        vh.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mProgress.show();
                Retrofit retrofit = new retrofit2.Retrofit.Builder()
                        .baseUrl("https://sibento.yafetrakan.com/api/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                ApiClient apiClient = retrofit.create(ApiClient.class);

                Call<ResponseBody> deleteTransaction = apiClient.deleteTransaction(data.getIdTransaction());
                deleteTransaction.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.code() == 200){
                            Toast.makeText(context.getApplicationContext(), "BERHASIL MENGHAPUS DATA TRANSAKSI", Toast.LENGTH_SHORT).show();
                            mProgress.hide();
                        }else{
                            Toast.makeText(context.getApplicationContext(), "GAGAL MENGHAPUS DATA TRANSAKSI", Toast.LENGTH_SHORT).show();
                            mProgress.hide();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(context.getApplicationContext(), "GAGAL HAPUS DATA TRANSAKSI", Toast.LENGTH_SHORT).show();
                        mProgress.hide();
                    }
                });
                TransactionBundle.remove(ifinal);
                notifyItemRemoved(ifinal);
                notifyItemRangeChanged(ifinal, getItemCount());
            }
        });
    }

    @Override
    public int getItemCount() {
//        return SparepartBundle.size();
        return TransactionListFilter.size();
    }
}
