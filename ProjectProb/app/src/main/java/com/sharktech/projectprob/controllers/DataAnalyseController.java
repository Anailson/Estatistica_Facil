package com.sharktech.projectprob.controllers;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.SparseArray;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.sharktech.projectprob.R;
import com.sharktech.projectprob.customtable.Variable;
import com.sharktech.projectprob.persistence.VariablePersistence;
import com.sharktech.projectprob.views.DataAnalyseView;
import com.sharktech.projectprob.views.DataDetailsView;
import com.sharktech.projectprob.views.DataTableView;

public class DataAnalyseController {

    private int mActualMenu;
    private Fragment mFragment;
    private Listeners mListener;
    private SparseArray<Fragment> mFragmentsChild;
    private Variable.IVariable mVariable;

    public DataAnalyseController(Fragment fragment) {
        this.mActualMenu = -1;
        this.mFragment = fragment;
        this.mListener = new Listeners();
        this.mFragmentsChild = new SparseArray<>();
        this.mVariable = null;
    }

    public AdapterView.OnItemSelectedListener getItemSelectedListener() {
        return mListener;
    }

    public BottomNavigationView.OnNavigationItemSelectedListener getNavItemSelectedListener() {
        return mListener;
    }

    public boolean selectMenu(int menuResource){

        Fragment fragment;
        switch (menuResource) {
            case R.id.menu_details: fragment = DataDetailsView.newInstance(mVariable); break;
            case R.id.menu_table: fragment = DataTableView.newInstance(mVariable); break;
            default: return false;
        }
        mActualMenu = menuResource;
        mFragmentsChild.put(mActualMenu, fragment);

        return replaceFragment(fragment);
    }

    private boolean replaceFragment(Fragment fragment){

        FragmentActivity activity = mFragment.getActivity();
        if(activity != null){
            FragmentManager manager = activity.getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.content_data_analyse, fragment).commit();
            return true;
        }
        return false;
    }

    private void changeVariable(int position){
        if(position > 0) {
            mVariable = VariablePersistence.getInstance().getVariables().get(position);
            ((DataAnalyseView.ChangeVariableListener) mFragmentsChild.get(mActualMenu)).onChangeVariable(mVariable);
        }
    }

    private class Listeners implements BottomNavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemSelectedListener {

        //BottomNavigationView.OnNavigationItemSelectedListener
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            return selectMenu(item.getItemId());
        }

        //AdapterView.OnItemSelectedListener
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            switch (parent.getId()) {
                case R.id.spn_variable: changeVariable(position - 1); break;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {}
    }
}
