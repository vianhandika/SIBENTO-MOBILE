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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;

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
//        btnPick.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Creating intent.
//                Intent intent = new Intent();
//
//                // Setting intent type as image to select image from phone storage.
//                intent.setType("image/*");
//                intent.setAction(Intent.ACTION_GET_CONTENT);
//                startActivityForResult(Intent.createChooser(intent, "Please Select Image"), Image_Request_Code);
//
//            }
//        });
//
//        btnAdd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                addSparepart();
//
//
//            }
//        });

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
            Log.d("Test1",FilePathUri.toString());


            try {

                Bitmap bitmap = MediaStore.Images.Media.getBitmap(dashboard.getContext().getContentResolver(), FilePathUri);
                imageSparepart.setImageBitmap(bitmap);
                ImageBitmap=bitmap;

//                ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                ImageBitmap.compress(Bitmap.CompressFormat.JPEG,100,baos);
//                InputStream is = getContentResolver().openInputStream(data.getData());
//
//                uploadImage(getBytes(is));

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

        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), data);
        RequestBody requestId =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), "1111-ASTRA-111");

        MultipartBody.Part body = MultipartBody.Part.createFormData("image_sparepart", "image.jpg", requestFile);

        Call<ResponseBody> call = retrofitInterface.uploadImage(body,requestId);
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

                    ResponseBody errorBody = response.errorBody();

                    Gson gson = new Gson();

                    try {

                        Response errorResponse = gson.fromJson(errorBody.string(), Response.class);
//                        Snackbar.make(findViewById(R.id.content), errorResponse.getMessage(),Snackbar.LENGTH_SHORT).show();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
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
