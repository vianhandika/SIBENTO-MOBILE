package com.example.dewa732corps.code03.Fragment.Customer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.dewa732corps.code03.Adapter.CustomerAdapter;
import com.example.dewa732corps.code03.Controller.ApiClient;
import com.example.dewa732corps.code03.Controller.Customer;
import com.example.dewa732corps.code03.Controller.CustomerList;
import com.example.dewa732corps.code03.Controller.SessionController;
import com.example.dewa732corps.code03.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CustomerTampil extends Fragment {

    View dashboard;
    FrameLayout framelay;
    Button btnTambahCustomer;
    android.support.v7.widget.Toolbar toolbar;
    private SearchView search;

    private RecyclerView rview;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layout;
    private List<CustomerList> customerLists;
    private List<Customer> customerData;

    CustomerAdapter sAdapter;
    ProgressDialog mProgress;
    ResponseBody AllData;

    SessionController session;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        dashboard= inflater.inflate(R.layout.menu_customerservice_customer_tampil,container,false);
        setinit();

        mProgress = new ProgressDialog(getContext());
        mProgress.setMessage("Loading Data");
        mProgress.show();

        rview = dashboard.findViewById(R.id.recyclerViewCustomer);
        rview.setHasFixedSize(true);
        layout = new LinearLayoutManager(getContext());
        rview.setLayoutManager(layout);

        customerLists = new ArrayList<>();

        session = new SessionController(getContext());
        session.checkLogin();

        Retrofit retrofit= new retrofit2.Retrofit.Builder()
                .baseUrl("http://10.53.2.0/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiClient apiClient = retrofit.create(ApiClient.class);

        Call<CustomerList> customerGet = apiClient.getCustomer();

        customerGet.enqueue(new Callback<CustomerList>() {
            @Override
            public void onResponse(Call<CustomerList> call, Response<CustomerList> response) {
                try {
                    customerData = response.body().getData();
                    sAdapter = new CustomerAdapter(response.body().getData(),getContext());
                    sAdapter.notifyDataSetChanged();

                    rview.setAdapter(sAdapter);

                    mProgress.hide();

                } catch (Exception e) {
                    Toast.makeText(getContext(), "Tidak Ada Customer!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CustomerList> call, Throwable t) {
                Toast.makeText(getContext(), "Fail", Toast.LENGTH_SHORT).show();
                mProgress.hide();
            }
        });
        return dashboard;
    }

    public void setinit(){
        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Manajemen Customer");
        Button btnTambahCustomer = dashboard.findViewById(R.id.btnTambahCustomer);

        search = dashboard.findViewById(R.id.searchBarCustomer);

        btnTambahCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CustomerForm.class);
                startActivity(intent);
            }
        });
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                Log.d("onQueryTextSubmit: ",query);
                //SAdapter.filter(query);
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d("onQueryTextChange: ","true");
                String text = newText.toLowerCase(Locale.getDefault());
                sAdapter.getFilter().filter(text);
//                adapter = SAdapter;
//                rview.setAdapter(adapter);
                return true;
            }
        });
    }
}