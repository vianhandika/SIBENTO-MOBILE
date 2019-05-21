package com.example.dewa732corps.code03.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.se.omapi.Session;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.dewa732corps.code03.Adapter.ListSparepartAdapter;
import com.example.dewa732corps.code03.Controller.ApiClient;
import com.example.dewa732corps.code03.Controller.SessionController;
import com.example.dewa732corps.code03.Controller.Transaction;
import com.example.dewa732corps.code03.Controller.TransactionList;
import com.example.dewa732corps.code03.R;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListSparepartFragment extends Fragment {
    View listSparepart;
    FrameLayout frameLayout;
    android.support.v7.widget.Toolbar toolbar;
    private SearchView search;

    private RecyclerView recyclerListSparepart;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layout;
    private List<TransactionList> transactionLists;
    private List<Transaction> transactionData;

    ListSparepartAdapter sAdapter;
    ProgressDialog mProgress;
    ResponseBody AllData;

    SessionController session;

    Spinner dropdownListSparepart;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        listSparepart= inflater.inflate(R.layout.menu_pelanggan_listsparepart,container,false);
        setinit();

        Retrofit retrofit= new retrofit2.Retrofit.Builder()
                .baseUrl("https://sibento.yafetrakan.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiClient apiClient = retrofit.create(ApiClient.class);
        Call<TransactionList> transactionGet = apiClient.getTransaction(); //bener ini apa gak ya

        transactionGet.enqueue(new Callback<TransactionList>() {
            @Override
            public void onResponse(Call<TransactionList> call, Response<TransactionList> response) {
                try{
                    transactionData = response.body().getData();
//                    sAdapter = new ListSparepartAdapter(response.body().getData(), getContext());
                    sAdapter.notifyDataSetChanged();
                }
                catch (Exception e)
                {
                    Toast.makeText(getContext(), "Tidak Ada Transaksi!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TransactionList> call, Throwable t) {
                Toast.makeText(getContext(), "Fail", Toast.LENGTH_SHORT).show();
                mProgress.hide();
            }
        });
        return listSparepart;
    }

    private void setinit(){
        recyclerListSparepart = listSparepart.findViewById(R.id.sparepart_list);
        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
//        toolbar.setTitle("List Sparepart");

        recyclerListSparepart = listSparepart.findViewById(R.id.sparepart_list);
        dropdownListSparepart = listSparepart.findViewById(R.id.sortSpinner);
    }
}
