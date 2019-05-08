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
import com.example.dewa732corps.code03.Controller.MotorCustomer;
import com.example.dewa732corps.code03.Controller.Sales;
import com.example.dewa732corps.code03.Controller.Sparepart;
import com.example.dewa732corps.code03.Fragment.BerandaFragment;
import com.example.dewa732corps.code03.Fragment.Motor_Customer.MotorCustomerForm;
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
import java.util.List;
import java.util.Locale;

public class MotorCustomerAdapter extends RecyclerView.Adapter<MotorCustomerAdapter.MyViewHolder>{
    private List<MotorCustomer> MotorCustomerBundle = new ArrayList<>();
    private List<MotorCustomer> MotorCustomerListFilter = new ArrayList<>();

    private Context context;
    ProgressDialog mProgress;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView PlatNomor, IdBrand, IdType;
        public ImageView btnAdd, btnEdit, btnDelete;

        public MyViewHolder(View v) {
            super(v);
            PlatNomor = v.findViewById(R.id.txtPlateMotorCustomer);
            IdBrand = v.findViewById(R.id.txtBrandMotorCustomer);
            IdType = v.findViewById(R.id.txtTypeMotorCustomer);

            btnEdit = v.findViewById(R.id.btnEditMotorCustomer);
            btnDelete = v.findViewById(R.id.btnDeleteMotorCustomer);
        }
    }

    public MotorCustomerAdapter(List<MotorCustomer> MotorCustomerBundle , Context context) {
        this.MotorCustomerBundle = MotorCustomerBundle;
        this.context = context;
        this.MotorCustomerListFilter = MotorCustomerBundle;
        mProgress = new ProgressDialog(context);
        mProgress.setMessage("Loading");
    }

    @NonNull
    @Override
    public MotorCustomerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        // create a new view
        View v = (View) LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.content_motorcustomer, viewGroup, false);
        return new MotorCustomerAdapter.MyViewHolder(v);
    }

    public Filter getFilter() { //INI JUGA KEK FILTER TO?
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    MotorCustomerListFilter = MotorCustomerBundle;
                } else
                    {
                    List<MotorCustomer> filteredList = new ArrayList<>();
                    for (MotorCustomer obj : MotorCustomerBundle) {
                        if (obj.getPlate().toLowerCase().contains(charString.toLowerCase())) { // || obj.getId().contains(charSequence)
                            filteredList.add(obj);
                        }
                    }
                    MotorCustomerListFilter = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = MotorCustomerListFilter;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                MotorCustomerListFilter = (ArrayList<MotorCustomer>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    @Override
    public void onBindViewHolder(@NonNull MotorCustomerAdapter.MyViewHolder vh, final int i) {

//        final Sparepart data = SparepartBundle.get(i);
        final MotorCustomer data = MotorCustomerListFilter.get(i);

        final int ifinal = vh.getAdapterPosition();
        vh.PlatNomor.setText(data.getPlate());
        vh.IdBrand.setText(data.getBrand());
        vh.IdType.setText(data.getType());

//        vh.btnAdd.setOnClickListener(new View.OnClickListener(){
//
//        });

        vh.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MotorCustomerForm.class);
                intent.putExtra("mode", i); // FUNGSI PUT EXTRA ITU UTK NGAMBIL DATA YANG SUDAH DISET SEBELUMNYA, CONTOHNYA UNTUK EDIT KAN SEBELUMNYA UDAH ADA DATA BIAR MUNCUL

                intent.putExtra("id", data.getId().toString());
                intent.putExtra("platnomor", data.getPlate());
                intent.putExtra("brand_motorcustomer", data.getIdBrand().toString());
                intent.putExtra("type_motorcustomer", data.getIdType().toString());

//                intent.putExtra("supplier", data.getSupplier());

                v.getContext().startActivity(intent);
            }
        });

        vh.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mProgress.show();
                Retrofit retrofit = new retrofit2.Retrofit.Builder()
                        .baseUrl("http://10.53.2.0/api/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                ApiClient apiClient = retrofit.create(ApiClient.class);

                Call<ResponseBody> deleteMotorCustomer = apiClient.deleteMotorCustomer(data.getId());
                deleteMotorCustomer.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.code() == 201){
                            Toast.makeText(context.getApplicationContext(), "BERHASIL MENGHAPUS DATA MOTOR CUSTOMER", Toast.LENGTH_SHORT).show();
                            mProgress.hide();
                        }else{
                            Toast.makeText(context.getApplicationContext(), "GAGAL MENGHAPUS DATA MOTOR CUSTOMER", Toast.LENGTH_SHORT).show();
                            mProgress.hide();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(context.getApplicationContext(), "GAGAL HAPUS DATA MOTOR CUSTOMER", Toast.LENGTH_SHORT).show();
                        mProgress.hide();
                    }
                });
                MotorCustomerBundle.remove(ifinal);
                notifyItemRemoved(ifinal);
                notifyItemRangeChanged(ifinal, getItemCount());
            }
        });
    }

    @Override
    public int getItemCount() {
//        return SparepartBundle.size();
        return MotorCustomerListFilter.size();
    }
}
