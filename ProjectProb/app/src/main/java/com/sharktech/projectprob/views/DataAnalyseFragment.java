package com.sharktech.projectprob.views;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.sharktech.projectprob.R;
import com.sharktech.projectprob.adapters.SpinAdapter;
import com.sharktech.projectprob.controllers.DataAnalyseController;
import com.sharktech.projectprob.customtable.Variable;
import com.sharktech.projectprob.persistence.VariablePersistence;

import java.util.ArrayList;

public class DataAnalyseFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_data_analyse, container, false);

        DataAnalyseController controller = new DataAnalyseController(this);
        SpinAdapter adapter = new SpinAdapter(getContext());
        Spinner spnVariable = view.findViewById(R.id.spn_variable);
        Spinner spnGraphs = view.findViewById(R.id.spn_graphs);

        spnVariable.setAdapter(adapter.getAdapter(getTitles()));
        spnGraphs.setAdapter(adapter.getAdapter(R.array.graphs));

        spnVariable.setOnItemSelectedListener(controller.getItemSelectedListener());
        spnGraphs.setOnItemSelectedListener(controller.getItemSelectedListener());

        String geometricAvg = Html.fromHtml("<sup><sup>&#0402;<sub>&#105;</sub></sup></sup>&#8730;").toString();



        ((TextView)view.findViewById(R.id.txt_geometric_avg_sym)).setText(geometricAvg);



        return view;
    }

    private String[] getTitles(){
        ArrayList<Variable.IVariable> variables = VariablePersistence.getInstance().getVariables();
        String[] titles = new String[variables.size() + 1];
        titles[0] = getString(R.string.txt_default);

        for(int i = 0; i < variables.size(); i++){
            titles[i + 1] = variables.get(i).getTitle();
        }
        return titles;
    }
}
