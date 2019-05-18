package com.example.dewa732corps.code03.Fragment.Sparepart_Procurement;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dewa732corps.code03.Adapter.SparepartProcurementAdapter;
import com.example.dewa732corps.code03.Adapter.SparepartProcurementDetailAdapter;
import com.example.dewa732corps.code03.Controller.ApiClient;
import com.example.dewa732corps.code03.Controller.RetrofitClient;
import com.example.dewa732corps.code03.Controller.Sales;
import com.example.dewa732corps.code03.Controller.SalesList;
import com.example.dewa732corps.code03.Controller.Sparepart;
import com.example.dewa732corps.code03.Controller.SparepartList;
import com.example.dewa732corps.code03.Controller.SparepartProcurementDetail;
import com.example.dewa732corps.code03.Controller.SparepartProcurementDetailList;
import com.example.dewa732corps.code03.Controller.SparepartProcurementList;
import com.example.dewa732corps.code03.Controller.Supplier;
import com.example.dewa732corps.code03.Controller.SupplierList;
import com.example.dewa732corps.code03.Fragment.Sales.SalesForm;
import com.example.dewa732corps.code03.MainActivity;
import com.example.dewa732corps.code03.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SparepartProcurementForm extends AppCompatActivity {


    FrameLayout framelay;
    Spinner dropdownNameOfSales, dropdownStatusProcess,dropdownSparepart;
    Button btnSaveSparepartProcurement,btnCancelSparepartProcurement,btnAddDetailProcurement;
    EditText txtTanggal, txtNamaSupplier, txtNamaSales, txtNoId;
    ProgressDialog mProgress;
    RecyclerView ProcDetailRecycler;
    RecyclerView.Adapter ProcDetailAdapter;
    RecyclerView.LayoutManager ProcDetailLayout;


    private int simpan=0;
    private int editMode=0, id;

    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    String selectedIdSales, selectedIdSparepart, idSupplier, idSales, statusProcurement, ddSalesProcurement;
    Integer indexSparepart;
    private List<String> listNameSales = new ArrayList<String>();
    private List<String> listIdSales = new ArrayList<String>();
    private List<String> listNameSparepart = new ArrayList<String>();
    private List<String> listIdSparepart = new ArrayList<String>();
    private List<String> listStatus = new ArrayList<String>();
    private List<SparepartProcurementDetail> detailProcurement = new ArrayList<>();
    private List<SparepartProcurementDetail> detailData;

    private List<Sparepart> sparepartData;

    private static final int INTENT_REQUEST_CODE = 100;
    public static final String URL = "https://sibento.yafetrakan.com/api/";

    android.support.v7.widget.Toolbar toolbar;

    protected void onCreate(Bundle savedInstanceState) {  //Fungsi didalamnya dijalankan pertama kali
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_ocs_sparepartprocurement_form);

        setInit();
        setDropdown();
        mProgress = new ProgressDialog(this);
        mProgress.setMessage("Loading Data");

//        mDisplayDate = (TextView) findViewById(R.id.tvDate);
//
//        mDisplayDate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Calendar cal = Calendar.getInstance();
//                int year = cal.get(Calendar.YEAR);
//                int month = cal.get(Calendar.MONTH);
//                int day = cal.get(Calendar.DAY_OF_MONTH);
//
//                DatePickerDialog dialog = new DatePickerDialog(
//                        SparepartProcurementForm.this,
//                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
//                        mDateSetListener,
//                        year,month,day);
//                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                dialog.show();
//            }
//        });
//
//        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
//                month = month + 1;
//                Log.d("Tanggal", "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);
//
//                String date = month + "/" + day + "/" + year;
//                mDisplayDate.setText(date);
//            }
//        };


        btnSaveSparepartProcurement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //Listener button btnSaveSparepart saat di klik
                if(formChecking()==0 && editMode ==0){ //kalau pengecekan form benar dan tidak pada mode edit
                    Log.d("sukses","addSparepartProcurement");
                    addSparepartProcurement();
                }
                else if (formChecking()==0 && editMode ==1){ //kalau pengecekan form benar dan pada mode edit
                    Log.d("sukses","editSparepartProcurement");
                    editSparepartProcurement();
                }
                else if (formChecking()==0 && editMode ==2){ //kalau pengecekan form benar dan pada mode edit
                    Log.d("sukses","editSparepartProcurement");

                    verifySparepartProcurement();
                }
            }
        });

        btnCancelSparepartProcurement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //Listener button btnSaveSparepart saat di klik
                Toast.makeText(SparepartProcurementForm.this, "Batal.", Toast.LENGTH_SHORT).show();
                final Intent intent = new Intent(SparepartProcurementForm.this, MainActivity.class);
                intent.putExtra("menuBefore", 5);
                startActivity(intent);
            }
        });

        dropdownNameOfSales.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { //Listener dropdown tipe sparepart saat dipilih
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int sales, long id) {
                selectedIdSales = listIdSales.get(sales); //Mendapatkan id dari dropdown yang dipilih
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });
        dropdownSparepart.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { //Listener dropdown tipe sparepart saat dipilih
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int sparepart, long id) {
                selectedIdSparepart = listIdSparepart.get(sparepart); //Mendapatkan id dari dropdown yang dipilih
                indexSparepart = sparepart;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });


        btnAddDetailProcurement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //Listener button btnAddDetailProcurement saat di klik
//                Integer idProcurementDetail, Integer price, String sellPrice, Integer amount, Integer subtotal, Integer idProcurement, String idSparepart, String nameSpa
                int unique = 1,i;
                Log.d("Index :",indexSparepart.toString());
                for(i=0;i<detailProcurement.size();i++) {
                    String idsp = detailProcurement.get(i).getIdSparepart();
                    Log.d("idsp",idsp);
                    Log.d("sidsp",selectedIdSparepart);

                    if(idsp.equalsIgnoreCase(selectedIdSparepart))
                    {
                        unique = 0;
                    }
                }
                if(unique == 1){
                    detailProcurement.add(new SparepartProcurementDetail(
                            0,
                            Integer.parseInt(sparepartData.get(indexSparepart).getBuyPrice()),
                            sparepartData.get(indexSparepart).getSellPrice(),
                            1,
                            Integer.parseInt(sparepartData.get(indexSparepart).getBuyPrice())*1,
                            0,
                            sparepartData.get(indexSparepart).getId(),
                            sparepartData.get(indexSparepart).getName()
                    ));
                    ProcDetailRecycler = findViewById(R.id.recyclerViewSparepartProcurementForm);
                    ProcDetailRecycler.setHasFixedSize(true);
                    ProcDetailLayout = new LinearLayoutManager(SparepartProcurementForm.this);
                    ProcDetailAdapter = new SparepartProcurementDetailAdapter(detailProcurement,SparepartProcurementForm.this);

                    ProcDetailRecycler.setLayoutManager(ProcDetailLayout);
                    ProcDetailRecycler.setAdapter(ProcDetailAdapter);
                    ProcDetailAdapter.notifyDataSetChanged();
                }
                else{
                    AlertDialog alertDialog = new AlertDialog.Builder(SparepartProcurementForm.this).create();
                    alertDialog.setTitle("Warning");
                    alertDialog.setMessage("Sparepart sudah ditambahkan!");
                    alertDialog.setButton(Dialog.BUTTON_POSITIVE,"OK",new DialogInterface.OnClickListener(){

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    alertDialog.show();
                }
            }
        });
    }

    public void setInit(){ //pendeklarasian objek-objek yang ada di layout

        //depan itu nama form

        txtTanggal = findViewById(R.id.txtTanggal);
        dropdownNameOfSales= findViewById(R.id.dropdownNamaSales_Procurement);
        dropdownStatusProcess = findViewById(R.id.dropdownStatus_Procurement);
        dropdownSparepart = findViewById(R.id.dropdownNamaSparepart_Procurement);

        btnAddDetailProcurement = findViewById(R.id.btnAddDetailProcurement);
        btnSaveSparepartProcurement = findViewById(R.id.btnSaveSparepartProcurement);
        btnCancelSparepartProcurement = findViewById(R.id.btnCancelSparepartProcurement);
    }

    private int getIndex(Spinner spinner, String myString){
        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                return i;
            }
        }
        return 0;
    }

    private int getIndexListString(List<String> Data, String myString){
        for (int i=0;i<Data.size();i++){
            if (Data.get(i).equalsIgnoreCase(myString)){
                Log.d("list String: ", Data.get(i).toString()+' '+myString+' '+String.valueOf(i));
                return i;
            }
        }
        return 0;
    }

    public void getPutExtra(){ // mendapatkan parsing data dari activity sebelumnya beserta pengesetan form sesuai parsing data
        Intent intent = getIntent();

//        Log.d("Id Sparepart", String.valueOf(((Intent) intent).getStringExtra("id")));
        if(!String.valueOf(intent.getStringExtra("id")).equals("null")) {


             id = Integer.parseInt(intent.getStringExtra("id"));
            Log.d("id",String.valueOf(id));
            String date_procurement = intent.getStringExtra("date");
            int id_sales = Integer.parseInt(intent.getStringExtra("id_sales"));
            String status_procurement = intent.getStringExtra("status_procurement");

            Log.d("ID Procurement: ", String.valueOf(id));
            Log.d("Tanggal: ", date_procurement);
            Log.d("ID Sales: ", String.valueOf(id_sales));
            Log.d("Status Process: ", status_procurement);

            txtTanggal.setText(date_procurement);

            int d = getIndexListString(listIdSales, String.valueOf(id_sales));

            Log.d("getIndexList: ",String.valueOf(d));
            dropdownNameOfSales.setSelection(getIndexListString(listIdSales, String.valueOf(id_sales)));
            dropdownStatusProcess.setSelection(getIndex(dropdownStatusProcess,status_procurement));

//            Toast.makeText(SparepartProcurementForm.this, getIndexListString(listIdSales, String.valueOf(id_sales)), Toast.LENGTH_SHORT).show();
            if(!String.valueOf(intent.getStringExtra("mode")).equals("verif"))
            {
                editMode = 1; //pengubahan menjadi mode edit

            }
            else{
                editMode =2;
                btnSaveSparepartProcurement.setText("Verif");
            }
            Retrofit retrofit= new retrofit2.Retrofit.Builder()
                    .baseUrl("https://sibento.yafetrakan.com/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            ApiClient apiClient = retrofit.create(ApiClient.class);

            Call<SparepartProcurementDetailList> detailProcurementGet = apiClient.getProcurementDetail(id);

            detailProcurementGet.enqueue(new Callback<SparepartProcurementDetailList>() {
                @Override
                public void onResponse(Call<SparepartProcurementDetailList> call, Response<SparepartProcurementDetailList> response) {
                    try {
                        detailProcurement = response.body().getData();
                        int data = detailProcurement.size();

                        Log.d("Data",String.valueOf(data));

                        ProcDetailRecycler = findViewById(R.id.recyclerViewSparepartProcurementForm);
                        ProcDetailRecycler.setHasFixedSize(true);
                        ProcDetailLayout = new LinearLayoutManager(SparepartProcurementForm.this);
                        ProcDetailAdapter = new SparepartProcurementDetailAdapter(detailProcurement,SparepartProcurementForm.this);

                        ProcDetailRecycler.setLayoutManager(ProcDetailLayout);
                        ProcDetailRecycler.setAdapter(ProcDetailAdapter);
                        ProcDetailAdapter.notifyDataSetChanged();

                        mProgress.hide();

                    } catch (Exception e) {
                        Toast.makeText(SparepartProcurementForm.this, "Tidak Ada Detail Sparepart Procurement!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<SparepartProcurementDetailList> call, Throwable t) {
                    Toast.makeText(SparepartProcurementForm.this, "Fail", Toast.LENGTH_SHORT).show();
                    mProgress.hide();
                }
            });
//            mProgress.hide();
        }
    }

    private int formChecking(){ //Fungsi Check Form

        String  tanggalPengadaan=txtTanggal.getText().toString();

        if(tanggalPengadaan.isEmpty()){
            txtTanggal.setError("Tanggal pengadaan diperlukan.");
            txtNamaSales.requestFocus();
            return 1;
        }
        return 0;
    }

    public void setDropdown(){ // Mengeset dropdown dari data pemanggilan API server
        listStatus.add("Unprocessed");
        listStatus.add("On Process");
        listStatus.add("Finish");

        ArrayAdapter<String> adapterStatus = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item
                , listStatus);
        dropdownStatusProcess.setAdapter(adapterStatus);
        // ===================================================

        ApiClient service = RetrofitClient.getRetrofitInstance().create(ApiClient.class);

        Call<SalesList> call = service.getSales();
        //Log.d("responsecode", String.valueOf(request.getSparepart()));

        call.enqueue(new Callback<SalesList>() {
            @Override
            public void onResponse(Call<SalesList> call, Response<SalesList> response) {
                if (response.isSuccessful()) {
                    List<Sales> spinnerArrayList = response.body().getData();
                    for (int i = 0; i < spinnerArrayList.size(); i++) {
                        String nameSales = spinnerArrayList.get(i).getName();
                        String supplierSales = spinnerArrayList.get(i).getSupplier().getName();

                        String idSales = spinnerArrayList.get(i).getId().toString();
                        listNameSales.add(nameSales+' '+supplierSales);
                        listIdSales.add(idSales);
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(SparepartProcurementForm.this,
                            android.R.layout.simple_spinner_dropdown_item
                            , listNameSales);
                    dropdownNameOfSales.setAdapter(adapter);
//                    getPutExtra();

                    Intent intent = getIntent();

//                    if(!String.valueOf(intent.getStringExtra("id")).equals("null")) { //pengecekan ada atau tidaknya parsing data dari aktivity sebelumnya
////                        mProgress.show();
//                        getPutExtra();
//                    }
                }
            }
            @Override
            public void onFailure(Call<SalesList> call, Throwable t) {
                Log.d("Error",t.getMessage());
            }

        });

        // ===================================================

        Call<SparepartList> call2 = service.getSparepart();
        //Log.d("responsecode", String.valueOf(request.getSparepart()));

        call2.enqueue(new Callback<SparepartList>() {
            @Override
            public void onResponse(Call<SparepartList> call2, Response<SparepartList> response) {
                if (response.isSuccessful()) {
                    List<Sparepart> spinnerArrayList = response.body().getData();
                    sparepartData = response.body().getData();
                    for (int i = 0; i < spinnerArrayList.size(); i++) {
                        String nameSparepart = spinnerArrayList.get(i).getName();
                        String brandSparepart = spinnerArrayList.get(i).getBrand();
                        String idSparepart = spinnerArrayList.get(i).getId();
                        listNameSparepart.add(nameSparepart+' '+brandSparepart);
                        listIdSparepart.add(idSparepart);
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(SparepartProcurementForm.this,
                            android.R.layout.simple_spinner_dropdown_item
                            , listNameSparepart);
                    dropdownSparepart.setAdapter(adapter);

                    Intent intent = getIntent();

                    if(!String.valueOf(intent.getStringExtra("id")).equals("null")) { //pengecekan ada atau tidaknya parsing data dari aktivity sebelumnya
//                        mProgress.show();
                        getPutExtra();
                    }
                }
            }
            @Override
            public void onFailure(Call<SparepartList> call, Throwable t) {
                Log.d("Error",t.getMessage());
            }

        });
    }

    private void addSparepartProcurement() { // Fungsi menambahkan sparepart
        mProgress.show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiClient retrofitInterface = retrofit.create(ApiClient.class);
        String date_procurement = txtTanggal.getText().toString();
        String status_procurement = dropdownStatusProcess.getSelectedItem().toString();
        String id_sales = selectedIdSales;

        Log.d("date_procurement: ", date_procurement); //untuk edit text
        Log.d("status_procurement: ", status_procurement);
        Log.d("id_sales: ", id_sales); //karena drop down gak perlu makannya


        Call<ResponseBody> call = retrofitInterface.addSparepartProcurement(date_procurement, status_procurement,Integer.parseInt(id_sales));
//        mProgressBar.setVisibility(View.VISIBLE);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {

                try {
                    JSONObject jsonRes = null;
                    jsonRes = new JSONObject(response.body().string());
                    String idprocurement =  jsonRes.getJSONObject("data").getString("id_procurement");
                    Log.d("idprocurement", idprocurement);
//                mProgressBar.setVisibility(View.GONE);

                    if (response.isSuccessful()) {

                        for(int x=0;x<detailProcurement.size();x++)
                        {
                            Log.d("masuk", "massuk");
                            Retrofit retrofit = new retrofit2.Retrofit.Builder()
                                    .baseUrl(URL)
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .build();

                            ApiClient apiClient = retrofit.create(ApiClient.class);

                            Double price_detail_procurement =  Double.parseDouble(detailProcurement.get(x).getPrice().toString());
                            int amount_detail_procurement = Integer.parseInt(detailProcurement.get(x).getAmount().toString());
                            Double subtotal_detail_procurement =  Double.parseDouble(detailProcurement.get(x).getSubtotal().toString());
                            String id_sparepart = detailProcurement.get(x).getIdSparepart();
                            int id_procurement = Integer.parseInt(idprocurement);

                            Call<ResponseBody> addProcurementDetail = apiClient.addProcurementDetail(
                                    price_detail_procurement,
                                    amount_detail_procurement,
                                    subtotal_detail_procurement,
                                    id_sparepart,
                                    id_procurement
                            );

                            addProcurementDetail.enqueue(new Callback<ResponseBody>() {
                                @Override
                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                    Log.d("successe", "hehehe");
                                    try {
                                        JSONObject jsonRes = new JSONObject(response.body().string());
                                        String iddetailprocurement =  jsonRes.getJSONObject("data").getString("id_procurement_detail");
                                        Log.d("idprocurement", iddetailprocurement);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void onFailure(Call<ResponseBody> call, Throwable t) {
                                }
                            });
                        }
//                        ResponseBody responseBody = response.body();
//                        Log.d("SUKSES",responseBody.toString());
                        Toast.makeText(SparepartProcurementForm.this, "Sukses", Toast.LENGTH_SHORT).show();
                        final Intent intent = new Intent(SparepartProcurementForm.this, MainActivity.class);
                        intent.putExtra("menuBefore", 5);
                        startActivity(intent);


                    } else {
                        Log.d( "onResponse: ",response.message());
                        Toast.makeText(SparepartProcurementForm.this, "Gagal", Toast.LENGTH_SHORT).show();
                        mProgress.dismiss();
                    }
                    mProgress.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                mProgress.dismiss();
                Log.d("onFailure: ",t.toString());

//                mProgressBar.setVisibility(View.GONE);
//                Log.d(TAG, "onFailure: "+t.getLocalizedMessage());
            }
        });
    }

    private void verifySparepartProcurement(){ // Fungsi Verif Data sparepart
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiClient retrofitInterface = retrofit.create(ApiClient.class);

        String ddSalesProcurement = dropdownNameOfSales.getSelectedItem().toString();

        final Integer id_procurement = id;
        String date_procurement = txtTanggal.getText().toString();
        Integer id_sales = Integer.parseInt(selectedIdSales);
        String status_procurement = "Finish";

        Log.d("id procurement: ", id_procurement.toString());
        Log.d("id date_procurement: ", date_procurement);
        Log.d("id id_sales: ", id_sales.toString());
        Log.d("id status_procurement: ", status_procurement);

        Call<ResponseBody> call = retrofitInterface.editSparepartProcurement(id_procurement, date_procurement, id_sales, status_procurement);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {

                try {
                    JSONObject jsonRes = null;
                    jsonRes = new JSONObject(response.body().string());
                    String idprocurement =  jsonRes.getJSONObject("data").getString("id_procurement");
                    Log.d("idprocurement", idprocurement);
//                mProgressBar.setVisibility(View.GONE);

                    if (response.isSuccessful()) {

                        for(int x=0;x<detailProcurement.size();x++)
                        {
                            Log.d("masuk", "massuk");
                            Retrofit retrofit = new retrofit2.Retrofit.Builder()
                                    .baseUrl(URL)
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .build();

                            ApiClient apiClient = retrofit.create(ApiClient.class);


                            int amount = Integer.parseInt(detailProcurement.get(x).getAmount().toString());
                            String id_sparepart = detailProcurement.get(x).getIdSparepart();
                            Log.d( "amount: ",String.valueOf(amount));
                            Log.d( "amount: ",id_sparepart);


                            Call<ResponseBody> verifymobile = apiClient.verifymobile(
                                    id_sparepart,
                                    amount

                            );

                            verifymobile.enqueue(new Callback<ResponseBody>() {
                                @Override
                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                                    Log.d("onResponse: ",String.valueOf(response.code()));
                                    if (response.code() == 200){
                                        Log.d("successe", "hehehe");
                                    }else{
                                        Log.d("onResponse: ",String.valueOf(response.code()));

                                    }

                                }

                                @Override
                                public void onFailure(Call<ResponseBody> call, Throwable t) {
                                }
                            });
                        }
//                        ResponseBody responseBody = response.body();
//                        Log.d("SUKSES",responseBody.toString());
                        Toast.makeText(SparepartProcurementForm.this, "Sukses", Toast.LENGTH_SHORT).show();
                        final Intent intent = new Intent(SparepartProcurementForm.this, MainActivity.class);
                        intent.putExtra("menuBefore", 5);
                        startActivity(intent);


                    }
//                    else {
//                        Log.d( "onResponse: ",response.message());
//                        Toast.makeText(SparepartProcurementForm.this, "Gagal", Toast.LENGTH_SHORT).show();
//                        mProgress.dismiss();
//                    }
                    mProgress.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
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

    private void editSparepartProcurement() { // Fungsi mengedit Data sparepart

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiClient retrofitInterface = retrofit.create(ApiClient.class);

        String ddSalesProcurement = dropdownNameOfSales.getSelectedItem().toString();

        final Integer id_procurement = id;
        String date_procurement = txtTanggal.getText().toString();
        Integer id_sales = Integer.parseInt(selectedIdSales);
        String status_procurement = dropdownStatusProcess.getSelectedItem().toString();

        Log.d("id procurement: ", id_procurement.toString());
        Log.d("id date_procurement: ", date_procurement);
        Log.d("id id_sales: ", id_sales.toString());
        Log.d("id status_procurement: ", status_procurement);

        Call<ResponseBody> call = retrofitInterface.editSparepartProcurement(id_procurement, date_procurement, id_sales, status_procurement);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {

                try {
                    JSONObject jsonRes = null;
                    jsonRes = new JSONObject(response.body().string());
                    String idprocurement =  jsonRes.getJSONObject("data").getString("id_procurement");
                    Log.d("idprocurement", idprocurement);
//                mProgressBar.setVisibility(View.GONE);

                    if (response.isSuccessful()) {

                        for(int x=0;x<detailProcurement.size();x++)
                        {
                            Log.d("masuk", "massuk");
                            Retrofit retrofit = new retrofit2.Retrofit.Builder()
                                    .baseUrl(URL)
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .build();

                            ApiClient apiClient = retrofit.create(ApiClient.class);

                            Double price_detail_procurement =  Double.parseDouble(detailProcurement.get(x).getPrice().toString());
                            int amount_detail_procurement = Integer.parseInt(detailProcurement.get(x).getAmount().toString());
                            Double subtotal_detail_procurement =  Double.parseDouble(detailProcurement.get(x).getSubtotal().toString());
                            String id_sparepart = detailProcurement.get(x).getIdSparepart();


                            Call<ResponseBody> addProcurementDetail = apiClient.addProcurementDetail(
                                    price_detail_procurement,
                                    amount_detail_procurement,
                                    subtotal_detail_procurement,
                                    id_sparepart,
                                    id_procurement
                            );

                            addProcurementDetail.enqueue(new Callback<ResponseBody>() {
                                @Override
                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                    Log.d("successe", "hehehe");
                                    try {
                                        JSONObject jsonRes = new JSONObject(response.body().string());
                                        String iddetailprocurement =  jsonRes.getJSONObject("data").getString("id_procurement_detail");
                                        Log.d("idprocurement", iddetailprocurement);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void onFailure(Call<ResponseBody> call, Throwable t) {
                                }
                            });
                        }
//                        ResponseBody responseBody = response.body();
//                        Log.d("SUKSES",responseBody.toString());
                        Toast.makeText(SparepartProcurementForm.this, "Sukses", Toast.LENGTH_SHORT).show();
                        final Intent intent = new Intent(SparepartProcurementForm.this, MainActivity.class);
                        intent.putExtra("menuBefore", 5);
                        startActivity(intent);


                    } else {
                        Log.d( "onResponse: ",response.message());
                        Toast.makeText(SparepartProcurementForm.this, "Gagal", Toast.LENGTH_SHORT).show();
                        mProgress.dismiss();
                    }
                    mProgress.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
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