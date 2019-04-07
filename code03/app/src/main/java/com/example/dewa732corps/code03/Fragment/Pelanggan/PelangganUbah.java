package com.example.dewa732corps.code03.Fragment.Pelanggan;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.dewa732corps.code03.R;

public class PelangganUbah extends Fragment {

    View dashboard;
    FrameLayout framelay;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        dashboard= inflater.inflate(R.layout.menu2_pelanggan_ubah,container,false);

        return dashboard;
    }
}