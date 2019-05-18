package com.example.dewa732corps.code03.Adapter;


import android.app.ProgressDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dewa732corps.code03.Controller.TransactionSparepart;
import com.example.dewa732corps.code03.Controller.TransactionSparepart;
import com.example.dewa732corps.code03.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TransactionDetailSparepartAdapter  extends RecyclerView.Adapter<TransactionDetailSparepartAdapter.MyViewHolder> {
    private List<TransactionSparepart> TransactionSparepartBundle = new ArrayList<>();
    private List<TransactionSparepart> TransactionSparepartListFilter = new ArrayList<>();
    private List<TransactionSparepart> Detail;

    private Context context;
    ProgressDialog mProgress;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView sparepart_name, amount, sub_total;
        public ImageView btnEdit, btnDelete;

        public MyViewHolder(View v) {
            super(v);
            sparepart_name = v.findViewById(R.id.txtNamaSparepart_TransactionDetailSparepart);
            amount = v.findViewById(R.id.txtJumlah_TransactionDetailSparepart);
            sub_total = v.findViewById(R.id.txtSubTotal_TransactionDetailSparepart);
        }
    }

    public TransactionDetailSparepartAdapter(List<TransactionSparepart> TransactionSparepartBundle, Context context) {
        this.TransactionSparepartBundle = TransactionSparepartBundle;
        this.TransactionSparepartListFilter = TransactionSparepartBundle;
        this.context = context;

        mProgress = new ProgressDialog(context);
        mProgress.setMessage("Loading");
    }

    @NonNull
    @Override
    public TransactionDetailSparepartAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        // create a new view
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.content_transaction_sparepartdetail, viewGroup, false);
        return new TransactionDetailSparepartAdapter.MyViewHolder(v);
    }

    public void filter(String charText) {
        Log.d( "filter: ", charText);

        charText = charText.toLowerCase(Locale.getDefault());
        TransactionSparepartListFilter.clear();
        if (charText.length() == 0) {
            TransactionSparepartListFilter.addAll(TransactionSparepartBundle);
        }
        else
        {
            for (TransactionSparepart obj : TransactionSparepartBundle) {
                if (obj.getNameSparepart().toLowerCase(Locale.getDefault()).contains(charText)) {
                    TransactionSparepartListFilter.add(obj);
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
                    TransactionSparepartListFilter = TransactionSparepartBundle;
                } else {
                    List<TransactionSparepart> filteredList = new ArrayList<>();
                    for (TransactionSparepart obj : TransactionSparepartBundle) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (obj.getNameSparepart().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(obj);
                        }
                    }

                    TransactionSparepartListFilter = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = TransactionSparepartListFilter;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                TransactionSparepartListFilter = (ArrayList<TransactionSparepart>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    @Override
    public void onBindViewHolder(@NonNull final TransactionDetailSparepartAdapter.MyViewHolder vh, final int i) {

        final TransactionSparepart data = TransactionSparepartListFilter.get(i);

        final int ifinal = vh.getAdapterPosition();

        vh.sparepart_name.setText(data.getNameSparepart());
        vh.amount.setText(data.getAmountTransactionSparepart().toString());
        vh.sub_total.setText(data.getSubtotalTransactionSparepart().toString());
    }

    @Override
    public int getItemCount() {
        return TransactionSparepartListFilter.size();
    }
}
