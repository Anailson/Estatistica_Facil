package com.sharktech.projectprob.views;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.rustamg.filedialogs.FileDialog;
import com.sharktech.projectprob.R;
import com.sharktech.projectprob.controllers.MainController;
import com.sharktech.projectprob.persistence.VariablePersistence;

import java.io.File;

public class MainView extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, FileDialog.OnFileSelectedListener {

    private MainController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        controller = new MainController(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        controller.selectMenu(R.id.nav_variables);
        controller.verifyStoragePermission();

        VariablePersistence.initDatabase(this);
    }

    public void resume(){
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        controller.selectMenu(item.getItemId());
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        controller.permissionResult(requestCode, grantResults);
    }

    @Override
    public void onFileSelected(FileDialog dialog, File file) {
        controller.selectedFile(dialog.getTag(), file.getName(), file.getParentFile());
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return controller.selectMenu(item.getItemId());
    }
}
