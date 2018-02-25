package com.sharktech.projectprob.views;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import com.sharktech.projectprob.R;
import com.sharktech.projectprob.adapters.SpinAdapter;
import com.sharktech.projectprob.controllers.DataAnalyseController;
import com.sharktech.projectprob.customtable.TableColumn;
import com.sharktech.projectprob.persistence.VariablePersistence;

import java.util.ArrayList;

public class DataAnalyseView extends Fragment {

    protected static String ANALYSES = "ANALYSES";

    private DataAnalyseController mController;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        FragmentActivity activity = getActivity();
        if(activity != null) activity.setTitle(getString(R.string.txt_data_analyse));

        View view = inflater.inflate(R.layout.fragment_data_analyse, container, false);

        mController = new DataAnalyseController(this);
        BottomNavigationView btnMenu = view.findViewById(R.id.nav_data_analyse);
        Spinner spnVariable = view.findViewById(R.id.spn_variable);

        SpinAdapter adapter = new SpinAdapter(getContext());
        ArrayList<String> titles = VariablePersistence.getInstance().getTitles();
        titles.add(0, getString(R.string.txt_default));

        spnVariable.setAdapter(adapter.getAdapter(titles));
        btnMenu.setOnNavigationItemSelectedListener(mController.getNavItemSelectedListener());
        spnVariable.setOnItemSelectedListener(mController.getItemSelectedListener());

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mController.selectMenu(R.id.menu_table);
    }

    public interface ChangeVariableListener{
        void onChangeVariable(TableColumn.IVariable variable);

        void onHasNoVariable();
    }
}
