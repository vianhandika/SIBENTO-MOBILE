package com.example.dewa732corps.code03.Fragment.Sparepart;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.example.dewa732corps.code03.Controller.SparepartType;
import com.example.dewa732corps.code03.Controller.SparepartTypeList;
import com.example.dewa732corps.code03.R;
import com.google.gson.Gson;

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

public class SparepartForm extends Fragment {

    View dashboard;
    FrameLayout framelay;
    ImageView imageSparepart;
    Spinner dropdownType,dropdownPosisi,dropdownTempat;
    Button btnPilihGambar, btnSaveSparepart,btnCancelSparepart;
    EditText txtIdSparepart,txtNamaSparepart,txtMinStock,txtStock,txtHargaJual,txtHargaBeli,txtNomor,txtMerek;

    public Bitmap ImageBitmap;
    int Image_Request_Code = 1;
    Uri FilePathUri,FilePathUri2;
    String selectedId;
    private List<String> listNameType = new ArrayList<String>();
    private List<String> listIdType = new ArrayList<String>();
    private List<String> listPosisi = new ArrayList<String>();
    private List<String> listTempat = new ArrayList<String>();

    private static final int INTENT_REQUEST_CODE = 100;
    public static final String URL = "https://sibento.yafetrakan.com/api/";

    android.support.v7.widget.Toolbar toolbar;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        dashboard= inflater.inflate(R.layout.menu2_sparepart_form,container,false);
        toolbar = (android.support.v7.widget.Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Tambah Sparepart");

        setInit();
        setDropdown();
        btnPilihGambar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Creating intent.
                Intent intent = new Intent();

                // Setting intent type as image to select image from phone storage.
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Please Select Image"), Image_Request_Code);

            }
        });

        btnSaveSparepart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addSparepart();
            }
        });

        dropdownType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                selectedId = listIdType.get(position);
                //String selected = parentView.getItemAtPosition(position).toString();
                Toast.makeText(getContext(), "Choose " + selectedId, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        return dashboard;
    }
    public void setInit(){

        dropdownType = dashboard.findViewById(R.id.dropdownType);
        dropdownPosisi = dashboard.findViewById(R.id.dropdownPosisi);
        dropdownTempat = dashboard.findViewById(R.id.dropdownTempat);

        txtIdSparepart = dashboard.findViewById(R.id.txtIdSparepart);
        txtNamaSparepart = dashboard.findViewById(R.id.txtNamaSparepart);
        txtHargaBeli = dashboard.findViewById(R.id.txtHargaBeli);
        txtHargaJual = dashboard.findViewById(R.id.txtHargaJual);
        txtNomor = dashboard.findViewById(R.id.txtNomor);
        txtMinStock =dashboard.findViewById(R.id.txtMinStock);
        txtStock =dashboard.findViewById(R.id.txtStock);
        txtMerek = dashboard.findViewById(R.id.txtMerek);


        imageSparepart = dashboard.findViewById(R.id.imageSparepart);

        btnPilihGambar = dashboard.findViewById(R.id.btnPilihGambar);
        btnSaveSparepart = dashboard.findViewById(R.id.btnSaveSparepart);
        btnCancelSparepart = dashboard.findViewById(R.id.btnCancelSparepart);


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Image_Request_Code && resultCode == RESULT_OK && data != null && data.getData() != null) {

            FilePathUri = data.getData();
//            Log.d("Test1",FilePathUri.toString());

            try {

                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), FilePathUri);
                ImageBitmap=bitmap;
                imageSparepart.setImageBitmap(bitmap);

            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setDropdown(){

        listPosisi.add("DPN");
        listPosisi.add("TGH");
        listPosisi.add("BLK");

        listTempat.add("KACA");
        listTempat.add("BAN");
        listTempat.add("DUS");
        listTempat.add("KAYU");

        ArrayAdapter<String> adapterPosisi = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_dropdown_item
                , listPosisi);
        dropdownPosisi.setAdapter(adapterPosisi);

        ArrayAdapter<String> adapterTempat = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_dropdown_item
                , listTempat);
        dropdownTempat.setAdapter(adapterTempat);

        ApiClient service = RetrofitClient.getRetrofitInstance().create(ApiClient.class);


        Call<SparepartTypeList> call = service.getSparepartType();
        //Log.d("responsecode", String.valueOf(request.getSparepart()));

        call.enqueue(new Callback<SparepartTypeList>() {
            @Override
            public void onResponse(Call<SparepartTypeList> call, Response<SparepartTypeList> response) {
                if (response.isSuccessful()) {
                    List<SparepartType> spinnerArrayList = response.body().getData();
                    for (int i = 0; i < spinnerArrayList.size(); i++) {
                        String nameType = spinnerArrayList.get(i).getName();
                        String idType = spinnerArrayList.get(i).getId().toString();
                        listNameType.add(nameType);
                        listIdType.add(idType);


                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                            android.R.layout.simple_spinner_dropdown_item
                            , listNameType);
                    dropdownType.setAdapter(adapter);
                }

            }

            @Override
            public void onFailure(Call<SparepartTypeList> call, Throwable t) {
                Log.d("Error",t.getMessage());
            }

        });
    }

    private void addSparepart() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiClient retrofitInterface = retrofit.create(ApiClient.class);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageBitmap.compress(Bitmap.CompressFormat.JPEG,100,baos);
        byte[] data =baos.toByteArray();

        String txtplacement = dropdownPosisi.getSelectedItem().toString()+'-'+dropdownTempat.getSelectedItem().toString()+'-'+txtNomor.getText().toString();
        Log.d("id_sparepart: ",txtIdSparepart.getText().toString());
        Log.d("name_sparepart: ",txtNamaSparepart.getText().toString());
        Log.d("brand_sparepart: ",txtMerek.getText().toString());
        Log.d("id_sparepart_type: ",selectedId);
        Log.d("buy_price: ",txtHargaBeli.getText().toString());
        Log.d("sell_price: ",txtHargaJual.getText().toString());
        Log.d("placement: ",txtplacement);
        Log.d("min_stock: ",txtMinStock.toString());
        Log.d("stock: ",txtStock.toString());

        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), data);


        RequestBody id_sparepart =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), txtIdSparepart.getText().toString());
//        RequestBody requestId =
//                RequestBody.create(
//                        MediaType.parse("multipart/form-data"), "1111-ASTRA-111");

        RequestBody name_sparepart =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), txtNamaSparepart.getText().toString());

        RequestBody brand_sparepart =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), txtMerek.getText().toString());

        RequestBody id_sparepart_type =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), selectedId);

        RequestBody buy_price =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), txtHargaBeli.getText().toString());

        RequestBody sell_price =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), txtHargaJual.getText().toString());

        RequestBody placement =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), txtplacement);

        RequestBody minimal_stock_sparepart =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), txtMinStock.getText().toString());

        RequestBody stock_sparepart =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), txtStock.getText().toString());




        MultipartBody.Part body = MultipartBody.Part.createFormData("image_sparepart", "image.jpg", requestFile);

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

        Call<ResponseBody> call = retrofitInterface.addSparepart(body,id_sparepart,name_sparepart,brand_sparepart,stock_sparepart,minimal_stock_sparepart,buy_price,sell_price,placement,id_sparepart_type);
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
