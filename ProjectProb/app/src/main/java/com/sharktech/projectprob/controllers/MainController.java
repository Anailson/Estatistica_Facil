package com.sharktech.projectprob.controllers;

import android.Manifest;
import android.app.DialogFragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.widget.Toast;

import com.rustamg.filedialogs.FileDialog;
import com.rustamg.filedialogs.OpenFileDialog;
import com.rustamg.filedialogs.SaveFileDialog;
import com.sharktech.projectprob.R;
import com.sharktech.projectprob.views.DataAnalyseView;
import com.sharktech.projectprob.views.InferenceView;
import com.sharktech.projectprob.views.VariableTableView;

import java.io.File;

public class MainController {

    private static final int REQ_STORAGE_PERMISSION = 0;
    private static final int INDEX_WRITE_STORAGE = 0;
    private static final int INDEX_READ_STORAGE = 1;
    private static final int PERMISSIONS = 2;
    private static final String TAG_IMPORT_FILE = "IMPORT_FILE";
    private static final String TAG_EXPORT_FILE = "EXPORT_FILE";
    private static final String CAN_READ = "CAN_READ";
    private static final String CAN_WRITE = "CAN_WRITE";

    private boolean canRead, canWrite;
    private FragmentActivity activity;

    public MainController(FragmentActivity activity) {
        SharedPreferences preferences = activity.getPreferences(Context.MODE_PRIVATE);
        this.canRead = preferences.getBoolean(CAN_READ, false);
        this.canWrite = preferences.getBoolean(CAN_WRITE, false);
        this.activity = activity;
    }

    public void verifyStoragePermission() {
        int selPermission = ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && selPermission != PackageManager.PERMISSION_GRANTED) {
            String[] requests = new String[PERMISSIONS];
            requests[INDEX_READ_STORAGE] = Manifest.permission.READ_EXTERNAL_STORAGE;
            requests[INDEX_WRITE_STORAGE] = Manifest.permission.WRITE_EXTERNAL_STORAGE;
            ActivityCompat.requestPermissions(activity, requests, REQ_STORAGE_PERMISSION);
        }
    }

    public void permissionResult(int requestCode, int[] grantResults) {
        if (requestCode == REQ_STORAGE_PERMISSION) {
            canRead = grantResults[INDEX_READ_STORAGE] == PackageManager.PERMISSION_GRANTED;
            canWrite = grantResults[INDEX_WRITE_STORAGE] == PackageManager.PERMISSION_GRANTED;
            SharedPreferences.Editor editor = activity.getPreferences(Context.MODE_PRIVATE).edit();
            editor.putBoolean(CAN_READ, canRead);
            editor.putBoolean(CAN_WRITE, canWrite);
            editor.apply();
        }
    }

    public void selectedFile(String tag, String name, File parent) {
        Log.e("selectedFile", parent.getAbsolutePath()
                + " tag " + tag
                + " name " + name);
    }

    public boolean selectMenu(int id) {

        Fragment fragment = null;
        switch (id) {
            case R.id.menu_export_data: exportFile(); break;
            case R.id.menu_import_data: importFile(); break;
            case R.id.nav_variables: fragment = new VariableTableView(); break;
            case R.id.nav_data_analyse: fragment = new DataAnalyseView(); break;
            case R.id.nav_inference: fragment = new InferenceView(); break;
            case R.id.nav_probability: showToast("Probabilidade: Em desenvolvimento"); break;
            case R.id.nav_formula: showToast("Fórmula: Em desenvolvimento"); break;
            default: return false;
        }
        return replaceFragment(fragment) > 0;
    }

    private int replaceFragment(Fragment fragment) {
        if (fragment == null) return -1;

        DrawerLayout drawer = activity.findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        return fragmentManager.beginTransaction().replace(R.id.layout_main, fragment).commit();
    }

    private void showToast(String text) {
        Toast.makeText(activity, text, Toast.LENGTH_SHORT).show();
    }

    private void importFile() {
        if(canRead) {
            Bundle bundle = new Bundle();
            bundle.putString(FileDialog.EXTENSION, "csv");

            FileDialog dialog = new OpenFileDialog();
            dialog.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.AppTheme);
            dialog.show(activity.getSupportFragmentManager(), TAG_IMPORT_FILE);
        }
    }

    private void exportFile() {
        if(canWrite) {
            SaveFileDialog dialog = new SaveFileDialog();
            dialog.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.AppTheme_NoActionBar);
            dialog.show(activity.getSupportFragmentManager(), TAG_EXPORT_FILE);
        }
    }
}