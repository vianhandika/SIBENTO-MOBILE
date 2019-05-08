package com.example.dewa732corps.code03;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.dewa732corps.code03.Controller.ApiClient;
import com.example.dewa732corps.code03.Controller.SessionController;
import com.example.dewa732corps.code03.Fragment.Customer.CustomerForm;

import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.dewa732corps.code03.Controller.SessionController.KEY_ID;
import static com.example.dewa732corps.code03.Controller.SessionController.KEY_ROLE;

public class UbahPassword extends Fragment {
    View view;
    FrameLayout framelay;
    EditText txtNewPassword,txtNewPassword2;
    String id_user;
    Button btnSavePassword,btnCancelPassword;
    private static final int INTENT_REQUEST_CODE = 100;
    public static final String URL = "http://10.53.2.0/api/";
    SessionController session;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        view= inflater.inflate(R.layout.ubah_password,container,false);
        setinit();
        session = new SessionController(getContext());
        HashMap<String, String> test = session.getUserSessionsDetails();
        id_user = test.get(KEY_ID);
//        Log.d( "Role: ",test.get(KEY_ROLE));

        btnSavePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //Listener button btnSaveSparepart saat di klik
                if(formChecking()==0)
                {
                    changepassword();

                }

            }
        });
        return view;
    }

    public void setinit(){
        txtNewPassword= view.findViewById(R.id.txtNewPassword);
        txtNewPassword2=view.findViewById(R.id.txtNewPassword2);
        btnSavePassword=view.findViewById(R.id.btnSavePassword);
        btnCancelPassword = view.findViewById(R.id.btnCancelPassword);
    }

    private void changepassword() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiClient retrofitInterface = retrofit.create(ApiClient.class);

        String newPassword = txtNewPassword.getText().toString();
        String id = id_user;
//        String idUser = id_user;
//
        Call<ResponseBody> call = retrofitInterface.editPassword(id, newPassword);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {


                if (response.isSuccessful()) {
                    ResponseBody responseBody = response.body();
//                    Log.d("SUKSES UPDATE DATA",responseBody.toString());
                    Toast.makeText(getContext(), "SUKSES UPDATE PASSWORD", Toast.LENGTH_SHORT).show();
                    final Intent intent = new Intent(getContext(), MainActivity.class);
                    intent.putExtra("menuBefore", 4);
                    startActivity(intent);

                } else {
                    Log.d( "onResponse: ",response.message());
                    Toast.makeText(getContext(), "GAGAL UPDATE PASSWORD", Toast.LENGTH_SHORT).show();
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
    private int formChecking(){ //Fungsi Check Form

        String  password1=txtNewPassword.getText().toString(),
                password2=txtNewPassword2.getText().toString();

        if(password1.isEmpty()){
            txtNewPassword.setError("Password diperlukan.");
            txtNewPassword.requestFocus();
            return 1;
        }

        if(password2.isEmpty()){
            txtNewPassword2.setError("Pasword konfirmasi diperlukan.");
            txtNewPassword2.requestFocus();
            return 1;
        }
        return 0;
    }


}
