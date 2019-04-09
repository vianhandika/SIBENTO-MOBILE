package com.example.dewa732corps.code03.Fragment.Supplier;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.dewa732corps.code03.Adapter.SparepartAdapter;
import com.example.dewa732corps.code03.Adapter.SupplierAdapter;
import com.example.dewa732corps.code03.Controller.ApiClient;
import com.example.dewa732corps.code03.Controller.SessionController;
import com.example.dewa732corps.code03.Controller.SparepartList;
import com.example.dewa732corps.code03.Controller.SupplierList;
import com.example.dewa732corps.code03.Fragment.Sparepart.SparepartForm;
import com.example.dewa732corps.code03.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SupplierTampil extends Fragment {

    View dashboard;
    FrameLayout framelay;
    Button btnTambahSupplier;
    private RecyclerView rview;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layout;
    private List<SupplierList> supplierLists;
    android.support.v7.widget.Toolbar toolbar;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        dashboard= inflater.inflate(R.layout.menu2_supplier_tampil,container,false);
        setinit();

        Button btnAddSupplier = dashboard.findViewById(R.id.btnAddSupplier);

        btnAddSupplier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.framelay, new SupplierForm());
                transaction.commit();
            }
        });

        rview = dashboard.findViewById(R.id.recyclerViewSupplier);
        rview.setHasFixedSize(true);
        layout = new LinearLayoutManager(getContext());
        rview.setLayoutManager(layout);

        supplierLists = new ArrayList<>();

        Retrofit retrofit= new retrofit2.Retrofit.Builder()
                .baseUrl("https://sibento.yafetrakan.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiClient apiClient = retrofit.create(ApiClient.class);

        Call<SupplierList> supplierGet = apiClient.getSupplier();

        supplierGet.enqueue(new Callback<SupplierList>() {
            @Override
            public void onResponse(Call<SupplierList> call, Response<SupplierList> response) {
                try {
                    adapter = new SupplierAdapter(response.body().getData(),getContext());
                    adapter.notifyDataSetChanged();
//                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
//                    rview.setLayoutManager(mLayoutManager);
//                    rview.setItemAnimator(new DefaultItemAnimator());
                    rview.setAdapter(adapter);
                } catch (Exception e) {
                    Toast.makeText(getContext(), "Tidak Ada Supplier!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SupplierList> call, Throwable t) {
                Toast.makeText(getContext(), "Fail", Toast.LENGTH_SHORT).show();
            }
        });
        return dashboard;
    }
    public void setinit(){
        toolbar = (android.support.v7.widget.Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Manajemen Supplier");
        Button btnAddSparepart = dashboard.findViewById(R.id.btnAddSupplier);
        btnAddSparepart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.framelay, new SparepartForm());
                transaction.commit();
            }
        });
    }
}