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
import com.example.dewa732corps.code03.Controller.Sparepart;
import com.example.dewa732corps.code03.Controller.Supplier;
import com.example.dewa732corps.code03.Fragment.BerandaFragment;
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

public class SupplierAdapter extends RecyclerView.Adapter<SupplierAdapter.MyViewHolder>{
    private List<Supplier> SupplierBundle = new ArrayList<>();
    private List<Supplier> SupplierListFilter = new ArrayList<>();

    private Context context;
    ProgressDialog mProgress;

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

    public SupplierAdapter(List<Supplier> SupplierBundle , Context context) {
        this.SupplierBundle = SupplierBundle;
        this.context = context;

        this.SupplierListFilter = SupplierBundle;
        mProgress = new ProgressDialog(context);
        mProgress.setMessage("Loading");
    }

    @NonNull
    @Override
    public SupplierAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        // create a new view
        View v = (View) LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.content_supplier, viewGroup, false);
        return new SupplierAdapter.MyViewHolder(v);
    }

    public void filter(String charText) {
        Log.d( "filter: ", charText);

        charText = charText.toLowerCase(Locale.getDefault());
        SupplierListFilter.clear();
        if (charText.length() == 0) {
            SupplierListFilter.addAll(SupplierBundle);
        }
        else
        {
            for (Supplier obj : SupplierBundle) {
                if (obj.getName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    SupplierListFilter.add(obj);
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
                    SupplierListFilter = SupplierBundle;
                } else {
                    List<Supplier> filteredList = new ArrayList<>();
                    for (Supplier obj : SupplierBundle) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (obj.getName().toLowerCase().contains(charString.toLowerCase())) // || obj.getId().contains(charString) ini error di constain nya karena integer
                        {
                            Log.d("search: ",charString.toLowerCase());
                            Log.d( "Nama: ", obj.getName());
                            filteredList.add(obj);
                        }
                    }

                    SupplierListFilter = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = SupplierListFilter;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                SupplierListFilter = (ArrayList<Supplier>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    @Override
    public void onBindViewHolder(@NonNull SupplierAdapter.MyViewHolder vh, final int i) {

//        final Sparepart data = SparepartBundle.get(i);
        final Supplier data = SupplierListFilter.get(i);

        final int ifinal = vh.getAdapterPosition();

        vh.Name.setText(data.getName());
        vh.Address.setText(data.getAddress());
        vh.PhoneNumber.setText(data.getPhoneNumber());

        vh.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), SupplierForm.class);
                intent.putExtra("mode", i);

                intent.putExtra("id", data.getId().toString());
                intent.putExtra("nama", data.getName());
                intent.putExtra("notelp", data.getPhoneNumber());
                intent.putExtra("alamat", data.getAddress());
//                intent.putExtra("listsales", data.getSales());
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

                Call<ResponseBody> deleteSupplier = apiClient.deleteSupplier(data.getId());
                deleteSupplier.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.code() == 201){
                            Toast.makeText(context.getApplicationContext(), "BERHASIL MENGHAPUS DATA SUPPLIER", Toast.LENGTH_SHORT).show();
                            mProgress.hide();
                        }else{
                            Toast.makeText(context.getApplicationContext(), "GAGAL MENGHAPUS DATA SUPPLIER", Toast.LENGTH_SHORT).show();
                            mProgress.hide();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(context.getApplicationContext(), "GAGAL HAPUS DATA SUPPLIER", Toast.LENGTH_SHORT).show();
                        mProgress.hide();
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
        return SupplierListFilter.size();
    }
}
