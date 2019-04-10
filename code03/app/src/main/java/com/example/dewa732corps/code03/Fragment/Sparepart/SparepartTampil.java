package com.example.dewa732corps.code03.Fragment.Sparepart;

import android.app.ProgressDialog;
import android.content.Intent;
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
import com.example.dewa732corps.code03.Controller.ApiClient;
import com.example.dewa732corps.code03.Controller.SessionController;
import com.example.dewa732corps.code03.Controller.SparepartList;
import com.example.dewa732corps.code03.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SparepartTampil extends Fragment {

    View dashboard;
    FrameLayout framelay;
    Button btnTambahSparepart;
    android.support.v7.widget.Toolbar toolbar;

    private RecyclerView rview;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layout;
    private List<SparepartList> sparepartLists;

    SessionController session;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        dashboard= inflater.inflate(R.layout.menu2_sparepart_tampil,container,false);
        setinit();

        rview = dashboard.findViewById(R.id.recyclerViewSparepart);
        rview.setHasFixedSize(true);
        layout = new LinearLayoutManager(getContext());
        rview.setLayoutManager(layout);

        sparepartLists = new ArrayList<>();

        session = new SessionController(getContext());
        session.checkLogin();

        Retrofit retrofit= new retrofit2.Retrofit.Builder()
                .baseUrl("http://10.53.12.230/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiClient apiClient = retrofit.create(ApiClient.class);

        Call<SparepartList> sparepartGet = apiClient.getSparepart();

        sparepartGet.enqueue(new Callback<SparepartList>() {
            @Override
            public void onResponse(Call<SparepartList> call, Response<SparepartList> response) {
                try {
                    adapter = new SparepartAdapter(response.body().getData(),getContext());
                    adapter.notifyDataSetChanged();
//                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
//                    rview.setLayoutManager(mLayoutManager);
//                    rview.setItemAnimator(new DefaultItemAnimator());
                    rview.setAdapter(adapter);
                } catch (Exception e) {
                    Toast.makeText(getContext(), "Tidak Ada Sparepart!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SparepartList> call, Throwable t) {
                Toast.makeText(getContext(), "Fail", Toast.LENGTH_SHORT).show();
            }
        });
        return dashboard;
    }

    public void setinit(){
        toolbar = (android.support.v7.widget.Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Manajemen Sparepart");
        Button btnTambahSparepart = dashboard.findViewById(R.id.btnTambahSparepart);
        btnTambahSparepart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SparepartForm.class);
                startActivity(intent);
            }
        });
    }
}