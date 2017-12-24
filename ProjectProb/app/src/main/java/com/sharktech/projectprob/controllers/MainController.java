package com.sharktech.projectprob.controllers;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.widget.Toast;

import com.sharktech.projectprob.R;
import com.sharktech.projectprob.customtable.Variable;
import com.sharktech.projectprob.views.DataAnalyseFragment;
import com.sharktech.projectprob.views.VariableTableFragment;

import java.util.ArrayList;

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
            case R.id.nav_variables: fragment = new VariableTableFragment(); break;
            case R.id.nav_data_analyse: fragment = new DataAnalyseFragment(); break;
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


    public static ArrayList<Variable> getVariables(Context context) {

        ArrayList<Variable> vars = new ArrayList<>();
        vars.add(getVariable(context, "Integer", new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20}));
        vars.add(getVariable(context, "String",  new String[]{"Um", "Dois", "Três"}));
        vars.add(getVariable(context, "Character", new Character[]{'U', 'D', 'T', 'Q'}));
        vars.add(getVariable(context, "Float", new Float[]{1.3f, 2.2f, 3.3f, 4.4f}));
        return vars;
    }

    private static <E> Variable getVariable(Context context, String title, E[] values){
        Variable<E> variable = new Variable<>(context, title);
        for(E value : values){
            variable.add(value);
        }
        return variable;
    }
}
