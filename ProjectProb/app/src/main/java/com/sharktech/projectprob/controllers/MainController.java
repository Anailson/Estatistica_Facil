package com.sharktech.projectprob.controllers;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.widget.Toast;

import com.sharktech.projectprob.R;
import com.sharktech.projectprob.views.DataAnalyseView;
import com.sharktech.projectprob.views.InferenceView;
import com.sharktech.projectprob.views.VariableTableView;

public class MainController {

    private FragmentActivity activity;

    public MainController(FragmentActivity activity) {
        this.activity = activity;
    }

    public boolean selectMenu(int id) {

        Fragment fragment = null;
        switch (id) {
            case R.id.menu_export_data: showToast("ExportData"); break;
            case R.id.menu_import_data: showToast("ImportData"); break;
            case R.id.nav_variables: fragment = new VariableTableView(); break;
            case R.id.nav_data_analyse: fragment = new DataAnalyseView(); break;
            case R.id.nav_inference: fragment = new InferenceView(); break;
            case R.id.nav_probability: Toast.makeText(activity, "Probabilidade: Em desenvolvimento", Toast.LENGTH_SHORT).show();break;
            case R.id.nav_formula: Toast.makeText(activity, "Fórmula: Em desenvolvimento", Toast.LENGTH_SHORT).show();break;
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
}