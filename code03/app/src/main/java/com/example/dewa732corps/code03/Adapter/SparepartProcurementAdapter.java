package com.example.dewa732corps.code03.Adapter;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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
import com.example.dewa732corps.code03.Controller.SparepartProcurement;
import com.example.dewa732corps.code03.Fragment.Sparepart.SparepartForm;
import com.example.dewa732corps.code03.Fragment.Sparepart_Procurement.SparepartProcurementForm;
import com.example.dewa732corps.code03.R;

import okhttp3.ResponseBody;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.ArrayList;
import java.util.List;

public class SparepartProcurementAdapter extends RecyclerView.Adapter<SparepartProcurementAdapter.MyViewHolder>{
    private List<SparepartProcurement> SparepartProcurementBundle = new ArrayList<>();
    private List<SparepartProcurement> SparepartProcurementListFilter = new ArrayList<>();

    private Context context;
    ProgressDialog mProgress;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView NoId, Date, Supplier, Sales, StatusProcurement;
        public ImageView btnEdit, btnDelete;

        public MyViewHolder(View v) {
            super(v);
            NoId = v.findViewById(R.id.txtNoIdSparepartProcurement);
            Date = v.findViewById(R.id.txtDate);
            Supplier = v.findViewById(R.id.txtNameSupplier);
            Sales = v.findViewById(R.id.txtNameSales);
            StatusProcurement = v.findViewById(R.id.txtStatusProcess);
            btnEdit = v.findViewById(R.id.btnEditSparepartProcurementTampil);
            btnDelete = v.findViewById(R.id.btnDeleteSparepartProcurementTampil);
        }
    }

    public SparepartProcurementAdapter(List<SparepartProcurement> SparepartProcurementBundle , Context context) {
        this.SparepartProcurementBundle = SparepartProcurementBundle;
        this.context = context;
        this.SparepartProcurementListFilter = SparepartProcurementBundle;
        mProgress = new ProgressDialog(context);
        mProgress.setMessage("Loading");
    }

    @NonNull
    @Override
    public SparepartProcurementAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        // create a new view
        View v = (View) LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.content_sparepartprocurement_tampil, viewGroup, false);
        return new SparepartProcurementAdapter.MyViewHolder(v);
    }

    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    SparepartProcurementListFilter = SparepartProcurementBundle;
                } else {
                    List<SparepartProcurement> filteredList = new ArrayList<>();
                    for (SparepartProcurement obj : SparepartProcurementBundle) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (obj.getNameSupplier().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(obj);
                        }
                    }
                    SparepartProcurementListFilter = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = SparepartProcurementListFilter;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                SparepartProcurementListFilter = (ArrayList<SparepartProcurement>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    @Override
    public void onBindViewHolder(@NonNull SparepartProcurementAdapter.MyViewHolder vh, final int i) {

//        final SparepartProcurement data = SparepartProcurementBundle.get(i);
        final SparepartProcurement data = SparepartProcurementListFilter.get(i);

        final int ifinal = vh.getAdapterPosition();
        vh.NoId.setText(data.getIdProcurement().toString());
        vh.Date.setText(data.getDateProcurement());
        vh.Supplier.setText(data.getNameSupplier());
        vh.Sales.setText(data.getNameSales());
        vh.StatusProcurement.setText(data.getStatusProcurement());
        if(data.getStatusProcurement().equalsIgnoreCase("Finish"))
        {
            vh.StatusProcurement.setBackgroundColor(Color.GREEN);
        }
        else{
            vh.StatusProcurement.setBackgroundColor(Color.RED);

        }

        vh.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), SparepartProcurementForm.class);
                intent.putExtra("mode", i);

                intent.putExtra("id", data.getIdProcurement().toString());
                intent.putExtra("date", data.getDateProcurement());
                intent.putExtra("id_sales", data.getIdSales().toString());
                intent.putExtra("status_procurement", data.getStatusProcurement());
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

                Call<ResponseBody> deleteSparepartProcurement = apiClient.deleteSparepartProcurement(data.getIdProcurement());
                deleteSparepartProcurement.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.code() == 200){
                            Toast.makeText(context.getApplicationContext(), "BERHASIL MENGHAPUS DATA SPAREPART PROCUREMENT", Toast.LENGTH_SHORT).show();
                            mProgress.hide();
                        }else{
                            Toast.makeText(context.getApplicationContext(), "GAGAL MENGHAPUS DATA SPAREPART PROCUREMENT", Toast.LENGTH_SHORT).show();
                            mProgress.hide();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(context.getApplicationContext(), "GAGAL HAPUS DATA SPAREPART PROCUREMENT", Toast.LENGTH_SHORT).show();
                        mProgress.hide();
                    }
                });
                SparepartProcurementBundle.remove(ifinal);
                notifyItemRemoved(ifinal);
                notifyItemRangeChanged(ifinal, getItemCount());
            }
        });
    }

    @Override
    public int getItemCount() {
        return SparepartProcurementListFilter.size();
    }
}
