package com.example.dewa732corps.code03.Fragment.Sales;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.dewa732corps.code03.Controller.ApiClient;
import com.example.dewa732corps.code03.Controller.RetrofitClient;
import com.example.dewa732corps.code03.Controller.Supplier;
import com.example.dewa732corps.code03.Controller.SupplierList;
import com.example.dewa732corps.code03.MainActivity;
import com.example.dewa732corps.code03.R;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SalesForm extends AppCompatActivity {

    FrameLayout framelay;
    Spinner dropdownNameOfSupplier;
    Button btnSaveSales,btnCancelSales;
    EditText txtIdSales, txtNamaSales, txtNoTelpSales;
    ProgressDialog mProgress;

    private int simpan=0;
    private int editMode=0;

    String selectedId, idSales;
    private List<String> listNameSupplier = new ArrayList<String>();
    private List<String> listIdSupplier = new ArrayList<String>();

    private static final int INTENT_REQUEST_CODE = 100;
    public static final String URL = "https://sibento.yafetrakan.com/api/";

    android.support.v7.widget.Toolbar toolbar;

    protected void onCreate(Bundle savedInstanceState) {  //Fungsi didalamnya dijalankan pertama kali
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_owner_sales_form);

        setInit();
        setDropdown();
        mProgress = new ProgressDialog(this);
        mProgress.setMessage("Loading Data");

        btnSaveSales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //Listener button btnSaveSparepart saat di klik
                if(formChecking()==0 && editMode ==0){ //kalau pengecekan form benar dan tidak pada mode edit
                    Log.d("sukses","addSales");
                    addSales();
                }
                else if (formChecking()==0 && editMode ==1){ //kalau pengecekan form benar dan pada mode edit
                    Log.d("sukses","editSales");
                    editSales();
                }
            }
        });

        btnCancelSales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //Listener button btnSaveSparepart saat di klik
                Toast.makeText(SalesForm.this, "Batal.", Toast.LENGTH_SHORT).show();
                final Intent intent = new Intent(SalesForm.this, MainActivity.class);
                intent.putExtra("menuBefore", 3);
                startActivity(intent);
            }
        });

        dropdownNameOfSupplier.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { //Listener dropdown tipe sparepart saat dipilih
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                selectedId = listIdSupplier.get(position); //Mendapatkan id dari dropdown yang dipilih
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });
    }

    public void setInit(){ //pendeklarasian objek-objek yang ada di layout

        //depan itu nama form

        txtNamaSales = findViewById(R.id.txtNamaSales);
        txtNoTelpSales = findViewById(R.id.txtNoTelpSales);
        dropdownNameOfSupplier = findViewById(R.id.dropdownNamaSalesSupplier);

        btnSaveSales = findViewById(R.id.btnSaveSales);
        btnCancelSales = findViewById(R.id.btnCancelSales);
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
            idSales = id;
            String nama = intent.getStringExtra("nama");
            String notelp = intent.getStringExtra("notelp");
            String name_supplier = intent.getStringExtra("name_supplier");

            Log.d("Nama: ", nama);
            Log.d("No Telp Sales: ", notelp);
            Log.d("Supplier: ", name_supplier);

            txtNamaSales.setText(nama);
            txtNoTelpSales.setText(notelp);

            dropdownNameOfSupplier.setSelection(getIndex(dropdownNameOfSupplier, name_supplier));
            editMode = 1; //pengubahan menjadi mode edit
//            mProgress.hide();
        }
    }

    private int formChecking(){ //Fungsi Check Form

        String  namaSales=txtNamaSales .getText().toString(),
                noTelpSales=txtNoTelpSales .getText().toString();

        if(namaSales.isEmpty()){
            txtNamaSales.setError("Nama sales diperlukan.");
            txtNamaSales.requestFocus();
            return 1;
        }

        if(noTelpSales.isEmpty()){
            txtNoTelpSales.setError("No telp sales diperlukan.");
            txtNoTelpSales.requestFocus();
            return 1;
        }
        return 0;
    }

    public void setDropdown(){ // Mengeset dropdown dari data pemanggilan API server

        ApiClient service = RetrofitClient.getRetrofitInstance().create(ApiClient.class);


        Call<SupplierList> call = service.getSupplier();
        //Log.d("responsecode", String.valueOf(request.getSparepart()));

        call.enqueue(new Callback<SupplierList>() {
            @Override
            public void onResponse(Call<SupplierList> call, Response<SupplierList> response) {
                if (response.isSuccessful()) {
                    List<Supplier> spinnerArrayList = response.body().getData();
                    for (int i = 0; i < spinnerArrayList.size(); i++) {
                        String nameSupplier = spinnerArrayList.get(i).getName();
                        String idSupplier = spinnerArrayList.get(i).getId().toString();
                        listNameSupplier.add(nameSupplier);
                        listIdSupplier.add(idSupplier);
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(SalesForm.this,
                            android.R.layout.simple_spinner_dropdown_item
                            , listNameSupplier);
                    dropdownNameOfSupplier.setAdapter(adapter);

                    Intent intent = getIntent();

                    if(!String.valueOf(intent.getStringExtra("id")).equals("null")) { //pengecekan ada atau tidaknya parsing data dari aktivity sebelumnya
//                        mProgress.show();
                        getPutExtra();
                    }
                }
            }
            @Override
            public void onFailure(Call<SupplierList> call, Throwable t) {
                Log.d("Error",t.getMessage());
            }

        });
    }

    private void addSales() { // Fungsi menambahkan sparepart

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiClient retrofitInterface = retrofit.create(ApiClient.class);

            Log.d("Nama: ", txtNamaSales.getText().toString()); //untuk edit text
            Log.d("No Telp Sales: ", txtNoTelpSales.getText().toString());
            Log.d("Drop: ", selectedId); //karena drop down gak perlu makannya

            RequestBody id_supplier =
                    RequestBody.create(
                            MediaType.parse("multipart/form-data"), selectedId);

            RequestBody name_sales =
                    RequestBody.create(
                            MediaType.parse("multipart/form-data"), txtNamaSales.getText().toString());

            RequestBody phone_number_sales =
                    RequestBody.create(
                            MediaType.parse("multipart/form-data"), txtNoTelpSales.getText().toString());

            Call<ResponseBody> call = retrofitInterface.addSales(id_supplier,name_sales,phone_number_sales);
//        mProgressBar.setVisibility(View.VISIBLE);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {

//                mProgressBar.setVisibility(View.GONE);

                    if (response.isSuccessful()) {

                        ResponseBody responseBody = response.body();
                        Log.d("SUKSES",responseBody.toString());
                        Toast.makeText(SalesForm.this, "Sukses", Toast.LENGTH_SHORT).show();
                        final Intent intent = new Intent(SalesForm.this, MainActivity.class);
                        intent.putExtra("menuBefore", 3);
                        startActivity(intent);
//                    mBtImageShow.setVisibility(View.VISIBLE);
//                     mImageUrl = URL + responseBody.getPath();
//                    Snackbar.make(findViewById(R.id.content), responseBody.getMessage(),Snackbar.LENGTH_SHORT).show();

                    } else {
                        Log.d( "onResponse: ",response.message());
                        Toast.makeText(SalesForm.this, "Gagal", Toast.LENGTH_SHORT).show();
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

    private void editSales() { // Fungsi mengedit Data sparepart

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiClient retrofitInterface = retrofit.create(ApiClient.class);

        String ddsalessupplier = dropdownNameOfSupplier.getSelectedItem().toString();

        Integer id_sales = Integer.parseInt(idSales);
        String id_supplier = selectedId;
        String name_sales = txtNamaSales.getText().toString();
        String phone_number_sales = txtNoTelpSales.getText().toString();

        Call<ResponseBody> call = retrofitInterface.editSales(id_sales, id_supplier, name_sales, phone_number_sales);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {


                if (response.isSuccessful()) {
                    ResponseBody responseBody = response.body();
//                    Log.d("SUKSES UPDATE DATA",responseBody.toString());
                    Toast.makeText(SalesForm.this, "SUKSES UPDATE SALES", Toast.LENGTH_SHORT).show();
                    final Intent intent = new Intent(SalesForm.this, MainActivity.class);
                    intent.putExtra("menuBefore", 3);
                    startActivity(intent);

                } else {
                    Log.d( "onResponse: ",response.message());
                    Toast.makeText(SalesForm.this, "Gagal Update Data", Toast.LENGTH_SHORT).show();

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