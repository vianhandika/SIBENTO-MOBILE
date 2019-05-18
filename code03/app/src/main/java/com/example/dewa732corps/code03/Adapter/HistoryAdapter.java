package com.example.dewa732corps.code03.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dewa732corps.code03.Controller.History;
import com.example.dewa732corps.code03.Controller.HistoryList;
import com.example.dewa732corps.code03.R;

import org.w3c.dom.Text;

public class
HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyViewHolder>{
    private HistoryList HistoryBundle;
    private Context context;

    public HistoryAdapter(HistoryList HistoryBundle) {
        this.HistoryBundle = HistoryBundle;
        this.context = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView idTransaction, paid, status;

        public MyViewHolder(View v){
            super(v);
            idTransaction = v.findViewById(R.id.txtIdTransaksi_CekService);
            paid = v.findViewById(R.id.txtStatusPembayaran_CekService);
            status = v.findViewById(R.id.txtProsesStatus_CekService);
        }
    }

    public HistoryAdapter(HistoryList HistoryBundle, Context context){
        this.HistoryBundle = HistoryBundle;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.content_cekservice, viewGroup, false);
        return new HistoryAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        final History data = HistoryBundle.getData().get(i);
        final int ifinal = myViewHolder.getAdapterPosition();
        myViewHolder.idTransaction.setText(data.getIdTransaction());
        myViewHolder.paid.setText(data.getTotalTransaction());
        myViewHolder.status.setText(data.getStatusPaid());
        if(data.getStatusPaid().equalsIgnoreCase("On Progress")){
            myViewHolder.status.setBackgroundColor(Color.parseColor("#f6d82d"));
        }
        else if(data.getStatusPaid().equalsIgnoreCase("unprocessed"))
        {
            myViewHolder.status.setBackgroundColor(Color.parseColor("#f62d30"));
        }
        else if(data.getStatusPaid().equalsIgnoreCase("finish"))
        {
            myViewHolder.status.setBackgroundColor(Color.parseColor("#45f62d"));
        }
    }

    @Override
    public int getItemCount(){
        return HistoryBundle.getData().size();
    }
}
