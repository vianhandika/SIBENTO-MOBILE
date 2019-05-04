package com.example.dewa732corps.code03.Fragment.Motor_Customer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.dewa732corps.code03.Adapter.MotorCustomerAdapter;
import com.example.dewa732corps.code03.Controller.ApiClient;
import com.example.dewa732corps.code03.Controller.MotorCustomer;
import com.example.dewa732corps.code03.Controller.MotorCustomerList;
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

public class MotorCustomerTampil extends AppCompatActivity {
    FrameLayout framelay;
    Button btnTambahMotorCustomer;
    android.support.v7.widget.Toolbar toolbar;
    private SearchView search;

    private RecyclerView rview;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layout;
    private List<MotorCustomerList> motorCustomerLists;
    private List<MotorCustomer> motorCustomerData=new ArrayList<>();

    private List<MotorCustomer> motorCustomerFilter=new ArrayList<>();

    MotorCustomerAdapter sAdapter;
    String id_customer;
    ProgressDialog mProgress;
    ResponseBody AllData;

    SessionController session;

    public void onCreate(Bundle savedInstanceState)  {
        //return super.onCreateView(inflater, container, savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_customerservice_motorcustomer_tampil);

        setinit();

        mProgress = new ProgressDialog(MotorCustomerTampil.this);
        mProgress.setMessage("Loading Data");
        mProgress.show();

        rview = findViewById(R.id.recyclerViewMotorCustomer);
        rview.setHasFixedSize(true);
        layout = new LinearLayoutManager(this);
        rview.setLayoutManager(layout);

        motorCustomerLists = new ArrayList<>();

        session = new SessionController(this);
        session.checkLogin();

        Retrofit retrofit= new retrofit2.Retrofit.Builder()
                .baseUrl("https://sibento.yafetrakan.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiClient apiClient = retrofit.create(ApiClient.class);

        Call<MotorCustomerList> motorCustomerGet = apiClient.getMotorCustomer();
//        Log.d("a",salesGet.toString());
        motorCustomerGet.enqueue(new Callback<MotorCustomerList>() {
            @Override
            public void onResponse(Call<MotorCustomerList> call, Response<MotorCustomerList> response) {
                try {
                    getPutExtra();
                    Log.d( "id Customer: ",id_customer);
                    motorCustomerData = response.body().getData();

                    for (MotorCustomer motor : motorCustomerData) { //INI FILTER UNTUK PER ID
                        if (motor.getIdCustomer().toString().equalsIgnoreCase(id_customer)) {
                            motorCustomerFilter.add(motor);
                        }
                    }
                    sAdapter = new MotorCustomerAdapter(motorCustomerFilter,MotorCustomerTampil.this); //response.body().getData()
                    sAdapter.notifyDataSetChanged();
                    rview.setAdapter(sAdapter);

                    mProgress.hide();

                } catch (Exception e) {
                    Toast.makeText(MotorCustomerTampil.this, "Tidak Ada Motor Customer!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MotorCustomerList> call, Throwable t) {
                Toast.makeText(MotorCustomerTampil.this, "Fail", Toast.LENGTH_SHORT).show();
                mProgress.hide();
            }
        });
        mProgress.hide();
        mProgress.dismiss();
    }

    public void setinit(){
        Button btnTambahMotorCustomer = findViewById(R.id.btnTambahMotorCustomer);

        search = findViewById(R.id.searchBarMotorCustomer);

        btnTambahMotorCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MotorCustomerTampil.this, MotorCustomerForm.class);
                intent.putExtra("id_customer", id_customer);
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
    public void getPutExtra(){ // mendapatkan parsing data dari activity sebelumnya beserta pengesetan form sesuai parsing data
        Intent intent = getIntent();

        if(!String.valueOf(intent.getStringExtra("id_customer")).equals("null")) {
            String id = intent.getStringExtra("id_customer");
            //Log.d("id",id);
            id_customer = id;
        }
    }


}