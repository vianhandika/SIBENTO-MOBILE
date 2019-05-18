package com.example.dewa732corps.code03.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dewa732corps.code03.Controller.ApiClient;
import com.example.dewa732corps.code03.Controller.SparepartProcurement;
import com.example.dewa732corps.code03.Controller.SparepartProcurementDetail;
import com.example.dewa732corps.code03.Fragment.Sparepart_Procurement.SparepartProcurementForm;
import com.example.dewa732corps.code03.R;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SparepartProcurementDetailAdapter extends RecyclerView.Adapter<SparepartProcurementDetailAdapter.MyViewHolder> {
    private List<SparepartProcurementDetail> DetailProcurementBundle = new ArrayList<>();
    private List<SparepartProcurementDetail> DetailProcurementListFilter = new ArrayList<>();

    private Context context;
    ProgressDialog mProgress;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView Name, Amount, Subtotal, Price, StatusProcurement;
        public Button btnPlus, btnMin;
        public ImageView btnDel;

        public MyViewHolder(View v) {
            super(v);
            Name = v.findViewById(R.id.ProcNameSparepart);
            Amount = v.findViewById(R.id.ProcAmountSparepart);
            Subtotal = v.findViewById(R.id.ProcSubtotalSparepart);
//            Price= v.findViewById(R.id.ProcPriceSparepart);
            btnPlus = v.findViewById(R.id.PlusStock);
            btnMin = v.findViewById(R.id.MinStock);
            btnDel = v.findViewById(R.id.btnDelSparepart);


        }
    }

    public SparepartProcurementDetailAdapter(List<SparepartProcurementDetail> DetailProcurementBundle , Context context) {
        this.DetailProcurementBundle = DetailProcurementBundle;
        this.context = context;
        this.DetailProcurementListFilter = DetailProcurementBundle;

        mProgress = new ProgressDialog(context);
        mProgress.setMessage("Loading");
    }

    @NonNull
    @Override
    public SparepartProcurementDetailAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        // create a new view
        View v = (View) LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.content_sparepartprocurement_form, viewGroup, false);
        return new SparepartProcurementDetailAdapter.MyViewHolder(v);
    }

    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    DetailProcurementListFilter = DetailProcurementBundle;
                } else {
                    List<SparepartProcurementDetail> filteredList = new ArrayList<>();
                    for (SparepartProcurementDetail obj : DetailProcurementBundle) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (obj.getNameSparepart().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(obj);
                        }
                    }

                    DetailProcurementListFilter = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = DetailProcurementListFilter;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                DetailProcurementListFilter = (ArrayList<SparepartProcurementDetail>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    @Override
    public void onBindViewHolder(@NonNull SparepartProcurementDetailAdapter.MyViewHolder vh, final int i) {

//        final SparepartProcurement data = SparepartProcurementBundle.get(i);
        final SparepartProcurementDetail data = DetailProcurementListFilter.get(i);

        final int ifinal = vh.getAdapterPosition();
        vh.Name.setText(data.getNameSparepart());
        vh.Amount.setText(data.getAmount().toString());
        vh.Subtotal.setText(data.getSubtotal().toString());

        vh.btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.setAmount(data.getAmount()+1);
                data.setSubtotal(data.getPrice() * data.getAmount());
                notifyDataSetChanged();
            }
        });

        vh.btnMin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.setAmount(data.getAmount()+-1);
                data.setSubtotal(data.getPrice() * data.getAmount());
                notifyDataSetChanged();
            }
        });
        vh.btnDel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    DetailProcurementBundle.remove(ifinal);
                    notifyItemRemoved(ifinal);
                    notifyItemRangeChanged(ifinal, getItemCount());
                }
        });

    }

    @Override
    public int getItemCount() {
        return DetailProcurementListFilter.size();
    }
}
