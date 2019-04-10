package com.example.dewa732corps.code03.Adapter;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dewa732corps.code03.Controller.ApiClient;
import com.example.dewa732corps.code03.Controller.Sparepart;
import com.example.dewa732corps.code03.Controller.Supplier;
import com.example.dewa732corps.code03.Fragment.BerandaFragment;
import com.example.dewa732corps.code03.Fragment.Sparepart.SparepartForm;
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

public class SupplierAdapter extends RecyclerView.Adapter<SupplierAdapter.MyViewHolder>{
    private List<Supplier> SupplierBundle = new ArrayList<>();
    private Context context;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView Name, Address, PhoneNumber;
        public ImageView btnEdit, btnDelete;
//        public LinearLayout topWraper;
//        public LinearLayout bottomWraper;

        public MyViewHolder(View v) {
            super(v);
            Name = v.findViewById(R.id.txtNameSupplier);
            Address = v.findViewById(R.id.txtAddressSupplier);
            PhoneNumber = v.findViewById(R.id.txtPhoneNumberSupplier);
            btnEdit = v.findViewById(R.id.btnEditSupplier);
            btnDelete = v.findViewById(R.id.btnDeleteSupplier);

//            bottomWraper = v.findViewById(R.id.bottom_wrapper);
//            topWraper = v.findViewById(R.id.top_wrapper);
        }
    }

    public SupplierAdapter(List<Supplier> SupplierBundler , Context context) {
        this.SupplierBundle = SupplierBundler;
        this.context = context;
    }

    @NonNull
    @Override
    public SupplierAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        // create a new view
        View v = (View) LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.content_supplier, viewGroup, false);
        return new SupplierAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SupplierAdapter.MyViewHolder vh, final int i) {

        final Supplier data = SupplierBundle.get(i);
        final int ifinal = vh.getAdapterPosition();
        vh.Name.setText(data.getName());
        vh.Address.setText(data.getAddress());
        vh.PhoneNumber.setText(data.getPhoneNumber());
        vh.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(context, AddSupplier.class);
//                intent.putExtra("simpan", i);
//                intent.putExtra("name", data.getSupplierName());
//                intent.putExtra("address", data.getSupplierAddress());
//                intent.putExtra("number", data.getSupplierPhoneNumber());
//                intent.putExtra("id", data.getIdSupplier());
//                context.startActivity(intent);

            }
        });

        vh.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Retrofit retrofit = new retrofit2.Retrofit.Builder()
                        .baseUrl("http://10.53.12.230/api/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                ApiClient apiClient = retrofit.create(ApiClient.class);

                Call<ResponseBody> deleteSupplier = apiClient.deleteSupplier(data.getId());
                deleteSupplier.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.code() == 201){
                            Toast.makeText(context.getApplicationContext(), "BERHASIL MENGHAPUS DATA SUPPLIER", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(context.getApplicationContext(), "GAGAL MENGHAPUS DATA SUPPLIER", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(context.getApplicationContext(), "GAGAL MENGHAPUS DATA SUPPLIER", Toast.LENGTH_SHORT).show();
                    }
                });
                SupplierBundle.remove(ifinal);
                notifyItemRemoved(ifinal);
                notifyItemRangeChanged(ifinal, getItemCount());
            }
        });
    }

    @Override
    public int getItemCount() {
        return SupplierBundle.size();
    }
}
