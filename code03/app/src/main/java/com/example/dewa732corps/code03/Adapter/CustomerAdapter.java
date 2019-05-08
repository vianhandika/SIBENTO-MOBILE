package com.example.dewa732corps.code03.Adapter;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dewa732corps.code03.Controller.ApiClient;
import com.example.dewa732corps.code03.Controller.Customer;
import com.example.dewa732corps.code03.Fragment.Customer.CustomerForm;
import com.example.dewa732corps.code03.Fragment.Motor_Customer.MotorCustomerTampil;
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

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.MyViewHolder>{
    private List<Customer> CustomerBundle = new ArrayList<>();
    private List<Customer> CustomerListFilter = new ArrayList<>();

    private Context context;
    ProgressDialog mProgress;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView Name, Address, PhoneNumber;
        public ImageView btnMotorCustomer, btnEdit, btnDelete;
//        public LinearLayout topWraper;
//        public LinearLayout bottomWraper;

        public MyViewHolder(View v) {
            super(v);
            Name = v.findViewById(R.id.txtNameCustomer);
            Address = v.findViewById(R.id.txtAddressCustomer);
            PhoneNumber = v.findViewById(R.id.txtPhoneNumberCustomer);

            btnMotorCustomer = v.findViewById(R.id.btnMotorCustomer);
            btnEdit = v.findViewById(R.id.btnEditCustomer);
            btnDelete = v.findViewById(R.id.btnDeleteCustomer);
        }
    }

    public CustomerAdapter(List<Customer> CustomerBundle , Context context) {
        this.CustomerBundle = CustomerBundle;
        this.context = context;

        this.CustomerListFilter = CustomerBundle;
        mProgress = new ProgressDialog(context);
        mProgress.setMessage("Loading");
    }

    @NonNull
    @Override
    public CustomerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        // create a new view
        View v = (View) LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.content_customer, viewGroup, false);
        return new CustomerAdapter.MyViewHolder(v);
    }

    public void filter(String charText) {
        Log.d( "filter: ", charText);

        charText = charText.toLowerCase(Locale.getDefault());
        CustomerListFilter.clear();
        if (charText.length() == 0) {
            CustomerListFilter.addAll(CustomerBundle);
        }
        else
        {
            for (Customer obj : CustomerBundle) {
                if (obj.getName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    CustomerListFilter.add(obj);
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
                    CustomerListFilter = CustomerBundle;
                } else {
                    List<Customer> filteredList = new ArrayList<>();
                    for (Customer obj : CustomerBundle) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (obj.getName().toLowerCase().contains(charString.toLowerCase())) // || obj.getId().contains(charString) ini error di constain nya karena integer
                        {
                            Log.d("search: ",charString.toLowerCase());
                            Log.d( "Nama: ", obj.getName());
                            filteredList.add(obj);
                        }
                    }

                    CustomerListFilter = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = CustomerListFilter;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                CustomerListFilter = (ArrayList<Customer>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerAdapter.MyViewHolder vh, final int i) {

        final Customer data = CustomerListFilter.get(i);

        final int ifinal = vh.getAdapterPosition();

        vh.Name.setText(data.getName());
        vh.Address.setText(data.getAddress());
        vh.PhoneNumber.setText(data.getPhoneNumber());

        vh.btnMotorCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MotorCustomerTampil.class);
//                intent.putExtra("mode", i);

                intent.putExtra("id_customer", data.getId().toString());
//                intent.putExtra("listsales", data.getSales());
                v.getContext().startActivity(intent);
            }
        });

        vh.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), CustomerForm.class);
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
                        .baseUrl("http://10.53.2.0/api/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                ApiClient apiClient = retrofit.create(ApiClient.class);

                Call<ResponseBody> deleteCustomer = apiClient.deleteCustomer(data.getId());
                deleteCustomer.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.code() == 201){
                            Toast.makeText(context.getApplicationContext(), "BERHASIL MENGHAPUS DATA CUSTOMER", Toast.LENGTH_SHORT).show();
                            mProgress.hide();
                        }else{
                            Toast.makeText(context.getApplicationContext(), "GAGAL MENGHAPUS DATA CUSTOMER", Toast.LENGTH_SHORT).show();
                            mProgress.hide();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(context.getApplicationContext(), "GAGAL HAPUS DATA CUSTOMER", Toast.LENGTH_SHORT).show();
                        mProgress.hide();
                    }
                });
                CustomerBundle.remove(ifinal);
                notifyItemRemoved(ifinal);
                notifyItemRangeChanged(ifinal, getItemCount());
            }
        });
    }

    @Override
    public int getItemCount() {
        return CustomerListFilter.size();
    }
}
