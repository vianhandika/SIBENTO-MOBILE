package com.example.dewa732corps.code03.Fragment.Supplier;

import android.app.Activity;
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
import com.example.dewa732corps.code03.Controller.Supplier;
import com.example.dewa732corps.code03.Fragment.Supplier.SupplierForm;
import com.example.dewa732corps.code03.Fragment.Supplier.SupplierTampil;
import com.example.dewa732corps.code03.MainActivity;
import com.example.dewa732corps.code03.R;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
    Spinner dropdownType,dropdownPosisi,dropdownTempat;
    Button btnSaveSupplier,btnCancelSupplier;
    EditText txtNamaSupplier,txtAlamatSupplier,txtNoTelpSupplier;

    private int simpan=-1;

    public Bitmap ImageBitmap;
    int Image_Request_Code = 1;
    Uri FilePathUri,FilePathUri2;

    private static final int INTENT_REQUEST_CODE = 100;
    public static final String URL = "https://sibento.yafetrakan.com/api/";

    android.support.v7.widget.Toolbar toolbar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu2_supplier_form);

        setInit();
//        simpan = getIntent().getIntExtra("simpan", 0);
//
//        Intent intent = getIntent();
//        Log.d("intentnya", String.valueOf(((Intent) intent).getStringExtra("id")));
//        if(!String.valueOf(intent.getStringExtra("id")).equals("null")) {
//            String id = intent.getStringExtra("id");
//            String nama = intent.getStringExtra("nama");
//            String alamat = intent.getStringExtra("alamat");
//            String notelp = intent.getStringExtra("notelp");
//
//            if(simpan <= 0){
//                txtNamaSupplier.setText(nama);
//                txtAlamatSupplier.setText(alamat);
//                txtNoTelpSupplier.setText(notelp);
//
//                simpan=-1;
//            }
//            else
//            {
//                txtNamaSupplier.setText(getIntent().getStringExtra("nama"));
//                txtAlamatSupplier.setText(getIntent().getStringExtra("alamat"));
//                txtNoTelpSupplier.setText(getIntent().getStringExtra("notelp"));
//            }



//            dropdownPosisi.set(posisi);
//            dropdownTempat.setAdapter(tempat);
//            dropdownType.setAdapter(tipe);
//            etTipe.setText(tipe);
//            buttonInsertImage.setVisibility(View.GONE);
//        }

        btnSaveSupplier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(formChecking()==0){
                    addSupplier();
                }
            }
        });
    }

    private int formChecking(){
        //Fungsi Check Form

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

    public void setInit(){


        //depan itu nama form

        txtNamaSupplier = findViewById(R.id.txtNamaSupplier);
        txtAlamatSupplier = findViewById(R.id.txtAlamatSupplier);
        txtNoTelpSupplier = findViewById(R.id.txtNoTelpSupplier);

        btnSaveSupplier = findViewById(R.id.btnSaveSupplier);
        btnCancelSupplier = findViewById(R.id.btnCancelSupplier);
    }

    private void addSupplier() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiClient retrofitInterface = retrofit.create(ApiClient.class);

        Log.d("name_supplier: ", txtNamaSupplier.getText().toString());
        Log.d("address_supplier: ", txtAlamatSupplier.getText().toString());
        Log.d("phonenumber_supplier: ", txtNoTelpSupplier.getText().toString());


        RequestBody name_supplier =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), txtNamaSupplier.getText().toString());
//        RequestBody requestId =
//                RequestBody.create(
//                        MediaType.parse("multipart/form-data"), "1111-ASTRA-111");

        RequestBody address_supplier =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), txtAlamatSupplier.getText().toString());

        RequestBody phonenumber_supplier =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), txtNoTelpSupplier.getText().toString());
//

        Call<ResponseBody> call = retrofitInterface.addSupplier(name_supplier, address_supplier, phonenumber_supplier);
//        mProgressBar.setVisibility(View.VISIBLE);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {

//                mProgressBar.setVisibility(View.GONE);

                if (response.isSuccessful()) {

                    ResponseBody responseBody = response.body();
                    Log.d("SUKSES", responseBody.toString());
                    Toast.makeText(SupplierForm.this, "Sukses", Toast.LENGTH_SHORT).show();
                    final Intent intent = new Intent(SupplierForm.this, MainActivity.class);
                    intent.putExtra("addDialog", 1);
                    startActivity(intent);
//                    mBtImageShow.setVisibility(View.VISIBLE);
//                     mImageUrl = URL + responseBody.getPath();
//                    Snackbar.make(findViewById(R.id.content), responseBody.getMessage(),Snackbar.LENGTH_SHORT).show();

                } else {
                    Log.d("onResponse: ", response.message());
                    Toast.makeText(SupplierForm.this, "Gagal", Toast.LENGTH_SHORT).show();

//                    ResponseBody errorBody = response.errorBody();
//
//                    Gson gson = new Gson();
//
//                    try {
//
//                        Response errorResponse = gson.fromJson(errorBody.string(), Response.class);
////                        Snackbar.make(findViewById(R.id.content), errorResponse.getMessage(),Snackbar.LENGTH_SHORT).show();
//
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("onFailure: ", t.toString());

//                mProgressBar.setVisibility(View.GONE);
//                Log.d(TAG, "onFailure: "+t.getLocalizedMessage());
            }
        });
    }
}