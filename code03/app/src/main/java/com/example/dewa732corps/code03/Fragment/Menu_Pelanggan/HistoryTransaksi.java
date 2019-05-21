package com.example.dewa732corps.code03.Fragment.Menu_Pelanggan;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.dewa732corps.code03.Adapter.HistoryAdapter;
import com.example.dewa732corps.code03.Controller.ApiClient;
import com.example.dewa732corps.code03.Controller.History;
import com.example.dewa732corps.code03.Controller.HistoryList;
import com.example.dewa732corps.code03.Fragment.BerandaFragment;
import com.example.dewa732corps.code03.Fragment.CekServiceFragment;
import com.example.dewa732corps.code03.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HistoryTransaksi extends AppCompatActivity {

    Button homeButton;
    RecyclerView rview;
    private HistoryAdapter adapter;
    private RecyclerView.LayoutManager layout;
    private List<History> HistoryBundleFull;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_pelanggan_history_tampil);
        init();

        homeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(HistoryTransaksi.this, BerandaFragment.class);
                startActivity(intent);
            }
        });

        Retrofit retrofit = new retrofit2.Retrofit.Builder()
                .baseUrl("https://sibento.uafetrakan.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiClient apiClient = retrofit.create(ApiClient.class);

        Call<HistoryList> getHistory = apiClient.checkMotor(getIntent().getStringExtra("license_number"), getIntent().getStringExtra("phone_number"));

        getHistory.enqueue(new Callback<HistoryList>() {
            @Override
            public void onResponse(Call<HistoryList> call, Response<HistoryList> response) {
                try{
                    adapter = new HistoryAdapter(response.body());
                    HistoryBundleFull = response.body().getData();

                    rview.setAdapter(adapter);
                }
                catch (Exception e)
                {
                    Intent intent = new Intent (HistoryTransaksi.this, CekServiceFragment.class);
                    Toast.makeText(HistoryTransaksi.this, "Data tidak ditemukan!", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<HistoryList> call, Throwable t) {
                Toast.makeText(HistoryTransaksi.this, "Fail", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void init(){
        rview = findViewById(R.id.recyclerViewCekServiceTampil);
        rview.setHasFixedSize(true);
        layout = new LinearLayoutManager(getApplicationContext());
        rview.setLayoutManager(layout);
        homeButton = findViewById(R.id.btnHome);
    }
}
