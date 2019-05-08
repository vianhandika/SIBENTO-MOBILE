package com.example.dewa732corps.code03.Adapter;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dewa732corps.code03.Controller.ApiClient;
import com.example.dewa732corps.code03.Controller.Sparepart;
import com.example.dewa732corps.code03.Fragment.Sparepart.SparepartForm;
import com.example.dewa732corps.code03.R;

import okhttp3.ResponseBody;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.ArrayList;
import java.util.List;

public class SparepartAdapter extends RecyclerView.Adapter<SparepartAdapter.MyViewHolder>{
    private List<Sparepart> SparepartBundle = new ArrayList<>();
    private List<Sparepart> SparepartListFilter = new ArrayList<>();

    private Context context;
    ProgressDialog mProgress;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView Name, Type, Brand, Stock;
        public ImageView btnEdit, btnDelete;

        public MyViewHolder(View v) {
            super(v);
            Name = v.findViewById(R.id.txtNameSparepart);
            Type = v.findViewById(R.id.txtTypeSparepart);
            Brand = v.findViewById(R.id.txtBrandSparepart);
            Stock = v.findViewById(R.id.txtStockSparepart);
            btnEdit = v.findViewById(R.id.btnEditSparepart);
            btnDelete = v.findViewById(R.id.btnDeleteSparepart);
        }
    }

    public SparepartAdapter(List<Sparepart> SparepartBundle , Context context) {
        this.SparepartBundle = SparepartBundle;
        this.context = context;
        this.SparepartListFilter = SparepartBundle;
        mProgress = new ProgressDialog(context);
        mProgress.setMessage("Loading");
    }

    @NonNull
    @Override
    public SparepartAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        // create a new view
        View v = (View) LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.content_sparepart, viewGroup, false);
        return new SparepartAdapter.MyViewHolder(v);
    }

    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    SparepartListFilter = SparepartBundle;
                } else {
                    List<Sparepart> filteredList = new ArrayList<>();
                    for (Sparepart obj : SparepartBundle) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (obj.getName().toLowerCase().contains(charString.toLowerCase()) || obj.getId().contains(charSequence)) {
                            filteredList.add(obj);
                        }
                    }

                    SparepartListFilter = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = SparepartListFilter;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                SparepartListFilter = (ArrayList<Sparepart>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    @Override
    public void onBindViewHolder(@NonNull SparepartAdapter.MyViewHolder vh, final int i) {

//        final Sparepart data = SparepartBundle.get(i);
        final Sparepart data = SparepartListFilter.get(i);

        final int ifinal = vh.getAdapterPosition();
        vh.Name.setText(data.getName());
        vh.Type.setText(data.getId());
        vh.Brand.setText(data.getBrand());
        vh.Stock.setText(data.getStock());

        vh.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), SparepartForm.class);
                intent.putExtra("mode", i);

                intent.putExtra("id", data.getId());
                intent.putExtra("nama", data.getName());
                intent.putExtra("merk", data.getBrand());
                intent.putExtra("buy", data.getBuyPrice());
                intent.putExtra("sell", data.getSellPrice());
                intent.putExtra("gambar", data.getImage());

                intent.putExtra("posisi", data.getPlacementPosition());
                intent.putExtra("tempat", data.getPlacementPlace());
                intent.putExtra("tipe", data.getType());

                intent.putExtra("nomor", data.getPlacementNumber());
                intent.putExtra("minimalstock", data.getMinStock());
                intent.putExtra("stock", data.getStock());
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

                Call<ResponseBody> deleteSparepart = apiClient.deleteSparepart(data.getId());
                deleteSparepart.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.code() == 201){
                            Toast.makeText(context.getApplicationContext(), "BERHASIL MENGHAPUS DATA SPAREPART", Toast.LENGTH_SHORT).show();
                            mProgress.hide();
                        }else{
                            Toast.makeText(context.getApplicationContext(), "GAGAL MENGHAPUS DATA SPAREPART", Toast.LENGTH_SHORT).show();
                            mProgress.hide();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(context.getApplicationContext(), "GAGAL HAPUS DATA SPAREPART", Toast.LENGTH_SHORT).show();
                        mProgress.hide();
                    }
                });
                SparepartBundle.remove(ifinal);
                notifyItemRemoved(ifinal);
                notifyItemRangeChanged(ifinal, getItemCount());
            }
        });
    }

    @Override
    public int getItemCount() {
        return SparepartListFilter.size();
    }
}
