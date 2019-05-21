package com.example.dewa732corps.code03.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.se.omapi.Session;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.SearchView;

import com.example.dewa732corps.code03.Controller.ApiClient;
import com.example.dewa732corps.code03.Controller.SessionController;
import com.example.dewa732corps.code03.Fragment.Customer.CustomerForm;
import com.example.dewa732corps.code03.Fragment.Menu_Pelanggan.HistoryTransaksi;
import com.example.dewa732corps.code03.R;

import org.json.JSONException;

import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CekServiceFragment extends Fragment {
    View cekService;
    FrameLayout frameLayout;
    Button checkButton;
    android.support.v7.widget.Toolbar toolbar;
    EditText license_number, phone_number;

    SessionController session;
    private View mProgressView;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        cekService = inflater.inflate(R.layout.menu_pelanggan_cekservice_form, container, false);
        setinit();

        checkButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                cekService();
            }
        });

        return cekService;
    }

    public void setinit(){
        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
//        toolbar.setTitle("CHECK SERVICE");

        checkButton= cekService.findViewById(R.id.btnCekService);
        license_number = cekService.findViewById(R.id.txtPlatMotor_cekservice);
        phone_number = cekService.findViewById(R.id.txtNoTelp_cekservice);
        mProgressView = cekService.findViewById(R.id.cekService_progress);


        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), HistoryTransaksi.class);
                startActivity(intent);
            }
        });
    }

    private void cekService() {
        if(license_number.getText().toString().isEmpty() || phone_number.getText().toString().isEmpty()){
            license_number.setError("Plat nomor harus diisi!");
            phone_number.setError("Nomor HP harus diisi!");
        }
        else
        {
            final ProgressDialog progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Authenticating...");
            progressDialog.show();

            Retrofit retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl("https://sibento.yafetrakan.com/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            ApiClient apiClient = retrofit.create(ApiClient.class);
//            Call<ResponseBody> call = apiClient.getTransaction(license_number.getText().toString(), phone_number.getText().toString());
//            call.enqueue(new Callback<ResponseBody>() {
//                @Override
//                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                    try{
//
//                    }
//                    catch (JSONException e){
//                        progressDialog.dismiss();
//                        e.printStackTrace();
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<ResponseBody> call, Throwable t) {
//
//                }
//            });
        }
    }
}
