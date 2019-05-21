package com.example.dewa732corps.code03.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dewa732corps.code03.Controller.Sparepart;
import com.example.dewa732corps.code03.Fragment.Menu_Pelanggan.ListSparepart;
import com.example.dewa732corps.code03.R;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ListSparepartAdapter extends RecyclerView.Adapter<ListSparepartAdapter.MyViewHolder> {

    private Context context;
    private List<Sparepart> SparepartBundle;
    private List<Sparepart> SparepartFilter;

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView sparepart_name, stock, sell_price;
        CardView cardView;
        ImageView sparepart_image;

        public LinearLayout topcard;

        public MyViewHolder(View v){
            super(v);
            sparepart_name = (TextView) itemView.findViewById(R.id.sparepart_name);
            stock = (TextView) itemView.findViewById(R.id.stock);
            sell_price = (TextView) itemView.findViewById(R.id.sell_price);
            cardView = (CardView) itemView.findViewById(R.id.cardview_id);
            sparepart_image = (ImageView) itemView.findViewById(R.id.sparepart_image);
            topcard = itemView.findViewById(R.id.topcard);
        }
    }
    public ListSparepartAdapter(List<Sparepart> SparepartBundle, Context context) {
        this.context = context;
        this.SparepartBundle = SparepartBundle;
        this.SparepartFilter = SparepartBundle;
    }

    @NonNull
    @Override
    public ListSparepartAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.content_listsparepart, viewGroup, false);
        return new MyViewHolder(v);
    }

    public Filter getFilter(){
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if(charString.isEmpty()) {
                    SparepartFilter = SparepartBundle;
                }
                else {
                    List<Sparepart> filteredList = new ArrayList<>();
                    for (Sparepart obj : SparepartBundle){
                        if(obj.getName().toLowerCase().contains(charString.toLowerCase())){
                            filteredList.add(obj);
                        }
                    }
                    SparepartFilter = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = SparepartFilter;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                SparepartFilter = (ArrayList<Sparepart>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    @Override
    public void onBindViewHolder(@NonNull final ListSparepartAdapter.MyViewHolder myViewHolder, final int i) {
        final Sparepart data = SparepartFilter.get(i);

        myViewHolder.sparepart_name.setText(data.getName());
        myViewHolder.stock.setText("Stok: "+ data.getStock());
        myViewHolder.sell_price.setText("Harga: "+ data.getSellPrice());
        Picasso.get().load("https://sibento.yafetrakan.com/images/" +data.getImage()).memoryPolicy(MemoryPolicy.NO_CACHE).networkPolicy(NetworkPolicy.NO_CACHE).into(myViewHolder.sparepart_image);
    }

    @Override
    public int getItemCount() {
        return SparepartFilter.size();
    }
}