package com.example.dewa732corps.code03.Fragment.Supplier;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dewa732corps.code03.Controller.ApiClient;
import com.example.dewa732corps.code03.R;

import java.io.ByteArrayOutputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SupplierForm extends Fragment {
    View dashboard;
    Button btnSaveSupplier, btnCancelSupplier;
    android.support.v7.widget.Toolbar toolbar;
    EditText txtNameSupplier,txtAddressSupplier,txtPhoneNumberSupplier;

    public static final String URL = "https://sibento.yafetrakan.com/api/";

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        dashboard= inflater.inflate(R.layout.menu2_supplier_form,container,false);
        toolbar = (android.support.v7.widget.Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Tambah Supplier");

        setInit();

        btnSaveSupplier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(formChecking()==0){
                    addSupplier();
                }
            }
        });

        return dashboard;
    }

    private int formChecking(){
        //Fungsi Check Form

        String nameSupplier=txtNameSupplier.getText().toString(),
                addressSupplier=txtAddressSupplier .getText().toString(),
                phoneNumberSupplier=txtPhoneNumberSupplier .getText().toString();

        if(nameSupplier.isEmpty()){
            txtNameSupplier.setError("Nama supplier diperlukan.");
            txtNameSupplier.requestFocus();
            return 1;
        }

        if(addressSupplier.isEmpty()){
            txtAddressSupplier.setError("Alamat supplier diperlukan.");
            txtAddressSupplier.requestFocus();
            return 1;
        }

        if(phoneNumberSupplier.isEmpty()){
            txtPhoneNumberSupplier.setError("Nomor telepon supplier diperlukan.");
            txtPhoneNumberSupplier.requestFocus();
            return 1;
        }
        return 0;
    }

    public void setInit(){

        //depan itu nama form

        txtNameSupplier = dashboard.findViewById(R.id.txtNameSupplier);
        txtAddressSupplier = dashboard.findViewById(R.id.txtAddressSupplier);
        txtPhoneNumberSupplier = dashboard.findViewById(R.id.txtPhoneNumberSupplier);
        btnSaveSupplier = dashboard.findViewById(R.id.btnSaveSupplier);
        btnCancelSupplier = dashboard.findViewById(R.id.btnCancelSupplier);
    }

    private void addSupplier() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiClient retrofitInterface = retrofit.create(ApiClient.class);

        RequestBody name_supplier =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), txtNameSupplier.getText().toString());

        RequestBody address_supplier =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), txtAddressSupplier.getText().toString());

        RequestBody phone_number_supplier =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), txtPhoneNumberSupplier.getText().toString());

//        Call<ResponseBody> call = retrofitInterface.updateImageSparepart(body,requestId);
////        mProgressBar.setVisibility(View.VISIBLE);
//        call.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
//
////                mProgressBar.setVisibility(View.GONE);
//
//                if (response.isSuccessful()) {
//
//                    ResponseBody responseBody = response.body();
//                    Log.d("SUKSES",responseBody.toString());
////                    mBtImageShow.setVisibility(View.VISIBLE);
////                     mImageUrl = URL + responseBody.getPath();
////                    Snackbar.make(findViewById(R.id.content), responseBody.getMessage(),Snackbar.LENGTH_SHORT).show();
//
//                } else {
//
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

        Call<ResponseBody> call = retrofitInterface.addSupplier(name_supplier, address_supplier, phone_number_supplier);
//        mProgressBar.setVisibility(View.VISIBLE);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {

//                mProgressBar.setVisibility(View.GONE);

                if (response.isSuccessful()) {

                    ResponseBody responseBody = response.body();
                    Log.d("SUKSES",responseBody.toString());
//                    mBtImageShow.setVisibility(View.VISIBLE);
//                     mImageUrl = URL + responseBody.getPath();
//                    Snackbar.make(findViewById(R.id.content), responseBody.getMessage(),Snackbar.LENGTH_SHORT).show();

                } else {
                    Log.d( "onResponse: ",response.message());
                    Toast.makeText(getContext(), "Gagal", Toast.LENGTH_SHORT).show();

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
                Log.d("onFailure: ",t.toString());

//                mProgressBar.setVisibility(View.GONE);
//                Log.d(TAG, "onFailure: "+t.getLocalizedMessage());
            }
        });
    }
}
