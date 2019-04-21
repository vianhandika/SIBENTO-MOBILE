package com.example.dewa732corps.code03.Fragment.Motor_Customer;

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
import com.example.dewa732corps.code03.Controller.BrandMotor;
import com.example.dewa732corps.code03.Controller.BrandMotorList;
import com.example.dewa732corps.code03.Controller.MotorCustomer;
import com.example.dewa732corps.code03.Controller.MotorCustomerList;
import com.example.dewa732corps.code03.Controller.RetrofitClient;
import com.example.dewa732corps.code03.Controller.Sparepart;
import com.example.dewa732corps.code03.Controller.SparepartType;
import com.example.dewa732corps.code03.Controller.SparepartTypeList;
import com.example.dewa732corps.code03.Controller.Supplier;
import com.example.dewa732corps.code03.Controller.SupplierList;
import com.example.dewa732corps.code03.Controller.TypeMotor;
import com.example.dewa732corps.code03.Controller.TypeMotorList;
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

public class MotorCustomerForm extends AppCompatActivity {

    FrameLayout framelay;
    Spinner dropdownBrandMotorCustomer, dropdownTypeMotorCustomer;
    Button btnSaveMotorCustomer,btnCancelMotorCustomer;
    EditText txtNomorPlatMotorCust;
    ProgressDialog mProgress;

    private int simpan=0;
    private int editMode=0;

    String selectedIdBrand, selectedIdType, idMotorCustomer;
    private List<String> listNameBrandMotorCustomer = new ArrayList<String>();
    private List<String> listIdBrandMotorCustomer = new ArrayList<String>();

    private List<String> listNameTypeMotorCustomer = new ArrayList<String>();
    private List<String> listIdTypeMotorCustomer = new ArrayList<String>();

    private static final int INTENT_REQUEST_CODE = 100;
    public static final String URL = "https://sibento.yafetrakan.com/api/";

    android.support.v7.widget.Toolbar toolbar;

    protected void onCreate(Bundle savedInstanceState) {  //Fungsi didalamnya dijalankan pertama kali
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu2_motorcustomer_form);

        setInit();
        setDropdown();
        mProgress = new ProgressDialog(this);
        mProgress.setMessage("Loading Data");

        btnSaveMotorCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //Listener button btnSaveSparepart saat di klik
                if(formChecking()==0 && editMode ==0){ //kalau pengecekan form benar dan tidak pada mode edit
                    Log.d("sukses","addMotorCustomer");
                    addMotorCustomer();
                }
                else if (formChecking()==0 && editMode ==1){ //kalau pengecekan form benar dan pada mode edit
                    Log.d("sukses","editMotorCustomer");
//                    editMotorCustomer();
                }
            }
        });

        btnCancelMotorCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //Listener button btnSaveSparepart saat di klik
                Toast.makeText(MotorCustomerForm.this, "Batal.", Toast.LENGTH_SHORT).show();
                final Intent intent = new Intent(MotorCustomerForm.this, MainActivity.class);
                intent.putExtra("menuBefore", 5);
                startActivity(intent);
            }
        });

        dropdownBrandMotorCustomer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { //Listener dropdown tipe sparepart saat dipilih
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int brand, long id) {
                selectedIdBrand = listIdBrandMotorCustomer.get(brand); //Mendapatkan id dari dropdown yang dipilih
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });

        dropdownTypeMotorCustomer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { //Listener dropdown tipe sparepart saat dipilih
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int type, long id) {
                selectedIdType = listIdTypeMotorCustomer.get(type); //Mendapatkan id dari dropdown yang dipilih
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });
    }

    public void setInit(){ //pendeklarasian objek-objek yang ada di layout

        //depan itu nama form

        txtNomorPlatMotorCust = findViewById(R.id.txtNomorPlatMotorCustomer);
        dropdownBrandMotorCustomer = findViewById(R.id.dropdownBrandMotorCustomer);
        dropdownTypeMotorCustomer = findViewById(R.id.dropdownTypeMotorCustomer);

        btnSaveMotorCustomer = findViewById(R.id.btnSaveMotorCustomer);
        btnCancelMotorCustomer = findViewById(R.id.btnCancelMotorCustomer);
    }

    private int getIndex(Spinner spinner, String myString){
        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                return i;
            }
        }

        return 0;
    }

    public void getPutExtra(){ // mendapatkan parsing data dari activity sebelumnya beserta pengesetan form sesuai parsing data
        Intent intent = getIntent();
        simpan = getIntent().getIntExtra("simpan", 0);

//        Log.d("Id Sparepart", String.valueOf(((Intent) intent).getStringExtra("id")));
        if(!String.valueOf(intent.getStringExtra("id")).equals("null")) {

            String id = intent.getStringExtra("id");
            //Log.d("id",id);
            idMotorCustomer = id;
            String platnomor = intent.getStringExtra("platnomor");
            String brand_motorcustomer = intent.getStringExtra("brand_motorcustomer");
            String type_motorcustomer = intent.getStringExtra("type_motorcustomer");

            txtNomorPlatMotorCust.setText(platnomor);

            dropdownBrandMotorCustomer.setSelection(getIndex(dropdownBrandMotorCustomer, brand_motorcustomer));
            dropdownTypeMotorCustomer.setSelection(getIndex(dropdownTypeMotorCustomer, type_motorcustomer));
            editMode = 1; //pengubahan menjadi mode edit
//            mProgress.hide();
        }
    }

    private int formChecking(){ //Fungsi Check Form

        String  platNomor=txtNomorPlatMotorCust .getText().toString();

        if(platNomor.isEmpty()){
            txtNomorPlatMotorCust.setError("Plat nomor diperlukan.");
            txtNomorPlatMotorCust.requestFocus();
            return 1;
        }
        return 0;
    }

    public void setDropdown(){ // Mengeset dropdown dari data pemanggilan API server

        ApiClient service = RetrofitClient.getRetrofitInstance().create(ApiClient.class);


        Call<BrandMotorList> call_brand = service.getBrandMotor();
        //Log.d("responsecode", String.valueOf(request.getSparepart()));

        call_brand.enqueue(new Callback<BrandMotorList>() {
            @Override
            public void onResponse(Call<BrandMotorList> call, Response<BrandMotorList> response) {
                if (response.isSuccessful()) {
                    List<BrandMotor> spinnerArrayList = response.body().getData();
                    for (int i = 0; i < spinnerArrayList.size(); i++) {
                        String nameBrand = spinnerArrayList.get(i).getBrand().toString();
                        String idBrand = spinnerArrayList.get(i).getId().toString();
                        listNameBrandMotorCustomer.add(nameBrand);
                        listIdBrandMotorCustomer.add(idBrand);
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(MotorCustomerForm.this,
                            android.R.layout.simple_spinner_dropdown_item
                            , listNameBrandMotorCustomer);
                    dropdownBrandMotorCustomer.setAdapter(adapter);

                    Intent intent = getIntent();

                    if(!String.valueOf(intent.getStringExtra("id")).equals("null")) { //pengecekan ada atau tidaknya parsing data dari aktivity sebelumnya
//                        mProgress.show();
                        getPutExtra();
                    }
                }

            }
            @Override
            public void onFailure(Call<BrandMotorList> call, Throwable t) {
                Log.d("Error",t.getMessage());
            }
        });

        Call<TypeMotorList> call_type = service.getTypeMotor();

        call_type.enqueue(new Callback<TypeMotorList>() {
            @Override
            public void onResponse(Call<TypeMotorList> call, Response<TypeMotorList> response) {
                if (response.isSuccessful()) {
                    List<TypeMotor> spinnerArrayList = response.body().getData();
                    for (int i = 0; i < spinnerArrayList.size(); i++) {
                        String nameType = spinnerArrayList.get(i).getType().toString();
                        String idType = spinnerArrayList.get(i).getId().toString();
                        listNameTypeMotorCustomer.add(nameType);
                        listIdTypeMotorCustomer.add(idType);
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(MotorCustomerForm.this,
                            android.R.layout.simple_spinner_dropdown_item
                            , listNameTypeMotorCustomer);
                    dropdownTypeMotorCustomer.setAdapter(adapter);

                    Intent intent = getIntent();

                    if(!String.valueOf(intent.getStringExtra("id")).equals("null")) { //pengecekan ada atau tidaknya parsing data dari aktivity sebelumnya
//                        mProgress.show();
                        getPutExtra();
                    }
                }

            }
            @Override
            public void onFailure(Call<TypeMotorList> call, Throwable t) {
                Log.d("Error",t.getMessage());
            }
        });
    }

    private void addMotorCustomer() { // Fungsi menambahkan sparepart

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiClient retrofitInterface = retrofit.create(ApiClient.class);

        RequestBody plate_motorcustomer =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), txtNomorPlatMotorCust.getText().toString());

        RequestBody id_brand_motorcustomer=
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), selectedIdBrand);

        RequestBody id_type_motorcustomer =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), selectedIdType);

        Call<ResponseBody> call = retrofitInterface.addMotorCustomer(plate_motorcustomer, id_brand_motorcustomer, id_type_motorcustomer);
//        mProgressBar.setVisibility(View.VISIBLE);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {

//                mProgressBar.setVisibility(View.GONE);

                if (response.isSuccessful()) {

                    ResponseBody responseBody = response.body();
                    Log.d("SUKSES",responseBody.toString());
                    Toast.makeText(MotorCustomerForm.this, "Sukses", Toast.LENGTH_SHORT).show();
                    final Intent intent = new Intent(MotorCustomerForm.this, MainActivity.class);
                    intent.putExtra("menuBefore", 5);
                    startActivity(intent);
//                    mBtImageShow.setVisibility(View.VISIBLE);
//                     mImageUrl = URL + responseBody.getPath();
//                    Snackbar.make(findViewById(R.id.content), responseBody.getMessage(),Snackbar.LENGTH_SHORT).show();

                } else {
                    Log.d( "onResponse: ",response.message());
                    Toast.makeText(MotorCustomerForm.this, "Gagal", Toast.LENGTH_SHORT).show();
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

//    private void editMotorCustomer() { // Fungsi mengedit Data sparepart
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        ApiClient retrofitInterface = retrofit.create(ApiClient.class);
//
//        String ddsalessupplier = dropdownNameOfSupplier.getSelectedItem().toString();
//
//        Integer id_sales = Integer.parseInt(idSales);
//        String id_supplier = selectedId;
//        String name_sales = txtNamaSales.getText().toString();
//        Integer phone_number_sales = Integer.parseInt(selectedId);
//
//        Call<ResponseBody> call = retrofitInterface.editSales(id_sales, id_supplier, name_sales, phone_number_sales);
//        call.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
//
//
//                if (response.isSuccessful()) {
//                    ResponseBody responseBody = response.body();
////                    Log.d("SUKSES UPDATE DATA",responseBody.toString());
//                    Toast.makeText(SalesForm.this, "SUKSES UPDATE SALES", Toast.LENGTH_SHORT).show();
//                    final Intent intent = new Intent(SalesForm.this, MainActivity.class);
//                    intent.putExtra("menuBefore", 3);
//                    startActivity(intent);
//
//                } else {
//                    Log.d( "onResponse: ",response.message());
//                    Toast.makeText(SalesForm.this, "Gagal Update Data", Toast.LENGTH_SHORT).show();
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                Log.d("onFailure: ",t.toString());
//
////                mProgressBar.setVisibility(View.GONE);
////                Log.d(TAG, "onFailure: "+t.getLocalizedMessage());
//            }
//        });
//    }
}