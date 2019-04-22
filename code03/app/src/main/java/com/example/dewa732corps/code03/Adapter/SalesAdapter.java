package com.example.dewa732corps.code03.Adapter;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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
import com.example.dewa732corps.code03.Controller.Sales;
import com.example.dewa732corps.code03.Controller.Sparepart;
import com.example.dewa732corps.code03.Fragment.BerandaFragment;
import com.example.dewa732corps.code03.Fragment.Sales.SalesForm;
import com.example.dewa732corps.code03.Fragment.Sparepart.SparepartForm;
import com.example.dewa732corps.code03.Fragment.Supplier.SupplierForm;
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

public class SalesAdapter extends RecyclerView.Adapter<SalesAdapter.MyViewHolder>{
    private List<Sales> SalesBundle = new ArrayList<>();
    private List<Sales> SalesListFilter = new ArrayList<>();

    private Context context;
    ProgressDialog mProgress;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView Name, PhoneNumber, IdSupplier;
        public ImageView btnEdit, btnDelete;
//        public LinearLayout topWraper;
//        public LinearLayout bottomWraper;

        public MyViewHolder(View v) {
            super(v);
            Name = v.findViewById(R.id.txtNameSales);
            PhoneNumber = v.findViewById(R.id.txtNoTelpSales);
            IdSupplier = v.findViewById(R.id.txtNameOfSupplier);

            btnEdit = v.findViewById(R.id.btnEditSales);
            btnDelete = v.findViewById(R.id.btnDeleteSales);

//            bottomWraper = v.findViewById(R.id.bottom_wrapper);
//            topWraper = v.findViewById(R.id.top_wrapper);
        }
    }

    public SalesAdapter(List<Sales> SalesBundle , Context context) {
        this.SalesBundle = SalesBundle;
        this.context = context;
        this.SalesListFilter = SalesBundle;
        mProgress = new ProgressDialog(context);
        mProgress.setMessage("Loading");
    }

    @NonNull
    @Override
    public SalesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        // create a new view
        View v = (View) LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.content_sales, viewGroup, false);
        return new SalesAdapter.MyViewHolder(v);
    }

    public void filter(String charText) {
        Log.d( "filter: ", charText);

        charText = charText.toLowerCase(Locale.getDefault());
        SalesListFilter.clear();
        if (charText.length() == 0) {
            SalesListFilter.addAll(SalesBundle);
        }
        else
        {
            for (Sales obj : SalesBundle) {
                if (obj.getName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    SalesListFilter.add(obj);
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
                    SalesListFilter = SalesBundle;
                } else {
                    List<Sales> filteredList = new ArrayList<>();
                    for (Sales obj : SalesBundle) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (obj.getName().toLowerCase().contains(charString.toLowerCase())) { // || obj.getId().contains(charSequence)
                            filteredList.add(obj);
                        }
                    }
                    SalesListFilter = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = SalesListFilter;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                SalesListFilter = (ArrayList<Sales>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    @Override
    public void onBindViewHolder(@NonNull SalesAdapter.MyViewHolder vh, final int i) {

//        final Sparepart data = SparepartBundle.get(i);
        final Sales data = SalesListFilter.get(i);

        final int ifinal = vh.getAdapterPosition();
        vh.Name.setText(data.getName());
        vh.PhoneNumber.setText(data.getPhoneNumber());
        vh.IdSupplier.setText(data.getSupplier().getName());

        vh.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), SalesForm.class);
                intent.putExtra("mode", i); // FUNGSI PUT EXTRA ITU UTK NGAMBIL DATA YANG SUDAH DISET SEBELUMNYA, CONTOHNYA UNTUK EDIT KAN SEBELUMNYA UDAH ADA DATA BIAR MUNCUL

                intent.putExtra("id", data.getId().toString());
                intent.putExtra("nama", data.getName());
                intent.putExtra("notelp", data.getPhoneNumber());
                intent.putExtra("name_supplier", data.getSupplier().getName().toString());

                Log.d("ID Sales: ", data.getId().toString());
                Log.d("Nama: ", data.getName());
                Log.d("No Telp: ", data.getPhoneNumber());
                Log.d("ID Supplier: ", data.getSupplier().getName().toString());

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

                Call<ResponseBody> deleteSales = apiClient.deleteSales(data.getId());
                deleteSales.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.code() == 201){
                            Toast.makeText(context.getApplicationContext(), "BERHASIL MENGHAPUS DATA SALES", Toast.LENGTH_SHORT).show();
                            mProgress.hide();
                        }else{
                            Toast.makeText(context.getApplicationContext(), "GAGAL MENGHAPUS DATA SALES", Toast.LENGTH_SHORT).show();
                            mProgress.hide();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(context.getApplicationContext(), "GAGAL HAPUS DATA SALES", Toast.LENGTH_SHORT).show();
                        mProgress.hide();
                    }
                });
                SalesBundle.remove(ifinal);
                notifyItemRemoved(ifinal);
                notifyItemRangeChanged(ifinal, getItemCount());
            }
        });
    }

    @Override
    public int getItemCount() {
//        return SparepartBundle.size();
        return SalesListFilter.size();
    }
}
