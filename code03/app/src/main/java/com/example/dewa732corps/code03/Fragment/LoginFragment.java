package com.example.dewa732corps.code03.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;


import com.example.dewa732corps.code03.Controller.ApiClient;
import com.example.dewa732corps.code03.Controller.SessionController;
import com.example.dewa732corps.code03.Main2Activity;
import com.example.dewa732corps.code03.MainActivity;
import com.example.dewa732corps.code03.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginFragment extends Fragment {
    View login;
    FrameLayout framelay;
    private EditText txtUsername,txtPassword;
    private Button btnLogin;
    SessionController session;

    private View mProgressView;
    private View mLoginFormView;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        login= inflater.inflate(R.layout.activity_login,container,false);

        setinit();
        session = new SessionController(getContext());
//        if(session.isLoggedIn())
//        {
//            Intent in = new Intent(getActivity(), MainActivity.class);
//            startActivity(in);
//        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        return login;
    }
    public void setinit(){
        btnLogin= login.findViewById(R.id.login_button);
        txtUsername=login.findViewById(R.id.username);
        txtPassword=login.findViewById(R.id.password);
        mProgressView = login.findViewById(R.id.login_progress);
    }
    public void login(){
        if(txtUsername.getText().toString().isEmpty()|| txtPassword.getText().toString().isEmpty()){
            txtUsername.setError("Username harus diisi!");
            txtPassword.setError("Password harus diisi!");

        }else{

            final ProgressDialog progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Authenticating...");
            progressDialog.show();

            Retrofit retrofit= new retrofit2.Retrofit.Builder()
                    .baseUrl("https://sibento.yafetrakan.com/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            ApiClient apiClient = retrofit.create(ApiClient.class);
//            Call<ResponseUser> call = apiUser.GetLogin(txtUsername.getText().toString(),txtPassword.getText().toString());
            Call<ResponseBody> call = apiClient.GetLogin(txtUsername.getText().toString(), txtPassword.getText().toString());
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        JSONObject jsonRes = new JSONObject(response.body().string());
                        Toast.makeText(getContext(), jsonRes.getJSONObject("data").getString("role"), Toast.LENGTH_SHORT).show();
                        session.createLoginSessions(
                                jsonRes.getJSONObject("data").getString("role"),
                                jsonRes.getJSONObject("data").getString("username"), jsonRes.getJSONObject("data").getString("id"));
//                        final Intent intent = new Intent(MainActivity.this, Beranda.class);

                        new android.os.Handler().postDelayed(
                                new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getContext(), "Berhasil Login", Toast.LENGTH_SHORT).show();
//                                        startActivity(intent);
                                        Intent in = new Intent(getActivity(), MainActivity.class);
                                        startActivity(in);


//                                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
//                                        transaction.add(R.id.framelay, new BerandaFragment());
//                                        transaction.commit();

                                        progressDialog.dismiss();
                                    }
                                },3000);

                    } catch (JSONException e){
                        progressDialog.dismiss();
                        e.printStackTrace();
                    } catch (IOException e) {
                        progressDialog.dismiss();
                        e.printStackTrace();
                    } catch (Throwable e){
                        progressDialog.dismiss();
//                        tError.setText("Username dan Password tidak cocok");
                        Toast.makeText(getContext(), "Username atau Password Salah!", Toast.LENGTH_SHORT).show();

                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
//                    tError.setText("Koneksi Internet Tidak Stabil");
                    Toast.makeText(getContext(), "Koneksi Internet Tidak Stabil", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    Toast.makeText(getContext(), "Internet err\n" + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

    }
}
