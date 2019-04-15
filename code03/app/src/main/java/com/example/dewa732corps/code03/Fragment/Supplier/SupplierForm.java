package com.example.dewa732corps.code03.Fragment.Supplier;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.dewa732corps.code03.Controller.ApiClient;
import com.example.dewa732corps.code03.Controller.RetrofitClient;
import com.example.dewa732corps.code03.Controller.Sparepart;
import com.example.dewa732corps.code03.Controller.SparepartType;
import com.example.dewa732corps.code03.Controller.SparepartTypeList;
import com.example.dewa732corps.code03.Controller.Supplier;
import com.example.dewa732corps.code03.Controller.SupplierType;
import com.example.dewa732corps.code03.Controller.SupplierTypeList;
import com.example.dewa732corps.code03.Fragment.Sparepart.SparepartForm;
import com.example.dewa732corps.code03.Fragment.Supplier.SupplierForm;
import com.example.dewa732corps.code03.Fragment.Supplier.SupplierTampil;
import com.example.dewa732corps.code03.MainActivity;
import com.example.dewa732corps.code03.R;
import com.google.gson.Gson;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.app.Activity.RESULT_OK;

public class SupplierForm extends AppCompatActivity {

    FrameLayout framelay;
    Button btnSaveSupplier,btnCancelSupplier;
    EditText txtNamaSupplier,txtAlamatSupplier,txtNoTelpSupplier;

    private int simpan=0;
    private int editMode=0;

    String selectedId, idSupplier;
    private List<String> listNameType = new ArrayList<String>();
    private List<String> listIdType = new ArrayList<String>();
    private List<String> listPosisi = new ArrayList<String>();
    private List<String> listTempat = new ArrayList<String>();

    private static final int INTENT_REQUEST_CODE = 100;
    public static final String URL = "https://sibento.yafetrakan.com/api/";

    android.support.v7.widget.Toolbar toolbar;

    protected void onCreate(Bundle savedInstanceState) {  //Fungsi didalamnya dijalankan pertama kali
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu2_supplier_form);

        setInit();
        getPutExtra();

        btnSaveSupplier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //Listener button btnSaveSparepart saat di klik
                if(formChecking()==0 && editMode ==0){ //kalau pengecekan form benar dan tidak pada mode edit
                    addSupplier();
                }
                else if (formChecking()==0 && editMode ==1){ //kalau pengecekan form benar dan pada mode edit
                    editSupplier();
                }
            }
        });

        btnCancelSupplier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //Listener button btnSaveSparepart saat di klik
                Toast.makeText(SupplierForm.this, "Batal.", Toast.LENGTH_SHORT).show();
                final Intent intent = new Intent(SupplierForm.this, MainActivity.class);
                intent.putExtra("menuBefore", 2);
                startActivity(intent);
            }
        });
    }

    public void setInit(){ //pendeklarasian objek-objek yang ada di layout

        //depan itu nama form
        txtNamaSupplier = findViewById(R.id.txtNamaSupplier);
        txtAlamatSupplier = findViewById(R.id.txtAlamatSupplier);
        txtNoTelpSupplier = findViewById(R.id.txtNoTelpSupplier);

        btnSaveSupplier = findViewById(R.id.btnSaveSupplier);
        btnCancelSupplier = findViewById(R.id.btnCancelSupplier);
    }

    public void getPutExtra(){ // mendapatkan parsing data dari activity sebelumnya beserta pengesetan form sesuai parsing data
        Intent intent = getIntent();
        simpan = getIntent().getIntExtra("simpan", 0);

//        Log.d("Id Sparepart", String.valueOf(((Intent) intent).getStringExtra("id")));
        if(!String.valueOf(intent.getStringExtra("nama")).equals("null")) {

            String id = intent.getStringExtra("id");
            //Log.d("id",id);
            idSupplier = id;
            String nama = intent.getStringExtra("nama");
            String alamat = intent.getStringExtra("alamat");
            String notelp = intent.getStringExtra("notelp");
//            List<ListSales>

//            Log.d("gambar","https://sibento.yafetrakan.com/"+gambar);

            txtNamaSupplier.setText(nama);
            txtAlamatSupplier.setText(alamat);
            txtNoTelpSupplier.setText(notelp);
//            txtListSales.setList(sales);

            editMode = 1; //pengubahan menjadi mode edit
            Log.d("Coba","ok");
        }
    }
    private int formChecking(){ //Fungsi Check Form

        String  namaSupplier=txtNamaSupplier .getText().toString(),
                alamatSupplier=txtAlamatSupplier  .getText().toString(),
                noTelpSupplier=txtNoTelpSupplier  .getText().toString();

        if(namaSupplier.isEmpty()){
            txtNamaSupplier.setError("Nama supplier diperlukan.");
            txtNamaSupplier.requestFocus();
            return 1;
        }

        if(alamatSupplier.isEmpty()){
            txtAlamatSupplier.setError("Alamat supplier diperlukan.");
            txtAlamatSupplier.requestFocus();
            return 1;
        }

        if(noTelpSupplier.isEmpty()){
            txtNoTelpSupplier.setError("No Telepon diperlukan.");
            txtNoTelpSupplier.requestFocus();
            return 1;
        }
        return 0;
    }

    private void addSupplier() { // Fungsi menambahkan supplier

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiClient retrofitInterface = retrofit.create(ApiClient.class);

        RequestBody name_supplier =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), txtNamaSupplier.getText().toString());

        RequestBody address_supplier =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), txtAlamatSupplier.getText().toString());

        RequestBody phone_number_supplier =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), txtNoTelpSupplier.getText().toString());

        Call<ResponseBody> call = retrofitInterface.addSupplier(name_supplier, address_supplier, phone_number_supplier);
//        mProgressBar.setVisibility(View.VISIBLE);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {

//                mProgressBar.setVisibility(View.GONE);

                if (response.isSuccessful()) {

                    ResponseBody responseBody = response.body();
                    Log.d("SUKSES",responseBody.toString());
                    Toast.makeText(SupplierForm.this, "Sukses", Toast.LENGTH_SHORT).show();
                    final Intent intent = new Intent(SupplierForm.this, MainActivity.class);
                    intent.putExtra("menuBefore", 2);
                    startActivity(intent);
//                    mBtImageShow.setVisibility(View.VISIBLE);
//                     mImageUrl = URL + responseBody.getPath();
//                    Snackbar.make(findViewById(R.id.content), responseBody.getMessage(),Snackbar.LENGTH_SHORT).show();

                } else {
                    Log.d( "onResponse: ",response.message());
                    Toast.makeText(SupplierForm.this, "Gagal", Toast.LENGTH_SHORT).show();

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

    private void editSupplier() { // Fungsi mengedit Data Supplier

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiClient retrofitInterface = retrofit.create(ApiClient.class);

        Integer id_supplier = Integer.parseInt(idSupplier);
        String name_supplier = txtNamaSupplier .getText().toString();
        String address_supplier=txtAlamatSupplier.getText().toString();
        String phone_number_supplier=txtNoTelpSupplier.getText().toString();


        Call<ResponseBody> call = retrofitInterface.editSupplier(id_supplier, name_supplier, address_supplier, phone_number_supplier);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {


                if (response.isSuccessful()) {
                    ResponseBody responseBody = response.body();
//                    Log.d("SUKSES UPDATE DATA",responseBody.toString());
                    Toast.makeText(SupplierForm.this, "SUKSES UPDATE SUPPLIER", Toast.LENGTH_SHORT).show();
                    final Intent intent = new Intent(SupplierForm.this, MainActivity.class);
                    intent.putExtra("menuBefore", 2);
                    startActivity(intent);

                } else {
                    Log.d( "onResponse: ",response.message());
                    Toast.makeText(SupplierForm.this, "Gagal Update Data Supplier", Toast.LENGTH_SHORT).show();
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