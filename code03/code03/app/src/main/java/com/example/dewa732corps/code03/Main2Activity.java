package com.example.dewa732corps.code03;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.dewa732corps.code03.Controller.SessionController;
import com.example.dewa732corps.code03.Fragment.BerandaFragment;
import com.example.dewa732corps.code03.Fragment.CekServiceFragment;
import com.example.dewa732corps.code03.Fragment.ListSparepartFragment;
import com.example.dewa732corps.code03.Fragment.LoginFragment;

public class Main2Activity extends AppCompatActivity {

    private TextView mTextMessage;
    SessionController session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.framelay, new BerandaFragment()).commit();
        session = new SessionController(this);
        if(session.isLoggedIn())
        {
            Intent in = new Intent(Main2Activity.this, MainActivity.class);
            startActivity(in);
//            FragmentTransaction transaction = getFragmentManager().beginTransaction();
//            transaction.replace(R.id.framelay, new BerandaFragment());
//            transaction.commit();
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.nav_beranda:
                    getSupportFragmentManager().beginTransaction().replace(R.id.framelay, new BerandaFragment()).commit();
                    return true;
                case R.id.nav_listsparepart:
                    getSupportFragmentManager().beginTransaction().replace(R.id.framelay, new ListSparepartFragment()).commit();
                    return true;
                case R.id.nav_panelpegawai:
                    getSupportFragmentManager().beginTransaction().replace(R.id.framelay, new LoginFragment()).commit();
//                    mTextMessage.setText("Panel Pegawai");
                    return true;
                case R.id.nav_pengecekanstatusservice:
                    getSupportFragmentManager().beginTransaction().replace(R.id.framelay, new CekServiceFragment()).commit();
                    return true;
            }
            return false;
        }
    };
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            getSupportFragmentManager().beginTransaction().replace(R.id.framelay,new BerandaFragment()).commit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
