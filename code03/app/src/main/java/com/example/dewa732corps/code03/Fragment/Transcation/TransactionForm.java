package com.example.dewa732corps.code03.Fragment.Transcation;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.example.dewa732corps.code03.Adapter.SparepartProcurementDetailAdapter;
import com.example.dewa732corps.code03.Adapter.TransactionDetailServiceAdapter;
import com.example.dewa732corps.code03.Adapter.TransactionDetailSparepartAdapter;
import com.example.dewa732corps.code03.Controller.ApiClient;
import com.example.dewa732corps.code03.Controller.Customer;
import com.example.dewa732corps.code03.Controller.CustomerList;
import com.example.dewa732corps.code03.Controller.Employee;
import com.example.dewa732corps.code03.Controller.EmployeeList;
import com.example.dewa732corps.code03.Controller.MotorCustomer;
import com.example.dewa732corps.code03.Controller.MotorCustomerList;
import com.example.dewa732corps.code03.Controller.RetrofitClient;
import com.example.dewa732corps.code03.Controller.ServicesList;
import com.example.dewa732corps.code03.Controller.Services;
import com.example.dewa732corps.code03.Controller.Sparepart;
import com.example.dewa732corps.code03.Controller.SparepartList;
import com.example.dewa732corps.code03.Controller.SparepartProcurementDetail;
import com.example.dewa732corps.code03.Controller.TransactionDetailServices;
import com.example.dewa732corps.code03.Controller.TransactionService;
import com.example.dewa732corps.code03.Controller.TransactionSparepart;
import com.example.dewa732corps.code03.Fragment.Sparepart_Procurement.SparepartProcurementForm;
import com.example.dewa732corps.code03.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransactionForm extends AppCompatActivity {

    Button btnSaveTransaction, btnCancelTransaction, btnAddService_TransaksiForm, btnAddSparepart_TransaksiForm;
    EditText txtTanggal_TransactionForm ,txtAmount_TransaksiFormSparepart;
    String idTransaction,
            selectedId,
            selectedIdCust,
            selectedIdService,
            selectedIdSparepart,
            selectedIdMontirService,
            selectedIdMontirSprarepart,
            selectedIdMotorService,
            selectedIdMotorSparepart;

    Integer indexService,
            indexSparepart,
            indexMontirService,
            indexMontirSparepart,
            indexMotorService,
            indexMotorSparepart;

    ProgressDialog mProgress;



    Spinner dropdownNamaPelanggan_TransaksiForm, dropdownStatusProses_TransaksiForm, dropdownJenisTransaksi_TransaksiForm,
            dropdownMontir_TransaksiFormService,dropdownPlatKendaraan_TransaksiFormService,dropdownJenisService_TransaksiFormService,
            dropdownSparepart_TransaksiFormSparepart,dropdownMontir_TransaksiFormSparepart,dropdownPlatKendaraan_TransaksiFormSparepart;
    private int editMode=0, simpan= -1, id;
    private static final int INTENT_REQUEST_CODE = 100;
    public static final String URL = "https://sibento.yafetrakan.com/api/";

    RecyclerView rview,rview2;
    private TransactionDetailServiceAdapter adapterService;
    private TransactionDetailSparepartAdapter adapterSparepart;
    private RecyclerView.LayoutManager layout,layout2;

    private List<TransactionService> detailServices = new ArrayList<>();
    private List<TransactionSparepart> detailSpareparts = new ArrayList<>();

    private List<String> listNameCustomer = new ArrayList<String>();
    private List<String> listIdCustomer = new ArrayList<String>();

    private List<String> listNameSparepart = new ArrayList<String>();
    private List<String> listIdSparepart = new ArrayList<String>();

    private List<String> listNameMontir = new ArrayList<String>();
    private List<String> listIdMontir = new ArrayList<String>();

    private List<String> listPlateMotor = new ArrayList<String>();
    private List<String> listIdMotor = new ArrayList<String>();

    private List<String> listNameService = new ArrayList<String>();
    private List<String> listIdService = new ArrayList<String>();




    private List<String> listStatus = new ArrayList<String>();
    private List<String> listTypeTransaction = new ArrayList<String>();

    private List<Customer> customerData;
    private List<MotorCustomer> motorData;
    private List<Sparepart> sparepartData;
    private List<Employee> mechanicData = new ArrayList<>();
    private List<Services> serviceData;

    private LinearLayout transactionService;
    private LinearLayout transactionSparepart;

    RecyclerView DetailServiceRecycler,DetailSparepartRecycler;
    RecyclerView.Adapter DetailServiceAdapter,DetailSparepartAdapter;
    RecyclerView.LayoutManager DetailServiceLayout,DetailSparepartLayout;



    protected void onCreate(Bundle savedInstanceState) {  //Fungsi didalamnya dijalankan pertama kali
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_customerservice_transaction_form);

        setInit();
        setDropdown();
        mProgress = new ProgressDialog(this);
        mProgress.setMessage("Loading Data");

        dropdownJenisTransaksi_TransaksiForm.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { //Listener dropdown tipe sparepart saat dipilih
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                switch (position){
                    case 0:
                        transactionSparepart.setVisibility(View.VISIBLE);
                        transactionService.setVisibility(View.GONE);
                        break;

                    case 1:
                        transactionService.setVisibility(View.VISIBLE);
                        transactionSparepart.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        transactionSparepart.setVisibility(View.GONE);
                        transactionService.setVisibility(View.VISIBLE);
                        break;


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });
        dropdownJenisService_TransaksiFormService.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { //Listener dropdown tipe sparepart saat dipilih
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int service, long id) {
                selectedIdService = listIdService.get(service); //Mendapatkan id dari dropdown yang dipilih
                indexService = service;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });

        dropdownMontir_TransaksiFormService.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { //Listener dropdown tipe sparepart saat dipilih
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int data, long id) {
                selectedIdMontirService = listIdMontir.get(data); //Mendapatkan id dari dropdown yang dipilih
                indexMontirService = data;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });

        dropdownMontir_TransaksiFormSparepart.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { //Listener dropdown tipe sparepart saat dipilih
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int data, long id) {
                selectedIdMontirSprarepart = listIdMontir.get(data); //Mendapatkan id dari dropdown yang dipilih
                indexMontirSparepart = data;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });

        dropdownPlatKendaraan_TransaksiFormService.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { //Listener dropdown tipe sparepart saat dipilih
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int data, long id) {
                selectedIdMotorService = listIdMotor.get(data); //Mendapatkan id dari dropdown yang dipilih
                indexMotorService = data;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });

        dropdownPlatKendaraan_TransaksiFormSparepart.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { //Listener dropdown tipe sparepart saat dipilih
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int data, long id) {
                selectedIdMotorSparepart = listIdMotor.get(data); //Mendapatkan id dari dropdown yang dipilih
                indexMotorSparepart = data;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });
        dropdownSparepart_TransaksiFormSparepart.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { //Listener dropdown tipe sparepart saat dipilih
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int data, long id) {
                selectedIdSparepart = listIdSparepart.get(data); //Mendapatkan id dari dropdown yang dipilih
                indexSparepart = data;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });

        btnAddService_TransaksiForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detailServices.add(new TransactionService(
                        0,
                        1,
                        Integer.parseInt(serviceData.get(indexService).getPriceService()),
                        Integer.parseInt(serviceData.get(indexService).getPriceService())*1,
                        "",
                        serviceData.get(indexService).getIdService(),
                        serviceData.get(indexService).getNameService(),
                        mechanicData.get(indexMontirService).getId(),
                        mechanicData.get(indexMontirService).getName(),
                        motorData.get(indexMotorService).getId(),
                        motorData.get(indexMotorService).getPlate()

                ));
                DetailServiceRecycler = findViewById(R.id.detailservice_list);
                DetailServiceRecycler.setHasFixedSize(true);
                DetailServiceLayout = new LinearLayoutManager(TransactionForm.this);
                DetailServiceAdapter = new TransactionDetailServiceAdapter(detailServices,TransactionForm.this);

                DetailServiceRecycler.setLayoutManager(DetailServiceLayout);
                DetailServiceRecycler.setAdapter(DetailServiceAdapter);
                DetailServiceAdapter.notifyDataSetChanged();

            }
        });
        btnAddSparepart_TransaksiForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.d( "onClick: ",sparepartData.get(indexSparepart).getName());
                detailSpareparts.add(new TransactionSparepart(
                        0,
                        Integer.parseInt(txtAmount_TransaksiFormSparepart.getText().toString()),
                        Integer.parseInt(sparepartData.get(indexSparepart).getSellPrice()),
                        Integer.parseInt(sparepartData.get(indexSparepart).getSellPrice())*Integer.parseInt(txtAmount_TransaksiFormSparepart.getText().toString()),
                        "",
                        sparepartData.get(indexSparepart).getId(),
                        sparepartData.get(indexSparepart).getType(),
                        sparepartData.get(indexSparepart).getName(),
                        sparepartData.get(indexSparepart).getBrand(),
                        mechanicData.get(indexMontirSparepart).getId(),
                        mechanicData.get(indexMontirSparepart).getName(),
                        motorData.get(indexMotorSparepart).getId(),
                        motorData.get(indexMotorSparepart).getPlate()

                ));
                DetailSparepartRecycler = findViewById(R.id.detailSparepart_list);
                DetailSparepartRecycler.setHasFixedSize(true);
                DetailSparepartLayout = new LinearLayoutManager(TransactionForm.this);
                DetailSparepartAdapter = new TransactionDetailSparepartAdapter(detailSpareparts,TransactionForm.this);

                DetailSparepartRecycler.setLayoutManager(DetailSparepartLayout);
                DetailSparepartRecycler.setAdapter(DetailSparepartAdapter);
                DetailSparepartAdapter.notifyDataSetChanged();
            }
        });


//        dropdownNamaPelanggan_TransaksiForm.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { //Listener dropdown tipe sparepart saat dipilih
//            @Override
//            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
//                selectedIdCust = listIdCustomer.get(position); //Mendapatkan id dari dropdown yang dipilih
//                Log.d("motorData: ",String.valueOf(motorData.size()));
//                for (int i = 0; i < motorData.size(); i++) {
//
//                    if( motorData.get(i).getIdCustomer().toString().equalsIgnoreCase(selectedIdCust)) {
//                        String plateMotor = motorData.get(i).getPlate();
//                        String idMotor = motorData.get(i).getId().toString();
//
//                        listPlateMotor.add(plateMotor);
//                        listIdMotor.add(idMotor);
//                    }
//
//                }
//                ArrayAdapter<String> adapter = new ArrayAdapter<>(TransactionForm.this,
//                            android.R.layout.simple_spinner_dropdown_item
//                            , listPlateMotor);
//                dropdownPlatKendaraan_TransaksiFormService.setAdapter(adapter);
//                dropdownPlatKendaraan_TransaksiFormSparepart.setAdapter(adapter);
//
//
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parentView) {
//            }
//        });
//        getPutExtra();
//
//        btnSaveTransaction.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) { //Listener button btnSaveSparepart saat di klik
////                if (formChecking() == 0 && editMode == 0) { //kalau pengecekan form benar dan tidak pada mode edit
//                    addTransaction();
////                } else if (formChecking() == 0 && editMode == 1) { //kalau pengecekan form benar dan pada mode edit
////                    editTransaction();
////                }
//            }
//        });
//
//        btnCancelTransaction.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) { //Listener button btnSaveSparepart saat di klik
//                Toast.makeText(TransactionForm.this, "Batal.", Toast.LENGTH_SHORT).show();
//                final Intent intent = new Intent(TransactionForm.this, MainActivity.class);
//                intent.putExtra("menuBefore", 6);
//                startActivity(intent);
//            }
//        });
//
//        btnAddDetailMotor_TransactionForm.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//
//        btnAddDetailService.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//
//        dropdownStatusProses_TransaksiForm.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { //Listener dropdown tipe sparepart saat dipilih
//            @Override
//            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
//                selectedId = listIdCustomer.get(position); //Mendapatkan id dari dropdown yang dipilih
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parentView) {
//            }
//        });
    }
        public void dropdownCustListener(){
            dropdownNamaPelanggan_TransaksiForm.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { //Listener dropdown tipe sparepart saat dipilih
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    selectedIdCust = listIdCustomer.get(position); //Mendapatkan id dari dropdown yang dipilih
                    Log.d("motorData: ",String.valueOf(motorData.size()));
                    Log.d("idCust: ",selectedIdCust);
                    listPlateMotor.clear();
                    listIdMotor.clear();
                    for (int i = 0; i < motorData.size(); i++) {
                        Log.d("idCustMotor: ",motorData.get(i).getIdCustomer().toString());

                        if( motorData.get(i).getIdCustomer().toString().equalsIgnoreCase(selectedIdCust)) {
                            String plateMotor = motorData.get(i).getPlate();
                            String idMotor = motorData.get(i).getId().toString();

                            listPlateMotor.add(plateMotor);
                            listIdMotor.add(idMotor);
                        }

                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(TransactionForm.this,
                            android.R.layout.simple_spinner_dropdown_item
                            , listPlateMotor);
                    dropdownPlatKendaraan_TransaksiFormService.setAdapter(adapter);
                    dropdownPlatKendaraan_TransaksiFormSparepart.setAdapter(adapter);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                }
            });
        }
        public void setInit(){ //pendeklarasian objek-objek yang ada di layout

            //depan itu nama form
//            txtTanggal_TransactionForm = findViewById(R.id.txtTanggal_TransactionForm);
//            txtNamaPelanggan_TransaksiForm = findViewById(R.id.txtNamaPelanggan_TransaksiForm);
//            txtAlamatCustomer_transaction = findViewById(R.id.txtAlamatCustomer_transaction);
//            txtNoTelp_TransactionForm = findViewById(R.id.txtNoTelpSales_transaction);

            dropdownNamaPelanggan_TransaksiForm = findViewById(R.id.dropdownNamaPelanggan_TransaksiForm);
            dropdownStatusProses_TransaksiForm  = findViewById(R.id.dropdownStatusProses_TransaksiForm);
            dropdownJenisTransaksi_TransaksiForm = findViewById(R.id.dropdownJenisTransaksi_TransaksiForm);
            dropdownJenisService_TransaksiFormService = findViewById(R.id.dropdownJenisService_TransaksiFormService);
            dropdownPlatKendaraan_TransaksiFormService = findViewById(R.id.dropdownPlatKendaraan_TransaksiFormService);
            dropdownMontir_TransaksiFormService = findViewById(R.id.dropdownMontir_TransaksiFormService);

            dropdownSparepart_TransaksiFormSparepart = findViewById(R.id.dropdownSparepart_TransaksiFormSparepart);
            dropdownMontir_TransaksiFormSparepart = findViewById(R.id.dropdownMontir_TransaksiFormSparepart);
            dropdownPlatKendaraan_TransaksiFormSparepart = findViewById(R.id.dropdownPlatKendaraan_TransaksiFormSparepart);
            txtAmount_TransaksiFormSparepart = findViewById(R.id.txtAmount_TransaksiFormSparepart);

            btnSaveTransaction = findViewById(R.id.btnSaveTransaksi);
            btnCancelTransaction = findViewById(R.id.btnCancelTransaksi);
            btnAddSparepart_TransaksiForm = findViewById(R.id.btnAddSparepart_TransaksiForm);
            btnAddService_TransaksiForm = findViewById(R.id.btnAddService_TransaksiForm);
            transactionService = findViewById(R.id.transactionService);
            transactionSparepart = findViewById(R.id.transactionSparepart);


            // dropdownNamaPelanggan_TransaksiForm
        }

        private int getIndex(Spinner spinner, String myString){
        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                return i;
            }
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
        dropdownStatusProses_TransaksiForm.setAdapter(adapterStatus);

        listTypeTransaction.add("Sparepart");
        listTypeTransaction.add("Sparepart dan Services");
        listTypeTransaction.add("Services");
        ArrayAdapter<String> adapterType = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item
                , listTypeTransaction);
        dropdownJenisTransaksi_TransaksiForm.setAdapter(adapterType);

        // =================================================== GET CUSTOMER

        ApiClient service = RetrofitClient.getRetrofitInstance().create(ApiClient.class);

        Call<CustomerList> call = service.getCustomer();
        //Log.d("responsecode", String.valueOf(request.getSparepart()));

        call.enqueue(new Callback<CustomerList>() {
            @Override
            public void onResponse(Call<CustomerList> call, Response<CustomerList> response) {
                if (response.isSuccessful()) {
                    List<Customer> spinnerArrayList = response.body().getData();
                    customerData = response.body().getData();
                    for (int i = 0; i < spinnerArrayList.size(); i++) {
                        String nameCust = spinnerArrayList.get(i).getName();
                        String idCust = spinnerArrayList.get(i).getId().toString();

                        listNameCustomer.add(nameCust);
                        listIdCustomer.add(idCust);
//                        listNameSales.add(nameSales+' '+supplierSales);
//                        listIdSales.add(idSales);
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(TransactionForm.this,
                            android.R.layout.simple_spinner_dropdown_item
                            , listNameCustomer);
                    dropdownNamaPelanggan_TransaksiForm.setAdapter(adapter);
//                    getPutExtra();

                    Intent intent = getIntent();

//                    if(!String.valueOf(intent.getStringExtra("id")).equals("null")) { //pengecekan ada atau tidaknya parsing data dari aktivity sebelumnya
////                        mProgress.show();
//                        getPutExtra();
//                    }
                }
            }
            @Override
            public void onFailure(Call<CustomerList> call, Throwable t) {
                Log.d("Error",t.getMessage());
            }

        });

        // =================================================== GET SPAREPART

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

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(TransactionForm.this,
                            android.R.layout.simple_spinner_dropdown_item
                            , listNameSparepart);
                    dropdownSparepart_TransaksiFormSparepart.setAdapter(adapter);

                    Intent intent = getIntent();

//                    if(!String.valueOf(intent.getStringExtra("id")).equals("null")) { //pengecekan ada atau tidaknya parsing data dari aktivity sebelumnya
////                        mProgress.show();
//                        getPutExtra();
//                    }
                }
            }
            @Override
            public void onFailure(Call<SparepartList> call, Throwable t) {
                Log.d("Error",t.getMessage());
            }

        });
        // =================================================== GET MECHANIC

        Call<EmployeeList> call3 = service.getEmployee();
        //Log.d("responsecode", String.valueOf(request.getSparepart()));

        call3.enqueue(new Callback<EmployeeList>() {
            @Override
            public void onResponse(Call<EmployeeList> call2, Response<EmployeeList> response) {
                if (response.isSuccessful()) {
                    List<Employee> spinnerArrayList = response.body().getData();
//                    mechanicData = response.body().getData();

                    for (int i = 0; i < spinnerArrayList.size(); i++) {
                        if( spinnerArrayList.get(i).getRole().equalsIgnoreCase("Mechanic")){
                            mechanicData.add(new Employee(
                                        spinnerArrayList.get(i).getId(),
                                        spinnerArrayList.get(i).getName(),
                                        spinnerArrayList.get(i).getPhoneNumber(),
                                        spinnerArrayList.get(i).getAddress(),
                                        spinnerArrayList.get(i).getSalary(),
                                        spinnerArrayList.get(i).getRole(),
                                        spinnerArrayList.get(i).getBranch()
                                        )
                                    );
                            String nameEmployee = spinnerArrayList.get(i).getName();
                            String idEmployee = spinnerArrayList.get(i).getId().toString();

                            listNameMontir.add(nameEmployee);
                            listIdMontir.add(idEmployee);
                        }

                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(TransactionForm.this,
                            android.R.layout.simple_spinner_dropdown_item
                            , listNameMontir);
                    dropdownMontir_TransaksiFormService.setAdapter(adapter);
                    dropdownMontir_TransaksiFormSparepart.setAdapter(adapter);

                    Intent intent = getIntent();

//                    if(!String.valueOf(intent.getStringExtra("id")).equals("null")) { //pengecekan ada atau tidaknya parsing data dari aktivity sebelumnya
////                        mProgress.show();
//                        getPutExtra();
//                    }
                }
            }
            @Override
            public void onFailure(Call<EmployeeList> call, Throwable t) {
                Log.d("Error",t.getMessage());
            }

        });
        // =================================================== GET SERVICE

        Call<ServicesList> call4 = service.getService();
        //Log.d("responsecode", String.valueOf(request.getSparepart()));

        call4.enqueue(new Callback<ServicesList>() {
            @Override
            public void onResponse(Call<ServicesList> call2, Response<ServicesList> response) {
                if (response.isSuccessful()) {
                    List<Services> spinnerArrayList = response.body().getData();
                    serviceData = response.body().getData();

                    for (int i = 0; i < spinnerArrayList.size(); i++) {

                        String nameService = spinnerArrayList.get(i).getNameService();
                        String idService = spinnerArrayList.get(i).getIdService().toString();

                        listNameService.add(nameService);
                        listIdService.add(idService);


                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(TransactionForm.this,
                            android.R.layout.simple_spinner_dropdown_item
                            , listNameService);
                    dropdownJenisService_TransaksiFormService.setAdapter(adapter);


                    Intent intent = getIntent();

//                    if(!String.valueOf(intent.getStringExtra("id")).equals("null")) { //pengecekan ada atau tidaknya parsing data dari aktivity sebelumnya
////                        mProgress.show();
//                        getPutExtra();
//                    }
                }
            }
            @Override
            public void onFailure(Call<ServicesList> call, Throwable t) {
                Log.d("Error",t.getMessage());
            }

        });
        // =================================================== GET SERVICE

        Call<MotorCustomerList> call5 = service.getMotorCustomer();
        //Log.d("responsecode", String.valueOf(request.getSparepart()));

        call5.enqueue(new Callback<MotorCustomerList>() {
            @Override
            public void onResponse(Call<MotorCustomerList> call2, Response<MotorCustomerList> response) {
                if (response.isSuccessful()) {
                    List<MotorCustomer> spinnerArrayList = response.body().getData();
                    motorData = response.body().getData();
                    dropdownCustListener();

//                    for (int i = 0; i < spinnerArrayList.size(); i++) {
////                        if( spinnerArrayList.get(i).getIdCustomer().toString().equalsIgnoreCase(selectedIdCust)) {
//                            String plateMotor = spinnerArrayList.get(i).getPlate();
//                            String idMotor = spinnerArrayList.get(i).getId().toString();
//
//                            listPlateMotor.add(plateMotor);
//                            listIdMotor.add(idMotor);
////                        }
//
//                    }

//                    ArrayAdapter<String> adapter = new ArrayAdapter<>(TransactionForm.this,
//                            android.R.layout.simple_spinner_dropdown_item
//                            , listPlateMotor);
//                    dropdownPlatKendaraan_TransaksiFormService.setAdapter(adapter);

//                    dropdownNamaPelanggan_TransaksiForm.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { //Listener dropdown tipe sparepart saat dipilih
//                        @Override
//                        public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
//                            selectedIdCust = listIdCustomer.get(position); //Mendapatkan id dari dropdown yang dipilih
//                            motorData
//                        }
//
//                        @Override
//                        public void onNothingSelected(AdapterView<?> parentView) {
//                        }
//                    });

//                    if(!String.valueOf(intent.getStringExtra("id")).equals("null")) { //pengecekan ada atau tidaknya parsing data dari aktivity sebelumnya
////                        mProgress.show();
//                        getPutExtra();
//                    }
                }
            }
            @Override
            public void onFailure(Call<MotorCustomerList> call, Throwable t) {
                Log.d("Error",t.getMessage());
            }

        });
    }

    public void getPutExtra(){ // mendapatkan parsing data dari activity sebelumnya beserta pengesetan form sesuai parsing data
        Intent intent = getIntent();

//        Log.d("Id Sparepart", String.valueOf(((Intent) intent).getStringExtra("id")));
        if(!String.valueOf(intent.getStringExtra("nama")).equals("null")) {

            String id = intent.getStringExtra("id");
            //Log.d("id",id);
            idTransaction = id;
            String tanggal = intent.getStringExtra("tanggal");
//            List<ListSales>

//            Log.d("gambar","http://10.53.11.64.com/"+gambar);

            txtTanggal_TransactionForm.setText(tanggal);
//            txtListSales.setList(sales);

            editMode = 1; //pengubahan menjadi mode edit
        }
    }

//    private int formChecking(){ //Fungsi Check Form
//
//        String  tanggalSparepart=txtTanggal_TransactionForm  .getText().toString(),
//                namaPelanggan=txtNamaPelanggan_TransaksiForm.getText().toString(),
//                noTelpPelanggan=txtNoTelp_TransactionForm .getText().toString(),
//                alamatPelanggan=txtAlamatCustomer_transaction .getText().toString()
//
//        if(tanggalSparepart.isEmpty()){
//            txtTanggal_TransactionForm.setError("Tanggal diperlukan.");
//            txtTanggal_TransactionForm.requestFocus(); //INPUTANNYA DIARAHKAN KESITU
//            return 1;
//        }
//
//        if(namaPelanggan.isEmpty()){
//            txtNamaPelanggan_TransaksiForm.setError("Nama pelanggan diperlukan.");
//            txtNamaPelanggan_TransaksiForm.requestFocus();
//            return 1;
//        }
//
//        if(noTelpPelanggan.isEmpty()){
//            txtNoTelp_TransactionForm.setError("No telp pelanggan diperlukan.");
//            txtNoTelp_TransactionForm.requestFocus();
//            return 1;
//        }
//
//        if(alamatPelanggan.isEmpty()){
//            txtAlamatCustomer_transaction.setError("Alamat pelanggan diperlukan.");
//            txtAlamatCustomer_transaction.requestFocus();
//            return 1;
//        }
//        return 0;
//    }

//    private void addTransaction() { // Fungsi menambahkan sparepart
//        mProgress.show();
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        ApiClient retrofitInterface = retrofit.create(ApiClient.class);
//        String type_transaction = dropdownNamaPelanggan_TransaksiForm.getSelectedItem().toString();
//        String status_process = dropdownStatusProses_TransaksiForm.getSelectedItem().toString();
//        String total_transaction = dropdow.getSelectedItem().toString();
//        String id_customer = selectedIdSales;
//
//        Log.d("date_procurement: ", date_procurement); //untuk edit text
//        Log.d("status_procurement: ", status_procurement);
//        Log.d("id_sales: ", id_sales); //karena drop down gak perlu makannya
//
//
//        Call<ResponseBody> call = retrofitInterface.addTransaction(type_transaction, status_process, total_transaction, id_customer);
////        mProgressBar.setVisibility(View.VISIBLE);
//        call.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
//
//                try {
//                    JSONObject jsonRes = null;
//                    jsonRes = new JSONObject(response.body().string());
//                    String idprocurement =  jsonRes.getJSONObject("data").getString("id_procurement");
//                    Log.d("idprocurement", idprocurement);
////                mProgressBar.setVisibility(View.GONE);
//
//                    if (response.isSuccessful()) {
//
//                        for(int x=0;x<detailProcurement.size();x++)
//                        {
//                            Log.d("masuk", "massuk");
//                            Retrofit retrofit = new retrofit2.Retrofit.Builder()
//                                    .baseUrl(URL)
//                                    .addConverterFactory(GsonConverterFactory.create())
//                                    .build();
//
//                            ApiClient apiClient = retrofit.create(ApiClient.class);
//
//                            Double price_detail_procurement =  Double.parseDouble(detailProcurement.get(x).getPrice().toString());
//                            int amount_detail_procurement = Integer.parseInt(detailProcurement.get(x).getAmount().toString());
//                            Double subtotal_detail_procurement =  Double.parseDouble(detailProcurement.get(x).getSubtotal().toString());
//                            String id_sparepart = detailProcurement.get(x).getIdSparepart();
//                            int id_procurement = Integer.parseInt(idprocurement);
//
//                            Call<ResponseBody> addProcurementDetail = apiClient.addProcurementDetail(
//                                    price_detail_procurement,
//                                    amount_detail_procurement,
//                                    subtotal_detail_procurement,
//                                    id_sparepart,
//                                    id_procurement
//                            );
//
//                            addProcurementDetail.enqueue(new Callback<ResponseBody>() {
//                                @Override
//                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                                    Log.d("successe", "hehehe");
//                                    try {
//                                        JSONObject jsonRes = new JSONObject(response.body().string());
//                                        String iddetailprocurement =  jsonRes.getJSONObject("data").getString("id_procurement_detail");
//                                        Log.d("idprocurement", iddetailprocurement);
//                                    } catch (JSONException e) {
//                                        e.printStackTrace();
//                                    } catch (IOException e) {
//                                        e.printStackTrace();
//                                    }
//                                }
//
//                                @Override
//                                public void onFailure(Call<ResponseBody> call, Throwable t) {
//                                }
//                            });
//                        }
////                        ResponseBody responseBody = response.body();
////                        Log.d("SUKSES",responseBody.toString());
//                        Toast.makeText(SparepartProcurementForm.this, "Sukses", Toast.LENGTH_SHORT).show();
//                        final Intent intent = new Intent(SparepartProcurementForm.this, MainActivity.class);
//                        intent.putExtra("menuBefore", 5);
//                        startActivity(intent);
//
//
//                    } else {
//                        Log.d( "onResponse: ",response.message());
//                        Toast.makeText(SparepartProcurementForm.this, "Gagal", Toast.LENGTH_SHORT).show();
//                        mProgress.dismiss();
//                    }
//                    mProgress.dismiss();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                mProgress.dismiss();
//                Log.d("onFailure: ",t.toString());
//
////                mProgressBar.setVisibility(View.GONE);
////                Log.d(TAG, "onFailure: "+t.getLocalizedMessage());
//            }
//        });
//    }
}
