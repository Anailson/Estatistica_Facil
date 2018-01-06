package com.sharktech.projectprob.controllers;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.sharktech.projectprob.R;
import com.sharktech.projectprob.customcharts.ChartFactory;
import com.sharktech.projectprob.customtable.Variable;
import com.sharktech.projectprob.persistence.VariablePersistence;

import java.util.ArrayList;
import java.util.Locale;

public class DataAnalyseController {

    private Fragment mFragment;
    private Listeners mListener;
    private ArrayList<Variable.IVariable> mVariables;

    public DataAnalyseController(Fragment fragment) {
        this.mFragment = fragment;
        this.mListener = new Listeners();
        this.mVariables =  VariablePersistence.getInstance().getVariables();
    }

    public AdapterView.OnItemSelectedListener getItemSelectedListener() {
        return mListener;
    }

    private void calculate(int position){

        if(position >= 0) {

            Variable.Analyses analyses  = new Variable<>(mFragment.getContext(), mVariables.get(position)).calculate();

            fillText(R.id.txt_arithmetic_avg, analyses.avgArithmetic());
            fillText(R.id.txt_geometric_avg, analyses.avgGeometric());
            fillText(R.id.txt_weighted_avg, analyses.avgWeighted());
            fillText(R.id.txt_quadratic_avg, analyses.avgQuadratic());
            fillText(R.id.txt_mode, analyses.mode() == null ? " = " : analyses.mode().toString());

            Activity activity = mFragment.getActivity();
            if(activity != null){
                Spinner spnVariable = activity.findViewById(R.id.spn_graphs);
                setGraph(spnVariable.getSelectedItemPosition());
            }
        } else {

            fillText(R.id.txt_arithmetic_avg, "-");
            fillText(R.id.txt_geometric_avg, "-");
            fillText(R.id.txt_weighted_avg, "-");
            fillText(R.id.txt_quadratic_avg, "-");
            fillText(R.id.txt_mode, "-");

            Activity activity = mFragment.getActivity();
            if(activity != null){
                RelativeLayout layout = activity.findViewById(R.id.lyt_charts);
                layout.removeAllViews();
            }
        }
    }

    private void setGraph(int chartIndex){
        Activity activity = mFragment.getActivity();
        if(chartIndex > 0 && activity != null){

            Spinner spnVariable = activity.findViewById(R.id.spn_variable);
            int posVariable = spnVariable.getSelectedItemPosition() - 1;

            if(posVariable >= 0){

                RelativeLayout layout = activity.findViewById(R.id.lyt_charts);
                layout.removeAllViews();

                switch (chartIndex){
                    case ChartFactory.PIE:
                        layout.addView(ChartFactory.newPieChart(activity, mVariables.get(posVariable)));
                        break;
                    case ChartFactory.BAR:
                        layout.addView(ChartFactory.newBarChart(activity, mVariables.get(posVariable)));
                        break;
                    case ChartFactory.DISPERSION:
                        break;
                    case ChartFactory.BOX:
                        break;
                    case ChartFactory.HISTOGRAM:
                        break;
                }
            } else {
                Toast.makeText(activity, "Nenhuma variável selecionada", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void fillText(int id, Double d){
        fillText(id, String.format(Locale.getDefault(), "%.6f", d));
    }

    private void fillText(int id, String text){
        Activity activity = mFragment.getActivity();
        if(activity != null) {
            TextView view = activity.findViewById(id);
            view.setText(text);
        }
    }

    private class Listeners implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            switch (parent.getId()) {
                case R.id.spn_graphs: setGraph(position); break;
                case R.id.spn_variable: calculate(position - 1); break;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {}
    }
}
