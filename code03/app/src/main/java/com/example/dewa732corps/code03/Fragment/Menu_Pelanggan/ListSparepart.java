package com.example.dewa732corps.code03.Fragment.Menu_Pelanggan;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.dewa732corps.code03.Adapter.ListSparepartAdapter;
import com.example.dewa732corps.code03.Controller.ApiClient;
import com.example.dewa732corps.code03.Controller.Sparepart;
import com.example.dewa732corps.code03.Controller.SparepartList;
import com.example.dewa732corps.code03.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListSparepart extends AppCompatActivity {

    View v;
    RecyclerView rview;
    private ListSparepartAdapter adapter;
    private SearchView search;
    private Spinner sortSpinner;
    private RecyclerView.LayoutManager layout;
    private List<Sparepart> SparepartBundleFull;
    private List<SparepartList> sparepartLists;
    private ListSparepartAdapter sparepartAdapter;
    private SparepartList sparepartList1;
    private int simpan =-1;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_pelanggan_listsparepart);

//        init();
        simpan = getIntent().getIntExtra("simpan", -1);

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d("onQueryTextSubmit: ",query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d("onQueryTextChange: ", "true");
                String text = newText.toLowerCase(Locale.getDefault());
//                adapter.getFilter().filter(text);
                return true;
            }
        });

        sortSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        if(simpan > -1){
                            Collections.sort(SparepartBundleFull, new Comparator<Sparepart>() {
                                @Override
                                public int compare(Sparepart o1, Sparepart o2) {
                                    return o1.getName().toLowerCase().compareTo(o2.getName().toLowerCase());
                                }
                            });
//                            adapter.notifyDataSetChanged();
                        }
                        break;

                    case 1:
                        if(simpan > -1){
                            Collections.sort(SparepartBundleFull, new Comparator<Sparepart>() {
                                @Override
                                public int compare(Sparepart o1, Sparepart o2) {
                                    if(o1.getName() == null || o2.getName() == null)
                                        return 0;
                                    return o2.getName().compareTo(o1.getName());
                                }
                            });
//                            adapter.notifyDataSetChanged();
                        }

                    case 2:
                        if(simpan > -1){
                            Collections.sort(SparepartBundleFull, new Comparator<Sparepart>() {
                                @Override
                                public int compare(Sparepart o1, Sparepart o2) {
                                    return Integer.compare(Integer.parseInt(o1.getStock()), Integer.parseInt(o2.getStock()));
                                }
                            });
//                            adapter.notifyDataSetChanged;
                        }
                        break;

                    case 3:
                        if (simpan > -1){
                            Collections.sort(SparepartBundleFull, new Comparator<Sparepart>() {
                                @Override
                                public int compare(Sparepart o1, Sparepart o2) {
                                    if(Integer.parseInt(o1.getStock()) == 0 || Integer.parseInt(o2.getStock()) == 0)
                                        return 0;
                                    return Integer.compare(Integer.parseInt(o1.getStock()), Integer.parseInt(o2.getStock()));
                                }
                            });
//                            adapter.notifyDataSetChanged();
                        }
                        break;

                    case 4:
                        if (simpan > -1){
                            Collections.sort(SparepartBundleFull, new Comparator<Sparepart>() {
                                @Override
                                public int compare(Sparepart o1, Sparepart o2) {
                                    return Double.compare(Double.parseDouble(o1.getSellPrice()), Double.parseDouble(o2.getSellPrice()));
                                }
                            });
//                            adapter.notifyDataSetChanged();
                        }
                        break;

                    case 5:
                        if (simpan > -1){
                            Collections.sort(SparepartBundleFull, new Comparator<Sparepart>() {
                                @Override
                                public int compare(Sparepart o1, Sparepart o2) {
                                    if(Double.parseDouble(o1.getSellPrice()) == 0 || Double.parseDouble(o2.getSellPrice()) == 0)
                                        return 0;
                                    return Double.compare(Double.parseDouble(o1.getSellPrice()), Double.parseDouble(o2.getSellPrice()));
                                }
                            });
//                            adapter.notifyDataSetChanged();
                        }
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Retrofit retrofit = new retrofit2.Retrofit.Builder()
                .baseUrl("https://sibento.yafetrakan.com/api")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiClient apiClient = retrofit.create(ApiClient.class);

        final Call<SparepartList> sparepartGet = apiClient.getSparepart();

        sparepartGet.enqueue(new Callback<SparepartList>() {
            @Override
            public void onResponse(Call<SparepartList> call, Response<SparepartList> response) {
                try {
                    adapter = new ListSparepartAdapter(response.body().getData(), ListSparepart.this);
                    simpan = 1;
                    Collections.sort(SparepartBundleFull, new Comparator<Sparepart>() {
                        @Override
                        public int compare(Sparepart o1, Sparepart o2) {
                            return o1.getName().toLowerCase().compareTo(o2.getName().toLowerCase());
                        }
                    });
//                    adapter.notifyDataSetChanged();
                    rview.setAdapter(adapter);
                } catch (Exception e){
                    Toast.makeText(ListSparepart.this, "Tidak ada sparepart!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SparepartList> call, Throwable t) {
                Toast.makeText(ListSparepart.this, "Fail", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void init(){

        rview = findViewById(R.id.sparepart_list);
        rview.setHasFixedSize(true);
        layout = new LinearLayoutManager(ListSparepart.this);
        rview.setLayoutManager(new GridLayoutManager(this, 3));
        rview.setAdapter(adapter);
        search = findViewById(R.id.searchListSparepart);
        sortSpinner = findViewById(R.id.sortSpinner);

        sparepartLists = new ArrayList<>();
    }

}