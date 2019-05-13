package com.example.dewa732corps.code03.Fragment.Transcation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dewa732corps.code03.Fragment.Customer.CustomerForm;
import com.example.dewa732corps.code03.MainActivity;
import com.example.dewa732corps.code03.R;

public class TransactionForm extends AppCompatActivity {
//
//    Button btnSaveTransaction,btnCancelTransaction, btnAddDetailMotor_TransactionForm, btnAddDetailService;
//    EditText txtTanggal_TransactionForm,txtNoTelpSales_transaction,txtNoTelp_TransactionForm;
//    String idTransaction;
//    private int editMode=0;
//    private static final int INTENT_REQUEST_CODE = 100;
//    public static final String URL = "https://sibento.yafetrakan.com/api/";
//
//    protected void onCreate(Bundle savedInstanceState) {  //Fungsi didalamnya dijalankan pertama kali
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.menu_customerservice_customer_form);
//
//        setInit();
//        getPutExtra();
//
//        btnSaveTransaction.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) { //Listener button btnSaveSparepart saat di klik
//                if (formChecking() == 0 && editMode == 0) { //kalau pengecekan form benar dan tidak pada mode edit
//                    addTransaction();
//                } else if (formChecking() == 0 && editMode == 1) { //kalau pengecekan form benar dan pada mode edit
//                    editTransaction();
//                }
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
//    }
//
//        public void setInit(){ //pendeklarasian objek-objek yang ada di layout
//
//            //depan itu nama form
////            txtTanggal_TransactionForm = findViewById(R.id.txtTanggal_TransactionForm);
//            txtNoTelpSales_transaction = findViewById(R.id.txtNoTelpSales_transaction);
//            txtNoTelp_TransactionForm = findViewById(R.id.txtNoTelp_TransactionForm);
//
//            btnSaveTransaction = findViewById(R.id.btnSaveTransaction);
//            btnCancelTransaction = findViewById(R.id.btnCancelTransaction);
//
//            // dropdownNamaPelanggan_TransaksiForm
//            // dropdownNamaSparepart_TransactionForm
//        }
//
//    public void getPutExtra(){ // mendapatkan parsing data dari activity sebelumnya beserta pengesetan form sesuai parsing data
//        Intent intent = getIntent();
//
////        Log.d("Id Sparepart", String.valueOf(((Intent) intent).getStringExtra("id")));
//        if(!String.valueOf(intent.getStringExtra("nama")).equals("null")) {
//
//            String id = intent.getStringExtra("id");
//            //Log.d("id",id);
//            idTransaction = id;
//            String nama = intent.getStringExtra("nama");
//            String alamat = intent.getStringExtra("alamat");
//            String notelp = intent.getStringExtra("notelp");
////            List<ListSales>
//
////            Log.d("gambar","http://10.53.11.64.com/"+gambar);
//
//            txtNamaCustomer.setText(nama);
//            txtAlamatCustomer.setText(alamat);
//            txtNoTelpCustomer.setText(notelp);
////            txtListSales.setList(sales);
//
//            editMode = 1; //pengubahan menjadi mode edit
//        }
//    }
}
