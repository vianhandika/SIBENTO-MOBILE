package com.example.dewa732corps.code03;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

import com.example.dewa732corps.code03.Controller.SessionController;
import com.example.dewa732corps.code03.Fragment.Customer.CustomerTampil;
import com.example.dewa732corps.code03.Fragment.Sparepart_Procurement.SparepartProcurementTampil;
import com.example.dewa732corps.code03.Fragment.Sales.SalesTampil;
import com.example.dewa732corps.code03.Fragment.Sparepart.SparepartTampil;
import com.example.dewa732corps.code03.Fragment.Supplier.SupplierTampil;
import com.example.dewa732corps.code03.Fragment.Transaksi_Penjualan.TransaksiPenjualanTampil;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        toolbar.setTitle("DASHBOARD");

        getPutExtra();

    }

    public void getPutExtra() {
        switch (getIntent().getIntExtra("menuBefore",0)){
            case 0: getSupportFragmentManager().beginTransaction().replace(R.id.framelay, new SparepartTampil()).commit();Log.d("getPutExtra: ","0");
                break;
            case 1: getSupportFragmentManager().beginTransaction().replace(R.id.framelay, new SparepartTampil()).commit();Log.d("getPutExtra: ","1");
                break;
            case 2: getSupportFragmentManager().beginTransaction().replace(R.id.framelay, new SupplierTampil()).commit();Log.d("getPutExtra: ","2");
                break;
            case 3: getSupportFragmentManager().beginTransaction().replace(R.id.framelay, new SalesTampil()).commit();Log.d("getPutExtra", "3");
                break;
            case 4: getSupportFragmentManager().beginTransaction().replace(R.id.framelay, new CustomerTampil()).commit();Log.d("getPutExtra", "4");
                break;
            case 5: getSupportFragmentManager().beginTransaction().replace(R.id.framelay, new SparepartProcurementTampil()).commit();Log.d("getPutExtra", "5");
                break;
            case 6: getSupportFragmentManager().beginTransaction().replace(R.id.framelay, new TransaksiPenjualanTampil()).commit();Log.d("getPutExtra", "6");
                break;
//            case 3: getSupportFragmentManager().beginTransaction().replace(R.id.framelay, new SalesTampil()).commit();Log.d("getPutExtra", "3");
//                break;
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_sparepart) {
            getSupportFragmentManager().beginTransaction().replace(R.id.framelay, new SparepartTampil()).commit();
//            toolbar.setTitle("Manajemen Sparepart");
        } else if (id == R.id.nav_supplier) {
            getSupportFragmentManager().beginTransaction().replace(R.id.framelay, new SupplierTampil()).commit();
//            toolbar.setTitle("Manajemen Supplier");
        } else if (id == R.id.nav_sales) {
            getSupportFragmentManager().beginTransaction().replace(R.id.framelay, new SalesTampil()).commit();
//            toolbar.setTitle("Manajemen Sales");
        } else if (id == R.id.nav_pelanggan) {
            getSupportFragmentManager().beginTransaction().replace(R.id.framelay, new CustomerTampil()).commit();
//            toolbar.setTitle("Manajemen Pelanggan");
        }
//        else if (id == R.id.nav_kendaraan) {
//            getSupportFragmentManager().beginTransaction().replace(R.id.framelay, new MotorCustomerTampil()).commit();
//            toolbar.setTitle("Manajemen Kendaraan");
//        }
        else if (id == R.id.nav_pengadaansparepart) {
            getSupportFragmentManager().beginTransaction().replace(R.id.framelay, new SparepartProcurementTampil()).commit();
        } else if (id == R.id.nav_transaksipenjualan) {
            getSupportFragmentManager().beginTransaction().replace(R.id.framelay, new TransaksiPenjualanTampil()).commit();
        } else if (id == R.id.nav_ubahpassword) {
            getSupportFragmentManager().beginTransaction().replace(R.id.framelay, new UbahPassword()).commit();
        } else if (id == R.id.nav_logout) {
            SessionController session = new SessionController(this);
            session.logoutUser();
//            getSupportFragmentManager().beginTransaction().replace(R.id.framelay, new ListSparepartFragment()).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            getSupportFragmentManager().beginTransaction().replace(R.id.framelay,new SparepartTampil()).commit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}