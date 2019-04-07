package com.example.dewa732corps.code03.Fragment.Supplier;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.dewa732corps.code03.R;

public class SupplierTampil extends Fragment {

    View dashboard;
    FrameLayout framelay;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        dashboard= inflater.inflate(R.layout.menu2_supplier_tampil,container,false);

        Button btnTambahSupplier = dashboard.findViewById(R.id.btnTambahSupplier);

        btnTambahSupplier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.framelay, new SupplierTambah());
                transaction.commit();
            }
        });

        return dashboard;
    }
}