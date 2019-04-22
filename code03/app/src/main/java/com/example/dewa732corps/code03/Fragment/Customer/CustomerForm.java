package com.example.dewa732corps.code03.Fragment.Customer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.dewa732corps.code03.Controller.ApiClient;
import com.example.dewa732corps.code03.MainActivity;
import com.example.dewa732corps.code03.R;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CustomerForm extends AppCompatActivity {

    FrameLayout framelay;
    Button btnSaveCustomer,btnCancelCustomer;
    EditText txtNamaCustomer,txtAlamatCustomer,txtNoTelpCustomer;

    private int simpan=0;
    private int editMode=0;

    String selectedId, idCustomer;

    private static final int INTENT_REQUEST_CODE = 100;
    public static final String URL = "https://sibento.yafetrakan.com/api/";

    android.support.v7.widget.Toolbar toolbar;

    protected void onCreate(Bundle savedInstanceState) {  //Fungsi didalamnya dijalankan pertama kali
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu2_customer_form);

        setInit();
        getPutExtra();

        btnSaveCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //Listener button btnSaveSparepart saat di klik
                if(formChecking()==0 && editMode ==0){ //kalau pengecekan form benar dan tidak pada mode edit
                    addCustomer();
                }
                else if (formChecking()==0 && editMode ==1){ //kalau pengecekan form benar dan pada mode edit
                    editCustomer();
                }
            }
        });

        btnCancelCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //Listener button btnSaveSparepart saat di klik
                Toast.makeText(CustomerForm.this, "Batal.", Toast.LENGTH_SHORT).show();
                final Intent intent = new Intent(CustomerForm.this, MainActivity.class);
                intent.putExtra("menuBefore", 4);
                startActivity(intent);
            }
        });
    }

    public void setInit(){ //pendeklarasian objek-objek yang ada di layout

        //depan itu nama form
        txtNamaCustomer = findViewById(R.id.txtNamaCustomer);
        txtAlamatCustomer = findViewById(R.id.txtAlamatCustomer);
        txtNoTelpCustomer = findViewById(R.id.txtNoTelpCustomer);

        btnSaveCustomer = findViewById(R.id.btnSaveCustomer);
        btnCancelCustomer = findViewById(R.id.btnCancelCustomer);
    }

    public void getPutExtra(){ // mendapatkan parsing data dari activity sebelumnya beserta pengesetan form sesuai parsing data
        Intent intent = getIntent();
        simpan = getIntent().getIntExtra("simpan", 0);

//        Log.d("Id Sparepart", String.valueOf(((Intent) intent).getStringExtra("id")));
        if(!String.valueOf(intent.getStringExtra("nama")).equals("null")) {

            String id = intent.getStringExtra("id");
            //Log.d("id",id);
            idCustomer = id;
            String nama = intent.getStringExtra("nama");
            String alamat = intent.getStringExtra("alamat");
            String notelp = intent.getStringExtra("notelp");
//            List<ListSales>

//            Log.d("gambar","https://sibento.yafetrakan.com/"+gambar);

            txtNamaCustomer.setText(nama);
            txtAlamatCustomer.setText(alamat);
            txtNoTelpCustomer.setText(notelp);
//            txtListSales.setList(sales);

            editMode = 1; //pengubahan menjadi mode edit
        }
    }
    private int formChecking(){ //Fungsi Check Form

        String  namaCustomer=txtNamaCustomer .getText().toString(),
                alamatCustomer=txtAlamatCustomer  .getText().toString(),
                noTelpCustomer=txtNoTelpCustomer  .getText().toString();

        if(namaCustomer.isEmpty()){
            txtNamaCustomer.setError("Nama customer diperlukan.");
            txtNamaCustomer.requestFocus();
            return 1;
        }

        if(alamatCustomer.isEmpty()){
            txtAlamatCustomer.setError("Alamat customer diperlukan.");
            txtAlamatCustomer.requestFocus();
            return 1;
        }

        if(noTelpCustomer.isEmpty()){
            txtNoTelpCustomer.setError("No Telepon diperlukan.");
            txtNoTelpCustomer.requestFocus();
            return 1;
        }
        return 0;
    }

    private void addCustomer() { // Fungsi menambahkan supplier

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiClient retrofitInterface = retrofit.create(ApiClient.class);

        RequestBody name_customer =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), txtNamaCustomer.getText().toString());

        RequestBody address_customer =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), txtAlamatCustomer.getText().toString());

        RequestBody phone_number_customer =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), txtNoTelpCustomer.getText().toString());

        Call<ResponseBody> call = retrofitInterface.addCustomer(name_customer, address_customer, phone_number_customer);
//        mProgressBar.setVisibility(View.VISIBLE);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {

//                mProgressBar.setVisibility(View.GONE);

                if (response.isSuccessful()) {

                    ResponseBody responseBody = response.body();
                    Log.d("SUKSES",responseBody.toString());
                    Toast.makeText(CustomerForm.this, "Sukses", Toast.LENGTH_SHORT).show();
                    final Intent intent = new Intent(CustomerForm.this, MainActivity.class);
                    intent.putExtra("menuBefore", 4);
                    startActivity(intent);
//                    mBtImageShow.setVisibility(View.VISIBLE);
//                     mImageUrl = URL + responseBody.getPath();
//                    Snackbar.make(findViewById(R.id.content), responseBody.getMessage(),Snackbar.LENGTH_SHORT).show();

                } else {
                    Log.d( "onResponse: ",response.message());
                    Toast.makeText(CustomerForm.this, "Gagal", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("onFailure: ",t.toString());

//                mProgressBar.setVisibility(View.GONE);
//                Log.d(TAG, "onFailure: "+t.getLocalizedMessage());
            }
        });
    }

    private void editCustomer() { // Fungsi mengedit Data Supplier

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiClient retrofitInterface = retrofit.create(ApiClient.class);

        Integer id_customer = Integer.parseInt(idCustomer);
        String name_customer = txtNamaCustomer .getText().toString();
        String address_customer=txtAlamatCustomer.getText().toString();
        String phone_number_customer=txtNoTelpCustomer.getText().toString();


        Call<ResponseBody> call = retrofitInterface.editCustomer(id_customer, name_customer, address_customer, phone_number_customer);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {


                if (response.isSuccessful()) {
                    ResponseBody responseBody = response.body();
//                    Log.d("SUKSES UPDATE DATA",responseBody.toString());
                    Toast.makeText(CustomerForm.this, "SUKSES UPDATE CUSTOMER", Toast.LENGTH_SHORT).show();
                    final Intent intent = new Intent(CustomerForm.this, MainActivity.class);
                    intent.putExtra("menuBefore", 4);
                    startActivity(intent);

                } else {
                    Log.d( "onResponse: ",response.message());
                    Toast.makeText(CustomerForm.this, "Gagal Update Data Customer", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("onFailure: ",t.toString());

//                mProgressBar.setVisibility(View.GONE);
//                Log.d(TAG, "onFailure: "+t.getLocalizedMessage());
            }
        });

    }
}